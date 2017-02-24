package com.jipouille.usefullviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;

public class EditTextPlus extends EditText {
    private static final String TAG = "TextView";

    public EditTextPlus(Context context) {
        super(context);
    }

    public EditTextPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        setCustomFont(context, attrs);
    }

    public EditTextPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setCustomFont(context, attrs);
        ucFirst(context,attrs);
    }

    public void ucFirst(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        boolean ucfirst = a.getBoolean(R.styleable.GroupRadioPlus_ucFirst,false);
        if (ucfirst) {
            String txt = this.getText().toString().substring(0, 1).toUpperCase() + this.getText().toString().substring(1);
            this.setText(txt);
        }
    }

    private void setCustomFont(Context ctx, AttributeSet attrs) {
    	Log.d("TextViewPlus","setCustomFont");
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