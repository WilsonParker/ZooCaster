package com.graction.developer.zoocaster.Model.Item;

import com.graction.developer.zoocaster.Model.DataBase.AlarmTable;
import com.graction.developer.zoocaster.R;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Graction06 on 2018-01-26.
 */

public class AlarmItem implements Serializable {
    private static final String[] DayOfTheWeek = {"", "일", "월", "화", "수", "목", "금", "토"};
    private String memo, address;
    private int[] days;
    private boolean isMorning = true, isSpeaker;
    private int index, hour, minute, volume, isRunning, img_phone = R.drawable.phone_icon_on, img_speaker = R.drawable.sound_icon_off;

    public AlarmItem() {
    }

    public AlarmItem(AlarmTable table) {
        this.isRunning = table.getAlarm_running_state();
        this.index = table.getAlarm_index();
        this.address = table.getAlarm_address();
        this.memo = table.getAlarm_memo();
        this.isSpeaker = table.getAlarm_isSpeaker() == 1 ? true : false;
//            this.days = Arrays.stream(table.getAlarm_days().split(",")).mapToInt(Integer::parseInt).toArray();
        String[] sDays = table.getAlarm_days().split(",");
        int[] days = new int[sDays.length];
        System.out.println(Arrays.toString(sDays));
        for (int i = 0; i < sDays.length; i++) {
            days[i] = Integer.parseInt(sDays[i].replaceAll("\"", ""));
        }
        this.days = days;
        setHour(table.getAlarm_hourofday());
        this.minute = table.getAlarm_minute();
        setVolume(table.getAlarm_volume());
    }

    public AlarmItem(AlarmTable table, int index) {
        this(table);
        this.index = index;
    }

    public AlarmItem(int index, String address, String memo, int[] days, int hour, int minute, int volume, int isRunning, boolean isSpeaker) {
        this(address, memo, days, hour, minute, volume, isRunning, isSpeaker);
        this.index = index;
    }

    public AlarmItem(String address, String memo, int[] days, int hour, int minute, int volume, int isRunning, boolean isSpeaker) {
        this.address = address;
        this.memo = memo;
        this.days = days;
        this.minute = minute;
        this.isRunning = isRunning;
        this.isSpeaker = isSpeaker;
        this.volume = volume;
        setHour(hour);
    }

    private void setMorning(int hour) {
        if (hour > 12) {
            this.isMorning = false;
            this.hour = hour - 12;
        } else {
            this.hour = hour;
        }
    }

    public String getTimeInfo() {
        return isMorning ? "오전" : "오후";
    }

    public String getStringDays() {
        String sDay = "";
        for (int i = 1; i < days.length; i++) {
            if (days[i] == 1)
                sDay += DayOfTheWeek[i] + " ";
        }
        return sDay;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public int[] getDays() {
        return days;
    }

    public void setDays(int[] days) {
        this.days = days;
    }

    public boolean isMorning() {
        return isMorning;
    }

    public void setMorning(boolean morning) {
        isMorning = morning;
    }

    public boolean getIsSpeaker() {
        return isSpeaker;
    }

    public void setIsSpeaker(boolean speaker) {
        isSpeaker = speaker;
        if (isSpeaker) {
            this.img_phone = R.drawable.phone_icon_off;
            this.img_speaker = R.drawable.sound_icon_on;
        }
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getHour() {
        return hour;
    }

    public int getRealHour() {
        return isMorning ? hour : hour + 12;
    }

    public void setHour(int hour) {
        setMorning(hour);
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getImg_phone() {
        return img_phone;
    }

    public void setImg_phone(int img_phone) {
        this.img_phone = img_phone;
    }

    public int getImg_speaker() {
        return img_speaker;
    }

    public void setImg_speaker(int img_speaker) {
        this.img_speaker = img_speaker;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean getIsRunning() {
        return isRunning == 1 ? true : false;
    }

    public void setIsRunning(int isRunning) {
        this.isRunning = isRunning;
    }

    @Override
    public String toString() {
        return "AlarmItem{" +
                "memo='" + memo + '\'' +
                ", address ='" + address + '\'' +
                ", days=" + Arrays.toString(days) +
                ", isMorning=" + isMorning +
                ", isSpeaker=" + isSpeaker +
                ", isRunning=" + isRunning +
                ", index=" + index +
                ", hour=" + hour +
                ", minute=" + minute +
                ", volume=" + volume +
                '}';
    }
}