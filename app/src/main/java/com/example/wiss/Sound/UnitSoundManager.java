package com.example.wiss.Sound;

import java.util.LinkedList;

/**
 * class that describes how sound should be produced, sound sources should use subclasses of this class to produce sound
 * Created by ressay on 13/07/17.
 */

public abstract class UnitSoundManager implements SoundUpdatable
{
    /**
     * a Unit's sound needs SoundMapManager to manage sound on a map
     */
    protected SoundMapManager smm;
    /**
     * a list of SoundHandler if it produces multiple sounds
     */
    protected LinkedList<SoundHandler> shs = new LinkedList<>();

    UnitSoundManager(double maxDistance)
    {
        smm = new SoundMapManager(maxDistance);
    }

    protected SoundMapManager getSoundMapManager()
    {
        return smm;
    }

    protected LinkedList<SoundHandler> getSoundHandlerList()
    {
        return shs;
    }

    public void addSound(SoundHandler sh)
    {
        shs.add(sh);
    }

    public void addSound(int resID)
    {
        shs.add(new SoundHandler(resID));
    }
}
