package com.example.wiss.io.output.methods;

import android.content.Intent;

import com.example.wiss.gameGen.GameGen;
import com.example.wiss.myapplication.ChoiceParameters;
import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.myapplication.TransitionChoice;
import com.example.wiss.sound.SequenceSoundManager;
import com.example.wiss.sound.SoundHandler;
import com.example.wiss.updater.Updater;

/**
 * Created by ressay on 19/07/17.
 */

public class GameOEnd extends GameO
{
    private int[] resIDs;
    private GameGen gameGen = null;
    public GameOEnd(int... resID)
    {
        resIDs = new int[resID.length];
        for(int i=0;i<resID.length;i++)
            resIDs[i] = resID[i];
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
        SequenceSoundManager sequence = new SequenceSoundManager();
        sequence.addSounds(resIDs);
        // if there is a gameGen we generate next level to be played
        if(gameGen != null)
        {
            gameActivity.finish();
            TransitionChoice.startTransition(sequence,gameGen);
        }
        else // we run the sound sequence only and then we end the game activity
        {
            final GameActivity activity = gameActivity;
            final SequenceSoundManager ssm = sequence;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    activity.getUpdater().addToUpdate(ssm);
                    ssm.blockThreadWhilePlaying();
                    activity.finish();
                }
            }).start();
        }
    }

    public GameGen getGameGen() {
        return gameGen;
    }

    public void setGameGen(GameGen gameGen) {
        this.gameGen = gameGen;
    }
}
