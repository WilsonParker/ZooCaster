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
//        binding = FragmentAlarmBinding.inflate(inflater, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm, null, false);
        return binding.getRoot();
    }

    @Override
    protected void init(View view) {
        binding.setActivity(this);
        binding.fragmentAlarmRV.setLayoutManager(new LinearLayoutManager(getActivity()));
        initData();
    }

    private void initData() {
        if (DataBaseStorage.alarmDataBaseHelper == null)
            DataBaseStorage.alarmDataBaseHelper = new DataBaseHelper(getContext(), DATABASE_NAME, null, DataBaseStorage.Version.TABLE_ALARM_VERSION);
        List<AlarmTable> tableList = DataBaseStorage.alarmDataBaseHelper.selectList("SELECT * FROM " + DataBaseStorage.Table.TABLE_ALARM, AlarmTable.class);
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

    public void addAlarm(View view) {
        logger.log(HLogger.LogType.INFO, "addAlarm()", "addAlarm");
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
