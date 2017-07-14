package com.example.wiss.units;

import com.example.wiss.sound.SoundSourceNotInitialisedException;
import com.example.wiss.sound.UnitSimpleSoundManager;
import com.example.wiss.myapplication.Vector;

/**
 * Created by ressay on 13/07/17.
 */

public class SimpleSoundSource extends SoundSource
{
    Player player;
    int resID;
    double maxDistance;

    public SimpleSoundSource() { super(); }

    public SimpleSoundSource(double x, double y) { super(x,y); }

    public SimpleSoundSource(Vector pos) { super(pos); }

    /**
     * initializer of sound source with necessary inputs to manage the sound
     * @param player
     * @param resID
     * @param maxDistance
     */
    public void initialise(Player player,int resID,double maxDistance)
    {
        this.player = player;
        this.resID = resID;
        this.maxDistance = maxDistance;
        initialise(); // now this is initialised and won't throw exception when setupSoundManager is called
    }

    @Override
    public void setupSoundManager() throws SoundSourceNotInitialisedException
    {
        if(!isInitialised()) throw new SoundSourceNotInitialisedException();
        setUnitSoundManager(new UnitSimpleSoundManager(maxDistance,player,this,resID));
    }
}
