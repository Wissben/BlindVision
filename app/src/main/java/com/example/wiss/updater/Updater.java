package com.example.wiss.updater;

import android.util.Log;

import com.example.wiss.units.SoundSource;

import java.util.LinkedList;

/**
 * Created by ressay on 12/07/17.
 */

public class Updater extends Thread
{

    private boolean paused = false;
    private long delay = 100;
    private boolean cancelled = false;
    private LinkedList<Updatable> toUpdate = new LinkedList<>();
    private LinkedList<Updatable> pausedUpdate = new LinkedList<>();

    public Updater()
    {

    }

    public Updater(long delay)
    {
        this.delay = delay;
    }

    public void startUpdating()
    {
        this.start();
    }

    public void run()
    {

        while (!isCancelled())
        {
            long start = System.currentTimeMillis();

            if (!isPaused()) {
                int size = toUpdate.size();
                for (int i = 0; i < toUpdate.size(); i++)
                    if(toUpdate.get(i).hasEnded()) {
                        toUpdate.remove(i);
                        i--;
                    }
                    else
                        toUpdate.get(i).update();
                Log.d("Tag","updating inside updater size = " + toUpdate.size());
            }
            Log.d("Tag","still looping!");

            long elapsedTime = System.currentTimeMillis() - start;
            if (delay > elapsedTime)
                try {
                    sleep(delay - elapsedTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

    }

    public void addToUpdate(Updatable su)
    {
        toUpdate.add(su);
    }

    public void addToUpdate(LinkedList<Updatable> sus)
    {
        toUpdate.addAll(sus);
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

    public void clear()
    {
        toUpdate.clear();
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
        int size = toUpdate.size();
        for(int i=0; i<size;i++)
            toUpdate.get(i).stop();
        cancelled = true;
        Log.d("pause","updating cancelled");
    }

    public long getDelay()
    {
        return delay;
    }

    public void setDelay(long delay)
    {
        this.delay = delay;
    }

    public void pauseUpdating()
    {
        if(isPaused()) return;
        setPaused(true);
        int size = toUpdate.size();
        for(int i=0; i<size;i++)
            if(toUpdate.get(i).isPaused())
                pausedUpdate.add(toUpdate.get(i));
            else
            toUpdate.get(i).pause();
    }

    public void pauseAndStopUpdating()
    {
        cancelled = true;
        int size = toUpdate.size();
        for(int i=0; i<size;i++)
            if(toUpdate.get(i).isPaused())
                pausedUpdate.add(toUpdate.get(i));
            else
                toUpdate.get(i).pause();
    }

    public void resumeUpdating()
    {
        if(!isPaused()) return;
        setPaused(false);
        int size = toUpdate.size();
        for(int i=0; i<size;i++)
            if(!pausedUpdate.contains(toUpdate.get(i)))
            toUpdate.get(i).resume();

        pausedUpdate.clear();
        Log.d("pause","calling resume");
    }

    public boolean isPaused() {
        return paused;
    }

    private void setPaused(boolean paused) {
        this.paused = paused;
    }

    public Updater copy()
    {
        Updater updater = new Updater(delay);
        updater.addToUpdate(toUpdate);
        updater.setPaused(isPaused());
        return updater;
    }
}
