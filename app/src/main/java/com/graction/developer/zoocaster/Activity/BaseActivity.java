package com.graction.developer.zoocaster.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.graction.developer.zoocaster.Util.Log.HLogger;

/**
 * Created by JeongTaehyun
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected HLogger logger;

    @Override
    final protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseInit();
        onCreateMojo(savedInstanceState);
        init();
    }

    /*
     * 모든 자식들이 공통적으로 사용하는 메소드
     * 초기 설정
     */
    private void baseInit(){
        logger = new HLogger(getClass());
    }

    /*
     * 자식 각각에 대한 초기 설정
     */
    protected abstract void init();

    /*
     * OnCreate 메소드를 Override  하고자 할 경우
     * 대신 이 메소드를 Override 하면 된다
     */
    protected void onCreateMojo(@Nullable Bundle savedInstanceState){ }

}
