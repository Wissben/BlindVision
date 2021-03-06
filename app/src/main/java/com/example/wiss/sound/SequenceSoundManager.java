package com.example.wiss.sound;

import com.example.wiss.updater.Updatable;

import java.util.LinkedList;

/**
 * a sequence of sound files to be played one after the other
 * Created by ressay on 20/07/17.
 */

public class SequenceSoundManager extends SoundManager
{
    private LinkedList<Integer> sounds = new LinkedList<>();
    private SoundHandler sound = null;
    private int currentSound = -1;
    private boolean ended = false;

    public SequenceSoundManager()
    {
        setPlaying(true);
    }
    public void addSound(int resID)
    {
        sounds.add(resID);
    }

    public void addSounds(int... resIDs)
    {
        for(int i=0;i<resIDs.length;i++)
            sounds.add(resIDs[i]);
    }

    public LinkedList<Integer> getSounds()
    {
        return sounds;
    }

    @Override
    public void update()
    {
        if(currentSound >= sounds.size()) return;
        super.update();
        if(sound == null || !sound.isPlaying()) {
            if(sound != null)
            sound.releaseMediaPlayer();
            currentSound++;
            sound = null;
        }
        else return;

        if(currentSound < sounds.size()) {

            sound = new SoundHandler(sounds.get(currentSound));
            sound.playSound();
        }
        else {
            stop();
        }
    }

    @Override
    public void pause() {
        if(sound != null)
        sound.pauseSound();
        super.pause();
    }

    @Override
    public void resume() {
        if(currentSound>=sounds.size()) return;

        super.resume();
        if(sound != null)
        sound.playSound();
    }

    @Override
    public void stop() {
        super.stop();
        ended = true;
        if(sound == null) return;
        sound.stopSound();
        sound.releaseMediaPlayer();

    }

    @Override
    public boolean hasEnded()
    {
        return ended;
    }


}
