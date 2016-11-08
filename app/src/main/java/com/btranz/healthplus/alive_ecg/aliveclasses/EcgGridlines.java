// EcgGridlines.java
// Alive Technologies
package com.btranz.healthplus.alive_ecg.aliveclasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.btranz.healthplus.alive_ecg.EcgView;

public class EcgGridlines extends View {
	private static float IN_TO_CM = 2.54f; 
	private Path mMinorGridPath;
	private Path mMajorGridPath;
	private int mWidth;
	private int mHeight;
	private Paint mPaint;
	
	private float mXppcm;
	private float mYppcm;
	private Context mContext;
	
	public EcgGridlines(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mPaint = new Paint();
		setBackgroundColor(Color.rgb(64,64,64));
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    	int width = getMeasuredWidth();
    	int height = getMeasuredHeight();

	    if(height > EcgView.getEcgRangeHeight(mContext)) {
	    	height = EcgView.getEcgRangeHeight(mContext);
	    }
		height *= 2;
		ViewGroup parentView = (ViewGroup)getParent();
	    int parentHeight = parentView.getMeasuredHeight();
    	
	    if(height > 3*parentHeight/5) {
	    	height = 3*parentHeight/5;
	    }
	    setMeasuredDimension(width, height);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

		mXppcm = (float)(displayMetrics.xdpi / IN_TO_CM);
		mYppcm = (float)(displayMetrics.ydpi / IN_TO_CM);		
		mYppcm = mXppcm;
		
		mWidth = w;
		mHeight = h;

		setupGridPaths();
		
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	private void setupGridPaths() {
		if(mMinorGridPath==null) {
			mMinorGridPath = new Path();
			mMajorGridPath = new Path();
		}
		mMinorGridPath.rewind();
		mMajorGridPath.rewind();
		// minor grids
		float pos=0;
		int lines = (int)(mWidth/(mXppcm/10))+1;
		for(int i=0;i<lines;i++) {
			if(i%5!=0) {
				mMinorGridPath.moveTo(pos, 0);
				mMinorGridPath.lineTo(pos, mHeight);
			}
			pos+=mXppcm/10;
		}
		pos = 0;
		lines = (int)(mHeight/(mYppcm/10))+1;
		for(int i=0;i<lines/2;i++) {
			if(i%5!=0) {
				mMinorGridPath.moveTo(0, mHeight/2+pos);
				mMinorGridPath.lineTo(mWidth, mHeight/2+pos);
				mMinorGridPath.moveTo(0, mHeight/2-pos);
				mMinorGridPath.lineTo(mWidth, mHeight/2-pos);
			}
			pos+=mYppcm/10;
		}
		
		// major grids
		pos=0;
		lines = (int)(2*mWidth/mXppcm)+1;
		for(int i=0;i<lines;i++) {
			mMajorGridPath.moveTo(pos, 0);
			mMajorGridPath.lineTo(pos, mHeight);
			pos+=mXppcm/2;
		}
		
		mMajorGridPath.moveTo(0, mHeight/2);
		mMajorGridPath.lineTo(mWidth,mHeight/2);

		lines = (int)(2*mHeight/mYppcm)+2;
		pos=mYppcm/2;
		for(int i=1;i<lines/2;i++) {
			mMajorGridPath.moveTo(0, mHeight/2 + pos);
			mMajorGridPath.lineTo(mWidth,mHeight/2 + pos);
			mMajorGridPath.moveTo(0, mHeight/2 - pos);
			mMajorGridPath.lineTo(mWidth,mHeight/2 - pos);
			pos+=mYppcm/2;
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setAntiAlias(true);

		// Draw minor grids
		mPaint.setStrokeWidth(1);
		mPaint.setColor(Color.rgb(166, 166, 168));
		canvas.drawPath(mMinorGridPath, mPaint);
		
		// Draw major grids
		mPaint.setColor(Color.rgb(246, 246, 232));
		mPaint.setStrokeWidth(2.5f);
		canvas.drawPath(mMajorGridPath, mPaint);
	}
}