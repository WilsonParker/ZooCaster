package com.graction.developer.zoocaster.Data;


import com.graction.developer.zoocaster.Listener.AddressHandleListener;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualitySingleModel;
import com.graction.developer.zoocaster.Model.Response.WeatherModel;
import com.graction.developer.zoocaster.Model.VO.FineDustVO;

import java.util.ArrayList;

/**
 * Created by JeongTaehyun
 */

/*
 * 정적 데이터 모음
 */

public class DataStorage {
    public static boolean GpsPermissionOn = false;
    public static String NowNewAddress                                                  // 현재 주소 (변환 후)
                        , NowOriginAddress;                                             // 현재 주소 (변환 전)
    public static AddressHandleListener addressHandleListener;                          // 주소 검색 후 실행
    public static WeatherModel weatherModel;                                            // 날씨 Object
    public static IntegratedAirQualitySingleModel integratedAirQualitySingleModel;      // 통합대기지수 Object
    public static ArrayList<FineDustVO> fineDustStandard;                               // 미세먼지 규격 List

    public static double Latitude
                        , Longitude;

    public class Path{
        public static final String RESOURCE_URL = "http://192.168.0.8:8101/lumiAssets"; // 서버 Assets 주소

    }

    /*
     * Intent, Bundle 같은 Table 형식에 사용할 Key
     */
    public class Key {
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

    }

    /*
     * 다른 Activity 의 결과를 가져올 때 사용
     */
    public class Request{
        public static final int RESULT_OK = 200
                                , SEARCH_ADDRESS_REQUEST = 0x0003
                                , SEARCH_ADDRESS_OK = 1
                                , SEARCH_ADDRESS_NONE_SELECTED = 0
                                , PERMISSION_REQUEST = 0x0004
                                ;
    }

    /*
     * 알람 실행 시 사용할 Action
     */
    public class Action{
        public static final String RECEIVE_ACTION_SINGLE_ALARM = "com.graction.developer.zoocaster.SINGLE_ALARM"
                                    , RECEIVE_ACTION_MULTI_ALARM = "com.graction.developer.zoocaster.MULTI_ALARM"
                                    , RECEIVE_ACTION_ALARM_START = "com.graction.developer.zoocaster.ALARM_START"
                                    ;
    }

    public static class Date{
        public static final String[] DayOfTheWeek = {"","일", "월", "화", "수", "목", "금", "토"};

    }
}
