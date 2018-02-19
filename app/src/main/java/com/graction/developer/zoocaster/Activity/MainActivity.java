package com.graction.developer.zoocaster.Activity;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.graction.developer.zoocaster.Adapter.FragmentAdapter;
import com.graction.developer.zoocaster.Fragment.AlarmFragment;
import com.graction.developer.zoocaster.Fragment.Forecast5DayFragment;
import com.graction.developer.zoocaster.Fragment.HomeFragment;
import com.graction.developer.zoocaster.Fragment.Test2Fragment;
import com.graction.developer.zoocaster.Fragment.TestFragment;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private boolean isFirst;
    private FragmentAdapter fragmentAdapter;
    private Fragment fragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_current:
                    fragment = HomeFragment.getInstance();
                    break;
                case R.id.navigation_daily:
                    fragment = Forecast5DayFragment.getInstance();
                    break;
                case R.id.navigation_alarm:
                    fragment = AlarmFragment.getInstance();
                    break;
                case R.id.navigation_alarmtest:
                    fragment = TestFragment.getInstance();
                    break;
            }
            replaceContent();
            return true;
        }

    };

    @Override
    protected void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        if (!isFirst) {
            initViewPager();
            isFirst = !isFirst;
        }
    }

    private void initViewPager() {
        /*
            binding.activityMainVP.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            ViewPagerTabAdapter viewPagerTabAdapter = new ViewPagerTabAdapter((index, item) -> {
                binding.activityMainTab.getTabAt(index).setIcon(item.getResIcon());
            });
            viewPagerTabAdapter.addItem(viewPagerTabAdapter.new TabItem(HomeFragment.getInstance(), R.drawable.ic_home_black_24dp));
            viewPagerTabAdapter.addItem(viewPagerTabAdapter.new TabItem(Forecast5DayFragment.getInstance(), R.drawable.ic_dashboard_black_24dp));
            viewPagerTabAdapter.addItem(viewPagerTabAdapter.new TabItem(AlarmFragment.getInstance(), R.drawable.ic_notifications_black_24dp));
            viewPagerTabAdapter.addItem(viewPagerTabAdapter.new TabItem(TestFragment.getInstance(), R.drawable.ic_dashboard_black_24dp));
            TabLayoutSupport.setupWithViewPager(binding.activityMainTab, binding.activityMainVP, viewPagerTabAdapter);
        */

        ArrayList<FragmentAdapter.TabItem> items = new ArrayList<>();
        items.add(new FragmentAdapter.TabItem(HomeFragment.getInstance(), R.drawable.tab_home));
        items.add(new FragmentAdapter.TabItem(Test2Fragment.getInstance(), R.drawable.tab_week));
        items.add(new FragmentAdapter.TabItem(TestFragment.getInstance(), R.drawable.tab_dust));
        items.add(new FragmentAdapter.TabItem(AlarmFragment.getInstance(), R.drawable.tab_alarm));
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), items, (index, item) -> {
            binding.activityMainTab.getTabAt(index).setCustomView(fragmentAdapter.getView(MainActivity.this, R.layout.item_tab, item.getResIcon()));
            logger.log(HLogger.LogType.INFO, "setTabItem", "index : "+index);

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

        logger.log(HLogger.LogType.INFO, "initViewPager()", "initViewPager()");
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

    private void replaceContent() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_FL, fragment).commit();
    }

    private void replaceContent(Fragment fragment) {
//        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main_FL, fragment).commit();
    }

}
