package com.graction.developer.zoocaster.Fragment;

import android.content.Intent;
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
import com.graction.developer.zoocaster.UI.ProgressManager;
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

/**
 * Created by JeongTaehyun
 */

/*
 * 미세먼지 페이지
 */

public class FineDustFragment extends BaseFragment {
    private static final int SYNC_ID = 0B0100;
    private ProgressManager progressManager;
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
        progressManager = new ProgressManager(getActivity());
        binding.fragmentFinedustSwipe.setOnRefreshListener(() -> initFineDustStandard());
        initFineDustStandard();
    }

    /*
     * 초기화 및 새로고침 시에 실행
     */
    private void initFineDustStandard() {
        binding.fragmentFinedustSwipe.setRefreshing(false);
        progressManager.alertShow();
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

    /*
     * 통합대기지수 정보를 이용하여 View 설정
     */
    private void setIntegratedAirQualityItem() {
        binding.fragmentFinedustCP.drawWithAnimate();
        if(integratedAirQualitySingleModel == null)
            return;
        IntegratedAirQualityModel.IntegratedAirQualityModelItem item = integratedAirQualitySingleModel.getItem();
        item.setFineDustStandard(fineDustStandard);
        FineDustVO vo = item.getFineDustVO();
        binding.setPm10(IntegratedAirQualityModel.TYPE_PM10);
        binding.setPm25(IntegratedAirQualityModel.TYPE_PM25);
        binding.setModelItem(item);
        try {
            GlideImageManager.getInstance().bindImage(getContext(), binding.fragmentFinedustBackground, new RequestOptions().centerCrop(), vo.getBackground_img_path(), vo.getBackground_img_name(), vo.getBackground_img_url());
            GlideImageManager.getInstance().bindImage(getContext(), binding.fragmentFinedustIVCharacter, new RequestOptions().centerCrop(), vo.getCharacter_img_path(), vo.getCharacter_img_name(), vo.getCharacter_img_url());
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "void setIntegratedAirQualityItem", e);
        } finally {
            binding.fragmentFinedustSwipe.setRefreshing(false);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            setIntegratedAirQualityItem();
            binding.notifyChange();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void end() {
        endThread(SYNC_ID);
        progressManager.alertDismiss();
        binding.fragmentFinedustSwipe.setRefreshing(false);
    }

    @Override
    public void reScan() {
        initFineDustStandard();
    }
}