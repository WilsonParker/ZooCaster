package com.graction.developer.zoocaster.Util.Date;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by Hare on 2017-08-03.
 */

public class DateUIManager extends DateManager{
    private static final DateUIManager ourInstance = new DateUIManager();
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private View.OnClickListener onClickListener;
    private String stringDate, stringTime;

    private TextView textView;
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            stringDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            textView.setText(stringDate);
            if (onClickListener != null)
                onClickListener.onClick(null);
        }
    };

    private TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            stringTime = String.format("%02d:%02d", i, i1);
            textView.setText(stringTime);
            if (onClickListener != null)
                onClickListener.onClick(null);
        }
    };

    public static DateUIManager getInstance() {
        return ourInstance;
    }

    public void getDateYMD(Context context, TextView textView) {
        onClickListener = null;
        this.textView = textView;
        datePickerDialog = new DatePickerDialog(context, onDateSetListener, getIntegerDate("yyyy"), getIntegerDate("MM") - 1, getIntegerDate("dd"));
        datePickerDialog.show();
    }

    public void getDateYMD(Context context, TextView textView, View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.textView = textView;
        datePickerDialog = new DatePickerDialog(context, onDateSetListener, getIntegerDate("yyyy"), getIntegerDate("MM") - 1, getIntegerDate("dd"));
        datePickerDialog.show();
    }

    public void getDateTime(Context context, TextView textView) {
        onClickListener = null;
        this.textView = textView;
        timePickerDialog = new TimePickerDialog(context, onTimeSetListener, getIntegerDate("HH"), getIntegerDate("mm"), true);
        timePickerDialog.show();
    }

    public void getDateTime(Context context, TextView textView, View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        this.textView = textView;
        timePickerDialog = new TimePickerDialog(context, onTimeSetListener, getIntegerDate("HH"), getIntegerDate("mm"), true);
        timePickerDialog.show();
    }

}
