package com.graction.developer.zoocaster.UI;


import android.os.Handler;

/**
 * Created by Hare on 2017-07-21.
 */

public class HandlerManager {
    public static HandlerManager handlerManager = new HandlerManager();
    private Handler handler = new Handler();

    public static HandlerManager getInstance() {
        return handlerManager;
    }

    public Handler getHandler() {
        return handler;
    }
}
