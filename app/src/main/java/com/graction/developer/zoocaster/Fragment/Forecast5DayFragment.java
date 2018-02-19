package com.graction.developer.zoocaster.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Data.SyncObject;
import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Model.Response.Forecast5DayModel;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.GPS.GoogleLocationManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.databinding.FragmentForecast5dayBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forecast5DayFragment extends BaseFragment {
    private static final int SYNC_ID = 0B0010;
    private FragmentForecast5dayBinding binding;
    private GpsManager gpsManager;
    private GoogleLocationManager googleLocationManager;
    private Forecast5DayModel model;
    private SyncObject syncObject = SyncObject.getInstance();

    public static Fragment getInstance() {
        Fragment fragment = new Forecast5DayFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_forecast5day, null, false);
        return binding.getRoot();
    }

    @Override
    protected void init(View view) {
        gpsManager = new GpsManager(getActivity());

        googleLocationManager = new GoogleLocationManager(new AddressHandleListener() {
            @Override
            public void setAddress(String address) {
                logger.log(HLogger.LogType.INFO, "address : " + address);
                // binding.fragmentHomeTVAddress.setText(address);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        forecast5day();
    }

    public void forecast5day() {
        try {
            syncObject.addAction(() -> Net.getInstance().getFactoryIm().selectForecast5Day(gpsManager.getLatitude(), gpsManager.getLongitude()).enqueue(new Callback<Forecast5DayModel>() {
                @Override
                public void onResponse(Call<Forecast5DayModel> call, Response<Forecast5DayModel> response) {
                    if (response.isSuccessful()) {
                        logger.log(HLogger.LogType.INFO, "void forecast5day() - onResponse(Call<Forecast5DayModel> call, Response<Forecast5DayModel> response)", "isSuccessful");
                        model = response.body();
                        reloadWeatherInfo();
                    } else {
                        logger.log(HLogger.LogType.WARN, "void forecast5day() - onResponse(Call<Forecast5DayModel> call, Response<Forecast5DayModel> response)", "is not Successful");
                    }
                    endThread();
                }

                @Override
                public void onFailure(Call<Forecast5DayModel> call, Throwable t) {
                    logger.log(HLogger.LogType.ERROR, "void forecast5day() - onFailure(Call<Forecast5DayModel> call, Throwable t)", "onFailure", t);
                    endThread();
                }
            }), SYNC_ID);

            syncObject.start();
        } catch (InterruptedException e) {
            logger.log(HLogger.LogType.ERROR, "onFailure(Call<WeatherModel> call, Throwable t)", "InterruptedException", e);
        }
    }

    private void reloadWeatherInfo() {
        if (gpsManager.isGetLocation()) {

        } else {
            gpsManager.showSettingsAlert();
        }
    }

    private void endThread() {
        try {
            syncObject.end(SYNC_ID);
        } catch (InterruptedException e) {
            logger.log(HLogger.LogType.ERROR, "endThread()", "endThread InterruptedException", e);
        }
    }
}
