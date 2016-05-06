package com.pilotcraftsystems.games;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.WindowManager;
import android.widget.FrameLayout;
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

    private FrameLayout bg;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.find_the_beat);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            findTheBeet = new FindTheBeet();
            mHeartRate = (TextView) findViewById(R.id.heartText);
            bg = (FrameLayout) findViewById(R.id.container);
            bg.setBackgroundColor(Color.rgb(125, 125, 125));
            heartRateReader = new HeartRateReader(this){
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if(event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
                        Log.i(TAG, "Heart rate change: " + (int) event.values[0]);
                            if(event.values[0] != 0) {
                                String color = findTheBeet.update((int)(event.values[0]));
                                int red = Integer.parseInt(color.substring(0, color.indexOf("-")));
                                int blue = Integer.parseInt(color.substring(color.indexOf("-") + 1));
                                bg.setBackgroundColor(Color.rgb(red, 0, blue));
                                mHeartRate.setText((int)(event.values[0]) + "");
                                if(event.values[0]==findTheBeet.beetToFind){
                                    findTheBeet.beetSet();
                                }
                            }
                    }else {
                        Log.i(TAG, "Not a heart rate sensor.");
                    }
            }

        };

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        heartRateReader.destroy();
    }
}
