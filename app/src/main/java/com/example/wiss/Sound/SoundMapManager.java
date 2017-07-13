package com.example.wiss.Sound;

import android.graphics.Point;
import android.util.Log;

import com.example.wiss.myapplication.MyMath;
import com.example.wiss.myapplication.Vector;

/**
 * Created by ressay on 07/07/17.
 */

public class SoundMapManager
{

    double soundLimit;

    /**
     * constructor of a sound manager considering a map, taking maximum distance from which sound can not be heard
     * as input
     * @param maxDistance
     */
    public SoundMapManager(double maxDistance)
    {
        soundLimit = maxDistance;
    }

    /**
     * static method to help convert from android point to Vector instance
     * @param p android point
     * @return Vector with android point coordinates
     */

    static public Vector Vector(Point p)
    {
        return new Vector(p.x,p.y);
    }

    /**
     * produces sound given a distance (between two points) and an angle with unit vector (1,0) anticlockwise
     * so if the SoundHandler is already producing sound the method will just update volume
     * else it'll start producing sound
     * @param dis distance
     * @param ang angle
     * @param sh SoundHandler
     * @return instance of the SoundHandler that has been modified (case the instance has been created by one of the calls from coming methods)
     * the instance returned will be used in case change sound properties of the produced sound by this method
     */

    public SoundHandler produceSound(double dis, double ang,SoundHandler sh)
    {
        sh.setLimitVolume((float)soundLimit);
        float volume = MyMath.remap((float)dis,0,sh.getLimitVolume(),sh.getLimitVolume(),0);

//        if(ang > Math.PI) ang = 2*Math.PI-ang; // sound behind you or in front of you are treated same way
        Log.d("myTag",Math.cos(ang) + " is the cos");
        Log.d("myTag",ang + " is the angle and " + dis + " is the distance");
        sh.setPanning((float)Math.cos(ang));
        sh.playSound(volume);
        return sh;
    }

    /**
     * same method with different params (resID instead of SoundHandler) so this method will create the
     * SoundHandler that will produce the sound from the resource ID
     * this method will also return the SoundHandler created for further use and modification of sound properties
     * @param dis
     * @param ang
     * @param resID
     * @return
     */

    public SoundHandler produceSound(double dis, double ang,int resID)
    {
        return produceSound(dis,ang,new SoundHandler(resID));
    }

    /**
     * takes two points generate distance and angle to call produceSound method
     * @param p1
     * @param p2
     * @param sh
     * @return
     */

    public SoundHandler produceSoundBetweenPoints(Vector p1, Vector p2,SoundHandler sh)
    {
        Vector v1 = new Vector(p2,p1);
        double dis = v1.getAbsValue();
        double ang = v1.getAngle();
        return produceSound(dis,ang,sh);
    }

    /**
     * same method with different params (android Points instead of Vectors)
     * @param p1
     * @param p2
     * @param sh
     * @return
     */

    public SoundHandler produceSoundBetweenPoints(Point p1, Point p2,SoundHandler sh)
    {
        return produceSoundBetweenPoints(Vector(p1),Vector(p2),sh);
    }

    /**
     * same method with different params (resID instead of SoundHandler) so this method will create the
     * SoundHandler that will produce the sound from the resource ID
     * this method will also return the SoundHandler created for further use and modification of sound properties
     * @param p1
     * @param p2
     * @param resID
     * @return
     */

    public SoundHandler produceSoundBetweenPoints(Vector p1, Vector p2,int resID)
    {
        return produceSoundBetweenPoints(p1,p2,new SoundHandler(resID));
    }





}
