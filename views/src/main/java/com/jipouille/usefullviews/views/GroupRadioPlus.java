package com.jipouille.usefullviews.views;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.jipouille.usefullviews.views.listeners.GroupRadioPlusClickListener;

import java.util.List;

/**
 * Created by jp on 16/06/16.
 */
public class GroupRadioPlus extends TableLayout {
    private static final String TAG = "GroupRadioPlus";
    private List data;
    private String customFont;

    private int maxRowElements = 3;
    private int underlineHeight = 3;
    private int paddingLeft = 5;
    private int paddingTop = 40;
    private int paddingRight = 5;
    private int paddingBottom = 40;
    private int marginLeft = 1;
    private int marginTop = 1;
    private int marginRight = 1;
    private int marginBottom = 1;
    private int nbRows = 0;
    private int nbCellLastRow;
    private int underlineColor;
    private int underlineBackgroundColor;
    private int oldPaddingOffset = 0;
    private int oldRow = -1;
    private int btnBackground;
    private float textSize;
    private boolean ucFirst = false;
    private RadioButton clickedButton;
    private SparseArray<TableRow> rowsView;

    private GroupRadioPlusClickListener onClickListener;

    public GroupRadioPlus(Context context) {
        super(context);
    }

    public GroupRadioPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
        rowsView = new SparseArray<>();
    }

    private void initAttributes(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        TypedArray a2 = ctx.obtainStyledAttributes(attrs, R.styleable.GroupRadioPlus);
        customFont = a.getString(R.styleable.TextViewPlus_customFont);
        maxRowElements = a2.getInt(R.styleable.GroupRadioPlus_maxRowElements,3);
        btnBackground = a2.getResourceId(R.styleable.GroupRadioPlus_buttonBackground,R.drawable.radiobuttonbg);
        underlineHeight = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_underlineHeight,10);
        underlineColor = a2.getColor(R.styleable.GroupRadioPlus_underlineColor,Color.TRANSPARENT);
        underlineBackgroundColor = a2.getColor(R.styleable.GroupRadioPlus_underlineBackgroundColor,Color.TRANSPARENT);
        paddingLeft = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_btnPaddingLeft,5);
        paddingTop = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_btnPaddingTop,40);
        paddingRight = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_btnPaddingRight,5);
        paddingBottom = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_btnPaddingBottom,40);
        marginLeft = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_btnMarginLeft,1);
        marginTop = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_btnMarginTop,1);
        marginRight = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_btnMarginRight,1);
        marginBottom = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_btnMarginBottom,1);
        ucFirst = a2.getBoolean(R.styleable.GroupRadioPlus_ucFirst,false);
        textSize = a2.getDimension(R.styleable.GroupRadioPlus_textSize,12.0f);
        rowsView = new SparseArray<>();
        a.recycle();
    }



    public void setDataList(List data) {
        this.data = data;
        populate();
    }

    private void styleBtn(RadioButton b) {
        b.setButtonDrawable(new StateListDrawable());
        b.setBackgroundResource(btnBackground);
        b.setGravity(Gravity.CENTER);
        b.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        b.setPadding(paddingLeft,paddingTop,paddingRight,paddingBottom);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        lp.setMargins(marginLeft,marginTop,marginRight,marginBottom);
        b.setLayoutParams(lp);
    }

    private TableRow styleUnderline() {
        TableRow tr = new TableRow(this.getContext());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, underlineHeight));
        View v = new View(getContext());
        v.setBackgroundColor(underlineColor);
        v.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,underlineHeight,(float)(maxRowElements)));
        tr.addView(v);
        return tr;
    }

    private void calculateRowClicked(int i) {
        Log.d(TAG,"clickedItem "+i);
        int row = (i/maxRowElements);
        int rowPos = (i%maxRowElements);
        final TableRow tr = rowsView.get(row);
        int offset = 0;
        if (row == (nbRows-1) && nbCellLastRow != maxRowElements) {
            offset = (tr.getWidth()- nbCellLastRow*tr.getWidth()/maxRowElements)/2;
        }
        final int paddingOffset = offset+(i%maxRowElements)*tr.getWidth()/maxRowElements;

        if (oldRow == row) {
            ValueAnimator animation = ValueAnimator.ofInt(oldPaddingOffset, paddingOffset);
            animation.setDuration(300);
            animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    tr.setPadding((Integer.parseInt(valueAnimator.getAnimatedValue().toString())), 0, tr.getWidth() - Integer.parseInt(valueAnimator.getAnimatedValue().toString()) - tr.getWidth() / maxRowElements, 0);
                }
            });
            animation.start();
        } else {;
            tr.animate().alpha(1f).setDuration(300).start();
            if (oldRow != -1)
                rowsView.get(oldRow).animate().alpha(0f).setDuration(300).start();
            tr.setPadding(paddingOffset, 0, tr.getWidth() - paddingOffset - tr.getWidth() / maxRowElements, 0);
        }

        Log.d(TAG,"x:"+rowPos+" y:"+row+" marginLeft:"+paddingOffset+" marginRight:"+(tr.getWidth()-paddingOffset-tr.getWidth()/maxRowElements)+" offset:"+paddingOffset+" oldPaddingOffset:"+oldPaddingOffset+" "+nbCellLastRow);
        oldPaddingOffset = paddingOffset;
        oldRow = row;

    }

    public void populate() {
        TableRow tr = new TableRow(this.getContext());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow trUnderscore = styleUnderline();
        rowsView.put(0,trUnderscore);
        tr.setBaselineAligned(false);
        for (int i =0; i<data.size(); i++) {
            if ((i % maxRowElements) == 0 && i > 0) {
                tr = new TableRow(this.getContext());
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.setGravity(Gravity.CENTER_HORIZONTAL);
                tr.setBaselineAligned(false);
                trUnderscore = styleUnderline();
                rowsView.put(i/maxRowElements,trUnderscore);
            }
            RadioButton b = new RadioButton(this.getContext());
            styleBtn(b);
            String txtSkill = "";
            if (ucFirst) {
                char c[] = ((String) data.get(i)).toLowerCase().toCharArray();
                c[0] = Character.toUpperCase(c[0]);
                txtSkill = new String(c);
            } else {
                txtSkill = (String) data.get(i);
            }
            b.setText(txtSkill);
            b.setTag(i);
            tr.addView(b);
            b.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickedButton != null)
                        clickedButton.setChecked(false);
                    clickedButton = (RadioButton) v;
                    calculateRowClicked((int)v.getTag());
                    if (onClickListener != null)
                        onClickListener.onClick((int)v.getTag());
                }
            });
            if ((i % maxRowElements) == 0) {
                nbRows++;
                Log.d(TAG,"padding full width "+this.getWidth());
                trUnderscore.setAlpha(0.0f);
                this.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                this.addView(trUnderscore, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            }
            nbCellLastRow = 1+i%maxRowElements;
        }
    }

    public int getMaxRowElements() {
        return maxRowElements;
    }

    public void setMaxRowElements(int maxRowElements) {
        this.maxRowElements = maxRowElements;
    }

    public GroupRadioPlusClickListener getOnClickListener() {
        return onClickListener;
    }

    public void setOnClickListener(GroupRadioPlusClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public int getButtonPaddingBottom() {
        return paddingBottom;
    }

    public void setButtonPaddingBottom(int paddingBottom) {
        this.paddingBottom = paddingBottom;
    }

    public int getButtonPaddingLeft() {
        return paddingLeft;
    }

    public void setButtonPaddingLeft(int paddingLeft) {
        this.paddingLeft = paddingLeft;
    }

    public int getButtonPaddingTop() {
        return paddingTop;
    }

    public void setButtonPaddingTop(int paddingTop) {
        this.paddingTop = paddingTop;
    }

    public int getButtonPaddingRight() {
        return paddingRight;
    }

    public void setButtonPaddingRight(int paddingRight) {
        this.paddingRight = paddingRight;
    }
}
