package com.jipouille.usefullviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;

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
    private int underlineColor;
    private RadioButton clickedButton;
    private SparseArray<TableRow> rowsView;

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
        underlineHeight = a2.getDimensionPixelSize(R.styleable.GroupRadioPlus_underlineHeight,10);
        underlineColor = a2.getColor(R.styleable.GroupRadioPlus_underlineColor,Color.TRANSPARENT);
        rowsView = new SparseArray<>();
        a.recycle();
    }



    public void setDataList(List data) {
        this.data = data;
        populate();
    }

    private void styleBtn(RadioButton b) {
        b.setButtonDrawable(new StateListDrawable());
        b.setBackgroundResource(R.drawable.radiobuttonbg);
        b.setGravity(Gravity.CENTER);
        b.setPadding(5,40,5,40);
        TableRow.LayoutParams lp = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT);
        lp.setMargins(1,1,1,1);
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
        TableRow tr = rowsView.get(row);
        View v = tr.getChildAt(0);
        int marginLeft = i*v.getWidth()/maxRowElements;
        int marginRight = (maxRowElements-i-1)*v.getWidth()/maxRowElements;
        TableRow.LayoutParams lp = (TableRow.LayoutParams)v.getLayoutParams();
        lp.setMargins(marginLeft,0,marginRight,0);
        Log.d(TAG,"x:"+rowPos+" y:"+row+" marginLeft:"+marginLeft+" marginRight:"+marginRight);
        v.requestLayout();

    }

    public void populate() {
        TableRow tr = new TableRow(this.getContext());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        TableRow trUnderscore = styleUnderline();
        rowsView.put(0,trUnderscore);
        for (int i =0; i<data.size(); i++) {
            if ((i % maxRowElements) == 0 && i > 0) {
                tr = new TableRow(this.getContext());
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                tr.setGravity(Gravity.CENTER_HORIZONTAL);
                trUnderscore = styleUnderline();
                rowsView.put(i/maxRowElements,trUnderscore);
            }
            RadioButton b = new RadioButton(this.getContext());
            styleBtn(b);
            b.setText((String) data.get(i));
            b.setTag(i);
            tr.addView(b);
            b.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickedButton != null)
                        clickedButton.setChecked(false);
                    clickedButton = (RadioButton) v;
                    calculateRowClicked((int)v.getTag());
                }
            });
            if ((i % maxRowElements) == 0) {
                this.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                this.addView(trUnderscore, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            }
        }
    }
}
