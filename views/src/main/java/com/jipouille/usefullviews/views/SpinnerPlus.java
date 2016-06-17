package com.jipouille.usefullviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class SpinnerPlus extends Spinner {
    private static final String TAG = "SpinnerView";
    private Typeface tf;
    public SpinnerPlus(Context context) {
        super(context);
    }

    public SpinnerPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public SpinnerPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
    	Log.d("TextViewPlus","setCustomFont");
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        String customFont = a.getString(R.styleable.TextViewPlus_customFont);
        setCustomFont(ctx, customFont);
        a.recycle();
    }

    public boolean setCustomFont(Context ctx, String asset) {
        try {
        tf = Typeface.createFromAsset(ctx.getAssets(), "fonts/"+asset);  
        } catch (Exception e) {
            Log.e(TAG, "Could not get typeface "+asset+": "+e.getMessage());
            return false;
        }
        return true;
    }
}