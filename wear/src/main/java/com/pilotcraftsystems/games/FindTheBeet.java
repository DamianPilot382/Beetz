package com.pilotcraftsystems.games;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by 393359 on 4/21/16.
 */
public class FindTheBeet implements Game {

    private Color bgColor;
    private boolean running;
    public static final String TAG = "FindTheBeet";


    public FindTheBeet(){
        running = false;
        bgColor = new Color();
    }


    @Override
    public void update() {
        findBGColor();
    }

    @Override
    public void render() {

    }

    @Override
    public void loop() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                update();
                delta--;
            }
            if(running)
                render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;

                //Printer
                Log.i(TAG, Integer.toString(frames));
            }
        }
        stop();
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void start() {
        running = true;
        loop();
    }

    @Override
    public void stop() {
        running = false;
    }

    public void findBGColor(int heartBeet){

    }

}
