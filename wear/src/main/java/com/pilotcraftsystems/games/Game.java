package com.pilotcraftsystems.games;

import android.util.Log;

/**
 * Created by 393359 on 4/21/16.
 */
public abstract class Game {

    private boolean running = false;

    public abstract void update();

    public abstract void render();

    public void loop(){
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
                Log.i("Game loop Frames:", frames + "");
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public void start(){
        running = true;
    }

    public void stop(){
        running = false;
    }

    public void toggle(){
        running = !running;
    }

}
