package com.example.wiss.myapplication;

import android.app.Activity;
import android.content.Intent;

import com.example.wiss.gameGen.GameGen;
import com.example.wiss.sound.SequenceSoundManager;

/**
 * transitionChoice is a class that gives choices to a player after he loses/wins in a GameActivity
 * if he wants to play another game or exit the game
 * Created by ressay on 25/07/17.
 */

public class TransitionChoice implements Choiceable
{
    GameGen gameGen;
    Activity gameActivity;

    public TransitionChoice(GameGen gameGen)
    {
        this.gameGen = gameGen;
    }

    /**
     * swipe up/down if he wants to exit
     * left/right if he wants to start another game
     */
    @Override
    public void up()
    {
        BlindActivity.getCurrentActivity().finish();
    }

    @Override
    public void down()
    {
        up();
    }

    @Override
    public void left()
    {
        gameGen.generateGameParams();
        Activity act = BlindActivity.getCurrentActivity();
        // starting game activity with the generated parameters
        Intent intent = new Intent(act, GameActivity.class);
        act.finish();
        act.startActivity(intent);
    }

    @Override
    public void right()
    {
        left();
    }

    public static void startTransition(SequenceSoundManager ssm,GameGen gameGen)
    {
        Activity gameActivity = BlindActivity.getCurrentActivity();

        TransitionChoice transitionChoice = new TransitionChoice(gameGen);
        ChoiceParameters.setSequenceSoundManager(ssm);
        ChoiceParameters.setChoiceable(transitionChoice);

        Intent intent = new Intent(gameActivity, ChoiceActivity.class);
        gameActivity.startActivity(intent);
    }
}
