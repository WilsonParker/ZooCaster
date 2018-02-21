package com.graction.developer.zoocaster.UI.Layout;

import android.graphics.Paint;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.PointLabelFormatter;

/**
 * Created by Graction06 on 2018-02-21.
 */

public class CustomPointLabelFormatter2 extends PointLabelFormatter {
    private static final float DEFAULT_H_OFFSET_DP = 0;
    private static final float DEFAULT_V_OFFSET_DP = -4;
    private static final float DEFAULT_TEXT_SIZE_SP = 12;
    private int size;

    public CustomPointLabelFormatter2(int size) {
        this.size = size;
    }

    @Override
    protected void initTextPaint(Integer textColor) {
        if (textColor == null) {
            setTextPaint(null);
        } else {
            setTextPaint(new Paint());
            getTextPaint().setAntiAlias(true);
            getTextPaint().setColor(textColor);
            getTextPaint().setTextAlign(Paint.Align.CENTER);
            getTextPaint().setTextSize(PixelUtils.spToPix(DEFAULT_TEXT_SIZE_SP));
            //textPaint.setStyle(Paint.Style.STROKE);
        }
    }
}
