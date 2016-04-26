package com.pilotcraftsystems.games;

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
    public ColorChanger(int max, int min, int target){
    max=this.max;
        min=this.min;
        target=this.target;
        red=MAX_RGB;
        blue=MAX_RGB;

    }

    public void updateRGB(int heartBeet){
        int range;
        if(heartBeet==target){
            red=255;
            blue=255;
        }else if(heartBeet<target){
            range=target-min;
            red= (int)(((heartBeet-min)/range)*MAX_RGB);
            blue=255;
        }else{
            range=max-target;
            blue=(int)(((heartBeet-target)/range)*MAX_RGB);
        }
    }

}
