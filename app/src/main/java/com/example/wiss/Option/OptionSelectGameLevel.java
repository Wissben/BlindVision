package com.example.wiss.Option;

import android.app.Activity;
import android.content.Intent;

import com.example.wiss.gameGen.FirstGameGen;
import com.example.wiss.gameGen.TutorialSwipeGameGen;
import com.example.wiss.myapplication.GameActivity;

/**
 * Created by wiss on 19/07/17.
 */

public class OptionSelectGameLevel extends Options {
    public OptionSelectGameLevel(int ID, String title) {
        super(ID, title);
    }

    @Override
    public void runOption(Activity act)
    {
        FirstGameGen gameGen = new FirstGameGen(4);
        gameGen.generateGameParams();

        // starting game activity with the generated parameters
        Intent intent = new Intent(act, GameActivity.class);
        act.startActivity(intent);
    }
}
