package com.graction.developer.zoocaster.Util.GPS;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Util.Log.HLogger;

import static com.graction.developer.zoocaster.Data.DataStorage.Request.RESULT_OK;

/**
 * Created by Hare on 2017-07-16.
 */

public class GpsManager {
    private HLogger logger;
    private Activity activity;
    //              현재 GPS 사용 유무,       네트워크 사용 유무        GPS 상태값               설정창 사용 유무
    private boolean isGPSEnabled = false, isNetworkEnabled = false, isGetLocation = false, showSettingAlert = false;

    private Location location;
    private LocationManager locationManager;

    //             위도  경도
    private double lat, lon;
    //  최소 GPS 정보 업데이트 거리 (단위 : m)
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    //  최소 GPS 정보 업데이트 시간 (단위 : ms)
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    private final String[] Permission = new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    public GpsManager(Activity activity) {
        this.activity = activity;
        logger = new HLogger(getClass());
        getLocation();
    }

    public Location getLocation() {
        isGetLocation = false;
        isNetworkEnabled = false;
        isGPSEnabled = false;
        try {
            logger.log(HLogger.LogType.INFO, "getLocation()", String.format("Permission ACCESS_FINE_LOCATION : ACCESS_COARSE_LOCATION = : %s, %s", PermissionChecker.checkCallingOrSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED, PermissionChecker.checkCallingOrSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PermissionChecker.PERMISSION_GRANTED));
            if (PermissionChecker.checkCallingOrSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED || PermissionChecker.checkCallingOrSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PermissionChecker.PERMISSION_GRANTED) {

                locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);

                //  현재 네트워크 상태 값 가져오기
                isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                // GPS 정보 가져오기
                isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

                logger.log(HLogger.LogType.INFO, "getLocation()", String.format("Network : %s, GPS : %s", isNetworkEnabled, isGPSEnabled));
                logger.log(HLogger.LogType.INFO, "getLocation()", String.format("GPS : %s, Permission %s", isGPSEnabled, ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED));
                logger.log(HLogger.LogType.INFO, "getLocation()", String.format("GPS : %s, Permission %s", isNetworkEnabled, ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED));
                /*if (ContextCompat.checkSelfPermission(activity, Manifest.Permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    showSettingsAlert();
                }*/
                if (isGPSEnabled && isNetworkEnabled) {
                    //  GPS 와 네트워크가 사용가능 상태가 아닐 경우
                } else {
                    isGetLocation = true;
                    if (isNetworkEnabled) {
                        logger.log(HLogger.LogType.INFO, "getLocation()", "Network Enabled");

                        //  네트워크 정보로 부터 위치 값 가져오기
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);
                        if (locationManager != null) {
                            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            saveLocationInfo();
                        }

                    } else if (isGPSEnabled && ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        logger.log(HLogger.LogType.INFO, "getLocation()", String.format("location : %s, locatinoManager: %s", location == null, locationManager == null));
                        logger.log(HLogger.LogType.INFO, "getLocation()", "GPS Enabled");

                        if (location == null) {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, locationListener);

                            if (locationManager != null) {
                                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                saveLocationInfo();
                            }
                        }
                    } else {
                        logger.log(HLogger.LogType.WARN, "getLocation()", "err1");
                        showSettingsAlert();
                    }
                }
            } else {
                logger.log(HLogger.LogType.WARN, "getLocation()", "err2");
                showSettingsAlert();
            }
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "getLocation()", "getLocation Error", e);
            showSettingsAlert();
        }
        return location;
    }

    private boolean checkLocation() {
        return location != null;
    }

    private void saveLocationInfo() {
        if (checkLocation()) {
            //  위도 경도 저장
            lat = location.getLatitude();
            lon = location.getLongitude();
        }
    }

    //  GPS 종료
    public void stopUsingGPS() {
        if (checkLocation()) {
            locationManager.removeUpdates(locationListener);
        }
    }

    //  위도값을 가져온다
    public double getLatitude() {
        if (checkLocation())
            lat = location.getLatitude();
        return lat;
    }

    //  경도값을 가져온다
    public double getLongitude() {
        if (checkLocation())
            lon = location.getLongitude();
        return lon;
    }

    //  GPS 나 Network 정보가 사용 중 인지 확인
    public boolean isGetLocation() {
//        getLocation();
        return isGetLocation;
    }

    //  GPS 정보르르 가져오지 못했을 때 "설정" 으로 가는 창을 띄움
    public void showSettingsAlert() {
        if(!showSettingAlert)
            return;

        if (DataStorage.GpsPermissionOn)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            activity.requestPermissions(Permission, RESULT_OK);
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, RESULT_OK);
        }
     /*   if (activity.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PermissionChecker.PERMISSION_GRANTED || activity.checkCallingOrSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PermissionChecker.PERMISSION_GRANTED) {
            return;
        }*/
        logger.log(HLogger.LogType.INFO, "showSettingsAlert()", "showSettingAlert");
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);

        alertDialog.setTitle("GPS 사용 유무 세팅");
        alertDialog.setMessage("GPS 사용 설정이 안되어 있습니다 \n 설정 창으로 이동 하시겠습니까?");
        // Go 를 누를 경우 설정창으로 이동
        alertDialog.setPositiveButton("Go", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                activity.startActivity(intent);
            }
        });

        //  No 를 누를 경우 종료
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        alertDialog.show();
    }

    public void requestPermissions(String[] permissions, int requestCode){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            logger.log(HLogger.LogType.INFO,"SDK is upper than M");
            ActivityCompat.requestPermissions(activity, permissions, requestCode);
//            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, DataStorage.Request.GPS_REQUEST);
        }
    }

    public int checkPermissions(String permission){
        int permissionCheck = ContextCompat.checkSelfPermission(activity, permission);
        return permissionCheck;
    }


}
