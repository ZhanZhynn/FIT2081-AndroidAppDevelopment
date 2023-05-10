package com.example.myapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ScrollViewCustom extends ScrollView {
    public ScrollViewCustom(Context context) {
        super(context);
    }

    public ScrollViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ScrollViewCustom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
