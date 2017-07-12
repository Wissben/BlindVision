package com.example.wiss.myapplication;

/**
 * Created by Sidahmed on 12/07/17.
 * This class represents a Unit into the game. The unit can be a player, a sound, a distraction
 * sound ...etc
 */

public abstract class Unit {
    protected Vector position = null;


    public Unit() { }


    public Unit(float x, float y) { this.position = new Vector(x, y); }


    public Unit(Vector position) {
        this.position = new Vector(position.getX(), position.getY());
    }


    public abstract void move(Direction d);
}
