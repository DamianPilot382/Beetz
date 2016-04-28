package com.pilotcraftsystems.games;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import com.pilotcraftsystems.main.HeartRateReader;
import com.pilotcraftsystems.main.R;

/**
 * Created by 393359 on 4/21/16.
 */
public class FindTheBeetActivity extends WearableActivity {

    TextView mHeartRate;
    FindTheBeet findTheBeat;
    HeartRateReader heartRateReader;

    boolean running;

    int rotation;
    int beetToFind;

    public static final int BASE_HR = 80;
    public static final int HR_RANDOMNESS = 20;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.find_the_beat);
            mHeartRate = (TextView) findViewById(R.id.heartText);

            heartRateReader = new HeartRateReader(this);

            rotation = 0;
            beetToFind = (int) (Math.random() * HR_RANDOMNESS + BASE_HR);

            running = true;

            //startGame();
        /**
         * INSERT BORING CODE HERE... Time for other people to do something. Im too lazy...
         */

    }
}
