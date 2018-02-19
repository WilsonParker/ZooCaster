package com.graction.developer.zoocaster.Model.DataBase;

import com.graction.developer.zoocaster.DataBase.SqlIgnore;

/**
 * Created by Graction06 on 2018-01-25.
 */

public class AlarmTable {
    public static final int ENABLED = 1, DISABLED = 0;
    @SqlIgnore
    private int alarm_index;
    private int alarm_hourofday, alarm_minute, alarm_volume, alarm_running_state, alarm_isSpeaker;
    private String alarm_address, alarm_memo, alarm_days;

    public AlarmTable() {
    }

    public AlarmTable(int alarm_hourOfDay, int alarm_minute, String alarm_address, String alarm_memo, String alarm_days, int alarm_running_state, int alarm_volume, int isSpeaker) {
        this.alarm_running_state = alarm_running_state;
        this.alarm_hourofday = alarm_hourOfDay;
        this.alarm_minute = alarm_minute;
        this.alarm_address = alarm_address;
        this.alarm_memo = alarm_memo;
        this.alarm_days = alarm_days;
        this.alarm_volume = alarm_volume;
        this.alarm_isSpeaker = isSpeaker;
    }

    public int getAlarm_index() {
        return alarm_index;
    }

    public void setAlarm_index(int alarm_index) {
        this.alarm_index = alarm_index;
    }

    public int getAlarm_hourofday() {
        return alarm_hourofday;
    }

    public void setAlarm_hourofday(int alarm_hourofday) {
        this.alarm_hourofday = alarm_hourofday;
    }

    public int getAlarm_minute() {
        return alarm_minute;
    }

    public void setAlarm_minute(int alarm_minute) {
        this.alarm_minute = alarm_minute;
    }

    public int getAlarm_volume() {
        return alarm_volume;
    }

    public void setAlarm_volume(int alarm_volume) {
        this.alarm_volume = alarm_volume;
    }

    public String getAlarm_memo() {
        return alarm_memo;
    }

    public void setAlarm_memo(String alarm_memo) {
        this.alarm_memo = alarm_memo;
    }

    public String getAlarm_days() {
        return alarm_days;
    }

    public void setAlarm_days(String alarm_days) {
        this.alarm_days = alarm_days;
    }

    public int getAlarm_running_state() {
        return alarm_running_state;
    }

    public void setAlarm_running_state(int alarm_running_state) {
        this.alarm_running_state = alarm_running_state;
    }

    public String getAlarm_address() {
        return alarm_address;
    }

    public void setAlarm_address(String alarm_address) {
        this.alarm_address = alarm_address;
    }

    public int getAlarm_isSpeaker() {
        return alarm_isSpeaker;
    }

    public void setAlarm_isSpeaker(int alarm_isSpeaker) {
        this.alarm_isSpeaker = alarm_isSpeaker;
    }

    @Override
    public String toString() {
        return "AlarmTable{" +
                "alarm_index=" + alarm_index +
                ", alarm_hourofday=" + alarm_hourofday +
                ", alarm_minute=" + alarm_minute +
                ", alarm_volume=" + alarm_volume +
                ", alarm_running_state=" + alarm_running_state +
                ", alarm_isSpeaker=" + alarm_isSpeaker +
                ", alarm_address='" + alarm_address + '\'' +
                ", alarm_memo='" + alarm_memo + '\'' +
                ", alarm_days='" + alarm_days + '\'' +
                '}';
    }
}
