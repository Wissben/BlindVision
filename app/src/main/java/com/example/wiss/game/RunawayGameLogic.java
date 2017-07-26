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
import com.example.wiss.sound.SoundName;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;

import java.util.LinkedList;

/**
 * Created by wiss on 24/07/17.
 */

public class RunawayGameLogic extends GameLogic {

    //A list of the sound to avoid.
    LinkedList<SimpleSoundSource> enemies;
    // respawn target at least this distance away from player
    private double distanceRespawn = 500;
    // the distance between player and sheep from which we consider the player caught the sheep
    private double catchDist = 80;
    // the speed with which the wolf moves
    private double wolfSpeed = 4;
    // the number of times the player caught the sheep
    private int score = 0;
    //How many milliseconds the player has to run away from the sounds to win
    private int gameTime = 10000;
    //Countdown object
    private CountDownTimer countDown;
    //Maximum number of enemies
    private int enemies_number = 2;
    private boolean once = true;

    SequenceSoundManager intro = new SequenceSoundManager();

    public RunawayGameLogic() {
        super(new LinkedList<SoundSource>());

    }

    @Override
    public void initialize() {

        // this is the intro to classic game logic
        intro.addSounds(R.raw.introclassic, R.raw.challenge);
        gameIO.getGameActivity().getUpdater().addToUpdate(intro);

        this.enemies = new LinkedList<>();

        for (int i = 0; i < enemies_number; i++) {
            this.enemies.add(new SimpleSoundSource());
            int[] allIDs = SoundName.getAllSoundsID();
            int randomAnimal = MyMath.random(0, allIDs.length - 1);
            this.enemies.get(i).initialise(player, allIDs[randomAnimal], WelcomeActivity.getScreenVec().getAbsValue());
            soundSources.add(this.enemies.get(i));
        }

        soundUpdater.addSoundSourcesToUpdate(soundSources);
        soundUpdater.pause();
        // load output messages
        try {
            gameIO.addOutput("Lost", new RunawayGameLogic.GameOEnd());
            gameIO.addOutput("reachedTarget", new RunawayGameLogic.GameOReachedTarget());
        } catch (OutputStringAlreadyExistsException e) {
            e.printStackTrace();
        }

        this.countDown = new CountDownTimer(gameTime, 1000) {

            public void onTick(long millisUntilFinished) {
                Log.d("debugClock", "onTick: " + millisUntilFinished / 1000);
            }

            public void onFinish() {

                reachedTarget();
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
            for (SimpleSoundSource ss : this.enemies) {
                ss.setPosition(getRandomPos());
            }
            this.countDown.start();
            once = false;
        }

        player.setPosition(x, y);
    }

    int count = 0;

    @Override
    public void update() {
        Log.d("pause", "is paused" + isPaused());
        if (isPaused() || once || !intro.hasEnded()) return;
        super.update();
        moveWolfCloserTo(player.getPosition().copy());
        for (SimpleSoundSource ss : enemies) {
            if (player.getPosition().copy().getDistance(ss.getPosition()) < catchDist) {
                this.countDown.cancel();
                lostGame();
                break;
            }
        }
    }

    private void reachedTarget() {
        Log.d("classic", "reached target");
        score++;
        //sheep.setPosition(getRandomPos());

        try {
            gameIO.transferOutput("reachedTarget");
        } catch (OutputStringDoesNotExistException e) {
            e.printStackTrace();
        }
    }

    private void lostGame() {
        Log.d("classic", "lostGame with score " + score);
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

    private void moveWolfCloserTo(Vector tar) {
        for (SimpleSoundSource ss : enemies) {
            ss.getPosition().add(tar.sub(ss.getPosition()).resize(wolfSpeed));
        }

    }

    /**
     * gets the sound of the medal depending on the score
     *
     * @return
     */
    public int getMedal() {
        if (score < 10)
            return R.raw.iron;
        if (score < 15)
            return R.raw.bronze;
        if (score < 20)
            return R.raw.silver;
        if (score < 30)
            return R.raw.gold;

        return R.raw.legendary;
    }

    /**
     * method to call when game ends (one of the enemies catches the player)
     */

    public static class GameOEnd extends GameO {
        @Override
        public void output(String param, GameActivity gameActivity) {
            SequenceSoundManager ssm = new SequenceSoundManager();
            // inside param there is the medal that the player got
            ssm.addSounds(R.raw.wolfcatch, Integer.valueOf(param), R.raw.transitionclassic);
            gameActivity.finish();
            // either he wants to quit game or retry classicGame
            TransitionChoice.startTransition(ssm, new RunawayGameGen());
        }
    }

    /**
     * method to call when player catches a sheep
     */

    public static class GameOReachedTarget extends GameO {
        @Override
        public void output(String param, GameActivity gameActivity) {
            SequenceSoundManager ssm = new SequenceSoundManager();
            ssm.addSounds(R.raw.soo);
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

    public LinkedList<SimpleSoundSource> getEnemies() {
        return enemies;
    }

    public void setEnemies(LinkedList<SimpleSoundSource> enemies) {
        this.enemies = enemies;
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

    public int getEnemies_number() {
        return enemies_number;
    }

    public void setEnemies_number(int enemies_number) {
        this.enemies_number = enemies_number;
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
