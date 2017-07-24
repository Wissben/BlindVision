package com.example.wiss.myapplication;

import android.util.Log;

/**
 * Created by wiss on 30/06/17.
 */

public class MyMath {

    public static float getDistance(int x1 , int y1 , int x2 , int y2)
    {
        int pow1 = (x1+x2)*(x1+x2);
        int pow2 = (y1+y2)*(y1+y2);
        return (float)Math.sqrt(pow1+pow2);
    }

    public static void cartToPolar(int x , int y , float r , float theta)
    {
        r = (float)Math.sqrt (x*x +y*y);
        theta   = (float)Math.atan( y / x );

    }

    public static float remap(float value,float istart,float istop,float ostart,float ostop)
    {
        //this method remap a value from it starting interval [isrart..istop] to a new interval [ostart..ostop]
        //And keeps the same proportions
        return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
    }

    public static float getAngleFromCenter(int xCenter, int yCenter, int x , int y, int width , int height)
    {
        if(x>=width-1)
            x=width;
        //Due to some errors this line is a little cheat code :p


        float dx=x-xCenter;
        // dx is the distance from the point to the center, we will use it as a COEFFISSIOU to adjust the angle.
        float dy=y-yCenter;
        if(dx>0)
        {

            //Log.d("TAG","x;xCenter: "+x+";"+xCenter+"/dx: "+dx+"/width/2: "+width/2+"/remap: "+remap(dx,0,width/2,90,0));
            return remap(dx,0,width/2,90,0);
            // in the previous line we map the value of dx like this :
            // dx ---> 0 then angle ---> 90° ( the middle )
            // dx ---> width/2 then angle ---> 0°( the right side of the screen )

        }
        if(dx<0)
        {
            return remap(dx,-width/2,0,180,90);
            // in the previous line we map the value of dx like this :
            // dx ---> -width/2 then angle ---> 180° ( the left side of the screen )
            // dx ---> 0 then angle ---> 90°( the middle )

        }

        return 0;
    }

    static public double random(double min, double max)
    {
        double range = (max - min);
        return (Math.random() * range) + min;
    }

    public static Vector getRandomPosition(Vector point,double minDistance,Vector rectangleBottomLeft,Vector rectangleTopRight)
    {
        int attempts = 0;
        int limit = 100000;


        Vector position=null;
        // the rectangle is inside the minimum distance from the point
        if(point.getDistance(rectangleBottomLeft) <= minDistance && point.getDistance(rectangleTopRight) <= minDistance)
            return null;
        Vector range = rectangleTopRight.sub(rectangleBottomLeft).copy();

        do {
            double x = MyMath.random(0,range.getX()), y = MyMath.random(0,range.getY());
            position = new Vector(x,y);
            attempts++;

            if(attempts > limit) // to avoid infinite loop we return one of the rectangle points
                return rectangleBottomLeft.copy();
        } while (point.getDistance(position) < minDistance); // if the distance between the generated point and the center point is inferior to the minimum distance we generate another point
        Log.d("classic","found a point");
        return position;
    }

}
