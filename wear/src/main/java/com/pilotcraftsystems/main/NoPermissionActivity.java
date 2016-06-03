package com.pilotcraftsystems.main;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;

/**
 * MainActivity
 *
 * @Author Chris Minnig, Dutch Clark, Kristopher Rollert, and Damian Ugalde
 * @date 2016-04-20
 * @version 1.1
 */
public class NoPermissionActivity extends WearableActivity {

    private static final String TAG = "NoPermissionActivity";
    public static final int TIME_OUT = 5000;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState Saved state of the activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_permissions);

        getActionBar().setDisplayHomeAsUpEnabled(false);


    }


}