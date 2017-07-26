package com.example.wiss.Option;

import android.app.Activity;
import android.content.Intent;

import com.example.wiss.gameGen.ClassicGameGen;
import com.example.wiss.gameGen.RunawayGameGen;
import com.example.wiss.myapplication.GameActivity;

/**
 * Created by wiss on 26/07/17.
 */

public class OptionRunaway extends Options {
    public OptionRunaway(int ID, String title) {
        super(ID, title);
    }

    @Override
    public void runOption(Activity act) {
        RunawayGameGen gameGen = new RunawayGameGen();
        gameGen.generateGameParams();

        // starting game activity with the generated parameters
        Intent intent = new Intent(act, GameActivity.class);
        act.startActivity(intent);
    }
}
