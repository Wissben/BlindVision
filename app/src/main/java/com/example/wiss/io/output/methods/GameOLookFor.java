package com.example.wiss.io.output.methods;

import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.myapplication.R;
import com.example.wiss.sound.SequenceSoundManager;

/**
 * Created by ressay on 20/07/17.
 */

public class GameOLookFor extends GameO
{
    /**
     * produce sound of "look for resID" where resID is the sound given in param
     * @param param contains the id of the resource used to produce the "look for" sound
     * @param gameActivity
     * @throws InterruptedException
     */
    @Override
    public void output(String param, GameActivity gameActivity)
    {
        int resID = Integer.valueOf(param);
        SequenceSoundManager ssm = new SequenceSoundManager();
        ssm.addSounds(R.raw.lookfor,resID);
        gameActivity.getUpdater().addToUpdate(ssm);
    }
}
