package com.graction.developer.zoocaster.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Model.ImageModel;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualitySingleModel;
import com.graction.developer.zoocaster.Model.Response.SimpleResponseModel;
import com.graction.developer.zoocaster.Model.Response.WeatherModel;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.ProgressManager;
import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;
import com.graction.developer.zoocaster.Util.GPS.AddressManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Image.GifManager;
import com.graction.developer.zoocaster.Util.Image.GlideImageManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Parser.AddressParser;
import com.graction.developer.zoocaster.Util.Weather.WeatherManager;
import com.graction.developer.zoocaster.databinding.FragmentHomeBinding;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.graction.developer.zoocaster.Data.DataStorage.Latitude;
import static com.graction.developer.zoocaster.Data.DataStorage.Longitude;
import static com.graction.developer.zoocaster.Data.DataStorage.googleLocationManager;
import static com.graction.developer.zoocaster.Data.DataStorage.integratedAirQualitySingleModel;
import static com.graction.developer.zoocaster.Data.DataStorage.weatherModel;

public class HomeFragment extends BaseFragment {
    private static final HomeFragment instance = new HomeFragment();
    private static final int SYNC_ID = 0B0001;
    private GpsManager gpsManager;
    private FragmentHomeBinding binding;
    private ProgressManager progressManager;
    private BaseActivityFileManager baseActivityFileManager = BaseActivityFileManager.getInstance();

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
        gpsManager = new GpsManager(getActivity());
        progressManager = new ProgressManager(getActivity());
        progressManager.alertShow();
        binding.fragmentHomeSwipe.setOnRefreshListener(() -> {
            currentWeather();
        });
        currentWeather();
    }

    @Override
    public void onResume() {
        super.onResume();
        // callIntegratedAirQuality();
    }

    private void currentWeather() {
        Call call = Net.getInstance().getFactoryIm().selectWeather(Latitude, Longitude);
        setCall(call);
        addAction(() -> call.enqueue(new Callback<WeatherModel>() {
            @Override
            public void onResponse(Call<WeatherModel> call, Response<WeatherModel> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "isSuccessful");
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "response : " + response.body());
                    weatherModel = response.body();
                    reloadWeatherInfo();
                    if (integratedAirQualitySingleModel == null)
                        callIntegratedAirQuality(Latitude, Longitude, new Callback<IntegratedAirQualitySingleModel>() {
                            @Override
                            public void onResponse(Call<IntegratedAirQualitySingleModel> call, Response<IntegratedAirQualitySingleModel> response) {
                                if (response.isSuccessful()) {
                                    integratedAirQualitySingleModel = response.body();
                                    setBindingIntegratedAirQualityModel();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                logger.log(HLogger.LogType.ERROR, "callIntegratedAirQuality()", "callIntegratedAirQuality onFailure", t);
                                end();
                            }
                        });
                    setBindingIntegratedAirQualityModel();
                } else {
                    logger.log(HLogger.LogType.WARN, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "is not Successful");
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "response : " + response.body());
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "response : " + response.message());
                    logger.log(HLogger.LogType.INFO, "onResponse(Call<WeatherModel> call, Response<WeatherModel> response)", "response : " + response.toString());
                    end();
                }
            }

            @Override
            public void onFailure(Call<WeatherModel> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onFailure(Call<WeatherModel> call, Throwable t)", "onFailure", t);
                end();
            }
        }), SYNC_ID);
        startSync();
    }

    private void reloadWeatherInfo() {
        setBindingIntegratedAirQualityModel();
//        gifImageView.startAnimation();
        if (gpsManager.isGetLocation()) {
//            googleLocationManager.getAddress(gpsManager.getLocation());
            Net.getInstance().getFactoryIm().getAddressFromLocation(Latitude, Longitude).enqueue(new Callback<SimpleResponseModel<String>>() {
                @Override
                public void onResponse(Call<SimpleResponseModel<String>> call, Response<SimpleResponseModel<String>> response) {
                    if(response.isSuccessful()){
                        String address = response.body().getResult();
                        DataStorage.addressHandleListener.setAddress(AddressParser.getInstance().parseAddress(address), address);
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponseModel<String>> call, Throwable t) {
                    logger.log(HLogger.LogType.ERROR, "onFailure(Call<SimpleResponseModel<String>> call, Throwable t)", "getAddressFromLocation onFailure", t);
                    end();
                }
            });

//            googleLocationManager.getAlarm_address(gpsManager.getLocation());
            if (weatherModel != null) {
                binding.setWeatherModel(weatherModel);
                ImageModel imageModel = weatherModel.getImageModel();
                try {
                    // Background Image
                    GlideImageManager.getInstance().bindImage(getContext(), binding.fragmentHomeIVBackground, new RequestOptions().centerCrop(), imageModel.getBackground_img_path(), imageModel.getBackground_img_name(), weatherModel.getBackground_img_url());
                    GlideImageManager.getInstance().bindImage(getContext(), binding.fragmentHomeIVIconWeather, new RequestOptions().centerCrop(), weatherModel.getWeatherCategoryVO().getWeatherCategory_icon_path(), weatherModel.getWeatherCategoryVO().getWeatherCategory_icon_name(), weatherModel.getWeatherIcon_img_url());
                    // Character Image
//                    String character_path = "/assets/images/character/", character_img = "test2.gif";
                    String character_path = imageModel.getCharacter_img_path(), character_img = imageModel.getCharacter_img_name(),
                            effect_path = imageModel.getEffect_img_path(), effect_img = imageModel.getEffect_img_name();
//                    baseActivityFileManager.saveImage(imageModel.getCharacter_img_path(), imageModel.getCharacter_img_name(), weatherModel.getCharacter_img_url());
//                    baseActivityFileManager.isExistsAndSaveFile(character_path, character_img, "http://192.168.0.8:8101/lumi" + character_path + character_img);
                    logger.log(HLogger.LogType.INFO, "reloadWeatherInfo()", "%s : %s : %s", character_path, character_img, weatherModel.getCharacter_img_url());
                    baseActivityFileManager.isExistsAndSaveFile(character_path, character_img, weatherModel.getCharacter_img_url(), BaseActivityFileManager.FileType.ByteArray);
                    GifManager.getInstance().setGifAnimate(binding.fragmentHomeIVCharacter, character_path + character_img);

//                    baseActivityFileManager.isExistsAndSaveFile(effect_path, effect_img, weatherModel.getEffect_img_url(), BaseActivityFileManager.FileType.ByteArray);
//                    binding.fragmentHomeGVEffect.setImageDrawable(baseActivityFileManager.getDrawableFromAssets(effect_path+effect_img));
                    GlideImageManager.getInstance().bindImage(getContext(), binding.fragmentHomeGVEffect, new RequestOptions().centerCrop(), effect_path, effect_img, weatherModel.getEffect_img_url(), BaseActivityFileManager.FileType.ByteArray);

                } catch (Exception e) {
                    logger.log(HLogger.LogType.ERROR, "reloadWeatherInfo()", "reloadWeatherInfo Error", e);
                } finally {
                    end();
                }
            }
        } else {
            gpsManager.showSettingsAlert();
        }
    }

    private void setBindingIntegratedAirQualityModel() {
        if (integratedAirQualitySingleModel != null) {
            // binding.setIntegratedAirQualityModel(integratedAirQualitySingleModel);
            binding.setIntegratedAirQualityModelItem(integratedAirQualitySingleModel.getItem());
            logger.log(HLogger.LogType.INFO, "void callIntegratedAirQuality()", "response body: " + integratedAirQualitySingleModel);
            end();
        }
    }

    private void end() {
        progressManager.alertDismiss();
        binding.fragmentHomeSwipe.setRefreshing(false);
        endThread(SYNC_ID);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            new HLogger(getClass()).log(HLogger.LogType.INFO, "void setUserVisibleHint(boolean isVisibleToUser)", "HomeFragment");
        }
    }

    @Override
    public void reScan() {
        logger.log(HLogger.LogType.INFO, "void setUserVisibleHint(boolean isVisibleToUser)", "reScan");
        currentWeather();
    }
}
