package com.pilotcraftsystems.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.wearable.activity.WearableActivity;

import com.pilotcraftsystems.games.FindTheBeetActivity;

/**
 * MainActivity
 *
 * @Author Chris Minnig, Dutch Clark, Kristopher Rollert, and Damian Ugalde
 * @date 2016-04-20
 * @version 1.1
 */
public class SplashScreen extends WearableActivity {

    private static final String TAG = "SplashActivity";
    public static final int SPLASH_TIME_OUT = 3000;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState Saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        /**
         * Show splash screen with a timer.
         */
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run(){
                Intent i = new Intent(SplashScreen.this, FindTheBeetActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);



    }


}