package com.graction.developer.zoocaster.Util.Weather;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Model.Weather.Weather;
import com.graction.developer.zoocaster.Util.Log.HLogger;

/**
 * Created by graction03 on 2017-09-28.
 */

public class WeatherManager {
    private HLogger logger;
    private static final WeatherManager instance = new WeatherManager();
//    private PicassoImageManager imageManager = PicassoImageManager.getInstance();

    public WeatherManager() {
        logger = new HLogger(getClass());
    }

    public static WeatherManager getInstance() {
        return instance;
    }

    public void setWeatherBackground(Context context, ImageView imageView, Weather weather) {
//        imageManager.loadImage(imageManager.createRequestCreator(context, DataStorage.Path.PATH_ASSET + DataStorage.Path.PATH_BACKGROUND + DataStorage.weathers.get(weather.getId()).getImage(), PicassoImageManager.Type.FIT_TYPE).centerCrop(), imageView);
        Glide.with(context).load(DataStorage.Path.PATH_ASSET + DataStorage.Path.PATH_BACKGROUND + DataStorage.weathers.get(weather.getId())).into(imageView);
    }

}
