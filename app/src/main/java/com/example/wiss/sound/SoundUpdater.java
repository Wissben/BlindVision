package com.example.wiss.sound;

import com.example.wiss.units.SoundSource;

import java.util.LinkedList;

/**
 * Created by ressay on 12/07/17.
 */

public class SoundUpdater extends Thread
{


    private boolean cancelled = false;
    private LinkedList<SoundUpdatable> sounds = new LinkedList<>();

    public void startUpdating()
    {
        this.start();
    }

    public void run()
    {
        while(!isCancelled())
        {
            int size = sounds.size();
            for(int i=0; i<size;i++)
                sounds.get(i).update();
        }
    }

    public void addToUpdate(SoundUpdatable su)
    {
        sounds.add(su);
    }

    public void addToUpdate(LinkedList<SoundUpdatable> sus)
    {
        sounds.addAll(sus);
    }

    public void addSoundSourceToUpdate(SoundSource ss)
    {
        addToUpdate(ss.getUnitSoundManager());
    }

    public void addSoundSourcesToUpdate(LinkedList<SoundSource> ss)
    {
        int size = ss.size();
        for(int i=0;i<size;i++)
            addToUpdate(ss.get(i).getUnitSoundManager());
    }

    public boolean isCancelled()
    {
        return cancelled;
    }

    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public void cancel()
    {
        cancelled = true;
    }
}
