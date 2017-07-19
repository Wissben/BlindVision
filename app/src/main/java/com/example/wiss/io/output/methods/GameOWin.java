package com.example.wiss.io.output.methods;

import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.sound.SoundHandler;
import com.example.wiss.sound.SoundUpdater;

/**
 * Created by ressay on 19/07/17.
 */

public class GameOWin extends GameO
{
    private int resID;
    GameOWin(int resID)
    {
        this.resID = resID;
    }
    @Override
    public void output(String param, GameActivity gameActivity)
    {
        SoundHandler sh = new SoundHandler(resID);


    }
}
