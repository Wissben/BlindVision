package com.example.wiss.game;

import android.util.Log;

import com.example.wiss.gameGen.ClassicGameGen;
import com.example.wiss.io.output.OutputStringAlreadyExistsException;
import com.example.wiss.io.output.OutputStringDoesNotExistException;
import com.example.wiss.io.output.methods.GameO;
import com.example.wiss.myapplication.ChoiceParameters;
import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.myapplication.MyMath;
import com.example.wiss.myapplication.R;
import com.example.wiss.myapplication.TransitionChoice;
import com.example.wiss.myapplication.Vector;
import com.example.wiss.myapplication.WelcomeActivity;
import com.example.wiss.sound.SequenceSoundManager;
import com.example.wiss.sound.SoundHandler;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;
import com.example.wiss.updater.Updater;

import java.util.LinkedList;

/**
 * Created by ressay on 23/07/17.
 */

public class ClassicGameLogic extends GameLogic
{
    // target to collect
    private SimpleSoundSource sheep;
    // enemy to run from
    private SimpleSoundSource wolf;
    // respawn target at least this distance away from player
    private double distanceRespawn = 500;
    // the distance between player and sheep from which we consider the player caught the sheep
    private double catchDist = 80;
    // the speed with which the wolf moves
    private double wolfSpeed = 4;
    // the number of times the player caught the sheep
    private int score = 0;

    private boolean once = true;

    SequenceSoundManager intro = new SequenceSoundManager();

    public ClassicGameLogic()
    {
        super(new LinkedList<SoundSource>());

    }

    @Override
    public void initialize()
    {
        // this is the intro to classic game logic
        intro.addSounds(R.raw.introclassic,R.raw.challenge);
        gameIO.getGameActivity().getUpdater().addToUpdate(intro);


        sheep = new SimpleSoundSource();
        wolf = new SimpleSoundSource();
        sheep.initialise(player, R.raw.meza, WelcomeActivity.getScreenVec().getAbsValue());
        wolf.initialise(player, R.raw.wolfsound, WelcomeActivity.getScreenVec().getAbsValue());
        soundSources.add(sheep);
        soundSources.add(wolf);
        soundUpdater.addSoundSourcesToUpdate(soundSources);
        soundUpdater.pause();

        // load output messages
        try {
            gameIO.addOutput("Lost", new GameOEnd());
            gameIO.addOutput("reachedTarget", new GameOReachedTarget());
        } catch (OutputStringAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void movePlayerToPos(double x, double y)
    {
        if(isPaused() || !intro.hasEnded()) return;
        if(soundUpdater.isPaused())
            soundUpdater.resume();

        Log.d("classic", "received position: " + x + "," + y);

        // when he touches for first time we set a position for the wolf and the sheep at random
        if(once)
        {
            sheep.setPosition(getRandomPos());
            wolf.setPosition(getRandomPos());
            once = false;
        }

        player.setPosition(x,y);
        if (Vector.getDistance(this.player.getPosition(), sheep.getPosition()) <= catchDist) {
            this.reachedTarget();
        }
    }

    int count = 0;
    @Override
    public void update()
    {
        Log.d("pause","is paused"  + isPaused());
        if(isPaused() || once || !intro.hasEnded()) return;
        super.update();
        moveWolfCloserTo(player.getPosition().copy());

        if(player.getPosition().copy().getDistance(wolf.getPosition()) < catchDist)
            lostGame();

        count++;
        if(count%10 == 0) {
            Log.d("classic", "wolf position: " + wolf.getPosition());
            Log.d("classic", "sheep position: " + sheep.getPosition());
            Log.d("classic", "player position: " + player.getPosition());
        }
    }

    private void reachedTarget()
    {
        Log.d("classic","reached target");
        score++;
        sheep.setPosition(getRandomPos());

        try {
            gameIO.transferOutput("reachedTarget");
        } catch (OutputStringDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    private void lostGame()
    {
        Log.d("classic","lostGame with score "+ score);
        pause();
        try {
            gameIO.transferOutput("Lost",getMedal()+"");
        } catch (OutputStringDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    private Vector getRandomPos()
    {
        // we get any point from the screen that is far from the player with distanceFromPlayer
        return MyMath.getRandomPosition(player.getPosition(),distanceRespawn,new Vector(0,0), WelcomeActivity.getScreenVec());
    }

    private void moveWolfCloserTo(Vector tar)
    {
        wolf.getPosition().add( tar.sub(wolf.getPosition()).resize(wolfSpeed) );
    }

    /**
     * gets the sound of the medal depending on the score
     * @return
     */
    public int getMedal()
    {
        if(score < 10)
            return R.raw.iron;
        if(score < 15)
            return R.raw.bronze;
        if(score < 20)
            return R.raw.silver;
        if(score < 30)
            return R.raw.gold;

        return R.raw.legendary;
    }

    /**
     * method to call when game ends (wolf catches the player)
     */

    public static class GameOEnd extends GameO
    {
        @Override
        public void output(String param, GameActivity gameActivity)
        {
            SequenceSoundManager ssm = new SequenceSoundManager();
            // inside param there is the medal that the player got
            ssm.addSounds(R.raw.wolfcatch,Integer.valueOf(param),R.raw.transitionclassic);
            gameActivity.finish();
            // either he wants to quit game or retry classicGame
            TransitionChoice.startTransition(ssm,new ClassicGameGen());
        }
    }

    /**
     * method to call when player catches a sheep
     */

    public static class GameOReachedTarget extends GameO
    {
        @Override
        public void output(String param, GameActivity gameActivity)
        {
            SequenceSoundManager ssm = new SequenceSoundManager();
            ssm.addSounds(R.raw.collectcoin);
            gameActivity.getUpdater().addToUpdate(ssm);
            // when sequenceSoundManager finishes it automatically ends itself from update
        }
    }

    public double getDistanceRespawn() {
        return distanceRespawn;
    }

    public void setDistanceRespawn(double distanceFromPlayer) {
        this.distanceRespawn = distanceFromPlayer;
    }

    public double getCatchDist() {
        return catchDist;
    }

    public void setCatchDist(double dist) {
        this.catchDist = dist;
    }

    public double getWolfSpeed() {
        return wolfSpeed;
    }

    public void setWolfSpeed(double wolfSpeed) {
        this.wolfSpeed = wolfSpeed;
    }
}
