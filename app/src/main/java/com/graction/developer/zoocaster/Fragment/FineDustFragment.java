package com.graction.developer.zoocaster.Fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Model.Response.IntegratedAirQualityModel;
import com.graction.developer.zoocaster.Model.Response.SimpleResponseModel;
import com.graction.developer.zoocaster.Model.VO.FineDustVO;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.databinding.FragmentFinedustBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.graction.developer.zoocaster.Data.DataStorage.integratedAirQualityModel;

public class FineDustFragment extends BaseFragment {
    private static final int SYNC_ID = 0B0100;
    private FragmentFinedustBinding binding;
    private ArrayList<FineDustVO> fineDustStandard;

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

    private void initFineDustStandard() {
        Call<SimpleResponseModel<ArrayList<FineDustVO>>> call = Net.getInstance().getFactoryIm().selectFineDustStandard();
        setCall(call);
        addAction(() -> call.enqueue(new Callback<SimpleResponseModel<ArrayList<FineDustVO>>>() {
            @Override
            public void onResponse(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Response<SimpleResponseModel<ArrayList<FineDustVO>>> response) {
                if (response.isSuccessful()) {
                    logger.log(HLogger.LogType.INFO, "void onResponse(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Response<SimpleResponseModel<ArrayList<FineDustVO>>> response)", "isSuccessFul");
                    fineDustStandard = response.body().getResult();
                    setIntegratedAirQualityItem();
                }
                endThread(SYNC_ID);
                binding.fragmentFinedustSwipe.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<SimpleResponseModel<ArrayList<FineDustVO>>> call, Throwable t) {
                logger.log(HLogger.LogType.ERROR, "onFailure(Call<WeatherModel> call, Throwable t)", "onFailure", t);
                endThread(SYNC_ID);
                binding.fragmentFinedustSwipe.setRefreshing(false);
            }
        }), SYNC_ID);
        startSync();
    }

    private void setIntegratedAirQualityItem() {
        if (integratedAirQualityModel != null) {
            IntegratedAirQualityModel.IntegratedAirQualityModelItem item = DataStorage.integratedAirQualityModel.getFirstItem();
            item.setFineDustStandard(fineDustStandard);
            binding.setPm10(IntegratedAirQualityModel.TYPE_PM10);
            binding.setPm25(IntegratedAirQualityModel.TYPE_PM25);
            binding.setModelItem(item);
            logger.log(HLogger.LogType.INFO, "void setIntegratedAirQualityItem", "IntegratedAirQualityModelItem : " + DataStorage.integratedAirQualityModel.getFirstItem());
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            binding.donutProgress.drawWithAnimate();
            setIntegratedAirQualityItem();
//            binding.executePendingBindings();
            binding.notifyChange();
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

}
