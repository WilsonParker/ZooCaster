package com.graction.developer.zoocaster.Util.Image;

import android.content.res.AssetManager;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;

/**
 * Created by Graction06 on 2018-01-16.
 */

public class GifManager {
    private static final GifManager instance = new GifManager();

    public static GifManager getInstance() {
        return instance;
    }

    public ImageView bindGif(AssetManager assetManager, String file, ImageView imageView) throws IOException {
        GifDrawable gifDrawable = new GifDrawable(assetManager, file);
        return bindGif(assetManager, file, imageView, new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {
                gifDrawable.stop();
            }

        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifDrawable.start();
            }
        });
    }

    public ImageView bindGif(AssetManager assetManager, String file, ImageView imageView, AnimationListener animationListener) throws IOException {
        GifDrawable gifDrawable = new GifDrawable(assetManager, file);
        return bindGif(assetManager, file, imageView, animationListener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifDrawable.start();
            }
        });
    }

    public ImageView bindGif(AssetManager assetManager, String file, ImageView imageView, AnimationListener animationListener, View.OnClickListener onCLickListener) throws IOException {
        GifDrawable gifDrawable = new GifDrawable(assetManager, file);
        gifDrawable.addAnimationListener(animationListener);
        imageView.setImageDrawable(gifDrawable);
        imageView.setOnClickListener(onCLickListener);
        return imageView;
    }

    public ImageView bindGif(String file, ImageView imageView) throws IOException {
        GifDrawable gifDrawable = new GifDrawable(file);
        return bindGif(file, imageView, new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {
                gifDrawable.stop();
            }

        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifDrawable.start();
            }
        });
    }

    public ImageView bindGif(String file, ImageView imageView, AnimationListener animationListener) throws IOException {
        GifDrawable gifDrawable = new GifDrawable(file);
        imageView.setImageDrawable(gifDrawable);
        return bindGif(file, imageView, animationListener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifDrawable.start();
            }
        });
    }

    public ImageView bindGif(String file, ImageView imageView, AnimationListener animationListener, View.OnClickListener onCLickListener) throws IOException {
        GifDrawable gifDrawable = new GifDrawable(file);
        gifDrawable.addAnimationListener(animationListener);
        imageView.setImageDrawable(gifDrawable);
        imageView.setOnClickListener(onCLickListener);
        return imageView;
    }

    public ImageView bindGif(GifDrawable gifDrawable, ImageView imageView) throws IOException {
        return bindGif(gifDrawable, imageView, new AnimationListener() {
            @Override
            public void onAnimationCompleted(int loopNumber) {
                gifDrawable.stop();
            }

        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifDrawable.start();
            }
        });
    }

    public ImageView bindGif(GifDrawable gifDrawable, ImageView imageView, AnimationListener animationListener) throws IOException {
        imageView.setImageDrawable(gifDrawable);
        return bindGif(gifDrawable, imageView, animationListener, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifDrawable.start();
            }
        });
    }

    public ImageView bindGif(GifDrawable gifDrawable, ImageView imageView, AnimationListener animationListener, View.OnClickListener onCLickListener) throws IOException {
        gifDrawable.addAnimationListener(animationListener);
        imageView.setImageDrawable(gifDrawable);
        imageView.setOnClickListener(onCLickListener);
        return imageView;
    }
}
