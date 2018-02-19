package com.graction.developer.zoocaster.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.graction.developer.zoocaster.Util.Log.HLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Graction06 on 2018-01-25.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private static final HLogger logger = new HLogger(DataBaseHelper.class);
    private SQLiteDatabase db;

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    // DB를 새로 생성할 때 호출되는 함수
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(
                    String.format(
                            "CREATE TABLE %s (" +
                                    "%s INTEGER PRIMARY KEY AUTOINCREMENT," +
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
                            , DataBaseStorage.Column.COLUMN_ALARM_ADDRESS
                            , DataBaseStorage.Column.COLUMN_ALARM_MEMO
                            , DataBaseStorage.Column.COLUMN_ALARM_DAYS
                            , DataBaseStorage.Column.COLUMN_ALARM_HOUROFDAY
                            , DataBaseStorage.Column.COLUMN_ALARM_MINUTE
                            , DataBaseStorage.Column.COLUMN_ALARM_VOLUME
                            , DataBaseStorage.Column.COLUMN_ALARM_RUNNING_STATE
                            , DataBaseStorage.Column.COLUMN_ALARM_IS_SPEAKER
                    )
            );
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "onCreate(SQLiteDatabase)", e);
        }
    }

    private void init(){
        db = getWritableDatabase();
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DataBaseStorage.Table.TABLE_ALARM));
        onCreate(getWritableDatabase());
    }

    // DB 업그레이드를 위해 Version 이 변경될 때 호출되는 함수
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*logger.log(HLogger.LogType.ERROR, "onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)", "Database Upgrade");
        String sql = String.format("ALTER TABLE %s ADD %s", DataBaseStorage.Table.TABLE_ALARM, DataBaseStorage.Column.COLUMN_ALARM_RUNNING_STATE + " INTEGER UNSIGNED DEFAULT 0");
        db.execSQL(sql);*/
    }

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
            logger.log(HLogger.LogType.INFO, "insert(String, Object)", query);
            insert(query);
            db.close();
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "insert(String, Object)", e);
        }
    }

    public <T> T select(String query, Class cls) {
        T t = null;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            t = DataBaseParserManager.getInstance().parseObject(cursor, cls);
            db.close();
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "selectList(String query, Class cls)", e);
        }
        return t;
    }

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

    public boolean update(String table, ContentValues contentValues, String whereClause, String[] whereArgs) {
        boolean result = false;
        try {
//             contentValues.put("age", 11);
            db = getWritableDatabase();
            db.update(table, contentValues, whereClause, whereArgs);
            db.close();
            result = true;
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "update(String table, ContentValues contentValues, String whereClause, String[] whereArgs)", e);
        }
        return result;
    }

    public boolean delete(String table, String whereClause, String[] whereArgs) {
        boolean result = false;
        try {
            db = getWritableDatabase();
            db.delete(table, whereClause, whereArgs);
            db.close();
            result = true;
//        db.delete("tablename","id=? and name=?",new String[]{"1","jack"});
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "delete(String table, String whereClause, String[] whereArgs)", e);
        }
        return result;
    }
}
