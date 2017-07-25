package com.example.wiss.game;

import android.util.Log;

import com.example.wiss.myapplication.R;
import com.example.wiss.myapplication.Vector;
import com.example.wiss.myapplication.WelcomeActivity;
import com.example.wiss.sound.SequenceSoundManager;
import com.example.wiss.sound.SoundHandler;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;
import com.example.wiss.updater.Updater;

import java.util.LinkedList;

/**
 * Created by Sidahmed on 23/07/17.
 * This is the swipe tutorial. We teach the player how to move in the virtual world using by
 * swiping on the screen.
 */

public class TutorialSwipeGameLogic extends GameLogic {
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
    protected Updater up;


    /* Constructors ============================================================================== */

    public TutorialSwipeGameLogic(Player player) {
        super(new LinkedList<SoundSource>());
        this.player = player;
        this.player.setPosition(800, 500);
    }


    /* Methods =================================================================================== */

    @Override
    public void movePlayer(Vector swipe) {
        if(!this.operationalInput)
            return;

        if(soundUpdater.isPaused())
            soundUpdater.resume();

        swipe.setY( -swipe.getY());
        switch(this.tutorialCurrentStep) {
            case 1:
                /* We can reduce the precision of how much do we want the player to swipe to the
                   top, simply my modifying the angle range. */
                if (swipe.getAngle() >= Math.PI*3/8 && swipe.getAngle() <= Math.PI*5/8)
                    this.success();
                break;
            case 2:
                if (swipe.getAngle() >= Math.PI*11/8 && swipe.getAngle() < Math.PI*13/8)
                    this.success();
                break;
            case 3:
                if ( (swipe.getAngle() <= Math.PI/8 && swipe.getAngle() >= 0) || (swipe.getAngle() >= Math.PI*15/8))
                    this.success();
                break;
            case 4:
                if (swipe.getAngle() >= Math.PI*7/8 && swipe.getAngle() <= Math.PI*9/8)
                    this.success();
                break;
            case 5:
                /* I have to change this depending on how we are seeing the sound position
                   compared to the player. If it is used with cortesian coordinate system, then
                   the next instruction is okay, because I flipped the 'y' in the beginning of this
                   function. If it is compared to the screen position, then I will have to change the
                   next instruction. */
                this.player.getPosition().add(swipe);
                if(Vector.getDistance(this.player.getPosition(), target.getPosition()) <= 70)
                    this.reachedTarget(this.target);
                break;
            case 6:
                this.player.getPosition().add(swipe);
                if(Vector.getDistance(this.player.getPosition(), target.getPosition()) <= 70)
                    this.reachedTarget(this.target);
                break;
        }
    }

    @Override
    public void movePlayerToPos(double x, double y) { }

    @Override
    public void initialize() {

    }

    public void reachedTarget(SoundSource target) {
        this.success();
    }


    public void success() {
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

        /* This could be a lot shorter if we use array of functions. */
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
                case 5:
                    this.tutorialStep5();
                    break;
                case 6:
                    this.tutorialStep6();
                    break;
                case 7:
                    this.tutorialStep7();
                    break;
            }
        }
    }


    private void tutorialStep1() {
        /* Deactivate prevent update from launching another step and config the soundUpdater. */
        this.launchStep = false;
        soundUpdater.pause();

        runSoundSequence(R.raw.tutorial_swipe1,
                R.raw.tutorial_swipe2,
                R.raw.tutorial_swipe3,
                R.raw.tutorial_entry6,
                R.raw.tutorial_swipe4,
                R.raw.tutorial_swipe5_forward,
                R.raw.tutorial_swipe5_backward,
                R.raw.tutorial_swipe5_right,
                R.raw.tutorial_swipe5_left,
                R.raw.tutorial_swipe6,
                R.raw.tutorial_swipe6_move_forward
                );

        this.operationalInput = true;
    }


    private void tutorialStep2() {
        this.launchStep = false;
        soundUpdater.pause();

        runSoundSequence(R.raw.tutorial_swipe6_move_backward);

        this.operationalInput = true;
    }


    private void tutorialStep3() {
        this.launchStep = false;
        soundUpdater.pause();

        runSoundSequence(R.raw.tutorial_swipe6_move_right);

        this.operationalInput = true;
    }


    private void tutorialStep4() {
        this.launchStep = false;
        soundUpdater.pause();

        runSoundSequence(R.raw.tutorial_swipe6_move_left);

        this.operationalInput = true;
    }


    private void tutorialStep5() {
        this.launchStep = false;

        /* Prepare the target sound .*/
        this.target = new SimpleSoundSource(700, 600);
        this.target.initialise(this.player, R.raw.bearsound, WelcomeActivity.getScreenVec().getAbsValue());
        this.soundUpdater.clearAndRelease();
        this.soundUpdater.addSoundSourceToUpdate(this.target);
        soundUpdater.pause();

        runSoundSequence(R.raw.tutorial_swipe7,
                R.raw.tutorial_swipe8,
                R.raw.tutorial_entry7,
                R.raw.bear,
                R.raw.tutorial_swipe9);

        this.operationalInput = true;
        this.soundUpdater.resumeSound();
    }


    private void tutorialStep6() {
        this.launchStep = false;

        /* Prepare the target and the distraction sound .*/
        this.target = new SimpleSoundSource(500, 1000);
        this.target.initialise(this.player, R.raw.cowsound, WelcomeActivity.getScreenVec().getAbsValue());
        SimpleSoundSource distraction = new SimpleSoundSource(300, 300);
        distraction.initialise(this.player, R.raw.horsesound, WelcomeActivity.getScreenVec().getAbsValue());

        soundUpdater.pause();
        this.soundUpdater.clearAndRelease();
        this.soundUpdater.addSoundSourceToUpdate(this.target);
        this.soundUpdater.addSoundSourceToUpdate(distraction);

        runSoundSequence(R.raw.tutorial_entry10,
                R.raw.cow,
                R.raw.tutorial_wa,
                R.raw.horse,
                R.raw.tutorial_swipe10,
                R.raw.cow);

        this.operationalInput = true;
        this.soundUpdater.resumeSound();
    }


    private void tutorialStep7() {
        this.launchStep = false;
        this.soundUpdater.pause();
        this.soundUpdater.clearAndRelease();

        runSoundSequence(R.raw.tutorial_swipe11,
                R.raw.tutorial_swipe11_note1,
                R.raw.tutorial_swipe11_note2,
                R.raw.tutorial_swipe11_note3_1,
                R.raw.tutorial_swipe11_note3_2,
                R.raw.tutorial_swipe12);

        this.gameIO.getGameActivity().finish();
    }


    private void runSoundSequence(int... seq)
    {
        SequenceSoundManager ssm = new SequenceSoundManager();
        ssm.addSounds(seq);
        // to run SequenceSoundManager, an updater is required, so we create a new updater since we want to block
        // the current updater's thread
        up = new Updater(100);
        up.addToUpdate(ssm);
        up.startUpdating();
        // blocking this thread's updater
        ssm.blockThreadWhilePlaying();
        // ending the updater's loop
        up.cancel();
    }


    @Override
    public void stop() {
        super.stop();
        this.up.cancel();
    }

    @Override
    public void pause() {
        super.pause();
        this.up.pauseUpdating();
    }

    @Override
    public void resume()
    {
        super.resume();
        this.up.resumeUpdating();
    }
}
