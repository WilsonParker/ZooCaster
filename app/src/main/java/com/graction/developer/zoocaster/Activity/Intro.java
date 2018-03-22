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
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.UIFactory;
import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.System.AlarmManager;

import java.util.ArrayList;

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

/**
 * Created by JeongTaehyun
 */

/*
 * 초기 설정 Activity
 */

public class Intro extends BaseActivity {
    private Context context;
    private Handler handler = new Handler();
    private GpsManager gpsManager;
    private int completeState = 3               /*
                                                 * 완료 상태
                                                 * runState == completeState 가 true 라면 MainActivity 실행
                                                 */
                , runState                      /*
                                                 * 진행 중 상태
                                                 * 기능 별 State 를 추가해서 그 합을 completeState 에 할당하여
                                                 * 완료가 됬을 경우 MainActivity 실행
                                                 */
                , errorState;                   /*
                                                 * 에러 발생 여부
                                                 * 1: 에러
                                                 */
    private final long RUN_LIMIT_TIME = 5000;   /*
                                                 * 초기 설정 최대 시간 (단위/ms)
                                                 * 해당 시간 내에 complete 되지 못하면 App 종료
                                                 */
    private long currentTime;                   // 현재 시간 할당

            // 초기 설정 Thread
    private Thread iThread = new Thread(() -> dataInitialize())
            // Permission Check Thread
            , pThread = new Thread(() -> permissionInitialize())
            // Complete or Error Thread
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

    /*
     * 초기 설정
     */
    @Override
    protected void init() {
        context = this;
        handler.postDelayed(() -> {
            pThread.start();
            iThread.start();
            cThread.start();
        }, 250);
    }

    /*
     * 데이터 초기 설정
     */
    private void dataInitialize() {
        if (Looper.myLooper() == null) {
            Looper.prepare();
        }
        gpsManager = new GpsManager(this);
        Latitude = gpsManager.getLatitude();
        Longitude = gpsManager.getLongitude();

        UIFactory.init(this);
//      FontManager.getInstance().setAssetManager(getAssets());
        BaseActivityFileManager.getInstance().setActivity(this);
        AlarmManager.getInstance().init(getApplicationContext());
        DataBaseStorage.dataBaseHelper = new DataBaseHelper(getBaseContext(), DATABASE_NAME, null, DataBaseStorage.Version.TABLE_VERSION);
        runState++;
        callNetworkData();
    }

    /*
     * Permission 초기 설정
     */
    private void permissionInitialize() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            String[] permissions = new String[]{ACCESS_FINE_LOCATION, INTERNET, WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE};
            gpsManager.requestPermissions(permissions, DataStorage.Request.PERMISSION_REQUEST);
        } else
            runState++;
    }

    /*
     * 미세먼지 규격 불러오기
     * Korea, WHO
     */
    private void callNetworkData() {
        Net.getInstance().getFactoryIm().selectFineDustStandard().enqueue(new Callback<SimpleResponseModel<ArrayList<FineDustVO>>>() {
            @Override
            public void onResponse(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Response<SimpleResponseModel<ArrayList<FineDustVO>>> response) {
                if (response.isSuccessful()) {
                    fineDustStandard = response.body().getResult();
                    callIntegratedAirQualitySingleModel();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "void callNetworkData() - onFailure(Call<WeatherModel> call, Throwable t)", "onFailure", t);
            }
        });
    }

    /*
     * 통합대기지수 불러오기
     */
    private void callIntegratedAirQualitySingleModel(){
        Net.getInstance().getFactoryIm().selectIntegratedAirQuality(gpsManager.getLatitude(), gpsManager.getLongitude()).enqueue(new Callback<IntegratedAirQualitySingleModel>() {
            @Override
            public void onResponse(Call<IntegratedAirQualitySingleModel> call, Response<IntegratedAirQualitySingleModel> response) {
                if(response.isSuccessful()){
                    integratedAirQualitySingleModel = response.body();
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
