package com.graction.developer.zoocaster.UI;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import com.graction.developer.zoocaster.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by JeongTaehyun on 2017-08-10.
 */

/*
 * Progress 설정
 * ex) Loading
 */
public class ProgressManager {
    private Activity activity;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private SweetAlertDialog sweetAlertDialog;
    private View view;

    private int layout;
    private boolean isDialog;

    public ProgressManager(Activity activity) {
        this.activity = activity;
        initDialog();
    }

    public ProgressManager(Activity activity, int layout) {
        this.activity = activity;
        this.layout = layout;
        view = LayoutInflater.from(activity).inflate(layout, null);
        initCustom();
    }

    public ProgressManager(Activity activity, View view) {
        this.activity = activity;
        this.view = view;
        initCustom();
    }

    /*
     * AlertDialogBuilder 로 생성
     */
    private void initCustom() {
        isDialog = false;
        alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        alertDialog = alertDialogBuilder.create();
    }

    /*
     * AlertManager 로 생성
     */
    private void initDialog() {
        isDialog = true;
        sweetAlertDialog = AlertManager.getInstance().createLoadingDialog(activity);
    }

    // Show
    public void alertShow() {
        if (isDialog) {
            if (sweetAlertDialog != null && !sweetAlertDialog.isShowing())
                sweetAlertDialog.show();
        } else {
            if (alertDialog != null && !alertDialog.isShowing())
                alertDialog.show();
        }
    }

    // Dismiss
    public void alertDismiss() {
        if (isDialog) {
            if (sweetAlertDialog != null && sweetAlertDialog.isShowing())
                sweetAlertDialog.dismiss();
        } else {
            if (alertDialog != null && alertDialog.isShowing())
                alertDialog.dismiss();
        }
    }

}
