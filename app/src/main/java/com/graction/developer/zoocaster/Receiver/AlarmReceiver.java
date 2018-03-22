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
 * Created by JeongTaehyun on 2018-01-05.
 */

/*
 * 알람 Receiver
 */
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case DataStorage.Action.RECEIVE_ACTION_ALARM_START:
                intent.setClass(context, AlarmStartService.class);
                break;
        }
        context.startService(intent);
    }
}
