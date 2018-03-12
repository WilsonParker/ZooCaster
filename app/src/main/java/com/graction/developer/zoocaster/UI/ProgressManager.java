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
 * Created by Hare on 2017-08-10.
 */

/*
        compile 'cn.pedant.sweetalert:library:1.3'
 */
public class ProgressManager {
    private static final int THREAD_SLEEP = 150;

    private Activity activity;
    private ProgressBar progressBar;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertDialogBuilder;
    private SweetAlertDialog sweetAlertDialog;
    private View view;

    private Handler handler;
    private Thread checkThread, runThread, stateThread;
    private int layout;
    private boolean state = false;
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

    private void initCustom() {
        isDialog = false;

        handler = HandlerManager.getInstance().getHandler();
        alertDialogBuilder = new AlertDialog.Builder(activity);
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        alertDialog = alertDialogBuilder.create();
    }

    private void initDialog() {
        isDialog = true;
        handler = HandlerManager.getInstance().getHandler();
        sweetAlertDialog = AlertManager.getInstance().createLoadingDialog(activity);
    }

    public void alertShow() {
        if (isDialog) {
            if (sweetAlertDialog != null && !sweetAlertDialog.isShowing())
                sweetAlertDialog.show();
        } else {
            if (alertDialog != null && !alertDialog.isShowing())
                alertDialog.show();
        }
    }

    public void alertDismiss() {
        if (isDialog) {
            if (sweetAlertDialog != null && sweetAlertDialog.isShowing())
                sweetAlertDialog.dismiss();
        } else {
            if (alertDialog != null && alertDialog.isShowing())
                alertDialog.dismiss();
        }
    }

    public void action(OnProgressAction action) {
        runThread = new Thread(() -> action.run());
        checkThread = new Thread(() -> {
            try {
                handler.post(() -> alertShow());
                runThread.start();
                while (!(runThread.getState() == Thread.State.TERMINATED)) {
                    Thread.sleep(THREAD_SLEEP);
                }
                handler.post(() -> alertDismiss());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        checkThread.start();
    }

    public void actionWithState(OnProgressAction action) {
        state = false;
        stateThread = new Thread(() -> {
            try {
                handler.post(() -> alertShow());
                action.run();
                while (!state) {
                    Thread.sleep(THREAD_SLEEP);
                }
                handler.post(() -> alertDismiss());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        stateThread.start();
    }

    public void endRunning() {
        this.state = true;
    }

    public interface OnProgressAction {
        void run();
    }
}
