package com.example.wiss.game;

import android.util.Log;

import com.example.wiss.myapplication.Vector;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;
import com.example.wiss.units.Unit;

import java.util.LinkedList;

/**
 * Created by Sidahmed on 15/07/17.
 * This class will be called to update the player position on the map.
 */

public class SimpleGameLogic extends GameLogic {

    private SoundSource target;
    double dist;

    /* Constructors =============================================================================== */
    public SimpleGameLogic(Player player , LinkedList<SoundSource> soundsources, SoundSource target,double minimumDist)
    {
        super(soundsources);
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
        Vector currPos = this.target.getPosition();
        Log.d("myTag","moving player");
        player.setPosition(x,y);
            if(Vector.getDistance(this.player.getPosition(),target.getPosition())<=dist)
            {
                this.reachedTarget(this.target);
            }

    }


    public void reachedTarget(SoundSource target)
    {
        Log.d("TAG", "reachedTarget: "+target.getPosition().getX()+"/"+target.getPosition().getY());
    }
    @Override
    public void update()
    {
        super.update();
    }


    public SoundSource getTarget() {
        return target;
    }

    public double getDist() {
        return dist;
    }
}
