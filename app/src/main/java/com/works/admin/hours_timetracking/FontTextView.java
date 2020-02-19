package com.works.admin.hours_timetracking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by acer on 12/3/2018.
 */
@SuppressLint("AppCompatCustomView")
public class FontTextView extends TextView {
    String fonts[] = {"Muli.ttf", "Muli.ttf","Muli.ttf", "Muli.ttf"};

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public FontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }

    }


    public FontTextView(Context context) {
        super(context);
        if (!isInEditMode()) {
            init(null);
        }
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.FontTextView);
            if (a.getString(R.styleable.FontTextView_font_type) != null) {
                String fontName = fonts[Integer.valueOf(a.getString(R.styleable.FontTextView_font_type))];

                if (fontName != null) {
                    Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "font/" + fontName);
                    setTypeface(myTypeface);
                }
                a.recycle();
            }
        }
    }
}