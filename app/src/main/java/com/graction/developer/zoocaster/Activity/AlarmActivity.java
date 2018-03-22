package com.graction.developer.zoocaster.Activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.WindowManager;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Model.Item.AlarmItem;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.NotificationManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.System.VSManager;
import com.graction.developer.zoocaster.databinding.ActivityAlarmBinding;

import java.io.IOException;

/**
 * Created by JeongTaehyun
 */

/*
 * 알람 실행 Activity
 */

public class AlarmActivity extends BaseActivity {
    private static boolean isRunning;       // 알람이 울리고 있는가
    private ActivityAlarmBinding binding;
    private VSManager vsManager;
    private AlarmItem alarmItem;
    private NotificationManager.NotificationItem notificationItem;

    /*
     * 초기 설정
     */
    @Override
    protected void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_alarm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        Bundle bundle = getIntent().getBundleExtra(DataStorage.Key.KEY_BUNDLE);
        alarmItem = (AlarmItem) bundle.getSerializable(DataStorage.Key.KEY_ALARM_ITEM);
        notificationItem = (NotificationManager.NotificationItem) bundle.getSerializable(DataStorage.Key.KEY_NOTIFICATION_ITEM);
        vsManager = VSManager.getInstance();
        isRunning = true;
        noti(notificationItem);
        runAlarm();
    }

    /*
     * 알람 실행
     */
    private void runAlarm() {
        try {
            if (alarmItem.getIsSpeaker()) {
                vsManager.speak(this, alarmItem.getVolume());
            } else {
                vsManager.vibrate(this, VSManager.PATTERN_1, VSManager.REPEAT_INFINITE);
            }
        } catch (IOException e) {
            logger.log(HLogger.LogType.INFO, "runAlarm()", "runAlarm error", e);
        }
    }

    /*
     * Notification 실행
     */
    private void noti(NotificationManager.NotificationItem item) {
        NotificationManager.getInstance().noti(this, item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isRunning) {
            if (alarmItem.getIsSpeaker()) {
                vsManager.cancelSpeaker();
            } else {
                vsManager.cancelVibrate();
            }
        }
        isRunning = false;
    }

}
