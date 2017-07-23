package com.example.wiss.updater;

/**
 *
 * Created by ressay on 12/07/17.
 */

public interface Updatable
{
    /**
     * update method called in Updater thread
     * this will allow updating sound, units, and other necessary updates regularly in short periods depending how the game evolves
     */
    void update();
    void pause();
    void resume();
    void stop();

}
