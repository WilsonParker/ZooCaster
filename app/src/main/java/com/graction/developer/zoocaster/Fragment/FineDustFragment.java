package com.graction.developer.zoocaster.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.request.RequestOptions;
import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualityModel;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualitySingleModel;
import com.graction.developer.zoocaster.Model.Response.SimpleResponseModel;
import com.graction.developer.zoocaster.Model.VO.FineDustVO;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.File.BaseActivityFileManager;
import com.graction.developer.zoocaster.Util.GPS.GpsManager;
import com.graction.developer.zoocaster.Util.Image.GifManager;
import com.graction.developer.zoocaster.Util.Image.GlideImageManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.databinding.FragmentFinedustBinding;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.graction.developer.zoocaster.Data.DataStorage.Latitude;
import static com.graction.developer.zoocaster.Data.DataStorage.Longitude;
import static com.graction.developer.zoocaster.Data.DataStorage.fineDustStandard;
import static com.graction.developer.zoocaster.Data.DataStorage.integratedAirQualitySingleModel;
import static com.graction.developer.zoocaster.Data.DataStorage.weatherModel;

public class FineDustFragment extends BaseFragment {
    private static final int SYNC_ID = 0B0100;
    private FragmentFinedustBinding binding;

    public static Fragment getInstance() {
        Fragment fragment = new FineDustFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_finedust, null, false);
        return binding.getRoot();
    }

    @Override
    protected void init(View view) {
        binding.setActivity(this);
        binding.fragmentFinedustSwipe.setOnRefreshListener(() -> initFineDustStandard());
        initFineDustStandard();
    }

   /* private void initFineDustStandard() {
        Call<SimpleResponseModel<ArrayList<FineDustVO>>> call = Net.getInstance().getFactoryIm().selectFineDustStandard();
        setCall(call);
        addAction(() -> call.enqueue(new Callback<SimpleResponseModel<ArrayList<FineDustVO>>>() {
            @Override
            public void onResponse(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Response<SimpleResponseModel<ArrayList<FineDustVO>>> response) {
                if (response.isSuccessful()) {
                    fineDustStandard = response.body().getResult();
                    if (integratedAirQualitySingleModel == null) {
                        callIntegratedAirQuality(gpsManager, new Callback<IntegratedAirQualitySingleModel>() {
                            @Override
                            public void onResponse(Call<IntegratedAirQualitySingleModel> call, Response<IntegratedAirQualitySingleModel> response) {
                                if (response.isSuccessful()) {
                                    integratedAirQualitySingleModel = response.body();
                                    setIntegratedAirQualityItem();
                                    end();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                logger.log(HLogger.LogType.ERROR, "callIntegratedAirQuality()", "callIntegratedAirQuality onFailure", t);
                                end();
                            }
                        });
                    } else {
                        setIntegratedAirQualityItem();
                    }
                } else {
                    end();
                }
            }

            @Override
            public void onFailure(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onFailure(Call<WeatherModel> call, Throwable t)", "onFailure", t);
                end();
            }
        }), SYNC_ID);
        startSync();
    }*/

    // 초기화 및 새로고침 시에 Call IntegratedAirQualitySingleModel
    private void initFineDustStandard() {
        Call<SimpleResponseModel<ArrayList<FineDustVO>>> call = Net.getInstance().getFactoryIm().selectFineDustStandard();
        setCall(call);
        addAction(() ->
                        callIntegratedAirQuality(Latitude, Longitude, new Callback<IntegratedAirQualitySingleModel>() {
                            @Override
                            public void onResponse(Call<IntegratedAirQualitySingleModel> call, Response<IntegratedAirQualitySingleModel> response) {
                                if (response.isSuccessful()) {
                                    logger.log(HLogger.LogType.INFO, "onResponse is Successful", "lat %s, lon %s: ", Latitude, Longitude);
                                    logger.log(HLogger.LogType.INFO, "onResponse is Successful", "integratedAirQualitySingleModel " + integratedAirQualitySingleModel);
                                    integratedAirQualitySingleModel = response.body();
                                    setIntegratedAirQualityItem();
                                    end();
                                }
                            }

                            @Override
                            public void onFailure(Call call, Throwable t) {
                                logger.log(HLogger.LogType.ERROR, "callIntegratedAirQuality()", "callIntegratedAirQuality onFailure", t);
                                end();
                            }
                        })
                , SYNC_ID);
        startSync();
    }

    private void setIntegratedAirQualityItem() {
        logger.log(HLogger.LogType.INFO, "void setIntegratedAirQualityItem", "IntegratedAirQualityModelItem : " + (integratedAirQualitySingleModel == null));
        if(integratedAirQualitySingleModel == null)
            return;
        logger.log(HLogger.LogType.INFO, "void setIntegratedAirQualityItem", "IntegratedAirQualityModelItem : " + integratedAirQualitySingleModel.getItem());
//        logger.log(HLogger.LogType.INFO, "void setIntegratedAirQualityItem", "IntegratedAirQualityModelItem : " + integratedAirQualitySingleModel.getItem().getFineDustStandard());
        IntegratedAirQualityModel.IntegratedAirQualityModelItem item = integratedAirQualitySingleModel.getItem();
        item.setFineDustStandard(fineDustStandard);
        FineDustVO vo = item.getFineDustVO();
        binding.setPm10(IntegratedAirQualityModel.TYPE_PM10);
        binding.setPm25(IntegratedAirQualityModel.TYPE_PM25);
        binding.setModelItem(item);
        try {
            GlideImageManager.getInstance().bindImage(getContext(), binding.fragmentFinedustBackground, new RequestOptions().centerCrop(), vo.getBackground_img_path(), vo.getBackground_img_name(), vo.getBackground_img_url());
            GlideImageManager.getInstance().bindImage(getContext(), binding.fragmentFinedustIVCharacter, new RequestOptions().centerCrop(), vo.getCharacter_img_path(), vo.getCharacter_img_name(), vo.getCharacter_img_url());
//            BaseActivityFileManager.getInstance().isExistsAndSaveFile(vo.getCharacter_img_path(), vo.getCharacter_img_name(), vo.getCharacter_img_url(), BaseActivityFileManager.FileType.ByteArray);

//            GifManager.getInstance().setGifAnimate(binding.fragmentFinedustGifCharacter, vo.getCharacter_img_path()+ vo.getCharacter_img_name());
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "void setIntegratedAirQualityItem", e);
        } finally {
            binding.fragmentFinedustSwipe.setRefreshing(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            binding.donutProgress.drawWithAnimate();
            setIntegratedAirQualityItem();
            binding.notifyChange();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void end() {
        endThread(SYNC_ID);
        binding.fragmentFinedustSwipe.setRefreshing(false);
    }

    @Override
    public void reScan() {
        initFineDustStandard();
    }
}