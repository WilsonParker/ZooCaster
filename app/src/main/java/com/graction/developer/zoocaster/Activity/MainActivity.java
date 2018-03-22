package com.graction.developer.zoocaster.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;

import com.graction.developer.zoocaster.Adapter.FragmentAdapter;
import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Fragment.AlarmFragment;
import com.graction.developer.zoocaster.Fragment.BaseFragment;
import com.graction.developer.zoocaster.Fragment.FineDustFragment;
import com.graction.developer.zoocaster.Fragment.HomeFragment;
import com.graction.developer.zoocaster.Fragment.Test2Fragment;
import com.graction.developer.zoocaster.Model.Location;
import com.graction.developer.zoocaster.Model.Response.SimpleResponseModel;
import com.graction.developer.zoocaster.Net.Net;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by JeongTaehyun
 */

/*
 * 메인, 메뉴 및 페이지 설정 Activity
 */

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private boolean isFirst;                    // initViewPager 를 한번만 실행시키기 위함
    private FragmentAdapter fragmentAdapter;
    private ArrayList<FragmentAdapter.TabItem> items;

    /*
     * 초기 설정
     */
    @Override
    protected void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (!isFirst) {
            initViewPager();
            isFirst = !isFirst;
        }
        initLocation();
    }

    /*
     * 상단 메뉴 (Tab 및 ViewPager) 할당
     */
    private void initViewPager() {
        items = new ArrayList<>();
        items.add(new FragmentAdapter.TabItem(HomeFragment.getInstance(), R.drawable.tab_home));
        items.add(new FragmentAdapter.TabItem(Test2Fragment.getInstance(), R.drawable.tab_week));
        items.add(new FragmentAdapter.TabItem(FineDustFragment.getInstance(), R.drawable.tab_dust));
        items.add(new FragmentAdapter.TabItem(AlarmFragment.getInstance(), R.drawable.tab_alarm));
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), items, (index, item) -> {
            binding.activityMainTab.getTabAt(index).setCustomView(fragmentAdapter.getView(MainActivity.this, R.layout.item_tab, item.getResIcon()));
        });
        binding.activityMainTab.setupWithViewPager(binding.activityMainVP);
        binding.activityMainVP.setAdapter(fragmentAdapter);
        binding.activityMainVP.setOffscreenPageLimit(items.size() - 1);
    }

    /*
     * 초기 위치 설정
     */
    private void initLocation() {
        binding.activityMainTVLocation.setOnClickListener((v) -> startActivityForResult(new Intent(this, SearchAddressActivity.class), DataStorage.Request.SEARCH_ADDRESS_REQUEST));
        DataStorage.addressHandleListener = (newAddress, originAddress) -> {
            setLocation(newAddress, originAddress);
        };
    }

    /*
     * 주소 설정
     */
    private void setLocation(String newAddress, String originAddress){
        DataStorage.NowNewAddress = newAddress;
        DataStorage.NowOriginAddress = originAddress;
        binding.activityMainTVLocation.setText(DataStorage.NowNewAddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DataStorage.Request.SEARCH_ADDRESS_REQUEST && resultCode == DataStorage.Request.SEARCH_ADDRESS_OK) {
            setLocation(data.getStringExtra(DataStorage.Key.KEY_NEW_ADDRESS), data.getStringExtra(DataStorage.Key.KEY_ORIGIN_ADDRESS));
            Net.getInstance().getFactoryIm().getLocationFromAddress(DataStorage.NowOriginAddress).enqueue(new Callback<SimpleResponseModel<Location>>() {
                @Override
                public void onResponse(Call<SimpleResponseModel<Location>> call, Response<SimpleResponseModel<Location>> response) {
                    if(response.isSuccessful()){
                        Location location = response.body().getResult();
                        DataStorage.Latitude = Double.parseDouble(location.getLat());
                        DataStorage.Longitude = Double.parseDouble(location.getLng());
                        ((BaseFragment) items.get(0).getFragment()).reScan();   // HomeFragment
                        ((BaseFragment) items.get(2).getFragment()).reScan();   // FineDustFragment
                    }
                }

                @Override
                public void onFailure(Call<SimpleResponseModel<Location>> call, Throwable t) {
                    logger.log(HLogger.LogType.ERROR, "onFailure(Call<SimpleResponseModel<Location>> call, Throwable t)", "onActivityResult onFailure", t);
                }
            });
        }
    }

}
