package com.example.wiss.units;

import android.util.Log;

import com.example.wiss.myapplication.Direction;
import com.example.wiss.myapplication.Vector;

/**
 * Created by Sidahmed on 12/07/17.
 * This class represents a Unit into the game. The unit can be a player, a sound, a distraction
 * sound ...etc
 */

public abstract class Unit {
    protected Vector position = null;


    /* Constructors =============================================================================== */

    public Unit() { this.position = new Vector(0, 0); }


    public Unit(double x, double y) { this.position = new Vector(x, y); }


    public Unit(Vector pos) {
        this.position = new Vector(pos.getX(), pos.getY());
    }


    /* Methods =========================================================================== */

    /**
     * This method moves the unit in 'd' direction. There is no map, the unit moves when it's (x, y) changes.
     * For now, it's simple, it just move with one ; we can modify it later to have speed ... etc
     * @param d the direction in which the unit has to move.
     */
    public void move(Direction d) {
        double currX = position.getX();
        double currY = position.getY();

        /* Change the position according to the direction d. */
        if( d.equals(Direction.UP) )
            setPosition(currX, currY + 1);
        else if( d.equals(Direction.DOWN) )
            setPosition(currX, currY - 1);
        else if( d.equals(Direction.RIGHT) )
            setPosition(currX + 1, currY);
        else if( d.equals(Direction.LEFT) )
            setPosition(currX - 1, currY);
        else if( d.equals(Direction.UP_RIGHT) )
            setPosition(currX + 1, currY + 1);
        else if( d.equals(Direction.UP_LEFT) )
            setPosition(currX - 1, currY + 1);
        else if( d.equals(Direction.DOWN_RIGHT) )
            setPosition(currX + 1, currY - 1);
        else if( d.equals(Direction.DOWN_LEFT) )
            setPosition(currX - 1, currY - 1);

        /* For debuging. */
        Log.i("UNIT", "Moved " + d.toString() + ", New position : (" + position.getX() +
                ", " + position.getY() + ")");
    }


    /* Setters & Getters ========================================================================== */

    public Vector getPosition() { return this.position; }

    public void setPosition(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setPosition(Vector pos){
        this.position.setX(pos.getX());
        this.position.setY(pos.getY());
    }
}
