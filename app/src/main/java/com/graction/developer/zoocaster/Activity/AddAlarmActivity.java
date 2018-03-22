package com.graction.developer.zoocaster.Activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TimePicker;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Model.DataBase.AlarmTable;
import com.graction.developer.zoocaster.Model.Item.AlarmItem;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.Layout.CustomArrayView;
import com.graction.developer.zoocaster.Util.System.AlarmManager;
import com.graction.developer.zoocaster.Util.Date.DateManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.NullChecker;
import com.graction.developer.zoocaster.Util.StringUtil;
import com.graction.developer.zoocaster.databinding.ActivityAddAlarmBinding;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by JeongTaehyun
 */

/*
 * 알람 추가 Activity
 */

public class AddAlarmActivity extends BaseActivity {
    private ActivityAddAlarmBinding binding;
    private int hourOfDay                       // 24시간 형태의 시
                , minute;                       // 분
    private int[] selectedWeek = new int[8];    // 선택된 요일
    private boolean isSpeaker = true;           // 스피커 or 진동 기본 설정
    private String new_address                  // origin_address 를 가공한 주소
                    , origin_address;           // API 에서 제공하는 주소

    /*
     * 초기 설정
     */
    @Override
    protected void init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_alarm);
        binding.setActivity(this);
        createArray();
        selectAlarmType(isSpeaker);
        binding.activityAddAlarmSBVolume.setOnTouchListener((v, event) -> !isSpeaker);
        binding.activityAddAlarmSBVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                                                        @Override
                                                                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                                                            progress = progress > 0 ? progress : 1;
                                                                            seekBar.setProgress(progress);
                                                                        }

                                                                        @Override
                                                                        public void onStartTrackingTouch(SeekBar seekBar) {

                                                                        }

                                                                        @Override
                                                                        public void onStopTrackingTouch(SeekBar seekBar) {

                                                                        }
                                                                    }
        );
    }

    /*
     * 요일 리스트 생성
     */
    private void createArray() {
        ArrayList<CustomArrayView.CustomItemViewModel> items = new ArrayList<>();
        for (int i = 1; i < DataStorage.Date.DayOfTheWeek.length; i++)
            items.add(binding.activityAddAlarmCustomArrayView.new CustomItemViewModel(DataStorage.Date.DayOfTheWeek[i], i));
        binding.activityAddAlarmCustomArrayView.setWeekClickListener((idx, value) -> selectedWeek[idx] = value);
        binding.activityAddAlarmCustomArrayView.bindView(this, items);
    }

    /*
     * OnClick
     * 알람 추가
     */
    public void addAlarm(View view) {
        if (!validCheck())
            return;
        AlarmTable alarmTable = new AlarmTable(hourOfDay, minute, new_address, origin_address, binding.activityAddAlarmETMemo.getText() + "", StringUtil.arrayToString(selectedWeek), AlarmTable.ENABLED, binding.activityAddAlarmSBVolume.getProgress(), isSpeaker ? AlarmTable.ENABLED : AlarmTable.DISABLED);
        AlarmItem item = new AlarmItem(alarmTable);
        DataBaseStorage.dataBaseHelper.insert(DataBaseStorage.Table.TABLE_ALARM, alarmTable);
        AlarmManager.getInstance().setAlarmService(item);
        DataBaseStorage.alarmList.add(item);
        onBackPressed();
    }

    /*
     * 데이터 점검
     */
    private boolean validCheck() {
        if (NullChecker.getInstance().isNull(new_address))
            return false;
        hourOfDay = binding.activityAddAlarmTimePicker.getCurrentHour();
        minute = binding.activityAddAlarmTimePicker.getCurrentMinute();
        return true;
    }

    /*
     * OnClick
     * 스피커, 진동 설정
     */
    public void selectAlarmType(boolean enabled) {
        isSpeaker = enabled;
        if (enabled)
            binding.activityAddAlarmSBVolume.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.seekbar_on2, null));
        else
            binding.activityAddAlarmSBVolume.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.seekbar_off2, null));
    }

    /*
     * 주소 검색 Activity 실행
     */
    public void searchEvent(View view) {
        startActivityForResult(new Intent(this, SearchAddressActivity.class), DataStorage.Request.SEARCH_ADDRESS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == DataStorage.Request.SEARCH_ADDRESS_REQUEST && resultCode == DataStorage.Request.SEARCH_ADDRESS_OK) {
            new_address = data.getStringExtra(DataStorage.Key.KEY_NEW_ADDRESS);
            origin_address = data.getStringExtra(DataStorage.Key.KEY_ORIGIN_ADDRESS);
            binding.activityAddAlarmTVAddress.setText(new_address);
        }
    }
}
