package com.graction.developer.zoocaster.Data;


import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualityModel;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualitySingleModel;
import com.graction.developer.zoocaster.Model.Response.SimpleResponseModel;
import com.graction.developer.zoocaster.Model.Response.WeatherModel;
import com.graction.developer.zoocaster.Model.VO.FineDustVO;
import com.graction.developer.zoocaster.Model.Xml.Weather;
import com.graction.developer.zoocaster.Util.GPS.GoogleLocationManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Hare on 2017-07-16.
 */

public class DataStorage {
//    public static double Location_Longitude, Location_Latitude;
//    public static FragmentEventListener S_FragmentEventListener;

    public static boolean GpsPermissionOn = false;
    public static String NowNewAddress, NowOriginAddress;
//    public static GpsManager gpsManager;
    public static GoogleLocationManager googleLocationManager;
    public static AddressHandleListener addressHandleListener;
    public static WeatherModel weatherModel;
    public static IntegratedAirQualitySingleModel integratedAirQualitySingleModel;
    public static Map<Integer, Weather> weathers;
    public static ArrayList<FineDustVO> fineDustStandard;

    public static double Latitude, Longitude;

    public class OpenWeather {
        public static final String
                PARAM_KEY_APPID = "appid",
                PARAM_KEY_SEARCH = "q",
                PARAM_KEY_MDOE = "mode",
                PARAM_KEY_LAT = "lat",
                PARAM_KEY_LON = "lon";

        /*
        public static final String API_KEY = "5a165ea40110d89c6a33e762fb7501c6",
                API_URL = "http://api.openweathermap.org",
                RESOURCE_MODE = "json";
        */
    }

    public class Path{
        public static final String PATH_ASSET = "file:///android_asset/"
                                , PATH_XML = "xml/"
                                , PATH_IMAGES= "images/"
                                , PATH_BACKGROUND= PATH_IMAGES+"background/"
                                , RESOURCE_URL = "http://192.168.0.8:8101/lumiAssets"
                                ;

    }

    public class Key{
        public static final String KEY_BUNDLE = "bundle"

                                    , KEY_WEEK = "week"
                                    , KEY_ALARM_ITEM = "alarm_item"
                                    , KEY_ALARM_INDEX = "alarm_index"
                                    , KEY_ADDRESS = "address"
                                    , KEY_NEW_ADDRESS = "new_address"
                                    , KEY_ORIGIN_ADDRESS = "origin_address"
                                    , KEY_ADDRESS_ITEM = "address_item"
                                    , KEY_NOTIFICATION_ITEM = "notification_item"

                                    , KEY_LATITUDE= "lat"
                                    , KEY_LONGITUDE= "lon"

                                    ;

        public static final int KEY_SEEKBAR_ENABLED = 0x0001
                                ;

    }

    public class Request{
        public static final int PLACE_PICKER_REQUEST = 0x0001

                                , RESULT_OK = 200

                                , GOOGLE_PLACE_REQUEST = 0x0002
                                , GOOGLE_PLACE_OK = -1
                                , GOOGLE_PLACE_NONE_SELECTED = 0
                                , SEARCH_ADDRESS_REQUEST = 0x0003
                                , SEARCH_ADDRESS_OK = 1
                                , SEARCH_ADDRESS_NONE_SELECTED = 0
                                , PERMISSION_REQUEST = 0x0004

                                ;
    }

    public class Action{
        public static final String RECEIVE_ACTION_SINGLE_ALARM = "com.graction.developer.zoocaster.SINGLE_ALARM"
                                    , RECEIVE_ACTION_MULTI_ALARM = "com.graction.developer.zoocaster.MULTI_ALARM"
                                    , RECEIVE_ACTION_ALARM_START = "com.graction.developer.zoocaster.ALARM_START"

                            ;
    }

    public class Preference{
        public static final String PREFERENCE_ALARM_DATA= "alarm_dataT"

                ;
    }

    public static class Date{
        public static final String[] DayOfTheWeek = {"","일", "월", "화", "수", "목", "금", "토"};

    }
}
