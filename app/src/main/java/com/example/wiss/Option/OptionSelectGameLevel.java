package com.example.wiss.Option;

import android.app.Activity;
import android.content.Intent;

import com.example.wiss.game.SimpleGameLogic;
import com.example.wiss.gameGen.FirstGameGen;
import com.example.wiss.gameGen.GameGen;
import com.example.wiss.gameGen.SequenceGameManager;
import com.example.wiss.gameGen.TutorialGameGen;
import com.example.wiss.gameGen.TutorialSwipeGameGen;
import com.example.wiss.myapplication.FileIO;
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
        FileIO fileIO = new FileIO("tutorial");
        if(fileIO.readFromFile(act.getApplicationContext()).equals("done")) {
            GameGen gameGen = SequenceGameManager.generateSequence(new FirstGameGen(2),
                    new FirstGameGen(3),
                    new FirstGameGen(4),
                    new FirstGameGen(5));
            gameGen.generateGameParams();

            // starting game activity with the generated parameters
            Intent intent = new Intent(act, GameActivity.class);
            act.startActivity(intent);
        }
        else
        {
            fileIO.writeToFile("done",act.getApplicationContext());
            GameGen gameGen = SequenceGameManager.generateSequence(new TutorialGameGen(),
                    new FirstGameGen(2),
                    new FirstGameGen(3),
                    new FirstGameGen(4),
                    new FirstGameGen(5));
            gameGen.generateGameParams();

            // starting game activity with the generated parameters
            Intent intent = new Intent(act, GameActivity.class);
            act.startActivity(intent);

        }
    }
}
