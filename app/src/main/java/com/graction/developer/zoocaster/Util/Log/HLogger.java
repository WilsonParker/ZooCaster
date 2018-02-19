package com.graction.developer.zoocaster.Util.Log;

import android.util.Log;

/**
 * Created by graction03 on 2017-09-27.
 */

public class HLogger {
    private static final String TAG = "LUMI";
    private static final String NOTI = "############################################################";
    private static final boolean isDebug = true, onError = true;
//    private static final HLogger instance = new HLogger();
    private Class<?> cls;
    private StringBuilder builder;

    public enum LogType {
        INFO, DEBUG, WARN, ERROR
    }

    public HLogger(Class<?> cls) {
        this.cls = cls;
    }

    private void print(LogType logType, String message) {
        if(isDebug){
            switch (logType) {
                case INFO:
                    Log.i(TAG, message);
                    break;
                case DEBUG:
                    Log.d(TAG, message);
                    break;
                case WARN:
                    Log.w(TAG, message);
                    break;
                case ERROR:
                    Log.e(TAG, message);
                    break;
            }
        }else{
            builder = new StringBuilder();
        }
    }

    // Error Method Message
    private void setBuliderData(String... str) {
        builder = new StringBuilder();
        builder.append(NOTI + "\n");
        builder.append("# Class : " + cls.getCanonicalName() + "\n");
        switch (str.length) {
            case 3:
                builder.append("# Error : " + str[2] + "\n");
            case 2:
                builder.append("# Method : " + str[1] + "\n");
            case 1:
                builder.append("# Message : " + str[0] + "\n");
                break;
        }
        builder.append(NOTI + "\n");
    }

    /*
     * builder.append(NOTI+"\n");
     * builder.append("# Class : " + cls.getCanonicalName() + "\n");
     * builder.append("# Method : " + method + "\n");
     * builder.append("# Message : " + message + "\n");
     * builder.append("# Error: " + t.getMessage() + "\n");
     * builder.append(NOTI+"\n");
     */
    public void log(LogType logType, String message) {
        log(logType, "", message);
    }

    public void log(LogType logType, String method, String message) {
        setBuliderData(message, method);
        print(logType, builder.toString());
    }

    public void log(LogType logType, String method, String format, Object... params) {
        setBuliderData(String.format(format, params), method);
        print(logType, builder.toString());
    }

    public void log(LogType logType, String format, Object... params) {
        log(logType, "", format, params);
        print(logType, builder.toString());
    }

    public void log(LogType logType, String message, Throwable t) {
        log(logType, "", message, t);
    }

    public void log(LogType logType, String message, Exception e) {
        log(logType, "", message, e);
    }

    public void log(LogType logType, String method, String message, Throwable t) {
        setBuliderData(message, method, t.getMessage());
        print(logType, builder.toString());
        t.printStackTrace();
    }

    public void log(LogType logType, String method, String message, Exception e) {
        setBuliderData(message, method, e.getMessage());
        print(logType, builder.toString());
        e.printStackTrace();
    }


}
