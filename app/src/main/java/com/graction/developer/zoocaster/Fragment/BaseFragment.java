package com.graction.developer.zoocaster.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;

import com.graction.developer.zoocaster.Data.SyncObject;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;

import retrofit2.Call;
import retrofit2.Callback;

abstract public class BaseFragment extends Fragment {
    protected HLogger logger;
    private SyncObject syncObject = SyncObject.getInstance();
    private Call call;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        init(view);
    }

    protected abstract void init(View view);

    private void init() {
        logger = new HLogger(getClass());
    }

    @Override
    public void onPause() {
        super.onPause();
        if (call != null && call.isExecuted())
            call.cancel();
    }

    protected void setCall(Call call){
        this.call = call;
    }

    protected void startSync() {
        try {
            syncObject.start();
        } catch (InterruptedException e) {
            logger.log(HLogger.LogType.ERROR, "startSync()", "startSync InterruptedException", e);
        }
    }

    protected void addAction(SyncObject.OnSyncAction action, int id) {
        try {
            syncObject.addAction(action, id);
        } catch (InterruptedException e) {
            logger.log(HLogger.LogType.ERROR, "void addAction(SyncObject.OnSyncAction action, int id)", "addAction InterruptedException", e);
        }
    }

    protected void endThread(int id) {
        try {
            syncObject.end(id);
        } catch (InterruptedException e) {
            logger.log(HLogger.LogType.ERROR, "void endThread(int id)", "endThread InterruptedException", e);
        }
    }

    protected void callIntegratedAirQuality(GpsManager gpsManager, Callback callback) {
        logger.log(HLogger.LogType.INFO, "void callIntegratedAirQuality(GpsManager gpsManager, Callback callback)", "gpsManager is null? "+(gpsManager == null));

        call = Net.getInstance().getFactoryIm().selectIntegratedAirQuality(gpsManager.getLatitude(), gpsManager.getLongitude());
        call.enqueue(callback);
    }
}
