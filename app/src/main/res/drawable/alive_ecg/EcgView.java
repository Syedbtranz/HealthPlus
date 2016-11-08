// EcgView.java
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
import android.view.ViewGroup;

import com.btranz.healthplus.alive_ecg.aliveclasses.MainsFilter;
import com.btranz.healthplus.buffers.CirFloatBuffer;

public class EcgView extends View {
	@SuppressWarnings("unused")
	private static final String TAG = "EcgView";
	private static float IN_TO_CM = 2.54f;
	
	private final int SWEEPBAR_WIDTH = 5; // 5mm
	private Paint mPaintEcg = new Paint();
	private MainsFilter mEcgFilter = null; // Using simple averaging LP filter, that also removes mains noise
	private boolean mInitalized = false;
	private CirFloatBuffer mEcgBuffer;
	private Path mPath;
	private int mSamplesPerWidth;

	private float mYOffset;
	private float mXScale;
	private float mYScale;
	private int mWidth;

	private DisplayMetrics mDisplayMetrics;
	private float mXppcm;
	private float mYppcm;
	private Context mContext;

	static public int getEcgRangeHeight(Context context) {
		DisplayMetrics mDisplayMetrics = context.getResources().getDisplayMetrics();
		
		float xppcm = (float)(mDisplayMetrics.xdpi / IN_TO_CM);
		float yppcm = (float)(mDisplayMetrics.ydpi / IN_TO_CM);	
		yppcm = xppcm;
		
		// Height of 5 mV range
		return((int)(5*yppcm));	
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    	int width = getMeasuredWidth();
    	int height = getMeasuredHeight();

	    if(height > EcgView.getEcgRangeHeight(mContext)) {
	    	height = EcgView.getEcgRangeHeight(mContext);
	    }
	    ViewGroup parentView = (ViewGroup)getParent();
	    int parentHeight = parentView.getMeasuredHeight();
    	
	    if(height > 3*parentHeight/5) {
	    	height = 3*parentHeight/5;
	    }
	    setMeasuredDimension(width, height);
	}

	public EcgView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
		mPaintEcg.setColor(Color.BLACK);
		mPaintEcg.setStrokeWidth(px);
		mPaintEcg.setAntiAlias(true);
		mPaintEcg.setStrokeCap(Paint.Cap.ROUND);
		mPaintEcg.setStrokeJoin(Paint.Join.ROUND);
		mPaintEcg.setStyle(Paint.Style.STROKE);
		
		// Turn off hardware acceleration, else get aliasing effect when redrawing ECG trace with lots of noise
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		
		mDisplayMetrics = new DisplayMetrics();
		
	}


	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		synchronized (this) {
			mDisplayMetrics = getResources().getDisplayMetrics();
			
			mXppcm = (float)(mDisplayMetrics.xdpi / IN_TO_CM);
			mYppcm = (float)(mDisplayMetrics.ydpi / IN_TO_CM);	
			mYppcm = mXppcm;
			
			// XScale set to 25mm/s. Sample rate is 300 Hz, so 300 samples per 25mm
			mXScale = 2.5f * mXppcm / 300.0f;
    		
			// YScale set to 10mm/mV. 8bit, 20uV LSB or 50=1mV
    		mYScale = mYppcm/50.0f;
		
			mYOffset = h * 0.5f;
			mWidth = w;
			mSamplesPerWidth =(int)(w/mXScale); 
			
			if(mInitalized) {
				int bufferSamples = mSamplesPerWidth+12; // Extra samples for filter delay etc
				mEcgBuffer.resize(bufferSamples);
		
			}
		}
		super.onSizeChanged(w, h, oldw, oldh);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		synchronized (this) {
			if(!mInitalized) return; // Nothing to draw	
				
			int sweepbarSamples = (int)(SWEEPBAR_WIDTH*300/25.0);
	
			mPath.rewind();
			
			int startIndex=0;
			int endIndex = mEcgBuffer.getCount()-1;
	
			if(endIndex>=(mSamplesPerWidth-sweepbarSamples)) {
				startIndex = endIndex - (mSamplesPerWidth-sweepbarSamples);
				endIndex = startIndex + (mSamplesPerWidth-sweepbarSamples);
			}
			int nCount=0;
			float lastXPos=0;
			for(int x= startIndex;x<endIndex;x++) {
				float xPos = x * mXScale;
				xPos = xPos % mWidth;
				float yPos = mYOffset -mEcgBuffer.get(x)*mYScale;
	
				if(x==startIndex || xPos<lastXPos) {
					mPath.moveTo(xPos, yPos);
				}else {
					mPath.lineTo(xPos, yPos);
				}
				lastXPos = xPos;
				
				// TODO: Test optimization - drawing short paths
				nCount++;
				if(nCount==200) {
					canvas.drawPath(mPath, mPaintEcg);
					mPath.rewind();
					mPath.moveTo(xPos, yPos);
					nCount=0;
				}
				
			}
			if(nCount>1) {
				canvas.drawPath(mPath, mPaintEcg);
			}
		}
	}

	public void resetECG() {
		synchronized (this) {
			
			if(mInitalized) {
				mEcgFilter.reset();
				mEcgBuffer.reset();
			}
			postInvalidate();
		}
	}

	
	public void onAlivePacket(final int timeSampleCount,final AliveHeartMonitorPacket packet) {
		synchronized (this) {
			int len = packet.getECGLength();
			if (len > 0) {
				if(!mInitalized) {
					int bufferSamples = mSamplesPerWidth+12; // Extra samples for filter delay etc
					
					mPath = new Path();
					mPath.incReserve(bufferSamples);
					mEcgBuffer = new CirFloatBuffer(bufferSamples);
					mEcgFilter = new MainsFilter();
					mInitalized = true;
				}
				int startIndex = packet.getECGDataIndex();
				byte[] ecgBuffer = packet.getPacketData();
				for (int i = 0; i < len; i++) {
                	byte s = ecgBuffer[startIndex+i];
                	int smp = (byte)s & 0xFF;
                	float val = smp-128;
					val = (float) mEcgFilter.filter(-val);
					mEcgBuffer.add(val);

				}
				postInvalidate();
			}
		}
	}
}