package com.example.wiss.sound;

import com.example.wiss.units.Player;
import com.example.wiss.units.Unit;

/**
 * class that describes how sound should be produced and updated for simple sound sources
 * Created by ressay on 13/07/17.
 */

public class UnitSimpleSoundManager extends UnitSoundManager
{
    /**
     * player is receiver, we will suppose there is just one receiver.
     */
    Player player;

    /**
     * the source of sound this manager is managing
     */
    Unit source;

    /**
     * constructor with max distance from which sound can not be heard, player that receives the sound, the sound source Unit, and the simple sound to produce
     * @param maxDistance
     * @param player
     * @param source
     * @param resID
     */
    public UnitSimpleSoundManager(double maxDistance, Player player,Unit source,int resID)
    {
        super(maxDistance);
        setPlayer(player);
        setSource(source);
        addSound(resID);
    }


    @Override
    public void update()
    {
        super.update();
        // if sound not started yet, start it, else change sound depending of position of player and source
        getSoundMapManager().produceSoundBetweenPoints( getSource().getPosition(),
                                                        getPlayer().getPosition(),
                                                        getSoundHandlerList().get(0) );
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Unit getSource() {
        return source;
    }

    public void setSource(Unit source) {
        this.source = source;
    }
}
