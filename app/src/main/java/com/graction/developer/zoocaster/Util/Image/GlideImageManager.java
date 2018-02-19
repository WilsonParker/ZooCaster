package com.graction.developer.zoocaster.Util.Image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;

import java.io.IOException;

/**
 * Created by Hare on 2017-07-19.
 */

/*
    compile 'com.squareup.picasso:picasso:2.5.2'
 */
public class GlideImageManager {
    private static GlideImageManager imageManager = new GlideImageManager();

    //    public static final int BASIC_TYPE = 0x0000, FIT_TYPE = 0x0001, PICTURE_TYPE = 0x0010, THUMBNAIL_TYPE = 0x0011, ICON_TYPE = 0x0100;
    public enum Type {
        BASIC_TYPE, FIT_TYPE, PICTURE_TYPE, THUMBNAIL_TYPE, ICON_TYPE
    }

    public static GlideImageManager getInstance() {
        return imageManager;
    }

    private void init(Context context, ImageView img) {
        Glide.with(context).load("").into(img);
    }

   /* private RequestCreator basicSetting(RequestCreator requestCreator) {
        GlideApp.with
        return requestCreator
//                .error(R.drawable.noimage)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE);
    }

    private RequestCreator requestCreatorSetCase(RequestCreator requestCreator, Type type) {
        requestCreator = basicSetting(requestCreator);
        switch (type) {
            case BASIC_TYPE:
                break;
            case FIT_TYPE:
                requestCreator.fit();
                break;
            case PICTURE_TYPE:
                requestCreator.resize(1280, 720);
                break;
            case THUMBNAIL_TYPE:
                requestCreator.resize(640, 360);
                break;
            case ICON_TYPE:
                requestCreator.resize(320, 180);
                break;
        }
        return requestCreator;
    }*/

    public Bitmap resizeImage(Bitmap bitmap, int newSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int newWidth = 0;
        int newHeight = 0;

        if (width > height) {
            newWidth = newSize;
            newHeight = (newSize * height) / width;
        } else if (width < height) {
            newHeight = newSize;
            newWidth = (newSize * width) / height;
        } else if (width == height) {
            newHeight = newSize;
            newWidth = newSize;
        }

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                width, height, matrix, true);

        return resizedBitmap;
    }


    /*
     * If you have an image, import it download as url if not
     */
    public void bindImage(Context context, ImageView imageView, RequestOptions requestOptions, String path, String name, String url) throws IOException, InterruptedException {
        BaseActivityFileManager baseActivityFileManager = BaseActivityFileManager.getInstance();
        RequestBuilder requestBuilder;
        if (baseActivityFileManager.isExistsAndSaveFile(path, name, url)) {
            requestBuilder = Glide.with(context).load(baseActivityFileManager.getFile(path + name));
        } else {
            requestBuilder = Glide.with(context).load(url);
        }
        if (requestOptions != null)
            requestBuilder.apply(requestOptions);
        requestBuilder.into(imageView);
    }

}


/* private void initGlideGifImageView() {

        showProgressDialog("Loading image...");
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imgSafetyGif, 1);
        Glide
                .with(this)
                .load(GIF_SOURCE_URL)
                .placeholder(R.drawable.img_placeholder_1)
                .error(R.drawable.img_error_1_280_text)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .listener(new RequestListener<String, GlideDrawable>() {

                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        hideProgressDialog();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        hideProgressDialog();

                        GifDrawable gifDrawable = null;
                        Handler handler = new Handler();
                        if (resource instanceof GifDrawable) {
                            gifDrawable = (GifDrawable) resource;

                            int duration = 0;
                            GifDecoder decoder = gifDrawable.getDecoder();
                            for (int i = 0; i < gifDrawable.getFrameCount(); i++) {
                                duration += decoder.getDelay(i);
                            }

                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    SplashScreenActivity.this.finish();
                                }
                            }, (duration + 3000));

                        }

                        return false;
                    }

                })
                .into(imageViewTarget);
    }*/