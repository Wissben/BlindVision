package com.example.wiss.io.output.methods;

import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.sound.SoundHandler;
import com.example.wiss.sound.SoundManager;

/**
 * output a simple sound in the mid of the game and blocks the game thread
 * Created by ressay on 20/07/17.
 */

public class GameOSimpleSound extends GameO
{
    private SoundManager soundManager;
    private boolean stop = false;

    public GameOSimpleSound(SoundManager soundManager)
    {
        this.soundManager = soundManager;
    }

    public void setStopThreadWhilePlaying(boolean stop)
    {
        this.stop = stop;
    }

    @Override
    public void output(String param, GameActivity gameActivity)
    {
        // this will make the sound manager start working and thus producing the desired sound given in input
        gameActivity.getUpdater().addToUpdate(soundManager);

        while (stop && soundManager.isPlaying())
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
