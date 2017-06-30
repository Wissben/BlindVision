package com.example.wiss.myapplication;

import android.content.Context;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;
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
    private float limitVolume; //
    private float angle;
    private float leftVolume,rightVolume;



    public SoundHandler(int resID, Context currentContext)
    {
        //Default constructor
        this.mp=MediaPlayer.create(currentContext,resID);
        //A media player object is created
        this.limitVolume=1;
        //The default sound volume is 1 ( this value is applied directly on the media player mp )
        this.angle=0;
        //Default angle is 0 ( the middle )
        this.mp.setVolume(limitVolume,limitVolume);
        //This method sets the left/right volume of the media player object
    }

    public SoundHandler(int resID, Context currentContext,float angle,float limitVolume)
    {
        //Surcharged constructor :
        this.mp=MediaPlayer.create(currentContext,resID);
        //A media player object is created
        this.setLimitVolume(limitVolume);
        //We set the limitVolume
        this.angle=angle;
        //We set the initial angle
        // angle in [0;180]
        this.setMpVolume(angle,limitVolume);
        //We set up the MP volume according to the angle and the limitVolume
        //See setMpVolume next ->

    }

    private void setMpVolume(float angle,float limitVolume)
    {
        //Angle here is in 0..180
        //We check the values of the angle
        if(angle > 180)
        {
            angle =180;
        }
        if(angle < 0)
        {
            angle =0;
        }
        //End of Tests

        if(angle == 90)
        {
            this.mp.setVolume(limitVolume,limitVolume);
            //if its the middle then the volume is at its max on both sides
        }
        if(angle < 90 )
        {
            //if the angle is between 0 and 90 ( right side )
            float anglePercent = MyMath.remap(angle,0,90,0,1);
            // in the previous line we remap the value of the angle so that :
            // angle ---> 0 the lefVolume decrease
            // angle ---> 90 the leftVolume increases
            //We set the leftVolume according the anglePercent we've calculated earlier

            //Log.d("TAG","anglePercent < 90 : "+anglePercent);
            this.setVolume(anglePercent*limitVolume,limitVolume);
        }
        if (angle > 90)
        {
            //if the angle is between 90 and 180 ( left side )
            float anglePercent = MyMath.remap(angle,90,180,1,0);
            // in the previous line we remap the value of the angle so that :
            // angle ---> 180 the rightVolume decrease
            // angle ---> 90 the rightVolume increases
            //We set the rightVolume according the anglePercent we've calculated earlier

            //Log.d("TAG","anglePercent > 90 : "+anglePercent);
            this.setVolume(limitVolume,(anglePercent)*limitVolume);
        }
    }


    public void setVolume(float left , float right)
    {
        //left and right must be in 0..1, so we'll use the remap function :
        if(left>limitVolume)
            leftVolume=left=limitVolume;
        if(left<0)
            leftVolume=left=0;


        if(right>limitVolume)
            rightVolume=right=limitVolume;
        if(right<0)
            rightVolume=right=0;


        leftVolume=  left  = MyMath.remap(left,0,limitVolume,0,1);
        rightVolume= right = MyMath.remap(right,0,limitVolume,0,1);

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

    public float getAngle() {
        return angle;
    }
    //End of getters

    public void setLimitVolume(float vol)
    {
        this.limitVolume=vol;
    }


    public void setAngle(float angle)
    {
        //The setAngle method by itself call the the setMpVolume method after chaging the actual value of the angle
        this.angle= angle;
        setMpVolume(angle,this.limitVolume);//This method does the rest of the tests
    }

    public void playSound(float angle)
    {
        if(mp != null && !mp.isPlaying())
        {
            //We set the angle
            this.setAngle(angle);
            this.mp.start();
        }
    }

    public void stopSound()
    {
        if(mp != null && mp.isPlaying())
        {
            this.mp.stop();
        }
    }
}
