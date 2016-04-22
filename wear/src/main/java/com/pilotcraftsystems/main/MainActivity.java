package com.pilotcraftsystems.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.TextView;

import com.pilotcraftsystems.games.FindTheBeetActivity;

/**
 * MainActivity
 *
 * @Author Chris Minnig, Dutch Clark, Kristopher Rollert, and Damian Ugalde
 */
public class MainActivity extends WearableActivity {

    private static final String TAG = "MainActivity";
    private TextView mTextViewHeart;
    HeartRateReader heartRateReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        heartRateReader = new HeartRateReader(this);

        Intent i = new Intent(MainActivity.this, FindTheBeetActivity.class);
        heartRateReader.destroy();
        startActivity(i);

    }

    @Override
    public void onResume(){
        super.onResume();
    }

}