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
            /*
            *
            *   Alarm Table
            * */
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

            /*
            *
            *   Favorite Table
            * */
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
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DataBaseStorage.Table.TABLE_ALARM));
        db.execSQL(String.format("DROP TABLE IF EXISTS %s", DataBaseStorage.Table.TABLE_FAVORITE));
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

    public void insertIFNull(String table, Map selectWhereCause, Object obj) {
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(String.format("SELECT count(*) FROM %s where %s", table, ObjectParserManager.getInstance().mapToString(selectWhereCause)), null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if (count == 0)
                insert(table, obj);
            db.close();
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "void insertIFNull(String table, String selectWhereCause, Object obj)", e);
        }
    }

    public void select(String query, OnRawQuery onRawQuery) {
        try {
            logger.log(HLogger.LogType.INFO, "void select(String query, OnRawQuery onRawQuery)", query);
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            onRawQuery.getData(cursor);
            db.close();
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "void select(String query, OnRawQuery onRawQuery)", e);
        }
    }

    public <T> T select(String query, Class cls) {
        T t = null;
        try {
            logger.log(HLogger.LogType.INFO, "<T> T select(String query, Class cls)", query);
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
            logger.log(HLogger.LogType.INFO, "<T extends Object> List<T> selectList(String query, Class cls)", query);
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

    public <T> boolean selectIsNull(String query, Class cls) {
        T t = null;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            t = DataBaseParserManager.getInstance().parseObject(cursor, cls);
            db.close();
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "selectIsNull(String query, Class cls)", e);
        }
        return (t == null);
    }

    public boolean selectIsNull(String table, String selection, String[] selectionArgs) {
        boolean result = false;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.query(table, new String[]{"count(*)"}, selection, selectionArgs, null, null, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            logger.log(HLogger.LogType.INFO, "boolean selectIsNull(String table, String selection, String[] selectionArgs)", "count : " + count);

            db.close();
            if (count == 0)
                result = true;
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "boolean selectIsNull(String table, String selection, String[] selectionArgs)", e);
        }
        return result;
    }

    public boolean selectIsNull(String table, Map map) {
        boolean result = false;
        try {
            String query = String.format("SELECT count(*) FROM %s WHERE %s", table, ObjectParserManager.getInstance().mapToString(map));
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            logger.log(HLogger.LogType.INFO, "boolean selectIsNull(String table, Map map)", query);
            logger.log(HLogger.LogType.INFO, "boolean selectIsNull(String table, Map map)", "count : " + count);

            db.close();
            if (count == 0)
                result = true;
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "boolean selectIsNull(String table, Map map)", e);
        }
        return result;
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

    public static void createHelper(Context context) {
        if (DataBaseStorage.dataBaseHelper == null)
            DataBaseStorage.dataBaseHelper = new DataBaseHelper(context, DATABASE_NAME, null, DataBaseStorage.Version.TABLE_VERSION);
    }

    public interface OnRawQuery {
        void getData(Cursor cursor);
    }
}
