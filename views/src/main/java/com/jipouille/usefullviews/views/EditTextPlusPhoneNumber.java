package com.jipouille.usefullviews.views;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


public class EditTextPlusPhoneNumber extends EditTextPlus {
	private View errorViewContainer = null;
	private TextView errorTxtView = null;
	private int resId = -1;
	
	public EditTextPlusPhoneNumber(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setError(CharSequence error, Drawable icon) {
	    setCompoundDrawables(null, null, null, null);
	    if (resId != -1)
	    	setBackgroundResource(resId);
	    if (errorTxtView != null)
	    	errorTxtView.setText(error);
	    if (errorViewContainer != null)
	    	errorViewContainer.setVisibility(View.VISIBLE);
	}

	public View getErrorViewContainer() {
		return errorViewContainer;
	}

	public void setErrorViewContainer(View v) {
		this.errorViewContainer = v;
	}

	public TextView getErrorTxtView() {
		return errorTxtView;
	}

	public void setErrorTxtView(TextView errorTxtView) {
		this.errorTxtView = errorTxtView;
	}
	
	/**
	 * Set a drawable ressource id used as background when an error occurs
	 * @param resId
	 */
	public void setErrorBackgroundResource(int resId) {
		this.resId = resId;
		
	}

}
