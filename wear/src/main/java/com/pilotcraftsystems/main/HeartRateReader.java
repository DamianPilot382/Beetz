package com.pilotcraftsystems.main;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * HeartRateReader
 *
 * This class implements the {@code SensorEventListener} class, so that it can use and read the
 * inputs from the many sensors available in the watch. However, this class focuses only on the
 * heart rate sensor. The method <b>destroy()</b> should be called whenever the activity is paused,
 * stopped, or destroyed. You should create a new instance of the class each time the activity is
 * resumed. The method <b>onSensorChange()</b> must be overwritten each time for full
 * implementation, since this method is called each time a heart rate read is received.
 *
 * @Author Chris Minnig, Dutch Clark, Kristopher Rollert, and Damian Ugalde
 * @date 2016-05-10
 */
public class HeartRateReader implements SensorEventListener {
public static boolean isNull=false;
    public static final String TAG = "HeartRateReader";
    private SensorManager mSensorManager;
    private Sensor mHeartRateSensor;
    private SensorEventListener sensorEventListener;
    private int currentHeartRate;

    /**
     * Creates a HeartRateReader object, with a sensor manager that only reads heart rate.
     * @param activity the current activity.
     */
    public HeartRateReader(Activity activity) {

        mSensorManager = ((SensorManager) activity.getSystemService(activity.SENSOR_SERVICE));
        mHeartRateSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if(mHeartRateSensor.equals(null)){
            isNull=true;
        }
        mSensorManager.registerListener(this, mHeartRateSensor, SensorManager.SENSOR_DELAY_NORMAL);
        Log.i(TAG, "LISTENER REGISTERED.");

        mSensorManager.registerListener(sensorEventListener, mHeartRateSensor,
                mSensorManager.SENSOR_DELAY_FASTEST);
        currentHeartRate = 0;
    }

    /**
     *
     * @return Previously calculated heart rate
     */
    public int getCurrentHeartRate(){
        return currentHeartRate;
    }

    /**
     * Unregisters the listener, so that the heart rate doesn't remain on and drain the watch
     * battery. This method should be called on pause, stop, and destroy of activity that created
     * the object.
     */
    public void destroy(){
        mSensorManager.unregisterListener(sensorEventListener, mHeartRateSensor);
    }

    /**
     * Overwritten method that is called everytime the sensor reads a new heart rate.
     * @param event The value that is read from the device sensors.
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            Log.i(TAG, "Heart rate change: " + (int) event.values[0]);
            currentHeartRate = (int) event.values[0];
        }else {
            Log.i(TAG, "Not a heart rate sensor.");
        }
    }

    /**
     * Called everytime the accuracy of the sensor is changed. Had to be implemented. Method
     * is empty.
     * @param sensor Sensor that accuracy changed.
     * @param i New precision of the method.
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}
