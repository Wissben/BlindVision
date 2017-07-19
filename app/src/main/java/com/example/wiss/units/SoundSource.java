package com.example.wiss.units;

import com.example.wiss.sound.SoundSourceNotInitialisedException;
import com.example.wiss.sound.UnitSoundManager;
import com.example.wiss.myapplication.Vector;

/**
 * Created by ressay on 13/07/17.
 */

public abstract class SoundSource extends Unit
{

    /**
     * a SoundSource uses a SoundManager to produce sound and update it
     */
    private UnitSoundManager usm;

    public SoundSource()
    {
        super();
    }

    public SoundSource(double x, double y)
    {
        super(x,y);
    }

    public SoundSource(Vector pos)
    {
        super(pos);
    }

    public UnitSoundManager getUnitSoundManager()
    {
        return usm;
    }

    public void setUnitSoundManager(UnitSoundManager usm)
    {
        this.usm = usm;
    }


    public void stopSound()
    {
        getUnitSoundManager().stopSound();
    }

    public void pauseSound()
    {
        getUnitSoundManager().pauseSound();
    }

}
