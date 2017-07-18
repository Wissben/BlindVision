package com.example.wiss.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.wiss.Option.OptionExit;
import com.example.wiss.Option.OptionTutorial;
import com.example.wiss.Option.Options;
import com.example.wiss.sound.SoundHandler;

import java.util.LinkedList;

public class WelcomeActivity extends AppCompatActivity implements Choiceable {
    public View.OnTouchListener handleSwipe;
    int choice = 0;
    LinkedList<Options> options;
    static private Activity currentActivity = null;

    /* We put the static objects here (like gameLogic and stuff). */


    /* Methods =================================================================================== */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /* We tell the player the available choices before using the next instructions. */
        this.handleSwipe = new SwipeChoice(this);
        this.findViewById(android.R.id.content).setOnTouchListener(this.handleSwipe);

        this.options = new LinkedList<>();
        setCurrentActivity(this);
        this.options.push(new OptionTutorial(R.raw.meza,"Tutorial"));
        this.options.push(new OptionTutorial(R.raw.dolphin,"Tutorial"));
        this.options.push(new OptionTutorial(R.raw.soo,"Tutorial"));
        this.options.push(new OptionTutorial(R.raw.tromp,"Tutorial"));
        this.options.push(new OptionExit(R.raw.snk,"EXIT"));

        //As suggested by @ressay we wil use a class Randomize to generate a random set of parameters for a gamelogic
        //
    }

    /**
     * to call in onCreate of activities
     *
     * @param ac
     */

    static void setCurrentActivity(Activity ac) {
        currentActivity = ac;
    }

    static public Activity getCurrentActivity() {
        return currentActivity;
    }

    @Override
    public void up() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);

        this.choice++;
        if (this.choice >= this.options.size())
            this.choice = this.options.size() - 1;
        for (int i = 0; i < this.options.size(); i++) {
            if (i != choice)
                options.get(i).stopTitleSound();
        }
        Log.i("CHOICE", "The player choosed up" + choice);
        this.options.get(choice).playTitleSound();
    }

    @Override
    public void down() {
        this.choice--;
        if (this.choice < 0)
            this.choice = 0;
        for (int i = 0; i < this.options.size(); i++) {
            if (i != choice)
            {
                options.get(i).stopTitleSound();
            }
        }
        Log.i("CHOICE", "The player choosed down" + choice);
        this.options.get(choice).playTitleSound();
    }

    @Override
    public void left() {
        Log.i("CHOICE", "The player choosed left");
        this.options.get(this.choice).runOption(this);
    }

    @Override
    public void right() {
        Log.i("CHOICE", "The player choosed right");
        this.options.get(this.choice).runOption(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < this.options.size(); i++) {

                options.get(i).stopTitleSound();
        }
    }

}
