package com.example.wiss.sound;

import android.support.annotation.CallSuper;
import com.example.wiss.updater.Updatable;

/**
 * Created by ressay on 20/07/17.
 */

public abstract class SoundManager implements Updatable
{
    protected boolean playing = false;
    protected boolean paused = false;

    @Override @CallSuper
    public void update()
    {
        if(!isPaused())
            playing = true;
    }

    @Override @CallSuper
    public void pause() { paused = true; }

    @Override @CallSuper
    public void resume() {
        playing = true;
        paused = false;
    }

    @Override @CallSuper
    public void stop() {
        playing = false;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void blockThreadWhilePlaying()
    {
        while(isPlaying())
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    public boolean isPaused()
    {
        return paused;
    }

}
