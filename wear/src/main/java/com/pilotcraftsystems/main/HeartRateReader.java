package com.pilotcraftsystems.main;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by Damian U. on 4/21/16.
 */
public class HeartRateReader implements SensorEventListener {

    public static final String TAG = "HeartRateReader";
    SensorManager mSensorManager;
    Sensor mHeartRateSensor;
    SensorEventListener sensorEventListener;

    int currentHeartRate;

    public HeartRateReader(Activity activity) {

        mSensorManager = ((SensorManager) activity.getSystemService(activity.SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "LISTENER REGISTERED.");

        mSensorManager.registerListener(sensorEventListener, mHeartRateSensor,
                mSensorManager.SENSOR_DELAY_FASTEST);
        currentHeartRate = 0;
    }

    public int getCurrentHeartRate(){
        return currentHeartRate;
    }

    public void destroy(){
        mSensorManager.unregisterListener(sensorEventListener, mHeartRateSensor);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            Log.i(TAG, "Heart rate change: " + (int) event.values[0]);
            currentHeartRate = (int) event.values[0];
        }else {
            Log.i(TAG, "Not a heart rate sensor.");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
