// EcgView.java
// Alive Technologies
package com.btranz.healthplus.nonin_pulse_oxi;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.btranz.healthplus.buffers.CirFloatBuffer;

/**
 * Created by Andries on 17/06/2015.
 */
public class PpgView extends View {

	private static float IN_TO_CM = 2.54f;
	private Path mPath;
	private Context mContext;
	private Paint mPaintPpg = new Paint();
	private boolean mInitalized;

	private final int SWEEPBAR_WIDTH = 1;
	private int mSamplesPerWidth;
	private CirFloatBuffer mPpgBuffer;
	private DisplayMetrics mDisplayMetrics;

	private float mYOffset;
	private float mXScale;
	private float mYScale;
	private int mWidth;
	private float mXppcm;
	private float mYppcm;

	public  PpgView(Context context, AttributeSet attrs)
	{
		super(context, attrs);

		//Setup paint brush for waveform
		mPaintPpg.setColor(Color.rgb(33,150,243));
		mPaintPpg.setStrokeWidth(5);
		mPaintPpg.setAntiAlias(true);
		mPaintPpg.setStrokeCap(Paint.Cap.ROUND);
		mPaintPpg.setStrokeJoin(Paint.Join.ROUND);
		mPaintPpg.setStyle(Paint.Style.STROKE);

		// Turn off hardware acceleration, else get aliasing effect when redrawing ECG trace with lots of noise
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		synchronized (this) {
			mDisplayMetrics = getResources().getDisplayMetrics();

			mXppcm = (float)(mDisplayMetrics.xdpi / IN_TO_CM); //convert pixels per inch to pixels per cm
			mYppcm = (float)(mDisplayMetrics.ydpi / IN_TO_CM);
			//mYppcm = mXppcm;

			// XScale set to 25mm/s. Sample rate is 75 Hz, so 75 samples per 25mm
			mXScale = 2.5f * mXppcm / 75.0f;

			// YScale set to 10mm/mV. 16bit, 20uV LSB or 50=1mV
			mYScale = mYppcm/50.0f;

			mYOffset = h * 0.5f;
			mWidth = w;
			mSamplesPerWidth =(int)(w/mXScale);

			if(mInitalized) {
				int bufferSamples = mSamplesPerWidth;
				mPpgBuffer.resize(bufferSamples);

			}
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		synchronized (this) {
			if(!mInitalized) return; // Nothing to draw

			int sweepbarSamples = (int)(SWEEPBAR_WIDTH*75/15.0);

			mPath.rewind();

			int startIndex=0;
			int endIndex = mPpgBuffer.getCount()-1;

			if(endIndex>=(mSamplesPerWidth-sweepbarSamples)) {
				startIndex = endIndex - (mSamplesPerWidth-sweepbarSamples);
				endIndex = startIndex + (mSamplesPerWidth-sweepbarSamples);
			}
			int nCount=0;
			float lastXPos=0;
			for(int x = startIndex; x<endIndex; x++) {
				float xPos = x * mXScale;
				xPos = xPos % mWidth;
				float yPos = mYOffset - mPpgBuffer.get(x) * mYScale;

				if( x==startIndex || xPos<lastXPos) {
					mPath.moveTo(xPos, yPos);
				} else {
					mPath.lineTo(xPos, yPos);
				}
				lastXPos = xPos;

				// TODO: Test optimization - drawing short paths
				nCount++;
				if(nCount==75) {
					canvas.drawPath(mPath, mPaintPpg);
					mPath.rewind();
					mPath.moveTo(xPos, yPos);
					nCount=0;
				}

			}
			if(nCount>1) {
				canvas.drawPath(mPath, mPaintPpg);
			}
		}
	}

	static public int getPpgRangeHeight(Context context) {
		DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();

		float xppcm = (float)(mDisplayMetrics.xdpi / IN_TO_CM);
		float yppcm = (float)(mDisplayMetrics.ydpi / IN_TO_CM);
		//yppcm = xppcm;

		// Height of 5 mV range
		return((int)(5*yppcm));
	}

	public void onPulseOxiPacket(final int timeSampleCount,final NoninPulseOxiPacket packet) {
		synchronized (this) {
			if (!mInitalized) {
				int bufferSamples = mSamplesPerWidth; // Extra samples for filter delay etc

				mPath = new Path();
				mPath.incReserve(bufferSamples);
				mPpgBuffer = new CirFloatBuffer(bufferSamples);
				mInitalized = true;
			}

			int[] ppgBuffer = packet.getPlethData();
			for (int ppg : ppgBuffer)
			{
				mPpgBuffer.add(ppg - 128.0f);
			}

			postInvalidate();
		}
	}
}