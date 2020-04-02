package com.lizhehan.wanandroid.widget;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

public class ScaleInTransformer implements ViewPager2.PageTransformer {

    private static final float DEFAULT_MIN_SCALE = 0.85f;
    private static final float DEFAULT_CENTER = 0.5f;

    private float mMinScale = DEFAULT_MIN_SCALE;

    @Override
    public void transformPage(@NonNull View page, float position) {
        page.setElevation(-Math.abs(position));
        float pageWidth = page.getWidth();
        float pageHeight = page.getHeight();

        page.setPivotY(pageHeight / 2);
        page.setPivotX(pageWidth / 2);
        if (position < -1) {
            page.setScaleX(mMinScale);
            page.setScaleY(mMinScale);
            page.setPivotX(pageWidth);
        } else if (position <= 1) {
            if (position < 0) {
                float scaleFactor = (1 + position) * (1 - mMinScale) + mMinScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * (DEFAULT_CENTER + DEFAULT_CENTER * -position));
            } else {
                float scaleFactor = (1 - position) * (1 - mMinScale) + mMinScale;
                page.setScaleX(scaleFactor);
                page.setScaleY(scaleFactor);
                page.setPivotX(pageWidth * ((1 - position) * DEFAULT_CENTER));
            }
        } else {
            page.setPivotX(0f);
            page.setScaleX(mMinScale);
            page.setScaleY(mMinScale);
        }
    }
}
