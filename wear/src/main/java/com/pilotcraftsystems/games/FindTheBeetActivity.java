package com.pilotcraftsystems.games;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pilotcraftsystems.main.HeartRateReader;
import com.pilotcraftsystems.main.R;

/**
 * Created by 393359 on 4/21/16.
 */
public class FindTheBeetActivity extends WearableActivity {

    TextView mHeartRate;
    FindTheBeet findTheBeet;
    HeartRateReader heartRateReader;

    private BoxInsetLayout bg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.find_the_beat);
            mHeartRate = (TextView) findViewById(R.id.heartText);
            RelativeLayout currentLayout = (RelativeLayout) findViewById(R.id.container);
            bg = (BoxInsetLayout) findViewById(R.id.container);
            heartRateReader = new HeartRateReader(this){
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                    Log.i(TAG, "Heart rate change: " + (int) event.values[0]);
                    bg.setBackgroundColor(Color.parseColor(findTheBeet.update((int) event.values[0])));
                }else {
                    Log.i(TAG, "Not a heart rate sensor.");
                }
            }

        };

    }
}
