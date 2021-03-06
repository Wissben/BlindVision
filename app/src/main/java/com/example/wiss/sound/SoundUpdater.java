package com.example.wiss.sound;

import android.util.Log;

import com.example.wiss.units.SoundSource;
import com.example.wiss.units.Unit;
import com.example.wiss.updater.Updatable;

import java.util.LinkedList;

/**
 * Created by ressay on 17/07/17.
 */

public class SoundUpdater implements Updatable
{

    private boolean cancelled = false;
    private boolean paused = false;
    private boolean ended = false;
    LinkedList<UnitSoundManager> sounds = new LinkedList<>();

    @Override
    public void update()
    {
        if(isPaused()) return;
        Log.d("TUTORIAL","size of sounds is " + sounds.size());
        int size = sounds.size();
        for(int i=0; i<size;i++)
            sounds.get(i).update();
        Log.d("myTag","updating sounds inside SoundUpdater!");
    }

    @Override
    public void pause() {
        pauseSounds();
    }

    @Override
    public void resume() {
        resumeSound();
    }

    @Override
    public void stop() {
        ended = true;
    }

    @Override
    public boolean hasEnded() {
        return ended;
    }

    public void addToUpdate(UnitSoundManager su)
    {

        if(!sounds.contains(su))
            sounds.add(su);
    }

    public void addToUpdate(LinkedList<UnitSoundManager> sus)
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

    public void cancel()
    {
        cancelled = true;
        int size = sounds.size();
        for(int i=0; i<size;i++)
            sounds.get(i).stop();
        releaseAllSounds();
    }

    public void releaseAllSounds()
    {
        for(int i=0;i<sounds.size();i++)
            sounds.get(i).releaseMediaPlayers();
    }

    public void removeSoundFromUpdate(UnitSoundManager soundManager)
    {
        sounds.remove(soundManager);
    }

    public void removeAndReleaseSoundFromUpdate(UnitSoundManager soundManager)
    {
        removeSoundFromUpdate(soundManager);
        soundManager.releaseMediaPlayers();
    }

    public void clear()
    {
        sounds.clear();
    }

    public void clearAndRelease()
    {
        for(int i=0;i<sounds.size();i++)
            sounds.get(i).releaseMediaPlayers();
        sounds.clear();
    }

    public boolean isPaused()
    {
        return paused;
    }

    public void resumeSound()
    {
        if(!isPaused()) return;

        paused = false;
        int size = sounds.size();
        for(int i=0; i<size;i++)
            sounds.get(i).resume();
    }

    public void pauseSounds()
    {
        if(isPaused()) return;
        paused = true;
        int size = sounds.size();
        for(int i=0; i<size;i++)
            sounds.get(i).pause();
    }


}
