package com.jipouille.usefullviews.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.List;

/**
 * Created by jp on 16/06/16.
 */
public class GroupRadioPlus extends TableLayout {
    private List data;
    private String customFont;
    private int maxRowElements = 3;
    private SparseArray<Button> buttonList;

    public GroupRadioPlus(Context context) {
        super(context);
        buttonList = new SparseArray<>();
    }

    public GroupRadioPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttributes(context, attrs);
         buttonList = new SparseArray<>();
    }

    private void initAttributes(Context ctx, AttributeSet attrs) {
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.TextViewPlus);
        customFont = a.getString(R.styleable.usefullviews_customFont);
        maxRowElements = a.getInt(R.styleable.usefullviews_maxRowElements,3);
        a.recycle();
    }



    public void setDataList(List data) {
        this.data = data;
        populate();
    }

    public void populate() {
        TableRow tr = new TableRow(this.getContext());
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        for (int i =0; i<data.size(); i++) {
            if ((i % maxRowElements) == 0 && i>0) {
                tr = new TableRow(this.getContext());
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            }
            Button b = new Button(this.getContext());
            b.setText((String)data.get(i));
            b.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));
            tr.addView(b);
            b.setTag(i);
            buttonList.put(i,b);
            if ((i%maxRowElements) == 0)
                this.addView(tr, new TableLayout.LayoutParams(LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
