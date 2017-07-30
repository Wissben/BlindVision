package com.example.wiss.Option;

import android.app.Activity;
import android.content.Intent;

import com.example.wiss.gameGen.ClassicGameGen;
import com.example.wiss.gameGen.FirstGameGen;
import com.example.wiss.gameGen.GameGen;
import com.example.wiss.gameGen.RunawayGameGen;
import com.example.wiss.gameGen.SequenceGameManager;
import com.example.wiss.gameGen.TutorialGameGen;
import com.example.wiss.myapplication.FileIO;
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

        FileIO fileIO = new FileIO("tutorial");
        if(fileIO.readFromFile(act.getApplicationContext()).equals("done")) {
            RunawayGameGen gameGen = new RunawayGameGen();
            gameGen.generateGameParams();

            // starting game activity with the generated parameters
            Intent intent = new Intent(act, GameActivity.class);
            act.startActivity(intent);
        }
        else
        {
            fileIO.writeToFile("done",act.getApplicationContext());
            GameGen gameGen = SequenceGameManager.generateSequence(new TutorialGameGen(),
                    new RunawayGameGen());
            gameGen.generateGameParams();

            // starting game activity with the generated parameters
            Intent intent = new Intent(act, GameActivity.class);
            act.startActivity(intent);

        }
    }
}
