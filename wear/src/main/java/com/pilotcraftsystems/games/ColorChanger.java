package com.pilotcraftsystems.games;

import android.util.Log;

/**
 * Created by csastudent2015 on 4/22/16.
 * csastudent2015 is super cool.
 */
public class ColorChanger {
    private int max;
    private int min;
    private int target;
    public final int MAX_RGB=255;
    public final int MIN_RGB=0;
    private static int red;
    private static int blue;
    public final String TAG= "IMPORTANT INFO";
    public ColorChanger(int max, int min, int target){
    this.max=max;
        this.min=min;
        this.target=target;
        red=MAX_RGB;
        blue=MAX_RGB;

    }

    public void updateRGB(int heartBeet){
        int range;
        heartBeet= clamp(heartBeet,min,max);
        //if the beat hits the target
        if(heartBeet==target){
            //max red and blue
            red=255;
            blue=255;
            Log.i(TAG,"The red value is: "+red+", The blue value is: "+blue);
        }
        //if the beat is below the target...
        else if(heartBeet<target){
            range=target-min;
            //remove some red
            red= (int)(((heartBeet-min)/range)*MAX_RGB);
            blue=255;
            Log.i(TAG,"The red value is: "+red+", The blue value is: "+blue);
        }
        //if the beat is above the target...
        else{
            range=max-target;
            red=255;
            //remove some blue
            blue=(int)(((range-(heartBeet-target))/range)*MAX_RGB);
            Log.i(TAG,"The red value is: "+red+", The blue value is: "+blue);

        }
    }
    public ColorChanger testingUpdateRGB(int heartBeet){
        int range;
        heartBeet= clamp(heartBeet,min,max);
        //if the beat hits the target
        if(heartBeet==target){
            //max red and blue
            red=255;
            blue=255;
            return  this;

        }
        //if the beat is below the target...
        else if(heartBeet<target){
            range=target-min;
            //remove some red
            red= (int)(((heartBeet-min)/range)*MAX_RGB);
            blue=255;
            return this;
        }
        //if the beat is above the target...
        else{
            range=max-target;
            red=255;
            //remove some blue
            blue=(int)(((range-(heartBeet-target))/range)*MAX_RGB);
            return this;

        }
    }

    public int getRed(){
        return red;
    }
    public int getBlue(){
        return blue;
    }

    public int clamp(int value, int min, int max){
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
