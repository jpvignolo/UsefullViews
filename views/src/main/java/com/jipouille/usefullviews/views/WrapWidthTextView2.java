package com.jipouille.usefullviews.views;

import android.R.string;
import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;


public class WrapWidthTextView2 extends TextViewPlus {
	private static String TAG = "WrapWidthTextView2";
	public WrapWidthTextView2(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public WrapWidthTextView2(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public WrapWidthTextView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
	    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	    Layout layout = getLayout();
	    if (layout != null) {
	        int width = (int)Math.ceil(getMaxLineWidth(layout))
	                + getCompoundPaddingLeft() + getCompoundPaddingRight();
	        int height = getMeasuredHeight();            
	        //setMeasuredDimension(width, height);
	        if (width < getMeasuredWidth())
	        	super.onMeasure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.getMode(widthMeasureSpec)), heightMeasureSpec);
	    }
	}

	private float getMaxLineWidth(Layout layout) {
	    float max_width = 0.0f;
	    int lines = layout.getLineCount();
	    for (int i = 0; i < lines; i++) {
	        if (layout.getLineWidth(i) > max_width) {
	            max_width = layout.getLineWidth(i);
	        }
	    }
	    return max_width;
	}

}
