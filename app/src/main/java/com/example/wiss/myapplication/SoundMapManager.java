package com.example.wiss.myapplication;

import android.graphics.Point;

/**
 * Created by ressay on 07/07/17.
 */

public class SoundMapManager
{
    SoundHandler sh;
    Vector screenVector;
    SoundMapManager(SoundHandler s,Vector screenVec)
    {
        sh = s;
        screenVector = screenVec;
        sh.setLimitVolume((float)screenVector.getAbsValue());
    }

    static public Vector Vector(Point p)
    {
        return new Vector(p.x,p.y);
    }

    public void produceSoundBetweenPoints(Point p1, Point p2)
    {
        Vector v1 = new Vector(Vector(p1),Vector(p2));
        double dis = v1.getAbsValue();
        double ang = v1.getAngle();
        produceSound(dis,ang);
    }

    public void produceSound(double dis, double ang)
    {
        float volume = MyMath.remap((float)dis,0,(float)screenVector.getAbsValue(),(float)screenVector.getAbsValue(),0);

    }

}
