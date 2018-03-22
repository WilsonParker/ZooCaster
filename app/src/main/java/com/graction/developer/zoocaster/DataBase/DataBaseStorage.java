package com.graction.developer.zoocaster.DataBase;

import com.graction.developer.zoocaster.Model.DataBase.FavoriteTable;
import com.graction.developer.zoocaster.Model.Item.AlarmItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JeongTaehyun
 */

/*
 * Database 관련 정적 데이터 모음
 */

public class DataBaseStorage {
    public static DataBaseHelper dataBaseHelper;

    public static ArrayList<AlarmItem> alarmList;
    public static List<FavoriteTable> favoriteTables;

    public static final String DATABASE_NAME = "caster.db";

    /*
     * Table 이름
     */
    public class Table{
        public static final String TABLE_ALARM = "ALARM"
                                    , TABLE_FAVORITE = "FAVORITE"
                                    ;
    }

    public class Column{
        private static final String COLUMN_SEPARATE = "_"
                                    , COLUMN_ALARM = Table.TABLE_ALARM+COLUMN_SEPARATE
                                    , COLUMN_FAVORITE = Table.TABLE_FAVORITE+COLUMN_SEPARATE
                                    ;
        public static final String COLUMN_ALARM_INDEX = COLUMN_ALARM+"INDEX"                                // 알람 Index
                                    , COLUMN_ALARM_NEW_ADDRESS = COLUMN_ALARM+"NEW_ADDRESS"                 // 알람 변환 후 주소
                                    , COLUMN_ALARM_ORIGIN_ADDRESS = COLUMN_ALARM+"ORIGIN_ADDRESS"           // 알람 변환 전 주소
                                    , COLUMN_ALARM_MEMO = COLUMN_ALARM+"MEMO"                               // 알람 메모
                                    , COLUMN_ALARM_DAYS = COLUMN_ALARM+"DAYS"                               // 알람 요일
                                    , COLUMN_ALARM_HOUROFDAY = COLUMN_ALARM+"HOUROFDAY"                     // 알람 시간
                                    , COLUMN_ALARM_MINUTE= COLUMN_ALARM+"MINUTE"                            // 알람 분
                                    , COLUMN_ALARM_VOLUME = COLUMN_ALARM+"VOLUME"                           // 알람 볼륨
                                    , COLUMN_ALARM_RUNNING_STATE = COLUMN_ALARM+"RUNNING_STATE"             // 알람 On/Off
                                    , COLUMN_ALARM_IS_SPEAKER = COLUMN_ALARM+"ISSPEAKER"                    // 알람 스피커 여부

                                    , COLUMN_FAVORITE_ORIGIN_ADDRESS = COLUMN_FAVORITE+"ORIGIN_ADDRESS"     // 즐겨찾기 변환 전 주소
                                    , COLUMN_FAVORITE_NEW_ADDRESS = COLUMN_FAVORITE+"NEW_ADDRESS"           // 즐겨찾기 변환 후 주소
                                    , COLUMN_FAVORITE_SET_TIME = COLUMN_FAVORITE+"SET_TIME"                 // 즐겨찾기 설정 시간
                                    ;
    }

    /*
     * Database Version
     */
    public class Version{
        public static final int TABLE_VERSION = 4
                                    ;
    }

}
