package com.example.wiss.Option;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.wiss.gameGen.FirstGameGen;
import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.myapplication.MainActivity;
import com.example.wiss.sound.SoundHandler;

/**
 * Created by wiss on 18/07/17.
 */

public class OptionTutorial extends Options {

    public OptionTutorial(int ID,String displayText) {
        super(ID,displayText);
    }

    @Override
    public void runOption(Activity act) {
        Log.d("run", "runOption: "+act);
        // generating game logic and gameIO
        FirstGameGen gameGen = new FirstGameGen();
        gameGen.generateGameParams();

        // starting game activity with the generated parameters
        Intent intent = new Intent(act, GameActivity.class);
        act.startActivity(intent);

    }


}
