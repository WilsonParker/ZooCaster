package com.graction.developer.zoocaster.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.DataBase.DataBaseHelper;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualitySingleModel;
import com.graction.developer.zoocaster.Model.Response.SimpleResponseModel;
import com.graction.developer.zoocaster.Model.VO.FineDustVO;
import com.graction.developer.zoocaster.Model.Xml.Weather;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.UIFactory;
import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;
import com.graction.developer.zoocaster.Util.File.PreferenceManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Parser.XmlPullParserManager;
import com.graction.developer.zoocaster.Util.System.AlarmManager;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static com.graction.developer.zoocaster.Data.DataStorage.Latitude;
import static com.graction.developer.zoocaster.Data.DataStorage.Longitude;
import static com.graction.developer.zoocaster.Data.DataStorage.fineDustStandard;
import static com.graction.developer.zoocaster.Data.DataStorage.integratedAirQualitySingleModel;
import static com.graction.developer.zoocaster.DataBase.DataBaseStorage.DATABASE_NAME;

public class Intro extends BaseActivity {
    private Context context;
    private Handler handler = new Handler();
    private GpsManager gpsManager;
    private int completeState = 3, runState, errorState;
    private static final long RUN_LIMIT_TIME = 5000;
    private long currentTime;
    // initialize Thread
    private Thread iThread = new Thread(() -> dataInitialize())
            // Check Permission
            , pThread = new Thread(() -> permissionInitialize())
            // Check Thread
            , cThread = new Thread(() -> {
        currentTime = System.currentTimeMillis();
        while (true) {
            if (completeState == runState) {
                startActivity(new Intent(context, MainActivity.class));
                finish();
                break;
            } else if (errorState == 1) {
                break;
            } else if(System.currentTimeMillis() > currentTime + RUN_LIMIT_TIME){
                errorState = 1;
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                logger.log(HLogger.LogType.ERROR, "cThread error", e);
                errorState = 1;
            }
        }
        Looper.prepare();
        handler.post(() -> {
            if (errorState == 1){
                Toast.makeText(Intro.this, "초기화에 문제가 발생하였습니다", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    });

    @Override
    protected void onCreateMojo(Bundle savedInstanceState) {
        setContentView(R.layout.activity_intro);
    }

    @Override
    protected void init() {
        context = this;
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        handler.postDelayed(() -> {
            pThread.start();
            iThread.start();
            cThread.start();
        }, 250);
    }

    private void dataInitialize() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        gpsManager = new GpsManager(this);
        Latitude = gpsManager.getLatitude();
        Longitude = gpsManager.getLongitude();

        XmlPullParserManager xmlPullParserManager = XmlPullParserManager.getInstance();
        xmlPullParserManager.setContext(context);

        UIFactory.init(this);
//            FontManager.getInstance().setAssetManager(getAssets());
        BaseActivityFileManager.getInstance().setActivity(this);
        ;
        PreferenceManager.setContext(getBaseContext());
        AlarmManager.getInstance().init(getApplicationContext());
        DataBaseStorage.dataBaseHelper = new DataBaseHelper(getBaseContext(), DATABASE_NAME, null, DataBaseStorage.Version.TABLE_VERSION);
        try {
            DataStorage.weathers = new HashMap<>();
            for (Weather weather : xmlPullParserManager.<Weather>xmlParser(Weather.class, R.xml.weathers))
                DataStorage.weathers.put(weather.getId(), weather);
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "iThread error", e);
            errorState = 1;
            return;
        }
        runState++;

        callNetworkData();
    }

    private void permissionInitialize() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            String[] permissions = new String[]{ACCESS_FINE_LOCATION, INTERNET, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};
            gpsManager.requestPermissions(permissions, DataStorage.Request.PERMISSION_REQUEST);
        } else
            runState++;
    }

    private void callNetworkData() {
        Net.getInstance().getFactoryIm().selectFineDustStandard().enqueue(new Callback<SimpleResponseModel<ArrayList<FineDustVO>>>() {
            @Override
            public void onResponse(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Response<SimpleResponseModel<ArrayList<FineDustVO>>> response) {
                if (response.isSuccessful()) {
                    fineDustStandard = response.body().getResult();
                    logger.log(HLogger.LogType.INFO
                            , "void callNetworkData() - void onResponse(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Response<SimpleResponseModel<ArrayList<FineDustVO>>> response)"
                            ,"isSuccessful, is null? "+(fineDustStandard == null));

                    callIntegratedAirQualitySingleModel();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "void callNetworkData() - onFailure(Call<WeatherModel> call, Throwable t)", "onFailure", t);
            }
        });
    }

    private void callIntegratedAirQualitySingleModel(){
        Net.getInstance().getFactoryIm().selectIntegratedAirQuality(gpsManager.getLatitude(), gpsManager.getLongitude()).enqueue(new Callback<IntegratedAirQualitySingleModel>() {
            @Override
            public void onResponse(Call<IntegratedAirQualitySingleModel> call, Response<IntegratedAirQualitySingleModel> response) {
                if(response.isSuccessful()){
                    integratedAirQualitySingleModel = response.body();
//                    integratedAirQualitySingleModel.getItem().setFineDustStandard(fineDustStandard);
                    logger.log(HLogger.LogType.INFO
                            , "void callNetworkData() - void onResponse(Call<IntegratedAirQualitySingleModel> call, Response<IntegratedAirQualitySingleModel> response)"
                            ,"isSuccessful");
                    runState++;
                }
            }

            @Override
            public void onFailure(Call<IntegratedAirQualitySingleModel> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "void callNetworkData() - onFailure(Call<IntegratedAirQualitySingleModel> call, Throwable t)", "onFailure", t);
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int grant : grantResults)
            if (grant == -1)
                return;
        runState++;
    }
}
