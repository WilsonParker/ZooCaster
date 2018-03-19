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
import com.graction.developer.zoocaster.Util.GPS.GoogleLocationManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.Parser.AddressParser;
import com.graction.developer.zoocaster.databinding.ActivityMainBinding;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private boolean isFirst;
    private FragmentAdapter fragmentAdapter;
    private ArrayList<FragmentAdapter.TabItem> items;

    @Override
    protected void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (!isFirst) {
            initViewPager();
            isFirst = !isFirst;
        }
        initLocation();
    }

    private void initViewPager() {
        items = new ArrayList<>();
        items.add(new FragmentAdapter.TabItem(HomeFragment.getInstance(), R.drawable.tab_home));
        items.add(new FragmentAdapter.TabItem(Test2Fragment.getInstance(), R.drawable.tab_week));
        items.add(new FragmentAdapter.TabItem(FineDustFragment.getInstance(), R.drawable.tab_dust));
        items.add(new FragmentAdapter.TabItem(AlarmFragment.getInstance(), R.drawable.tab_alarm));
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), items, (index, item) -> {
            binding.activityMainTab.getTabAt(index).setCustomView(fragmentAdapter.getView(MainActivity.this, R.layout.item_tab, item.getResIcon()));
            logger.log(HLogger.LogType.INFO, "setTabItem", "index : " + index);

           /* ItemTabBinding itemTabBinding = ItemTabBinding.inflate(getLayoutInflater());
            View tabItem = itemTabBinding.getRoot();
            itemTabBinding.setItem(item);
            itemTabBinding.executePendingBindings();
            binding.activityMainTab.getTabAt(index).setCustomView(tabItem);*/
        });
       /* fragmentAdapter.setSetTabItem((index, item) -> {
            binding.activityMainTab.getTabAt(index).setCustomView(fragmentAdapter.getView(MainActivity.this, R.layout.item_tab, item.getResIcon()));
            logger.log(HLogger.LogType.INFO, "setTabItem", "index : "+index);

           *//* ItemTabBinding itemTabBinding = ItemTabBinding.inflate(getLayoutInflater());
            View tabItem = itemTabBinding.getRoot();
            itemTabBinding.setItem(item);
            itemTabBinding.executePendingBindings();
            binding.activityMainTab.getTabAt(index).setCustomView(tabItem);*//*
        });*/
//        fragmentAdapter.setItems(items);
        binding.activityMainTab.setupWithViewPager(binding.activityMainVP);
        binding.activityMainVP.setAdapter(fragmentAdapter);
        binding.activityMainVP.setOffscreenPageLimit(items.size() - 1);
    }

    private void initNavigation() {
        //        binding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //        binding.navigation.setSelectedItemId(R.id.navigation_current);
        /*ArrayList<CustomNavigationView.NavigationItem> items = new ArrayList<>();
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(HomeFragment.getInstance());
        }));
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(Forecast5DayFragment.getInstance());
        }));
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(AlarmFragment.getInstance());
        }));
        items.add(binding.activityMainCNV.new NavigationItem(R.drawable.dust_on, R.drawable.alarm_off, () -> {
            replaceContent(TestFragment.getInstance());
        }));
        binding.activityMainCNV.bindItemView(this, items, (imageView, resId) -> {
            Glide.with(MainActivity.this).load(resId).into(imageView);
        });
        binding.activityMainCNV.actionItem(0);*/
    }

    private void initLocation() {
        binding.activityMainTVLocation.setOnClickListener((v) -> startActivityForResult(new Intent(this, SearchAddressActivity.class), DataStorage.Request.SEARCH_ADDRESS_REQUEST));
        DataStorage.addressHandleListener = (newAddress, originAddress) -> {
            DataStorage.NowNewAddress = newAddress;
            DataStorage.NowOriginAddress = originAddress;
            binding.activityMainTVLocation.setText(DataStorage.NowNewAddress);
        };

        DataStorage.googleLocationManager = new GoogleLocationManager((newAddress, originAddress) -> {
            setLocation(newAddress, originAddress);
        });
    }

    private void setLocation(String newAddress, String originAddress){
        DataStorage.NowNewAddress = newAddress;
        DataStorage.NowOriginAddress = originAddress;
        binding.activityMainTVLocation.setText(DataStorage.NowNewAddress);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DataStorage.Request.SEARCH_ADDRESS_REQUEST && resultCode == DataStorage.Request.SEARCH_ADDRESS_OK) {
            setLocation(data.getStringExtra(DataStorage.Key.KEY_NEW_ADDRESS), data.getStringExtra(DataStorage.Key.KEY_ORIGIN_ADDRESS));
            logger.log(HLogger.LogType.INFO, "void onActivityResult(int requestCode, int resultCode, Intent data)", "originAddress  :"+DataStorage.NowOriginAddress);
            Net.getInstance().getFactoryIm().getLocationFromAddress(DataStorage.NowOriginAddress).enqueue(new Callback<SimpleResponseModel<Location>>() {
                @Override
                public void onResponse(Call<SimpleResponseModel<Location>> call, Response<SimpleResponseModel<Location>> response) {
                    if(response.isSuccessful()){
                        Location location = response.body().getResult();
                        logger.log(HLogger.LogType.INFO, "onResponse", "lat %s, lon %s", location.getLat(), location.getLng());
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
