package com.example.wiss.myapplication;

import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.example.wiss.Option.OptionClassicMode;
import com.example.wiss.Option.OptionExit;
import com.example.wiss.Option.OptionRunaway;
import com.example.wiss.Option.OptionSelectGameLevel;
import com.example.wiss.Option.OptionTutorial;
import com.example.wiss.Option.Options;
import com.example.wiss.sound.SoundHandler;

import java.util.LinkedList;

public class WelcomeActivity extends BlindActivity implements Choiceable {
    public View.OnTouchListener handleSwipe;
    int choice = 0;
    LinkedList<Options> options;
    static private Vector screenVec = null;
    private SoundHandler backgroundSound = null;
    protected SoundHandler howToBrowseChoices;

    /* We put the static objects here (like gameLogic and stuff). */


    /* Methods =================================================================================== */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        screenVec = getScreenVector();
        this.setUpBackgroundSound(R.raw.carefree);
        this.backgroundSound.playSound();
        /* We tell the player the available choices before using the next instructions. */
        this.handleSwipe = new SwipeChoice(this);
        this.findViewById(android.R.id.content).setOnTouchListener(this.handleSwipe);

        this.options = new LinkedList<>();
        this.options.push(new OptionExit(R.raw.exitthegame, "Exit"));
        this.options.add(new OptionClassicMode(R.raw.classicmode, "Classic mode"));
        this.options.add(new OptionRunaway(R.raw.runawaymode,"RunawayMode"));
        this.options.add(new OptionSelectGameLevel(R.raw.selectgamelevel, "Select level"));
        this.options.add(new OptionTutorial(R.raw.tutorial, "Tutorial"));

        /* We tell the player how to browse choices. */
        this.howToBrowseChoices = new SoundHandler(R.raw.howtobrowsechoices);
        this.howToBrowseChoices.playSound();

        //As suggested by @ressay we wil use a class Randomize to generate a random set of parameters for a gamelogic
        //
    }


    public void setUpBackgroundSound(int ID) {

        this.backgroundSound = new SoundHandler(ID);
        this.backgroundSound.getMp().setLooping(true);
        this.backgroundSound.setCurrentVolume(30);
    }

    @Override
    public void down() {
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        this.howToBrowseChoices.stopSound();

        this.choice++;
        if (this.choice >= this.options.size())
            this.choice = 0;
        for (int i = 0; i < this.options.size(); i++) {
            if (i != choice)
                options.get(i).stopTitleSound();
        }
        Log.i("CHOICE", "The player choosed down " + choice);
        this.options.get(choice).playTitleSound();
    }

    @Override
    public void up() {
        this.howToBrowseChoices.stopSound();
        this.choice--;
        if (this.choice < 0)
            this.choice = this.options.size() - 1;
        for (int i = 0; i < this.options.size(); i++) {
            if (i != choice) {
                options.get(i).stopTitleSound();
            }
        }
        Log.i("CHOICE", "The player choosed up " + choice);
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


    static public Vector getScreenVec() {
        return screenVec;
    }

    Vector getScreenVector() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new Vector(size.x, size.y);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.backgroundSound.pauseSound();
        this.howToBrowseChoices.pauseSound();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.backgroundSound.playSound();

        /* Ceci est du bricolage seulement. Je pense qu'on aura besoin d'une methode
           restartSound() dans 'SoundHandler' (Si le new SoundHanlder est entrain de poser
           un problem). */
        this.howToBrowseChoices.playSound();
    }
}
