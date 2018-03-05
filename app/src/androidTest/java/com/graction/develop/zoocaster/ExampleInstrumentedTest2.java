package com.graction.develop.zoocaster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graction.developer.zoocaster.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest2 {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.graction.develop.zoocaster", appContext.getPackageName());
    }

    public class MainActivity extends AppCompatActivity {
        private TextView contentTxt;
        public TextView battery_textView;
        private int ratio;

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
//            setContentView(R.id.layout);

            battery_textView = (TextView) findViewById(1);
            contentTxt = (TextView) findViewById(1);

            this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        }

        private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent intent) {
                // TODO Auto-generated method stub
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
                ratio = (level * 100) / scale;
                new Handler().post(new Runnable() {
                    @Override
                    public void run() {
                        contentTxt.setText(ratio + "%");
                        battery_textView.setText(ratio+"");
                    }
                });
            }
        };
    }
}
