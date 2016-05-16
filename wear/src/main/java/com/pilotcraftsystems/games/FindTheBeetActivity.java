package com.pilotcraftsystems.games;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pilotcraftsystems.main.HeartRateReader;
import com.pilotcraftsystems.main.R;

/**
 * FindTheBeetActivity
 *
 * Class implements the user interface for the Find The Beet game. Works in conjunction with the
 * {@code FindTheBeet} class.
 *
 * @Author Chris Minnig, Dutch Clark, Kristopher Rollert, and Damian Ugalde
 * @date 2016-05-10
 */
public class FindTheBeetActivity extends WearableActivity {

    TextView mHeartRate;
    FindTheBeet findTheBeet;
    HeartRateReader heartRateReader;
    TextView mCurrentHeart;

    private FrameLayout background;

    public static final int BEET_FUDGE = 5;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final String TAG = "FindTheBeetActivity";

    /**
     * Called when the activity is created. Initializes all the instance variables.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.find_the_beet);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            findTheBeet = new FindTheBeet();
            mHeartRate = (TextView) findViewById(R.id.heartText);
            background = (FrameLayout) findViewById(R.id.container);
            mCurrentHeart=(TextView) findViewById(R.id.target);
            mCurrentHeart.setText(""+findTheBeet.getBeetToFind());

            if(!checkPermissions()){
                requestPermissions();
            }

            heartRateReader = new HeartRateReader(this){
                /**
                 * Called each time the heart rate sensor receives a new reading. Updates the
                 * game based on the current heart rate.
                 * @param event The value that is read from the device sensors.
                 */
                @Override
                public void onSensorChanged(SensorEvent event) {
                    if(event.sensor.getType() == Sensor.TYPE_HEART_RATE) {

                        if(findViewById(R.id.loading_spinner).getVisibility() != View.GONE){
                            findViewById(R.id.loading_spinner).setVisibility(View.GONE);
                            ((TextView) findViewById(R.id.heartText)).setTextColor(Color.rgb(38,38,38));
                        }

                        Log.i(TAG, "Heart rate change: " + (int) event.values[0]);
                            if(event.values[0] != 0) {
                                if(Math.abs(event.values[0] - findTheBeet.getBeetToFind()) <= BEET_FUDGE){
                                    background.setBackgroundColor(Color.GREEN);
                                    findTheBeet.newBeet();
                                    mCurrentHeart.setText(""+findTheBeet.getBeetToFind());
                                }else {
                                    String color = findTheBeet.update((int) (event.values[0]));
                                    int red = Integer.parseInt(color.substring(0, color.indexOf("-")));
                                    int blue = Integer.parseInt(color.substring(color.indexOf("-") + 1));
                                    background.setBackgroundColor(Color.rgb(red, 0, blue));
                                }
                                mHeartRate.setText((int) (event.values[0]) + "");


                            }
                    }else {
                        Log.i(TAG, "Not a heart rate sensor.");
                    }
            }

        };

    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.BODY_SENSORS);

        if(shouldProvideRationale){
        ActivityCompat.requestPermissions(FindTheBeetActivity.this,
                new String[]{Manifest.permission.BODY_SENSORS},
                REQUEST_PERMISSIONS_REQUEST_CODE);


        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(FindTheBeetActivity.this,
                    new String[]{Manifest.permission.BODY_SENSORS},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    /**
     * Called when the activity is destroyed, destroys the heart rate reader.
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        heartRateReader.destroy();
    }

    /**
     * Called when the activity is paused, destroys the heart rate reader.
     */
    @Override
    public void onPause(){
        super.onPause();
        heartRateReader.destroy();
    }

    /**
     * Called when the activity is stopped, destroys the heart rate reader.
     */
    @Override
    public void onStop(){
        super.onStop();
        heartRateReader.destroy();
    }

    /**
     * Called when the activity is resumed, and creates a new heartRateReader object.
     */
    @Override
    public void onResume(){
        super.onResume();
        heartRateReader = new HeartRateReader(this);
    }
}
