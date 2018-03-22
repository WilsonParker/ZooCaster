package com.graction.developer.zoocaster.Util.Image;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;

import java.io.IOException;

/**
 * Create by JeongTaehyun
 */

/*
 * Glide 를 이용하여 Image 설정
 */
public class GlideImageManager {
    private static GlideImageManager imageManager = new GlideImageManager();

    public static GlideImageManager getInstance() {
        return imageManager;
    }

    /*
     * If you have an image, import it download as url if not
     */
    public void bindImage(Context context, ImageView imageView, RequestOptions requestOptions, String path, String name, String url) throws IOException, InterruptedException {
        bindImage(context, imageView, requestOptions, path, name, url, BaseActivityFileManager.FileType.Image);
    }

    public void bindImage(Context context, ImageView imageView, RequestOptions requestOptions, String path, String name, String url, BaseActivityFileManager.FileType fileType) throws IOException, InterruptedException {
        BaseActivityFileManager baseActivityFileManager = BaseActivityFileManager.getInstance();
        RequestBuilder requestBuilder;
        if (baseActivityFileManager.isExistsAndSaveFile(path, name, url, fileType)) {
            switch (fileType) {
                case ByteArray:
                    requestBuilder = Glide.with(context).load(baseActivityFileManager.getByteArrayFromStorage(path + name));
                    break;
                case File:
                case Image:
                default:
                    requestBuilder = Glide.with(context).load(baseActivityFileManager.getFile(path + name));
                    break;
            }
        } else {
            requestBuilder = Glide.with(context).load(url);
        }
        if (requestOptions != null)
            requestBuilder.apply(requestOptions);
        requestBuilder.into(imageView);
    }
}