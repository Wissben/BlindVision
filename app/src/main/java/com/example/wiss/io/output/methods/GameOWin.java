package com.example.wiss.io.output.methods;

import android.content.Intent;

import com.example.wiss.gameGen.GameGen;
import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.sound.SoundHandler;

/**
 * Created by ressay on 19/07/17.
 */

public class GameOWin extends GameO
{
    private int resID;
    private GameGen gameGen = null;
    public GameOWin(int resID)
    {
        this.resID = resID;
    }

    /**
     * play the winner sound.
     * if there is a next Game to be generated then generates the game and start it.
     * else quit the current gameActivity
     * @param param
     * @param gameActivity
     */
    @Override
    public void output(String param, GameActivity gameActivity)
    {
        SoundHandler sh = new SoundHandler(resID);
        sh.playSound();
        sh.blockThreadTillSoundEnd();
        if(gameGen != null)
        {
            gameGen.generateGameParams();
            Intent intent = new Intent(gameActivity, GameActivity.class);
            gameActivity.finish();
            gameActivity.startActivity(intent);
        }
        else gameActivity.finish();
    }
}
