package com.cinfy.jmvendor.base;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class KrutiDevTextView extends TextView {

    public KrutiDevTextView(Context context) {
        super(context);
        style(context);
    }

    public KrutiDevTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context);
    }

    public KrutiDevTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(context);
    }

    private void style(Context context) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(),"fonts/krutidev.ttf");
        setTypeface(tf);
//        setTextColor(getResources().getColor(R.color.white));
    }

}

