package com.graction.developer.zoocaster.Util.Image;

import android.view.View;
import android.widget.ImageView;

import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by JeongTaehyun on 2018-01-16.
 */

/*
 * Gif 설정
 */
public class GifManager {
    private static final GifManager instance = new GifManager();

    public static GifManager getInstance() {
        return instance;
    }

    public ImageView bindGif(GifDrawable gifDrawable, ImageView imageView, AnimationListener animationListener, View.OnClickListener onCLickListener) throws IOException {
        gifDrawable.addAnimationListener(animationListener);
        imageView.setImageDrawable(gifDrawable);
        imageView.setOnClickListener(onCLickListener);
        return imageView;
    }

    public void setGifAnimate(GifImageView gifImageView, String path) throws IOException {
        GifDrawable gifDrawable = new GifDrawable(new BufferedInputStream(new FileInputStream(BaseActivityFileManager.getInstance().getFile(path))));
        GifManager.getInstance().bindGif(gifDrawable
                , gifImageView
                , loopNumber -> gifDrawable.stop()
                , (view) -> gifDrawable.start()
        );
    }
}
