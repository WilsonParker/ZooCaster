package com.graction.developer.zoocaster.Util.System;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import java.io.IOException;

/**
 * Created by Graction06 on 2018-02-08.
 * Vibrate & Speaker Manager
 */

public class VSManager {
    private static final VSManager instance = new VSManager();
    private Vibrator vibrator;
    public static final int REPEAT_INFINITE = 0, REPEAT_NONE = -1;
    public static final long[] PATTERN_1 = {100, 300, 100, 500, 100, 700};
    /*
        대기,진동,대기,진동,....
        짝수 인덱스 : 대기시간
        홀수 인덱스 : 진동시간
        vibrator.vibrate(pattern, // 진동 패턴을 배열로
        -1);     // 반복 인덱스
        0 : 무한반복, -1: 반복없음,
        양의정수 : 진동패턴배열의 해당 인덱스부터 진동 무한반복
        출처:http://bitsoul.tistory.com/129 [Happy Programmer~]
    */

    private MediaPlayer mediaPlayer;
    private AssetFileDescriptor assetFileDescriptor;

    public static VSManager getInstance() {
        return instance;
    }

    /*
    *  Vibrator
    * */
    private void initVibrator(Context context){
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate(Context context, int millisecond) {
        initVibrator(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(millisecond, 10));
        } else {
            vibrator.vibrate(millisecond);
        }
    }

    public void vibrate(Context context, long[] pattern, int repeat) {
        initVibrator(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(pattern, repeat));
        } else {
            vibrator.vibrate(pattern, repeat);
        }
    }

    public void cancelVibrate() {
        vibrator.cancel();
    }

    /*
    * Speaker
    * */

    private void initSpeaker(Context context) throws IOException {
        mediaPlayer = new MediaPlayer();
        assetFileDescriptor = context.getAssets().openFd("kt.mp3");
    }

    public void speak(Context context,int volume) throws IOException {
        initSpeaker(context);
//            mediaPlayer = MediaPlayer.create(this, R.raw.kt);
//            AssetFileDescriptor assetFileDescriptor = getResources().openRawResourceFd(R.raw.kt);
        mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
        mediaPlayer.setVolume(volume, volume);
        assetFileDescriptor.close();

        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    public void cancelSpeaker() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
