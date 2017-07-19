package com.example.wiss.Option;

import android.app.Activity;

import com.example.wiss.sound.SoundHandler;

/**
 * Created by wiss on 18/07/17.
 */

public abstract class Options {

    public SoundHandler sh = null;
    public int ID = 0;
    public String displayText;

    public Options(int ID, String title) {
        this.sh = new SoundHandler(ID);
        this.ID = ID;
        this.displayText = title;

    }

    public abstract void runOption(Activity act);

    public void playTitleSound()
    {
        this.sh.playSound();
    }

    public void stopTitleSound()
    {
            this.sh.stopSound();
    }
}
