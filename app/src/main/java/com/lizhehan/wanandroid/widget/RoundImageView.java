package com.lizhehan.wanandroid.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class RoundImageView extends AppCompatImageView {

    private static final int ROUNDED_CORNER = 20;

    private float width;
    private float height;
    private Path path;

    public RoundImageView(Context context) {
        super(context);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        path = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width > ROUNDED_CORNER && height > ROUNDED_CORNER) {
            path.moveTo(ROUNDED_CORNER, 0);
            path.lineTo(width - ROUNDED_CORNER, 0);
            path.quadTo(width, 0, width, ROUNDED_CORNER);
            path.lineTo(width, height - ROUNDED_CORNER);
            path.quadTo(width, height, width - ROUNDED_CORNER, height);
            path.lineTo(ROUNDED_CORNER, height);
            path.quadTo(0, height, 0, height - ROUNDED_CORNER);
            path.lineTo(0, ROUNDED_CORNER);
            path.quadTo(0, 0, ROUNDED_CORNER, 0);
            canvas.clipPath(path);
        }
        super.onDraw(canvas);
    }
}
