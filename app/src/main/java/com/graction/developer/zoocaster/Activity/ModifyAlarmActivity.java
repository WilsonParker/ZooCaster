package com.graction.developer.zoocaster.Activity;

import android.content.ContentValues;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TimePicker;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.DataBase.DataBaseParserManager;
import com.graction.developer.zoocaster.DataBase.DataBaseStorage;
import com.graction.developer.zoocaster.Model.Address.AddressModel;
import com.graction.developer.zoocaster.Model.DataBase.AlarmTable;
import com.graction.developer.zoocaster.Model.Item.AlarmItem;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.UI.Layout.CustomArrayView;
import com.graction.developer.zoocaster.Util.System.AlarmManager;
import com.graction.developer.zoocaster.Util.Log.HLogger;
import com.graction.developer.zoocaster.Util.NullChecker;
import com.graction.developer.zoocaster.Util.StringUtil;
import com.graction.developer.zoocaster.databinding.ActivityModifyAlarmBinding;

import java.util.ArrayList;

public class ModifyAlarmActivity extends BaseActivity {
    private ActivityModifyAlarmBinding binding;
    private AlarmItem item;
    private int hourOfDay = -1, minute = -1, index;
    private int[] selectedWeek = new int[8];
    private boolean isSpeaker = true;
    private String address;

    @Override
    protected void init() {
        item = (AlarmItem) getIntent().getSerializableExtra(DataStorage.Key.KEY_ALARM_ITEM);
        index = getIntent().getIntExtra(DataStorage.Key.KEY_ALARM_INDEX, -1);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modify_alarm);

        createArray();
        binding.setActivity(this);
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

        logger.log(HLogger.LogType.INFO, "onCreate(Bundle savedInstanceState)", "index : " + index);
        initView();
    }

    private void initView() {
        binding.activityAddAlarmETMemo.setText(item.getMemo());
        binding.activityAddAlarmSBVolume.setProgress(item.getVolume());
        binding.activityAddAlarmTVAddress.setText(item.getAddress());

        binding.activityAddAlarmTimePicker.setCurrentHour(item.getRealHour());
        binding.activityAddAlarmTimePicker.setCurrentMinute(item.getMinute());

        selectAlarmType(item.getIsSpeaker());
    }

    private void createArray() {
        ArrayList<CustomArrayView.CustomItemViewModel> items = new ArrayList<>();
        int[] iArr = item.getDays();
        for (int i = 1; i < iArr.length; i++)
            items.add(binding.activityAddAlarmCustomArrayView.new CustomItemViewModel(DataStorage.Date.DayOfTheWeek[i], i, iArr[i] == CustomArrayView.WeekClickListener.click));
        binding.activityAddAlarmCustomArrayView.setWeekClickListener((idx, value) -> selectedWeek[idx] = value);
        binding.activityAddAlarmCustomArrayView.bindView(this, items);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    // onTimeChange
    public void onTimeChange(TimePicker view, int hourOfDay, int minute) {
    }

    // OnClick
    public void modifyAlarm(View view) {
//        AlarmData.AlarmItem item = alarmData.new AlarmItem(place_name, place_address, memo, selectedWeek, hourOfDay, minute);
        if (!validCheck())
            return;
        AlarmTable table = new AlarmTable(hourOfDay, minute, address, binding.activityAddAlarmETMemo.getText() + "", StringUtil.arrayToString(selectedWeek), AlarmTable.ENABLED, binding.activityAddAlarmSBVolume.getProgress(), isSpeaker ? AlarmTable.ENABLED : AlarmTable.DISABLED);
        AlarmItem alarmItem = new AlarmItem(table, item.getIndex());
        ContentValues values = null;
        try {
            values = DataBaseParserManager.getInstance().bindContentValues(table);
        } catch (Exception e) {
            logger.log(HLogger.LogType.ERROR, "modifyAlarm(View view)", "bindContentValues - Error", e);
        }
        logger.log(HLogger.LogType.INFO, "modifyAlarm(View view)", "AlarmTable : " + table);

        String whereClause = DataBaseStorage.Column.COLUMN_ALARM_INDEX + " = ?";
        String[] args = {alarmItem.getIndex() + ""};
        DataBaseStorage.alarmDataBaseHelper.update(DataBaseStorage.Table.TABLE_ALARM, values, whereClause, args);
        AlarmManager.getInstance().setAlarmService(alarmItem);
        DataBaseStorage.alarmList.remove(index);
        DataBaseStorage.alarmList.add(index, alarmItem);
        onBackPressed();
    }

    private boolean validCheck() {
        address = binding.activityAddAlarmTVAddress.getText() + "";
        if (NullChecker.getInstance().isNull(address))
            return false;
//            hourOfDay = Integer.parseInt(DateManager.getInstance().getNow(DateManager.DateType.HourOfDay));
//            minute = Integer.parseInt(DateManager.getInstance().getNow(DateManager.DateType.Minute));
        hourOfDay = binding.activityAddAlarmTimePicker.getCurrentHour();
        minute = binding.activityAddAlarmTimePicker.getCurrentMinute();
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
            AddressModel.Prediction item = (AddressModel.Prediction) data.getSerializableExtra(DataStorage.Key.KEY_ADDRESS_ITEM);
            binding.activityAddAlarmTVAddress.setText((address = item.getDescription()));
        }
    }
}
