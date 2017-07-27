package com.example.wiss.Option;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.wiss.sound.SoundHandler;

/**
 * Created by wiss on 18/07/17.
 */

public class OptionExit extends   Options{
    public OptionExit(int ID,String displayText) {
        super(ID,displayText);
    }

    @Override
    public void runOption(Activity act) {
        Log.d("run", "run EXIT: "+act);
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        act.startActivity(homeIntent);
    }

}
