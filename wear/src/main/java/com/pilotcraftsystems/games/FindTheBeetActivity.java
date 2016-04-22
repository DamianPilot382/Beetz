package com.pilotcraftsystems.games;

import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.pilotcraftsystems.main.HeartRateReader;
import com.pilotcraftsystems.main.R;

/**
 * Created by 393359 on 4/21/16.
 */
public class FindTheBeetActivity extends WearableActivity {

    TextView mHeartRate;
    ImageView arrow;
    FindTheBeet findTheBeat;
    HeartRateReader heartRateReader;

    boolean running;

    int rotation;
    int beetToFind;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_the_beat);
        mHeartRate = (TextView) findViewById(R.id.heartText);
        arrow = (ImageView) findViewById(R.id.arrow);

        heartRateReader = new HeartRateReader(this);

        rotation = 0;
        beetToFind = (int)(Math.random() * 40 + 60);

        running = true;

        //startGame();

    }

    public void update() {
        int current = heartRateReader.getCurrentHeartRate();
        if(current > beetToFind){
            int diff = current - beetToFind;
            if(diff >= 50) rotation = -90;
            else if(diff >= 25) rotation = -45;
            else rotation = -10;
        }else if(current < beetToFind){
            int diff = current - beetToFind;
            if(diff >= 50) rotation = 90;
            else if(diff >= 25) rotation = 45;
            else rotation = 10;
        }else{
            //WIN! Do something here...
        }

        render();
    }

    public void render(){
        arrow.setRotation(rotation);
    }

    public void startGame(){
    }


}
