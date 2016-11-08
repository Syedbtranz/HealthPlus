// AccView.java
// Alive Technologies
package drawable.alive_ecg;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;

import com.btranz.healthplus.buffers.CirByteBuffer;

public class AccView extends View {
	@SuppressWarnings("unused")
	private static final String TAG = "AccView";
	
	private static float IN_TO_CM = 2.54f;
	
	private final int SWEEPBAR_WIDTH = 5; // 5mm
	private final int ACC_SAMPLING_RATE = 75;
	private Paint mPaintX= new Paint();
	private Paint mPaintY= new Paint();
	private Paint mPaintZ= new Paint();

	private boolean mInitalized = false;
	private CirByteBuffer mAccBuffer;
	private Path mPath;
	private int mSamplesPerWidth;

	private float mYOffset;
	private float mXScale;
	private float mYScale;
	private int mWidth;

	private DisplayMetrics mDisplayMetrics;
	private float mXppcm;

	public AccView(Context context, AttributeSet attrs) {
		super(context, attrs);
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
		mPaintX.setColor(Color.RED);
		mPaintX.setStrokeWidth(px);
		mPaintX.setAlpha(0x80);
		mPaintX.setAntiAlias(true);
		mPaintX.setStrokeCap(Paint.Cap.ROUND);
		mPaintX.setStrokeJoin(Paint.Join.ROUND);
		mPaintX.setStyle(Paint.Style.STROKE);

		mPaintY.setColor(Color.GREEN);
		mPaintY.setStrokeWidth(px);
		mPaintY.setAlpha(0x80);
		mPaintY.setAntiAlias(true);
		mPaintY.setStrokeCap(Paint.Cap.ROUND);
		mPaintY.setStrokeJoin(Paint.Join.ROUND);
		mPaintY.setStyle(Paint.Style.STROKE);
		
		mPaintZ.setColor(Color.BLUE);

		mPaintZ.setStrokeWidth(px);
		mPaintZ.setAlpha(0x80);
		mPaintZ.setAntiAlias(true);
		mPaintZ.setStrokeCap(Paint.Cap.ROUND);
		mPaintZ.setStrokeJoin(Paint.Join.ROUND);
		mPaintZ.setStyle(Paint.Style.STROKE);
		
		// Turn off hardware acceleration, else get aliasing effect when redrawing ECG trace with lots of noise
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		
		mDisplayMetrics = new DisplayMetrics();
		setBackgroundColor(Color.WHITE);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		synchronized (this) {
			mDisplayMetrics = getResources().getDisplayMetrics();
			
			mXppcm = (float)(mDisplayMetrics.xdpi / IN_TO_CM);
			
			// XScale set to 25mm/s. Sample rate is 75 Hz, so 75 samples per 25mm
			mXScale = 2.5f * mXppcm / ACC_SAMPLING_RATE;
    		
			// YScale set to 10mm/mV. 8bit, 20uV LSB or 50=1mV
    		mYScale = h/255f;
		
			mYOffset = h * 0.5f;
			mWidth = w;
			mSamplesPerWidth =(int)(w/mXScale); 
			
			if(mInitalized) {
				int bufferSamples = mSamplesPerWidth+12; // Extra samples for filter delay etc
				mAccBuffer.resize(bufferSamples*3);
			}
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		synchronized (this) {
			if(!mInitalized) return; // Nothing to draw	
				
			int sweepbarSamples = (int)(SWEEPBAR_WIDTH*ACC_SAMPLING_RATE/25.0);
			int startIndex=0;
			int endIndex = mAccBuffer.getCount()/3 - 1;
	
			if(endIndex>=(mSamplesPerWidth-sweepbarSamples)) {
				startIndex = endIndex - (mSamplesPerWidth-sweepbarSamples);
				endIndex = startIndex + (mSamplesPerWidth-sweepbarSamples);
			}
			
			int nCount=0;
			float lastXPos=0;
			mPath.rewind();
			for(int x= startIndex;x<endIndex;x++) {
				float xPos = x * mXScale;
				xPos = xPos % mWidth;
				float yPos = mYOffset - ((mAccBuffer.get(x*3)&0xFF)-128)*mYScale;
				if(x==startIndex || xPos<lastXPos) {
					mPath.moveTo(xPos, yPos);
				}else {
					mPath.lineTo(xPos, yPos);
				}
				lastXPos = xPos;
				nCount++;
			}
			if(nCount>1) {
				canvas.drawPath(mPath, mPaintX);
			}
			
			nCount=0;
			lastXPos=0;
			mPath.rewind();
			for(int x= startIndex;x<endIndex;x++) {
				float xPos = x * mXScale;
				xPos = xPos % mWidth;
				float yPos = mYOffset - ((mAccBuffer.get(x*3+1)&0xFF)-128)*mYScale;
				if(x==startIndex || xPos<lastXPos) {
					mPath.moveTo(xPos, yPos);
				}else {
					mPath.lineTo(xPos, yPos);
				}
				lastXPos = xPos;
				nCount++;
			}
			if(nCount>1) {
				canvas.drawPath(mPath, mPaintY);
			}
			
			nCount=0;
			lastXPos=0;
			mPath.rewind();
			for(int x= startIndex;x<endIndex;x++) {
				float xPos = x * mXScale;
				xPos = xPos % mWidth;
				float yPos = mYOffset - ((mAccBuffer.get(x*3+2)&0xFF)-128)*mYScale;
				if(x==startIndex || xPos<lastXPos) {
					mPath.moveTo(xPos, yPos);
				}else {
					mPath.lineTo(xPos, yPos);
				}
				lastXPos = xPos;
				nCount++;
			}
			if(nCount>1) {
				canvas.drawPath(mPath, mPaintZ);
			}
		}
	}

	public void reset() {
		synchronized (this) {
			if(mInitalized) {
				mAccBuffer.reset();
			}
			postInvalidate();
		}
	}

	public void onAlivePacket(final int timeSampleCount, final AliveHeartMonitorPacket packet) {
		synchronized (this) {
			int len = packet.getAccLength();
			if (len > 0) {
				if(!mInitalized) {
					int bufferSamples = mSamplesPerWidth+12; // Extra samples for filter delay etc
					
					mPath = new Path();
					mPath.incReserve(bufferSamples);
					mAccBuffer = new CirByteBuffer(bufferSamples*3);
					mInitalized = true;
				}
				int startIndex = packet.getAccDataIndex();
				byte[] accBuffer = packet.getPacketData();
				for (int i = 0; i < len; i+=3) {
					mAccBuffer.add(accBuffer[startIndex+i]);
					mAccBuffer.add(accBuffer[startIndex+i+1]);
					mAccBuffer.add(accBuffer[startIndex+i+2]);
				}
				postInvalidate();
			}
		}
	}
}