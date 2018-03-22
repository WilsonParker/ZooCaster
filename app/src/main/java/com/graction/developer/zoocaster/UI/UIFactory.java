package com.graction.developer.zoocaster.UI;

import android.app.Activity;
import android.graphics.Point;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Parser.MathematicsManager;
import com.graction.developer.zoocaster.Util.Parser.SizeManager;

/**
 * Created by JeongTaehyun
 */

/*
 * 해상도별 사이즈 적용
 * 해당 View 단위 px 로 설정 필요
 */

public class UIFactory {
    public static final int TYPE_BASIC = 0B00000001, TYPE_MARGIN = 0B00000010, TYPE_BASIC_MARGIN = 0B00000100, TYPE_RADIUS = 0B00001000, TYPE_TEXT_SIZE = 0B00010000;

    private static UIFactory uiFactory = new UIFactory();
    private static boolean isActivity = false;
    private static final int BASE_DIGIT = 3, BASE_WIDTH = 360, BASE_HEIGHT = 640;
    private static double RAT_DEVICE_WIDTH, RAT_DEVICE_HEIGHT;
    private static final MathematicsManager math = MathematicsManager.getInstance();
    private Activity activity;
    private View e;

    public static UIFactory getInstance(View view) {
        uiFactory.setResource(view);
        isActivity = false;
        return uiFactory;
    }

    public static UIFactory getInstance(Activity activity) {
        uiFactory.setResource(activity);
        isActivity = true;
        return uiFactory;
    }

    public static void init(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        RAT_DEVICE_WIDTH = math.rounds(size.x / BASE_WIDTH, BASE_DIGIT);
        RAT_DEVICE_HEIGHT = math.rounds(size.y / BASE_HEIGHT, BASE_DIGIT);
    }

    public <E extends View> E createView(int id) {
        E e;
        if (isActivity)
            e = activity.findViewById(id);
        else
            e = this.e.findViewById(id);
        return e;
    }

    public <E extends View> E createViewWithRateParams(int id, int type) {
        return setViewWithRateParams(createView(id), type);
    }

    public static <E extends View> E setViewWithRateParams(E e, int type) throws ClassCastException {
        ViewGroup.MarginLayoutParams mLayoutParams = (ViewGroup.MarginLayoutParams) e.getLayoutParams();
        if ((type & TYPE_BASIC) != 0) {
            int width = mLayoutParams.width, height = mLayoutParams.height;
            if (width != ViewGroup.LayoutParams.MATCH_PARENT && width != ViewGroup.LayoutParams.WRAP_CONTENT)
                mLayoutParams.width = math.rounds(mLayoutParams.width * RAT_DEVICE_WIDTH, BASE_DIGIT);
            if (height != ViewGroup.LayoutParams.MATCH_PARENT && height != ViewGroup.LayoutParams.WRAP_CONTENT)
                mLayoutParams.height = math.rounds(mLayoutParams.height * RAT_DEVICE_HEIGHT, BASE_DIGIT);
        }

        if ((type & TYPE_MARGIN) != 0) {
            mLayoutParams.topMargin = math.rounds(mLayoutParams.topMargin * RAT_DEVICE_HEIGHT, BASE_DIGIT);
            mLayoutParams.bottomMargin = math.rounds(mLayoutParams.bottomMargin * RAT_DEVICE_HEIGHT, BASE_DIGIT);
            mLayoutParams.leftMargin = math.rounds(mLayoutParams.leftMargin * RAT_DEVICE_WIDTH, BASE_DIGIT);
            mLayoutParams.rightMargin = math.rounds(mLayoutParams.rightMargin * RAT_DEVICE_WIDTH, BASE_DIGIT);
        }

        if ((type & TYPE_RADIUS) != 0) {
            RoundedBitmapDrawable gradientDrawable = ((RoundedBitmapDrawable) e.getBackground());
            gradientDrawable.setCornerRadius(math.rounds(SizeManager.getInstance().convertDpToPixels(gradientDrawable.getCornerRadius()) * RAT_DEVICE_HEIGHT, BASE_DIGIT));
        }

        if ((type & TYPE_TEXT_SIZE) != 0) {
            ((TextView) e).setTextSize(math.rounds(((TextView) e).getTextSize() * RAT_DEVICE_WIDTH, BASE_DIGIT));
        }
        e.setLayoutParams(mLayoutParams);
        return e;
    }

    public void setResource(Activity activity) {
        this.activity = activity;
    }

    public void setResource(View view) {
        this.e = view;
    }

    public static int getBaseDigit() {
        return BASE_DIGIT;
    }

    public static double getRatDeviceWidth() {
        return RAT_DEVICE_WIDTH;
    }

    public static double getRatDeviceHeight() {
        return RAT_DEVICE_HEIGHT;
    }
}
