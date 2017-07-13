package com.example.wiss.Sound;

import com.example.wiss.myapplication.Unit;
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


    private boolean initialised = false;

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

    public boolean isInitialised()
    {
        return initialised;
    }

    /**
     * a sound source might need to be initialised before setting up sound manager
     * initialisation usually feeds the object with necessary information in order for sound manager to work properly (this depend on the type of the sound manager)
     * for instance a simpleSoundManager needs information about Player which must be passed to this instance
     * if a sound source that requires initialisation to setup soundManager calls the setupSoundManager method without initialisation an exception is thrown
     */

    protected void initialise()
    {
        initialised = true;
    }

    /**
     * sets up the behaviour of sound manager for this sound source
     */
    public abstract void setupSoundManager() throws SoundSourceNotInitialisedException;

}
