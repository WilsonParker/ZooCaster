package com.graction.developer.zoocaster.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.graction.developer.zoocaster.Util.Log.HLogger;

public abstract class BaseActivity extends AppCompatActivity {
    protected HLogger logger;

    @Override
    final protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseInit();
        onCreateMojo(savedInstanceState);
        init();
    }

    private void baseInit(){
        logger = new HLogger(getClass());
    }

    protected abstract void init();

    protected void onCreateMojo(@Nullable Bundle savedInstanceState){
        /*
         * You need to override this method When you want to use onCreate Method
         */
    }

}
