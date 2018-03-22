package com.graction.developer.zoocaster.UI.Layout;

import android.graphics.Paint;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.PointLabelFormatter;

/**
 * Created by JeongTaehyun on 2018-02-21.
 */

public class CustomPointLabelFormatter extends PointLabelFormatter {

    @Override
    protected void initTextPaint(Integer textColor) {
        if (textColor == null) {
            setTextPaint(null);
        } else {
            setTextPaint(new Paint());
            getTextPaint().setAntiAlias(true);
            getTextPaint().setColor(textColor);
            getTextPaint().setTextAlign(Paint.Align.LEFT);
            getTextPaint().setTextSize(PixelUtils.spToPix(15));
            //textPaint.setStyle(Paint.Style.STROKE);
        }
    }
}
