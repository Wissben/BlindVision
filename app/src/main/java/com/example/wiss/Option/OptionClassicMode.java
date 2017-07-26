package com.example.wiss.Option;

import android.app.Activity;
import android.content.Intent;

import com.example.wiss.gameGen.ClassicGameGen;
import com.example.wiss.gameGen.TutorialGameGen;
import com.example.wiss.myapplication.GameActivity;

/**
 * Created by wiss on 19/07/17.
 */

public class OptionClassicMode extends Options {
    public OptionClassicMode(int ID, String title) {
        super(ID, title);
    }

    @Override
    public void runOption(Activity act)
    {

        ClassicGameGen gameGen = new ClassicGameGen();
        gameGen.generateGameParams();

        // starting game activity with the generated parameters
        Intent intent = new Intent(act, GameActivity.class);
        act.startActivity(intent);
    }
}
