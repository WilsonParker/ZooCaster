package com.graction.developer.zoocaster.Fragment;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.graction.developer.zoocaster.Data.SyncObject;
import com.graction.developer.zoocaster.Model.ImageModel;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualityModel;
import com.graction.developer.zoocaster.Model.Response.WeatherModel;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;
import com.graction.developer.zoocaster.Util.GPS.GoogleLocationManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Image.GifManager;
import com.graction.developer.zoocaster.Util.Image.GlideImageManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Weather.WeatherManager;
import com.graction.developer.zoocaster.databinding.FragmentHomeBinding;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.graction.developer.zoocaster.Data.DataStorage.integratedAirQualityModel;
import static com.graction.developer.zoocaster.Data.DataStorage.weatherModel;

public class HomeFragment extends BaseFragment {
    private static final HomeFragment instance = new HomeFragment();
    private static final int SYNC_ID = 0B0001;
    private FragmentHomeBinding binding;
    private WeatherManager weatherManager;
    private GpsManager gpsManager;
    private GoogleLocationManager googleLocationManager;
    private BaseActivityFileManager baseActivityFileManager = BaseActivityFileManager.getInstance();
    private Call call;
    private SyncObject syncObject = SyncObject.getInstance();


    private String background_img_url = "", character_img_url = "", effect_img_url = "";

    public static Fragment getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, null, false);
        return binding.getRoot();
//        return inflater.inflate(R.layout.fragment_home, null);
    }

    @Override
    protected void init(View view) {
        weatherManager = WeatherManager.getInstance();
        gpsManager = new GpsManager(getActivity());

        googleLocationManager = new GoogleLocationManager(address -> {
            logger.log(HLogger.LogType.INFO, "address : " + address);
//                TV_address.setText(address);
            binding.fragmentHomeTVAddress.setText(address);
        });

//        initUI();
        currentWeather();
    }

    private void initUI() {
        InputStream is = null;
        try {
            is = getActivity().getAssets().open("images/background/sunny.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap b = BitmapFactory.decodeStream(is);

        Glide.with(this).load(b).apply(new RequestOptions().centerCrop()).into(binding.fragmentHomeIVBackground);
    }

    @Override
    public void onResume() {
        super.onResume();
        // callIntegratedAirQuality();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (call.isExecuted())
            call.cancel();
    }


    private void currentWeather() {
        call = Net.getInstance().getFactoryIm().selectWeather(gpsManager.getLatitude(), gpsManager.getLongitude());
        try {
            syncObject.addAction(() -> call.enqueue(new Callback<WeatherModel>() {
                @Override
                public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                    if (response.isSuccessful()) {
                        logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "isSuccessful");
                        logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "response : " + response.body());
                        weatherModel = response.body();
                        reloadWeatherInfo();
                        callIntegratedAirQuality();
                    } else {
                        logger.log(HLogger.LogType.WARN, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "is not Successful");
                        logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "response : " + response.body());
                        endThread();
                    }
                }

                @Override
                public void onFailure(Call<WeatherModel> call, Throwable t) {
                    logger.log(HLogger.LogType.ERROR, "onFailure(Call<WeatherModel> call, Throwable t)", "onFailure", t);
                    endThread();
                }
            }), SYNC_ID);

            syncObject.start();
        } catch (InterruptedException e) {
            logger.log(HLogger.LogType.ERROR, "onFailure(Call<WeatherModel> call, Throwable t)", "InterruptedException", e);
        }
    }

    private void reloadWeatherInfo() {
//        gifImageView.startAnimation();
        if (gpsManager.isGetLocation()) {
//            googleLocationManager.getAlarm_address(gpsManager.getLocation());
            if (weatherModel != null) {
                binding.setWeatherModel(weatherModel);
                ImageModel imageModel = weatherModel.getImageModel();
                try {
                    // Background Image
                    GlideImageManager.getInstance().bindImage(getActivity(), binding.fragmentHomeIVBackground, new RequestOptions().centerCrop(), imageModel.getBackground_img_path(), imageModel.getBackground_img_name(), weatherModel.getBackground_img_url());

                    // Character Image
                    String character_path = "/assets/images/character/", character_img = "test2.gif";
//                        baseActivityFileManager.saveImage(imageModel.getCharacter_img_path(), imageModel.getCharacter_img_name(), weatherModel.getCharacter_img_url());
                    baseActivityFileManager.isExistsAndSaveFile(character_path, character_img, "http://192.168.0.8:8101/zoocasterAssets/" + character_path + character_img);
                    /*GifDrawable gifDrawable = new GifDrawable(new BufferedInputStream(new FileInputStream(baseActivityFileManager.getFile(character_path + character_img))));
                    GifManager.getInstance().bindGif(gifDrawable
                            , binding.fragmentHomeIVCharacter
                            , loopNumber -> gifDrawable.stop()
                            , (view) -> gifDrawable.start()
                    );*/

                    setGifAnimate(binding.fragmentHomeIVCharacter2, character_path + character_img);
                } catch (Exception e) {
                    logger.log(HLogger.LogType.ERROR, "reloadWeatherInfo()", "reloadWeatherInfo Error", e);
                }
            }
        } else {
            gpsManager.showSettingsAlert();
        }
    }

    private void callIntegratedAirQuality() {
        call = Net.getInstance().getFactoryIm().selectIntegratedAirQuality(gpsManager.getLatitude(), gpsManager.getLongitude());
        call.enqueue(new Callback<IntegratedAirQualityModel>() {
            @Override
            public void onResponse(Call<IntegratedAirQualityModel> call, Response<IntegratedAirQualityModel> response) {
                if (response.isSuccessful()) {
                    integratedAirQualityModel = response.body();
                    if (integratedAirQualityModel != null) {
                        binding.setIntegratedAirQualityModel(integratedAirQualityModel);
                        binding.setIntegratedAirQualityModelItem(integratedAirQualityModel.getFirstItem());
                        logger.log(HLogger.LogType.INFO, "void callIntegratedAirQuality()", "response body: " + integratedAirQualityModel);
                        endThread();
                    }
                }
            }

            @Override
            public void onFailure(Call<IntegratedAirQualityModel> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "callIntegratedAirQuality()", "callIntegratedAirQuality onFailure", t);
                endThread();
            }
        });
    }

    private void setGifAnimate(GifImageView gifImageView, String path) throws IOException {
        GifDrawable gifDrawable = new GifDrawable(new BufferedInputStream(new FileInputStream(baseActivityFileManager.getFile(path))));
        GifManager.getInstance().bindGif(gifDrawable
                , gifImageView
                , loopNumber -> gifDrawable.stop()
                , (view) -> gifDrawable.start()
        );
    }

    private void endThread(){
        try {
            syncObject.end(SYNC_ID);
        } catch (InterruptedException e) {
            logger.log(HLogger.LogType.ERROR, "endThread()", "endThread InterruptedException", e);
        }
    }
}
