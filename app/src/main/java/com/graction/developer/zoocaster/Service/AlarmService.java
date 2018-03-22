package com.graction.developer.zoocaster.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Model.Item.AlarmItem;
import com.graction.developer.zoocaster.Util.System.AlarmManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;

/**
 * Created by JeongTaehyun on 2018-01-05.
 */
public class AlarmService extends Service {
    private final IBinder binder = new AlarmBinder();
    private static final long MILLIS_IN_FUTURE = 1000 * 1000, COUNT_DOUNW_INTERVAL = 1000;
    private CountDownTimer timer;
    private HLogger logger = new HLogger(AlarmService.class);


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AlarmItem item = (AlarmItem) intent.getBundleExtra(DataStorage.Key.KEY_BUNDLE).getSerializable(DataStorage.Key.KEY_ALARM_ITEM);
        AlarmManager.getInstance().setAlarm(this, item);
        logger.log(HLogger.LogType.INFO, "AlarmService", "Service set Alarm : "+item);
        return START_NOT_STICKY;
    }

    public class AlarmBinder extends Binder {
        public AlarmService getService() {
            return AlarmService.this;
        }
    }
}
