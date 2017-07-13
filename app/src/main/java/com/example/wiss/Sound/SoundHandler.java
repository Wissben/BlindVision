package com.example.wiss.Sound;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import com.example.wiss.myapplication.MainActivity;
import com.example.wiss.myapplication.MyMath;
/**
 * Created by wiss on 29/06/17.
 * Overhaul :
 * To use this class one must first instantiate an object using either of the constructors implemented below
 * The minimum arguments are : an id of the Resource(the media to be played) and the context of the application
 * The Extra arguments are :
 *  -The angle of where the sound comes from 0° => right / 180° => left
 *  -The maximum volume
 * There are 5 fields up until now :
 *  -The mediaPlayer.
 *  -The maximumVolume.
 *  -The angle from which the sounds comes from
 *  -The actual value of the left/right volume.
 * After creating the object the main methods that can be used are :
 *  -PlaySound depending on the angle
 *  ... kash nhar nkamel had shit xD
 */

public class SoundHandler {

    private MediaPlayer mp;
    private float limitVolume; // the limit of the volume
    private float currentVolume;
    private float panR=1,panL=1;

    /**
     * constructor with id of the resource to be played, and the context of the application
     * @param resID
     */
    public SoundHandler(int resID)
    {

        //Default constructor
        Activity ac = MainActivity.getCurrentActivity();
        this.mp=MediaPlayer.create(ac.getApplicationContext(),resID);
        //A media player object is created
        setLimitVolume(100);
        setCurrentVolume(100);
        //The default sound limitVolume is 100
    }

    public SoundHandler(int resID,float limitVolume)
    {
        //Default constructor
        Activity ac = MainActivity.getCurrentActivity();
        this.mp=MediaPlayer.create(ac.getApplicationContext(),resID);
        //A media player object is created
        setLimitVolume(limitVolume);
        setCurrentVolume(limitVolume);
        //sets the limitVolume given in input
    }

    /**
     * sets panning of sound in headphones, from -1 to 1 with 0 max value for both left and right and it decreases
     * linearly to 0 for the right side when the value of the panning is -1 and 0 for the left side with a panning of 1
     * @param pan the panning input
     */

    public void setPanning(float pan)
    {
        panR = 1;
        panL = 1;
        if(pan < 0)
            panR = (1+pan);
        else if(pan > 0)
            panL = (1-pan);
        setVolume(panL*currentVolume,panR*currentVolume);
    }

    /**
     * this method is to rescale volume from scale 0 - limitVolume to 0 - 1
     * @param soundVolume volume we want to rescale
     * @return the volume after scaling
     */

    private float getRealVolume(float soundVolume)
    {
        if(soundVolume>limitVolume)
            soundVolume=limitVolume;
        if(soundVolume<0)
            soundVolume=0;
        return (float) (1 - (Math.log(limitVolume - soundVolume) / Math.log(limitVolume)));
    }

    /**
     * sets the volume right and left
     * @param left
     * @param right
     */
    public void setVolume(float left , float right)
    {
        left = getRealVolume(left);
        right = getRealVolume(right);

        //Log.i("TAG","left: "+left+" right: "+right);
        this.mp.setVolume(left,right);
    }



    //Getters
    public MediaPlayer getMp() {
        return mp;
    }

    public float getLimitVolume() {
        return limitVolume;
    }

    public float getCurrentVolume() {
        return currentVolume;
    }

    //End of getters

    /**
     * sets the scale of the volume from 0 to limitVolume
     * @param vol the new limitVolume
     */
    public void setLimitVolume(float vol)
    {
        this.limitVolume=vol;
    }

    /**
     * changes the current volume that the sound will run with
     * @param vol
     */

    public void setCurrentVolume(float vol)
    {
        currentVolume = vol;
        setVolume(panL*currentVolume,panR*currentVolume);
    }


    /**
     * starts playing sound of the resource given in constructor with a Volume
     * if sound already playing, change volume only
     * @param Vol
     */
    public void playSound(float Vol)
    {
        setCurrentVolume(Vol);
        playSound();
    }

    /**
     * starts playing sound of the resource given in constructor with left and right volumes
     * if sound already playing, change volume only
     * @param left
     * @param right
     */

    public void playSound(float left, float right)
    {
        this.setVolume(left,right);
        playSound();
    }

    /**
     * starts playing sound of the resource given in constructor with left and right volumes
     */

    public void playSound()
    {
        if(mp != null && !mp.isPlaying())
        {
            this.mp.start();
        }
    }

    /**
     * stops the sound if it is playing
     */

    public void stopSound()
    {
        if(mp != null && mp.isPlaying())
        {
            this.mp.stop();
        }
    }

    /**
     * pauses the sound if it is playing
     */

    public void pauseSound()
    {
        if(mp != null && mp.isPlaying())
        {
            this.mp.pause();
        }
    }
}
