package com.graction.developer.zoocaster.DataBase;

import com.graction.developer.zoocaster.Model.Item.AlarmItem;

import java.util.ArrayList;

/**
 * Created by Graction06 on 2018-01-25.
 */

public class DataBaseStorage {
//    public static AlarmData alarmData;
    public static DataBaseHelper alarmDataBaseHelper;

    public static ArrayList<AlarmItem> alarmList;

    public static final String DATABASE_NAME = "caster.db";

    public class Table{
        public static final String TABLE_ALARM = "ALARM";
    }

    public class Column{
        private static final String COLUMN_SEPARATE = "_"
                                    , COLUMN_ALARM = Table.TABLE_ALARM+COLUMN_SEPARATE
                                    ;
        public static final String COLUMN_ALARM_INDEX = COLUMN_ALARM+"INDEX"
                                    , COLUMN_ALARM_ADDRESS= COLUMN_ALARM+"ADDRESS"
//                                    , COLUMN_ALARM_PLACE_NAME= COLUMN_ALARM+"PLACE_NAME"
//                                    , COLUMN_ALARM_PLACE_ADDRESS = COLUMN_ALARM+"PLACE_ADDRESS"
                                    , COLUMN_ALARM_MEMO= COLUMN_ALARM+"MEMO"
                                    , COLUMN_ALARM_DAYS= COLUMN_ALARM+"DAYS"
                                    , COLUMN_ALARM_HOUROFDAY= COLUMN_ALARM+"HOUROFDAY"
                                    , COLUMN_ALARM_MINUTE= COLUMN_ALARM+"MINUTE"
                                    , COLUMN_ALARM_VOLUME= COLUMN_ALARM+"VOLUME"
                                    , COLUMN_ALARM_RUNNING_STATE= COLUMN_ALARM+"RUNNING_STATE"
                                    , COLUMN_ALARM_IS_SPEAKER= COLUMN_ALARM+"ISSPEAKER"
                                    ;
    }

    public class Version{
        public static final int TABLE_ALARM_VERSION = 3
                                    ;
    }

}
