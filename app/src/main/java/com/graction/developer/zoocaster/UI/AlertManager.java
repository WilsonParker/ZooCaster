package com.graction.developer.zoocaster.UI;

import android.content.Context;
import android.graphics.Color;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by JeongTaehyun on 2017-08-02.
 */

/*
 * SweetAlert 를 이용하여 Alert 설정
 * Loading 에 사용
 */

public class AlertManager {
    private static final AlertManager ourInstance = new AlertManager();
    private static final String DEFAULT_COLOR = "#000000"
                                , DEFAULT_LOADING_TEXT = "Loading"
                                ;

    public static AlertManager getInstance() {
        return ourInstance;
    }

    public SweetAlertDialog createLoadingDialog(Context context) {
        return createLoadingDialog(context, DEFAULT_LOADING_TEXT, DEFAULT_COLOR);
    }

    public SweetAlertDialog createLoadingDialog(Context context, String msg, String color) {
        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor(color));
        pDialog.setTitleText(msg);
        pDialog.setCancelable(false); // 백키를 눌러도 닫히지 않는다.
        return pDialog;
    }

}
