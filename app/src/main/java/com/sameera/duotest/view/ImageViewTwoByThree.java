package com.sameera.duotest.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by root on 2/7/17.
 */

public class ImageViewTwoByThree extends android.support.v7.widget.AppCompatImageView {
    public ImageViewTwoByThree(Context context) {
        super(context);
    }

    public ImageViewTwoByThree(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewTwoByThree(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int originalWidth = MeasureSpec.getSize(widthMeasureSpec);
        int originalHeight = MeasureSpec.getSize(heightMeasureSpec);

        int calculatedHeight = originalWidth * 2 / 3;

        int finalWidth, finalHeight;
        finalWidth = originalWidth;
        finalHeight = calculatedHeight;

        super.onMeasure(
                MeasureSpec.makeMeasureSpec(finalWidth, MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(finalHeight, MeasureSpec.EXACTLY));
    }
}
