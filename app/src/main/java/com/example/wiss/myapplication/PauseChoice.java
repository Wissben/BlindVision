package com.example.wiss.myapplication;

import android.app.Activity;
import android.content.Intent;

import com.example.wiss.gameGen.GameGen;
import com.example.wiss.sound.SequenceSoundManager;

/**
 * Created by ressay on 24/07/17.
 */

public class PauseChoice implements Choiceable
{
    Activity gameActivity;

    /**
     * constructor with the activity from which we paused
     * @param activity
     */
    public PauseChoice(Activity activity)
    {
        gameActivity = activity;
    }


    /**
     * if he swipes up/down he ends the pausing
     */
    @Override
    public void up()
    {
        gameActivity.finish();
        BlindActivity.getCurrentActivity().finish();
    }

    @Override
    public void down()
    {
        up();
    }

    /**
     * if he swipes left/right he quits the activity from which he paused
     */

    @Override
    public void left()
    {
        BlindActivity.getCurrentActivity().finish();
    }

    @Override
    public void right()
    {
        left();
    }


    public static void pauseGame(GameActivity gameActivity)
    {
        SequenceSoundManager ssm = new SequenceSoundManager();
        ssm.addSounds(R.raw.pausesound);
        PauseChoice pauseChoice = new PauseChoice(gameActivity);
        ChoiceParameters.setSequenceSoundManager(ssm);
        ChoiceParameters.setChoiceable(pauseChoice);

        Intent intent = new Intent(gameActivity, ChoiceActivity.class);
        gameActivity.startActivity(intent);
    }

    public static void pauseGame(GameActivity gameActivity, SequenceSoundManager ssm)
    {
        PauseChoice pauseChoice = new PauseChoice(gameActivity);
        ChoiceParameters.setSequenceSoundManager(ssm);
        ChoiceParameters.setChoiceable(pauseChoice);

        Intent intent = new Intent(gameActivity, ChoiceActivity.class);
        gameActivity.startActivity(intent);
    }
}
