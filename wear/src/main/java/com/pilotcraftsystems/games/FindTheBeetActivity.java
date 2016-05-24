package com.pilotcraftsystems.games;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pilotcraftsystems.main.HeartRateReader;
import com.pilotcraftsystems.main.R;
import com.pilotcraftsystems.main.SharedPreferencesThing;

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

    FindTheBeet findTheBeet;
    HeartRateReader heartRateReader;
    TextView mCurrentHeart;
    TextView mTarget;
    ProgressBar loadingSpinner;
    boolean showLoading;

    public SharedPreferencesThing _appPrefs;


    private FrameLayout background;
    private int highScore;
    private int score;

    public static final int BEET_FUDGE = 2;
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
            _appPrefs = new SharedPreferencesThing(getApplicationContext());
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            findTheBeet = new FindTheBeet();
            mCurrentHeart = (TextView) findViewById(R.id.heartText);
            mTarget = (TextView) findViewById(R.id.target);
            background = (FrameLayout) findViewById(R.id.container);
            loadingSpinner = (ProgressBar) findViewById(R.id.loading_spinner);
            mTarget.setText(""+findTheBeet.getBeetToFind());

            highScore = Integer.parseInt(_appPrefs.getSmsBody());
            score = 0;


            mCurrentHeart.setVisibility(View.GONE);
            mTarget.setVisibility(View.GONE);

            loadingSpinner.setVisibility(View.VISIBLE);

            showLoading = true;

            if(!checkPermissions()){
                requestPermissions();
            }
            if(!checkPermissions()){
                AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());

                builder.setTitle("Beetz");

                builder.setMessage("Sorry, but you need to accept the permissions to use Beetz.")
                        .setCancelable(false)
                        .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                                System.exit(0);
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

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

                        if(showLoading){
                            showLoading = false;
                            findViewById(R.id.loading_spinner).setVisibility(View.GONE);
                            mCurrentHeart.setVisibility(View.VISIBLE);
                            mTarget.setVisibility(View.VISIBLE);
                            mCurrentHeart.setTextColor(Color.rgb(38,38,38));
                        }

                        Log.i(TAG, "Heart rate change: " + (int) event.values[0]);
                            if(event.values[0] != 0) {
                                if(Math.abs(event.values[0] - findTheBeet.getBeetToFind()) <= BEET_FUDGE){
                                    background.setBackgroundColor(Color.GREEN);
                                    findTheBeet.newBeet();
                                    mCurrentHeart.setText(""+findTheBeet.getBeetToFind());
                                    if (score>highScore){
                                        _appPrefs.saveSmsBody(highScore + "");
                                    }
                                }else {
                                    String color = findTheBeet.update((int) (event.values[0]));
                                    int red = Integer.parseInt(color.substring(0, color.indexOf("-")));
                                    int blue = Integer.parseInt(color.substring(color.indexOf("-") + 1));
                                    background.setBackgroundColor(Color.rgb(red, 0, blue));
                                }
                                mCurrentHeart.setText((int) (event.values[0]) + "");


                            }
                    }else {
                        Log.i(TAG, "Not a heart rate sensor.");
                    }
            }

        };

        if(!heartRateReader.hasHeartRateSensor()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getApplicationContext());

            builder.setTitle("Beetz");

            builder.setMessage("Sorry, but you need to have a heart rate sensor to use Beetz.")
                    .setCancelable(false)
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                            System.exit(0);
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

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
