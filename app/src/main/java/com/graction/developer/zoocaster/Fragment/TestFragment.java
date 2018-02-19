package com.graction.developer.zoocaster.Fragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graction.developer.zoocaster.Data.DataStorage;
import com.graction.developer.zoocaster.Model.Item.AlarmItem;
import com.graction.developer.zoocaster.R;
import com.graction.developer.zoocaster.Service.AlarmService;
import com.graction.developer.zoocaster.UI.NotificationManager;
import com.graction.developer.zoocaster.Util.System.VSManager;
import com.graction.developer.zoocaster.databinding.FragmentTestBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.graction.developer.zoocaster.Util.Log.HLogger.LogType.INFO;

public class TestFragment extends BaseFragment {
    private FragmentTestBinding binding;
    private AlarmService alarmService;
    private boolean mBound = false; // 서비스 연결 여부

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AlarmService.AlarmBinder alarmBinder = (AlarmService.AlarmBinder) service;
            alarmService = alarmBinder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public static Fragment getInstance() {
        Fragment fragment = new TestFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, null, false);
        return binding.getRoot();
    }

    @Override
    protected void init(View view) {
        logger.log(INFO, "init");
        binding.setActivity(this);
        initService();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initService() {
        Intent serviceIntent = new Intent(getActivity(), AlarmService.class);
//        getActivity().bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    // onClick
    public void alarmTest(View view) {
//        getActivity().registerReceiver(new AlarmReceiver(), new IntentFilter(DataStorage.Action.RECEIVE_ACTION_SINGLE_ALARM));
        logger.log(INFO, "alarmTest onClick");
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(binding.fragmentTestETHour.getText() + ""));
        mCalendar.set(Calendar.MINUTE, Integer.parseInt(binding.fragmentTestETMinute.getText() + ""));
        mCalendar.set(Calendar.SECOND, Integer.parseInt(binding.fragmentTestETSecond.getText() + ""));
//        mCalendar.set(Calendar.SECOND, mCalendar.get(Calendar.SECOND)+3);
//        mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
//        mCalendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);

        Intent alarmIntent = new Intent(DataStorage.Action.RECEIVE_ACTION_SINGLE_ALARM);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getContext(),
                0,
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(
                AlarmManager.RTC_WAKEUP,
                mCalendar.getTimeInMillis(),
                pendingIntent
        );

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.log(INFO, simpleDateFormat.format(mCalendar.getTimeInMillis()));
    }

    public void test1() {
        VSManager.getInstance().vibrate(getActivity(), VSManager.PATTERN_1, VSManager.REPEAT_NONE);
    }

    public void test2() {
        VSManager.getInstance().vibrate(getActivity(),2000);
    }

    public void test3(boolean isSpeaker) {
        Context context = getActivity();
        AlarmItem item = new AlarmItem(3, "address", "memo", new int[]{1,1,1,1,1,1,1,1}, 0,0,5,1, isSpeaker);
        AlarmManager alarmManager = (android.app.AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(DataStorage.Action.RECEIVE_ACTION_ALARM_START);
//        Intent alarmIntent = new Intent(context, AlarmReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataStorage.Key.KEY_ALARM_ITEM, item);
        bundle.putSerializable(DataStorage.Key.KEY_NOTIFICATION_ITEM, new NotificationManager.NotificationItem("1", item.getMemo(), item.getMemo(), R.mipmap.ic_launcher_round));
        alarmIntent.putExtra(DataStorage.Key.KEY_BUNDLE, bundle);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                item.getIndex(),
                alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        alarmManager.set(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, pendingIntent);
//        alarmManager.setInexactRepeating(android.app.AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, 5000, pendingIntent);

    }
}
