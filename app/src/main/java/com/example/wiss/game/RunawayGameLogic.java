package com.example.wiss.game;

import android.os.CountDownTimer;
import android.util.Log;

import com.example.wiss.gameGen.RunawayGameGen;
import com.example.wiss.io.output.OutputStringAlreadyExistsException;
import com.example.wiss.io.output.OutputStringDoesNotExistException;
import com.example.wiss.io.output.methods.GameO;
import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.myapplication.MyMath;
import com.example.wiss.myapplication.R;
import com.example.wiss.myapplication.TransitionChoice;
import com.example.wiss.myapplication.Vector;
import com.example.wiss.myapplication.WelcomeActivity;
import com.example.wiss.sound.SequenceSoundManager;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;

import java.util.LinkedList;

/**
 * Created by wiss on 24/07/17.
 */

public class RunawayGameLogic extends GameLogic {

    //A list of the sound to avoid.
    LinkedList<SimpleSoundSource> predators;
    // respawn target at least this distance away from player
    private double distanceRespawn = 500;
    // the distance between player and sheep from which we consider the player caught the sheep
    private double catchDist = 40;
    // the speed with which the wolf moves
    private double predatorSpeed = 4;
    // the number of times the player caught the sheep
    private int score = 0;
    //How many milliseconds the player has to run away from the sounds to win
    private int gameTime = 50000;
    //Countdown object
    private CountDownTimer countDown;
    //Maximum number of predators
    private int predators_number = 2;
    private boolean once = true;
    private boolean won = false;
    SequenceSoundManager intro = new SequenceSoundManager();

    int count = 0;

    public RunawayGameLogic(LinkedList<SimpleSoundSource> predators, int gameTime) {
        super(new LinkedList<SoundSource>());
        this.predators = predators;
        this.gameTime = gameTime * 1000;
    }

    @Override
    public void initialize() {

        // this is the intro to classic game logic
        intro.addSounds(R.raw.runawayintro, R.raw.challenge);
        gameIO.getGameActivity().getUpdater().addToUpdate(intro);
        won = false;
        for (int i = 0; i < this.predators.size(); i++) {
            this.soundSources.add(this.predators.get(i));
        }

        soundUpdater.addSoundSourcesToUpdate(soundSources);
        soundUpdater.pause();
        // load output messages
        try {
            gameIO.addOutput("Lost", new RunawayGameLogic.GameOEnd());
            gameIO.addOutput("endOfCountdown", new GameOEndOfCountdown());
        } catch (OutputStringAlreadyExistsException e) {
            e.printStackTrace();
        }

        this.countDown = new CountDownTimer(gameTime, 1000) {

            public void onTick(long millisUntilFinished) {
                score++;
                Log.d("debugClock", "onTick: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                endOfCountdown();
            }
        };
    }

    @Override
    public void movePlayerToPos(double x, double y) {
        if (isPaused() || !intro.hasEnded()) return;
        if (soundUpdater.isPaused())
            soundUpdater.resume();

        Log.d("classic", "received position: " + x + "," + y);

        // when he touches for first time we set a position for the wolf and the sheep at random
        if (once) {
            for (SimpleSoundSource ss : this.predators) {
                ss.setPosition(getRandomPos());
            }
            this.countDown.start();
            once = false;
        }
        player.setPosition(x, y);
    }

    @Override
    public void update() {
        Log.d("pause", "is paused" + isPaused());
        if (isPaused() || once || !intro.hasEnded()) return;
        super.update();
        movePredatorCloserTo(player.getPosition().copy());
        for (SimpleSoundSource ss : predators) {
            if (player.getPosition().copy().getDistance(ss.getPosition()) < catchDist && !won) {
                this.countDown.cancel();
                lostGame();
                break;
            }
        }
    }

    private void endOfCountdown() {
        Log.d("Runaway", "end of countdown");
        score++;
        won = true;
        this.soundUpdater.pause();
        SequenceSoundManager ssm = new SequenceSoundManager();
        // inside param there is the medal that the player got
        ssm.addSounds(R.raw.youwin, getMedal(),R.raw.transitionclassic);
        // either he wants to quit game or retry runaway game
        TransitionChoice.startTransition(ssm, new RunawayGameGen());
        gameIO.getGameActivity().finish();


        try {
            gameIO.transferOutput("win");
        } catch (OutputStringDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    private void lostGame() {
        Log.d("Runaway", "lostGame");
        won=false;
        pause();
        try {
            gameIO.transferOutput("Lost", getMedal() + "");
        } catch (OutputStringDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    private Vector getRandomPos() {
        // we get any point from the screen that is far from the player with distanceFromPlayer
        return MyMath.getRandomPosition(player.getPosition(), distanceRespawn, new Vector(0, 0), WelcomeActivity.getScreenVec());
    }

    private void movePredatorCloserTo(Vector tar) {
        for (SimpleSoundSource ss : predators) {
            ss.getPosition().add(tar.sub(ss.getPosition()).resize(predatorSpeed));
        }

    }

    /**
     * gets the sound of the medal depending on the score
     * @return
     */
    public int getMedal() {
        if (score < 1/5*gameTime)
            return R.raw.iron;
        if (score < 2/5*gameTime)
            return R.raw.bronze;
        if (score < 3/5*gameTime)
            return R.raw.silver;
        if (score < 4/5*gameTime)
            return R.raw.gold;

        return R.raw.legendary;
    }

    /**
     * method to call when game ends (one of the predators catches the player)
     */

    public static class GameOEnd extends GameO {

        @Override
        public void output(String param, GameActivity gameActivity) {
            SequenceSoundManager ssm = new SequenceSoundManager();
            // inside param there is the medal that the player got
            ssm.addSounds(R.raw.lose, Integer.valueOf(param), R.raw.transitionclassic);
            gameActivity.finish();
            // either he wants to quit game or retry runaway game
            TransitionChoice.startTransition(ssm, new RunawayGameGen());
        }
    }

    /**
     * method to call when player wins
     */

    public static class GameOEndOfCountdown extends GameO {
        @Override
        public void output(String param, GameActivity gameActivity) {
            SequenceSoundManager ssm = new SequenceSoundManager();
            ssm.addSounds(R.raw.collectcoin);
            gameActivity.getUpdater().addToUpdate(ssm);
            // when sequenceSoundManager finishes it automatically ends itself from update
        }
    }


    @Override
    public void stop() {
        super.stop();
        //this.soundUpdater.stop();
    }

    @Override
    public void pause() {
        super.pause();
        //this.soundUpdater.pauseSounds();
    }

    @Override
    public void resume()
    {
        super.resume();
        //this.soundUpdater.resume();
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

    public double getPredatorSpeed() {
        return predatorSpeed;
    }

    public void setPredatorSpeed(double predatorSpeed) {
        this.predatorSpeed = predatorSpeed;
    }

    public LinkedList<SimpleSoundSource> getPredators() {
        return predators;
    }

    public void setPredators(LinkedList<SimpleSoundSource> predators) {
        this.predators = predators;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public CountDownTimer getCountDown() {
        return countDown;
    }

    public void setCountDown(CountDownTimer countDown) {
        this.countDown = countDown;
    }

    public int getPredators_number() {
        return predators_number;
    }

    public void setPredators_number(int predators_number) {
        this.predators_number = predators_number;
    }

    public boolean isOnce() {
        return once;
    }

    public void setOnce(boolean once) {
        this.once = once;
    }

    public SequenceSoundManager getIntro() {
        return intro;
    }

    public void setIntro(SequenceSoundManager intro) {
        this.intro = intro;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
