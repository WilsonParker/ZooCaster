package com.graction.developer.zoocaster.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Model.Response.DailyForecast;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.GPS.GoogleLocationManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Weather.WeatherManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastDailyFragment extends BaseFragment {
    private WeatherManager weatherManager;
    private GpsManager gpsManager;
    private GoogleLocationManager googleLocationManager;

    public static Fragment getInstance() {
        Fragment fragment = new ForecastDailyFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_forecast5day, null);
    }

    @Override
    protected void init(View view) {
        weatherManager = WeatherManager.getInstance();
        gpsManager = new GpsManager(getActivity());

        googleLocationManager = new GoogleLocationManager(new AddressHandleListener() {
            @Override
            public void setAddress(String address) {
                logger.log(HLogger.LogType.INFO, "address : " + address);
                // binding.fragmentHomeTVAddress.setText(address);
            }
        });
    }

    public void forecastDaily() {
        Net.getInstance().getFactoryIm().selectForecastDaily(gpsManager.getLatitude(), gpsManager.getLongitude()).enqueue(new Callback<DailyForecast>() {
            @Override
            public void onResponse(Call<DailyForecast> call, Response<DailyForecast> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<DailyForecast> call, Response<DailyForecast> response)", "isSuccessful");
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<DailyForecast> call, Response<DailyForecast> response)", "response : " + response.body());
                } else {
                    logger.log(HLogger.LogType.WARN, "onResponse(Call<DailyForecast> call, Response<DailyForecast> response)", "is not Successful");
                }
            }

            @Override
            public void onFailure(Call<DailyForecast> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onFailure(Call<DailyForecast> call, Throwable t)", "onFailure", t);
            }
        });
    }
}
