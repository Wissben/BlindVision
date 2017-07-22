package com.example.wiss.game;

import android.util.Log;

import com.example.wiss.io.output.OutputStringAlreadyExistsException;
import com.example.wiss.io.output.methods.GameO;
import com.example.wiss.io.output.methods.GameOWin;
import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.myapplication.Vector;
import com.example.wiss.myapplication.WelcomeActivity;
import com.example.wiss.sound.SequenceSoundManager;
import com.example.wiss.sound.SoundHandler;
import com.example.wiss.sound.SoundUpdater;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;
import com.example.wiss.myapplication.R;
import com.example.wiss.updater.Updater;

import java.util.LinkedList;

/**
 * Created by Sidahmed on 19/07/17.
 * This class is the GameLogic for the tutorial. It will introduce the player to the general
 * rules of the game.
 */

public class TutorialGameLogic extends GameLogic {
    protected SimpleSoundSource target;
    /**
     * This variable is used to launch the steps in the 'update()' method. If it is set to 'true',
     * the 'update()' will launch the appropriate step.
     */
    protected boolean launchStep = true;
    protected int tutorialCurrentStep = 1;
    /**
     * This variable is set to false during the explanation, to prevent the player from
     * using the input, and to prevent him from triggering the method 'movePlayerToPos'.
     */
    protected boolean operationalInput = false;

    /* Constructors =============================================================================== */

    public TutorialGameLogic(Player player)
    {
        super(new LinkedList<SoundSource>());
        this.player = player;
        this.player.setPosition(800, 500);
        this.target = new SimpleSoundSource(500, 1000);
        this.target.initialise(player, R.raw.bearsound, WelcomeActivity.getScreenVec().getAbsValue());
        this.soundUpdater.addSoundSourceToUpdate(this.target);
    }


    /* Methods ==================================================================================== */

    @Override
    public void movePlayerToPos(double x, double y) {
        if(!this.operationalInput)
            return;
        Log.i("TUTORIAL","target position1 " + this.target.getPosition());
        if(soundUpdater.isPaused())
        soundUpdater.resume();
        Log.i("TUTORIAL","target position2 " + this.target.getPosition());
        Log.i("TUTORIAL","player position " + this.player.getPosition());

        player.setPosition(x,y);
        if(Vector.getDistance(this.player.getPosition(),target.getPosition()) <= 70) {
            this.reachedTarget(this.target);
        }
    }

    public void reachedTarget(SoundSource target) {
        Log.d("TAG", "reachedTarget: "+target.getPosition().getX()+"/"+target.getPosition().getY());
        SoundHandler sound = new SoundHandler(R.raw.tutorial_goodjob);
        sound.playSound();
        sound.blockThreadTillSoundEnd();
        sound.releaseMediaPlayer();
        this.tutorialCurrentStep++;
        this.launchStep = true;
        this.operationalInput = false;
    }


    @Override
    public void update()
    {
        super.update();

        if(this.launchStep) {
            switch(this.tutorialCurrentStep){
                case 1:
                    this.tutorialStep1();
                    break;
                case 2:
                    this.tutorialStep2();
                    break;
                case 3:
                    this.tutorialStep3();
                    break;
                case 4:
                    this.tutorialStep4();
                    break;
            }
        }
    }


    /**
     * Introduce the player to the rules, and make him look for a sound.
     */
    private void tutorialStep1() {
        /* Deactivate prevent update from launching another step and config the soundUpdater. */
        this.launchStep = false;
        this.soundUpdater.addSoundSourceToUpdate(this.target);
        soundUpdater.pause();

        runSoundSequence(R.raw.tutorial_entry1,
                         R.raw.tutorial_entry2,
                         R.raw.tutorial_entry3,
                         R.raw.tutorial_entry4,
                         R.raw.tutorial_entry5,
                         R.raw.tutorial_entry6,
                         R.raw.tutorial_entry7,
                         R.raw.bear,
                         R.raw.tutorial_entry8);

        this.operationalInput = true;
    }

    /**
     * Make the player look for another sound.
     */
    private void tutorialStep2() {
        Log.i("TUTORIAL", "Started step2");
        this.launchStep = false;
        /* first we remove the first target's sound from soundUpdater and release its media player */
        this.soundUpdater.clearAndRelease();
        // we create new target
        this.target = new SimpleSoundSource(200, 1300);
        this.target.initialise(player, R.raw.cowsound, WelcomeActivity.getScreenVec().getAbsValue());
        /*
        we add this new target to the soundUpdater to update
         */
        this.soundUpdater.addSoundSourceToUpdate(this.target);
        this.soundUpdater.pause();

        // we run the next sequence of sounds
        runSoundSequence(R.raw.tutorial_entry9,
                         R.raw.tutorial_entry7,
                         R.raw.cow,
                         R.raw.tutorial_entry8);

        Log.i("TUTORIAL", "end step2");
        this.operationalInput = true;
    }


    /**
     * Make the player look for a sound with a distraction sound (it means it will be two sounds in
     * this step).
     */
    private void tutorialStep3(){
        Log.i("TUTORIAL", "Started step3");
        this.launchStep = false;

        this.soundUpdater.clearAndRelease();
        this.target = new SimpleSoundSource(700, 1200);
        this.target.initialise(player, R.raw.horsesound, WelcomeActivity.getScreenVec().getAbsValue());
        SimpleSoundSource distraction = new SimpleSoundSource(200, 200);
        distraction.initialise(this.player, R.raw.lionsound, WelcomeActivity.getScreenVec().getAbsValue());

        this.soundUpdater.addSoundSourceToUpdate(this.target);
        this.soundUpdater.addSoundSourceToUpdate(distraction);
        this.soundUpdater.pause();


        runSoundSequence(R.raw.tutorial_entry10,
                         R.raw.horse,
                         R.raw.tutorial_wa,
                         R.raw.lion,
                         R.raw.tutorial_entry11,
                         R.raw.horse);

        this.operationalInput = true;
    }


    /**
     *  Tell the player that the tutorial is over, and go back to the main screen.
     */
    private void tutorialStep4() {
        this.soundUpdater.stop();
        SoundHandler sound = new SoundHandler(R.raw.tutorial_entry12);
        sound.playSound();
        sound.blockThreadTillSoundEnd();
        sound.releaseMediaPlayer();
        this.gameIO.getGameActivity().finish();
    }

    private void runSoundSequence(int... seq)
    {
        SequenceSoundManager ssm = new SequenceSoundManager();
        ssm.addSounds(seq);
        // to run SequenceSoundManager, an updater is required, so we create a new updater since we want to block
        // the current updater's thread
        Updater up = new Updater(100);
        up.addToUpdate(ssm);
        up.startUpdating();
        // blocking this thread's updater
        ssm.blockThreadWhilePlaying();
        // ending the updater's loop
        up.cancel();
    }
}
