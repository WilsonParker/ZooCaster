package com.graction.developer.zoocaster.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Parser.ObjectParserManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.graction.developer.zoocaster.DataBase.DataBaseStorage.DATABASE_NAME;

/**
 * Created by JeongTaehyun
 */

/*
 * Database Helper
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final HLogger logger = new HLogger(DataBaseHelper.class);
    private SQLiteDatabase db;

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /*
     * DB를 새로 생성할 때 호출
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            /*
             *   Alarm Table
             */
            db.execSQL(
                    String.format(
                            "CREATE TABLE %s (" +
                                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                                    "%s TEXT," +
                                    "%s TEXT," +
                                    "%s TEXT," +
                                    "%s TEXT," +
                                    "%s INTEGER UNSIGNED," +
                                    "%s INTEGER UNSIGNED," +
                                    "%s INTEGER UNSIGNED," +
                                    "%s TINYINT UNSIGNED," +
                                    "%s TINYINT UNSIGNED" +
                                    ");"
                            , DataBaseStorage.Table.TABLE_ALARM
                            , DataBaseStorage.Column.COLUMN_ALARM_INDEX
                            , DataBaseStorage.Column.COLUMN_ALARM_NEW_ADDRESS
                            , DataBaseStorage.Column.COLUMN_ALARM_ORIGIN_ADDRESS
                            , DataBaseStorage.Column.COLUMN_ALARM_MEMO
                            , DataBaseStorage.Column.COLUMN_ALARM_DAYS
                            , DataBaseStorage.Column.COLUMN_ALARM_HOUROFDAY
                            , DataBaseStorage.Column.COLUMN_ALARM_MINUTE
                            , DataBaseStorage.Column.COLUMN_ALARM_VOLUME
                            , DataBaseStorage.Column.COLUMN_ALARM_RUNNING_STATE
                            , DataBaseStorage.Column.COLUMN_ALARM_IS_SPEAKER
                    )
            );

            /*
             *   Favorite Table
             */
            db.execSQL(
                    String.format(
                            "CREATE TABLE %s (" +
                                    "%s TEXT PRIMARY KEY," +    // origin address
                                    "%s TEXT," +    // new address
                                    "%s TEXT" +                // setTime
                                    ");"
                            , DataBaseStorage.Table.TABLE_FAVORITE
                            , DataBaseStorage.Column.COLUMN_FAVORITE_ORIGIN_ADDRESS
                            , DataBaseStorage.Column.COLUMN_FAVORITE_NEW_ADDRESS
                            , DataBaseStorage.Column.COLUMN_FAVORITE_SET_TIME
                    )
            );
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "onCreate(SQLiteDatabase)", e);
        }
    }

    private void init() {
        db = getWritableDatabase();
        onCreate(getWritableDatabase());
    }

    // DB 업그레이드를 위해 Version 이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    /*
     * Insert query 실행
     */
    public void insert(String query) {
        try {
            db = getWritableDatabase();
            db.execSQL(query);
            db.close();
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "insert(String)", e);
            init();
        }
    }

    public void insert(String table, Object obj) {
        try {
            String[] val = DataBaseParserManager.getInstance().fieldValueToString(obj, true);
            String query = String.format("INSERT INTO %s(%s) values(%s)", table, val[0], val[1]);
            insert(query);
            db.close();
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "insert(String, Object)", e);
        }
    }

    /*
     * Select query 실행
     * List 형태로 return
     */
    public <T extends Object> List<T> selectList(String query, Class cls) {
        List<T> list = new ArrayList<>();
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()) {
                list.add(DataBaseParserManager.getInstance().parseObject(cursor, cls));
            }
            db.close();
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "selectList(String query, Class cls)", e);
        }
        return list;
    }

    /*
     * Select 실행
     * Null 여부 return
     */
    public boolean selectIsNull(String table, String selection, String[] selectionArgs) {
        boolean result = false;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.query(table, new String[]{"count(*)"}, selection, selectionArgs, null, null, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            db.close();
            if (count == 0)
                result = true;
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "boolean selectIsNull(String table, String selection, String[] selectionArgs)", e);
        }
        return result;
    }

    /*
     * Update query 실행
     */
    public boolean update(String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
        boolean result = false;
        try {
            db = getWritableDatabase();
            db.update(table, contentValues, whereClause, whereArgs);
            db.close();
            result = true;
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "update(String table, ContentValues contentValues, String whereClause, String[] whereArgs)", e);
        }
        return result;
    }

    /*
     * Delete query 실행
     */
    public boolean delete(String table, String whereClause, String[] whereArgs) {
        boolean result = false;
        try {
            db = getWritableDatabase();
            db.delete(table, whereClause, whereArgs);
            db.close();
            result = true;
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "delete(String table, String whereClause, String[] whereArgs)", e);
        }
        return result;
    }

    /*
     * Helper 생성
     */
    public static void createHelper(Context context) {
        if (DataBaseStorage.dataBaseHelper == null)
            DataBaseStorage.dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, DataBaseStorage.Version.TABLE_VERSION);
    }
}
