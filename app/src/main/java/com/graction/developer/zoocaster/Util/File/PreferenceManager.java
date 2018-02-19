package com.graction.developer.zoocaster.Util.File;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Graction06 on 2018-01-09.
 */

public class PreferenceManager {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson = new Gson();
    private static Context context;
    public static final String PREFERENCE_ALARM = "pref_alarm";

    public static void setContext(Context context) {
        PreferenceManager.context = context;
    }

    public PreferenceManager(String pref) {
        sharedPreferences = context.getSharedPreferences(pref, MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public Object getValue(String key, Object def){
        String[] cls = def.getClass().getName().split("\\.");
        Object result;
        switch(cls[cls.length-1].toUpperCase()){
            case "BOOLEAN":
                result = sharedPreferences.getBoolean(key, (boolean) def);
                break;
            case "INTEGER":
                result = sharedPreferences.getInt(key, (int) def);
                break;
            case "LONG":
                result = sharedPreferences.getLong(key, (long) def);
                break;
            case "FLOAT":
                result = sharedPreferences.getFloat(key, (float) def);
                break;
            case "STRING":
                result = sharedPreferences.getString(key, (String) def);
                break;
            default :
                result = sharedPreferences.getString(key, "");
//                result = gson.fromJson(sharedPreferences.getString(key, ""), def.getClass());
                break;
        }
        return result;
    }

    public boolean setValue(String key, Object value){
        String[] cls = value.getClass().getName().split("\\.");
        switch(cls[cls.length-1].toUpperCase()){
            case "BOOLEAN":
                editor.putBoolean(key, (boolean) value);
                break;
            case "INTEGER":
                editor.putInt(key, (int) value);
                break;
            case "LONG":
                editor.putLong(key, (long) value);
                break;
            case "FLOAT":
                editor.putFloat(key, (float) value);
                break;
            case "STRING":
                editor.putString(key, (String) value);
                break;
            default :
                editor.putString(key, gson.toJson(value));
                break;
        }
        return editor.commit();
    }

    public void deleteKey(String key){
        editor.remove(key);
        editor.commit();
    }
}
