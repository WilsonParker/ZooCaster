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

public class AlarmActivity extends BaseActivity {
    private static boolean isRunning;
    private ActivityAlarmBinding binding;
    private VSManager vsManager;
    private AlarmItem alarmItem;
    private NotificationManager.NotificationItem notificationItem;

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
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "AlarmActivity init"); // com.graction.developer.zoocaster.ALARM_START
        noti(notificationItem);
        runAlarm();
    }

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

    private void noti(NotificationManager.NotificationItem item) {
       /* Notification notification =
                new NotificationCompat.Builder(this, "Alarm ID")
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(alarmItem.getMemo())
                        .setContentText(alarmItem.getAddress())
                        .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);*/
        NotificationManager.getInstance().noti(this, item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        logger.log(HLogger.LogType.INFO, "onResume()", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        logger.log(HLogger.LogType.INFO, "onPause()", "onPause");
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
        logger.log(HLogger.LogType.INFO, "onDestroy()", "onDestroy");
    }

}
