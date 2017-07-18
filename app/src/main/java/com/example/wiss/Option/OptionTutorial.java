package com.example.wiss.Option;

import android.app.Activity;
import android.util.Log;

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

    }


}
