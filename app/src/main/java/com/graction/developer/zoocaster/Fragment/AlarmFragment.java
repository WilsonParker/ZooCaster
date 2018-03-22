package com.graction.developer.zoocaster.Fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Activity.AddAlarmActivity;
import com.graction.developer.zoocaster.Adapter.AlarmListAdapter;
import com.graction.developer.zoocaster.DataBase.DataBaseHelper;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Model.DataBase.AlarmTable;
import com.graction.developer.zoocaster.Model.Item.AlarmItem;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.System.AlarmManager;
import com.graction.developer.zoocaster.databinding.FragmentAlarmBinding;

import java.util.ArrayList;
import java.util.List;

import static com.graction.developer.zoocaster.DataBase.DataBaseStorage.DATABASE_NAME;

/**
 * Created by JeongTaehyun
 */

/*
 * 알람 페이지
 */

public class AlarmFragment extends BaseFragment {
    private static final AlarmFragment instance = new AlarmFragment();
    private AlarmListAdapter alarmListAdapter;
    private FragmentAlarmBinding binding;
    private ArrayList<AlarmItem> alarmList;

    public static Fragment getInstance() {
        return instance;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm, null, false);
        return binding.getRoot();
    }

    /*
     * 초기 설정
     */
    @Override
    protected void init(View view) {
        binding.setActivity(this);
        binding.fragmentAlarmRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
    }

    /*
     * 데이터 초기 설정
     * 데이터 베이스 정보를 읽어온다
     */
    private void initData() {
        DataBaseHelper.createHelper(getContext());
        List<AlarmTable> tableList = DataBaseStorage.dataBaseHelper.selectList("SELECT * FROM " + DataBaseStorage.Table.TABLE_ALARM, AlarmTable.class);
        alarmList = new ArrayList<>();
        for (AlarmTable table : tableList) {
            AlarmItem item = new AlarmItem(table);
            AlarmManager.getInstance().setAlarm(getContext(), item);
            alarmList.add(item);
        }
        DataBaseStorage.alarmList = alarmList;
        alarmListAdapter = new AlarmListAdapter(alarmList);
        binding.fragmentAlarmRV.setAdapter(alarmListAdapter);
    }

    /*
     * 알람 추가 페이지 이동
     */
    public void addAlarm(View view) {
        startActivity(new Intent(getContext(), AddAlarmActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        alarmListAdapter.notifyDataSetChanged();
        if(alarmList == null || alarmList.size() == 0)
            initData();
    }
}
