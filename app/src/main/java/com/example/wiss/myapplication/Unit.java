package com.example.wiss.myapplication;

/**
 * Created by Sidahmed on 12/07/17.
 * This class represents a Unit into the game. The unit can be a player, a sound, a distraction
 * sound ...etc
 */

public abstract class Unit {
    protected Vector position = null;


    /* Constructors =============================================================================== */

    public Unit() { this.position = new Vector(0, 0); }


    public Unit(float x, float y) { this.position = new Vector(x, y); }


    public Unit(Vector pos) {
        this.position = new Vector(pos.getX(), pos.getY());
    }


    /* Abstract Methods =========================================================================== */

    public abstract void move(Direction d);


    /* Setters & Getters ========================================================================== */

    public Vector getPosition() { return this.position; }

    public void setPosition(float x, float y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setPosition(Vector pos){
        this.position.setX(pos.getX());
        this.position.setY(pos.getY());
    }
}
