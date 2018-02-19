package com.graction.developer.zoocaster.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Service.AlarmService;
import com.graction.developer.zoocaster.Service.AlarmStartService;
import com.graction.developer.zoocaster.Util.Log.HLogger;

/**
 * Created by Graction06 on 2018-01-05.
 */

public class AlarmReceiver extends BroadcastReceiver {
    private HLogger logger = new HLogger(AlarmReceiver.class);
    private PowerManager.WakeLock sCpuWakeLock;

    @Override
    public void onReceive(Context context, Intent intent) {
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "intent.getAction() : " + intent.getAction()); // com.graction.developer.zoocaster.ALARM_START

        switch (intent.getAction()) {
            case DataStorage.Action.RECEIVE_ACTION_ALARM_START:
                intent.setClass(context, AlarmStartService.class);
                logger.log(HLogger.LogType.INFO, "AlarmReceiver", "RECEIVE_ACTION_ALARM_START");
                break;
            case DataStorage.Action.RECEIVE_ACTION_SINGLE_ALARM:
                intent.setClass(context, AlarmService.class);
                break;
        }
        context.startService(intent);
    }

   /* private void wakeLock(Context context) {
        if (sCpuWakeLock == null) {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            sCpuWakeLock = pm.newWakeLock(
                    PowerManager.SCREEN_BRIGHT_WAKE_LOCK |
                            PowerManager.ACQUIRE_CAUSES_WAKEUP |
                            PowerManager.ON_AFTER_RELEASE
                    , "ALARM"
            );
        }
        sCpuWakeLock.acquire();

        if (sCpuWakeLock != null) {
            sCpuWakeLock.release();
            sCpuWakeLock = null;
        }
    }*/
}
