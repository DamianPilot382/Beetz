package com.pilotcraftsystems.games;

/**
 * Created by 393359 on 4/21/16.
 */
public interface Game {


    void update();

    void render();

    void loop();

    boolean isRunning();

    void start();

    void stop();

}
