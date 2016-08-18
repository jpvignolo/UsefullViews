package com.jipouille.usefullviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CheckedTextView;

public class CheckedTextViewPlus extends CheckedTextView {
    private static final String TAG = "TextView";

    public CheckedTextViewPlus(Context context) {
        super(context);
    }

    public CheckedTextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public CheckedTextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
    	Log.d("CheckedTextViewPlus","setCustomFont");
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        String customFont = a.getString(R.styleable.TextViewPlus_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        Typeface tf = null;
        try {
        tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/"+asset);  
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface "+asset+": "+e.getMessage());
            return false;
        }

        setTypeface(tf);  
        return true;
    }

}