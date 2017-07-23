package com.example.wiss.game;

import android.util.Log;

import com.example.wiss.io.output.OutputStringDoesNotExistException;
import com.example.wiss.myapplication.Vector;
import com.example.wiss.sound.SoundName;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;
import com.example.wiss.units.Unit;

import java.util.LinkedList;

/**
 * Created by Sidahmed on 15/07/17.
 * This class will be called to update the player position on the map.
 */

public class SimpleGameLogic extends GameLogic
{
    private SimpleSoundSource target;
    double dist;
    boolean once = true;
    boolean won = false;

    /* Constructors =============================================================================== */
    public SimpleGameLogic(Player player , LinkedList soundSources, SimpleSoundSource target,double minimumDist)
    {
        super(soundSources);
        this.player = player ;
        this.target = target;
        this.dist = minimumDist;

    }
    public SimpleGameLogic(Player player){
        super(null);
        this.player = player;
        this.dist=0;
    }

    /* Methods ==================================================================================== */

    /**
     * This will move the player to the specific position in the map.
     */
    @Override
    public void movePlayerToPos(double x, double y) {
        if(won) return;
        if(!isPaused()) {
            player.setPosition(x, y);

            // soundUpdater is paused at first update, so we resume it once the player moves
            if (soundUpdater.isPaused())
                soundUpdater.resume();
            if (Vector.getDistance(this.player.getPosition(), target.getPosition()) <= dist) {
                this.reachedTarget(this.target);
            }
        }

    }


    public void reachedTarget(SoundSource target)
    {
        soundUpdater.pause();
        won = true;
        // the output in case of win
        try {
            gameIO.transferOutput("win");
        } catch (OutputStringDoesNotExistException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void update()
    {

        if(isPaused()) return;

        if(once) // this is called once in the start of the game
        {
            once = false;
            soundUpdater.pause();
            // transfer a lookFor output and in its parameters the name of the target's sound
            try {

                gameIO.transferOutput("lookFor",""+ SoundName.getNameOfSound(target.getResID()));

            } catch (OutputStringDoesNotExistException e) {
                e.printStackTrace();
            }
        }


        super.update();
    }


    public SoundSource getTarget() {
        return target;
    }

    public double getDist() {
        return dist;
    }
}
