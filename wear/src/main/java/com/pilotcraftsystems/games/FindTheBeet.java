package com.pilotcraftsystems.games;

import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;

/**
 * Created by 393359 on 4/21/16.
 */
public class FindTheBeet {

    public static final String TAG = "FindTheBeet";
    private int beetToFind;

    private BoxInsetLayout bg;

    public static final int BASE_HR = 80;
    public static final int HR_RANDOMNESS = 20;


    public FindTheBeet(){
        beetToFind = (int) (Math.random() * HR_RANDOMNESS + BASE_HR);

    }

    public String update(int heartRate){
        return updateRGB(heartRate, 60, 120, beetToFind);
    }

    public static String updateRGB(int heartBeet,int min, int max, int target){
        double range;
        int red=255;
        int blue=255;
        heartBeet= clamp(heartBeet,min,max);
        //if the beat hits the target
        if(heartBeet==target){
            //max red and blue
            red=255;
            blue=255;
            Log.i(TAG,"The red value is: "+red+", The blue value is: "+blue);
            return ""+ Integer.toHexString(red)+Integer.toHexString(0)+Integer.toHexString(blue);
        }
        //if the beat is below the target...
        else if(heartBeet<target){
            range=target-min;
            //remove some red
            red= (int)(((heartBeet-min)/range)*255);
            blue=255;
            Log.i(TAG,"The red value is: "+red+", The blue value is: "+blue);
            return ""+ Integer.toHexString(red)+Integer.toHexString(0)+Integer.toHexString(blue);
        }
        //if the beat is above the target...
        else{
            range=max-target;
            red=255;
            //remove some blue
            blue=(int)(((range-(heartBeet-target))/range)*255);
            Log.i(TAG,"The red value is: "+red+", The blue value is: "+blue);
            return ""+ Integer.toHexString(red)+Integer.toHexString(0)+Integer.toHexString(blue);

        }
    }

    public static int clamp(int value, int min, int max){
        if(value>max){
            return max;
        }
        else if(value<min){
            return min;
        }
        else{
            return value;
        }
    }

}
