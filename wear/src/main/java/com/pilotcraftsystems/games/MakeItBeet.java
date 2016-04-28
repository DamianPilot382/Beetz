package com.pilotcraftsystems.games;

/**
 * Created by 393359 on 4/21/16.
 */
public class MakeItBeet implements Game {

    private boolean running;

    public MakeItBeet(){
        running = false;
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {

    }

    @Override
    public void loop() {

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
}
