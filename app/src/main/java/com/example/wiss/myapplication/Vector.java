package com.example.wiss.myapplication;

/**
 * Created by ressay on 07/07/17.
 */

public class Vector
{
    private double x,y;


    /**
     * constructor with x and y coordinates
     * @param x
     * @param y
     */
    public Vector(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * constructor from two points p1 and p2 and creates the Vector p1p2
     * @param p1 first Point
     * @param p2 second Point
     */

    public Vector(Vector p1, Vector p2)
    {
        setX(p2.getX() - p1.getX());
        setY(p2.getY() - p1.getY());
    }


    // getters and setters start

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // getters and setters end

    /**
     * creates a vector from its polar coordinates
     * @param ang angle in radian
     * @param r distance
     * @return a new Vector
     */

    static public Vector getVecFromPolarCoordinates(double ang, double r)
    {
        return new Vector(Math.cos(ang)*r,Math.sin(ang)*r);
    }

    public double getAbsValue()
    {
        return Math.sqrt(x*x+y*y);
    }

    /**
     * method that creates a copy of this Vector
     * @return a copy of the Vector
     */

    public Vector copy()
    {
        return new Vector(x,y);
    }

    /**
     * method that subtracts a Vector from this Vector
     * @param v Vector to subtract
     * @return this Vector for easy reuse
     */

    public Vector sub(Vector v)
    {
        x -= v.getX();
        y -= v.getY();
        return this;
    }

    /**
     * method that adds a Vector to this Vector
     * @param v Vector to add
     * @return this Vector for easy reuse
     */

    public Vector add(Vector v)
    {
        x += v.getX();
        y += v.getY();
        return this;
    }

    /**
     * method that multiply Vector by scalar
     * @param d scalar to multiply by
     * @return this Vector for easy reuse
     */

    public Vector mul(double d)
    {
        x *= d;
        y *= d;
        return this;
    }

    /**
     * method that divide Vector by scalar
     * @param d scalar to divide by
     * @return this Vector for easy reuse
     */

    public Vector div(double d)
    {
        x /= d;
        y /= d;
        return this;
    }

    /**
     * method that gets distance between this Vector and a Vector given in input (as points)
     * @param v
     * @return distance between this Vector and the Vector v given in input
     */

    public double getDistance(Vector v)
    {
        return copy().sub(v).getAbsValue();
    }

    /**
     * the static method of getDistance
     * @param v1 first Vector
     * @param v2 second Vector
     * @return distance between Vector v1 and Vector v2
     */

    static public double getDistance(Vector v1, Vector v2)
    {
        return v1.getDistance(v2);
    }

    /**
     * method that gets the unit Vector of this Vector
     * @return the Unit Vector
     */

    public Vector getUnitVector()
    {
        return copy().div(getAbsValue());
    }

    /**
     * method that resizes this Vector to a Vector with absolute value given in input
     * @param size the new absolute value
     * @return this Vector for easy reuse
     */

    public Vector resize(double size)
    {
        return div(getAbsValue()).mul(size);
    }

    /**
     * method that gets the angle between this Vector and unit Vector (1,0)
     * the angle will be ((1,0),this) anticlockwise
     * @return angle
     */

    public double getAngle()
    {

        double ang = Math.acos(getUnitVector().getX());
        if(getY()*getX() < 0)
            if(getX() > 0)
                ang = 2*Math.PI - ang;
            else
                ang = Math.PI - ang;
        else
            if(getX() < 0)
                ang += Math.PI;

        return ang;
    }

    /**
     * method that gets the angle between this Vector and Vector v given in input
     * @return angle (v,this) anticlockwise
     */

    public double getAngle(Vector v)
    {
        double ang = getAngle() - v.getAngle();
        if(ang < 0)
            ang = 2*Math.PI - ang;
        return ang;
    }

    /**
     * the static method of getAngle
     * @param v1 first Vector
     * @param v2 second Vector
     * @return the angle (v1,v2) anticlockwise
     */

    static public double getAngle(Vector v1, Vector v2)
    {
        return v2.getAngle(v1);
    }

}
