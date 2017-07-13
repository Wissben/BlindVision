package com.example.wiss.Sound;

/**
 * any unit in map that has sound to produce should implement one of UnitSoundManager's subclasses to manage its sound
 * Created by ressay on 12/07/17.
 */

public interface SoundUpdatable
{
    /**
     * update method called in SoundUpdater thread
     * this will allow updating sound regularly in short periods depending how the game evolves
     */
    void update();
}
