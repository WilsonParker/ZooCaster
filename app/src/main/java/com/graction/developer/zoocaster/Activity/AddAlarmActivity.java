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

public class AddAlarmActivity extends BaseActivity {
    private ActivityAddAlarmBinding binding;
    private int hourOfDay, minute;
    private int[] selectedWeek = new int[8];
    private boolean isSpeaker = true;
    private String address;

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

    private void createArray() {
        ArrayList<CustomArrayView.CustomItemViewModel> items = new ArrayList<>();
        for (int i = 1; i < DataStorage.Date.DayOfTheWeek.length; i++)
            items.add(binding.activityAddAlarmCustomArrayView.new CustomItemViewModel(DataStorage.Date.DayOfTheWeek[i], i));
        binding.activityAddAlarmCustomArrayView.setWeekClickListener((idx, value) -> selectedWeek[idx] = value);
        binding.activityAddAlarmCustomArrayView.bindView(this, items);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // onTimeChange
    public void onTimeChange(TimePicker view, int hourOfDay, int minute) {
        logger.log(HLogger.LogType.INFO, "AlarmReceiver", "%d : %d", hourOfDay, minute);
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        // 8: 15, 17 : 15
    }

    // OnClick
    public void addAlarm(View view) {
        logger.log(HLogger.LogType.INFO, "addAlarm(view)");
        logger.log(HLogger.LogType.INFO, "addAlarm(view)", "selectedWeek : " + Arrays.toString(selectedWeek));
//        AlarmData.AlarmItem item = alarmData.new AlarmItem(place_name, place_address, memo, selectedWeek, hourOfDay, minute);
        if (!validCheck())
            return;
        AlarmTable alarmTable = new AlarmTable(hourOfDay, minute, address, binding.activityAddAlarmETMemo.getText() + "", StringUtil.arrayToString(selectedWeek), AlarmTable.ENABLED, binding.activityAddAlarmSBVolume.getProgress(), isSpeaker ? AlarmTable.ENABLED : AlarmTable.DISABLED);
        AlarmItem item = new AlarmItem(alarmTable);
        DataBaseStorage.dataBaseHelper.insert(DataBaseStorage.Table.TABLE_ALARM, alarmTable);
        AlarmManager.getInstance().setAlarmService(item);
        DataBaseStorage.alarmList.add(item);
        onBackPressed();
    }

    private boolean validCheck() {
        if (NullChecker.getInstance().isNull(address))
            return false;
        if (hourOfDay == 0)
            hourOfDay = Integer.parseInt(DateManager.getInstance().getNow(DateManager.DateType.HourOfDay));
        if (minute == 0)
            minute = Integer.parseInt(DateManager.getInstance().getNow(DateManager.DateType.Minute));
        return true;
    }

    // OnClick
    public void selectAlarmType(boolean enabled) {
        isSpeaker = enabled;
        if (enabled)
            binding.activityAddAlarmSBVolume.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.seekbar_on2, null));
        else
            binding.activityAddAlarmSBVolume.setProgressDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.seekbar_off2, null));
    }

    // OnClick
    public void searchEvent(View view) {
        startActivityForResult(new Intent(this, SearchAddressActivity.class), DataStorage.Request.SEARCH_ADDRESS_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DataStorage.Request.SEARCH_ADDRESS_REQUEST && resultCode == DataStorage.Request.SEARCH_ADDRESS_OK) {
//            AddressModel.Prediction item = (AddressModel.Prediction) data.getSerializableExtra(DataStorage.Key.KEY_ADDRESS_ITEM);
//            address = item != null ? item.getDescription() : data.getStringExtra(DataStorage.Key.KEY_ADDRESS);
            binding.activityAddAlarmTVAddress.setText((address = data.getStringExtra(DataStorage.Key.KEY_ADDRESS)));
        }
    }
}
