package com.example.wiss.game;

import android.support.annotation.CallSuper;

import com.example.wiss.myapplication.Vector;
import com.example.wiss.sound.SoundUpdater;
import com.example.wiss.units.Player;
import com.example.wiss.units.SoundSource;
import com.example.wiss.updater.Updatable;

import java.util.LinkedList;

/**
 * Created by wiss on 15/07/17.
 * This class will update the units positions on the map.
 * In a simple case, we only need to change the player position, but we created this class
 * in case we wanna move other units.
 */

public abstract class GameLogic implements Updatable
{
    Player player;
    GameIO gameIO;
    LinkedList<SoundSource> soundSources=null;
    SoundUpdater soundUpdater;
    private boolean paused = false;

    public GameLogic(LinkedList<SoundSource> soundSources)
    {
        this.soundSources=soundSources;
        soundUpdater = new SoundUpdater();
        soundUpdater.addSoundSourcesToUpdate(soundSources);
    }

    /* Abstract Methods ========================================================================== */

    /**
     * This will move the player to the specific position in the map.
     */
    public abstract void movePlayerToPos(double x, double y);


    /**
     * Don't know where this will be used yet.
     */



    /* Methods ==================================================================================== */

    @Override @CallSuper
    public void update()
    {
        if(!isPaused())
        soundUpdater.update();
    }

    @Override @CallSuper
    public void pause() {
        pauseGame();
    }

    @Override @CallSuper
    public void resume() {
        resumeGame();
    }

    @Override @CallSuper
    public void stop()
    {

    }


    /**
     *
     */
    public void pauseGame()
    {
        if(isPaused()) return;
        setPaused(true);
        soundUpdater.pauseSounds();
    }

    /**
     *
     */
    public void resumeGame()
    {
        if(!isPaused()) return;
        setPaused(false);
        soundUpdater.resumeSound();
    }

    /**
     * This will move the player using the vector.
     */
    public void movePlayer(Vector vector)
    {
        Vector v = player.getPosition().copy();
        movePlayerToPos(v.add(vector));
    }

    /**
     * same method as movePlayerToPos with different parameters
     * @param vector
     */

    public void movePlayerToPos(Vector vector)
    {
        movePlayerToPos(vector.getX(),vector.getY());
    }


    /* Setters & Getters ========================================================================== */

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameIO getGameIO() {
        return gameIO;
    }

    public void setGameIO(GameIO gameIO) {
        this.gameIO = gameIO;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
