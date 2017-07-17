package com.example.wiss.sound;

import com.example.wiss.updater.Updatable;

import java.util.LinkedList;

/**
 * class that describes how sound should be produced, sound sources should use subclasses of this class to produce sound
 * Created by ressay on 13/07/17.
 */

public abstract class UnitSoundManager implements Updatable
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

    public void stopSound()
    {
        int size = shs.size();
        for(int i=0;i<size;i++)
            shs.get(i).stopSound();
    }

    public void pauseSound()
    {
        int size = shs.size();
        for(int i=0;i<size;i++)
            shs.get(i).pauseSound();
    }

    public void resumeSound()
    {
        int size = shs.size();
        for(int i=0;i<size;i++)
            shs.get(i).playSound();
    }

    @Override
    public void pause() {
        pauseSound();
    }

    @Override
    public void resume() {
        update();
    }

    @Override
    public void stop()
    {

    }
}
