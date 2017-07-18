package com.example.wiss.units;

import com.example.wiss.myapplication.Vector;

/**
 * Created by no_one on 12/07/17.
 */

public class Player extends Unit {

    private Vector vel = new Vector(0, 0);
    private Vector acc = new Vector(0, 0);
    /* Constructors =============================================================================== */

    public Player() {
        super();
    }


    public Player(double x, double y) {
        super(x, y);
    }


    public Player(Vector pos) {
        super(pos);
    }

    public void setVelAccel(Vector vel, Vector acc) {
        this.vel = vel;
        this.acc = acc;
    }

    public void updatePosition() {
        this.vel.add(this.acc);
        this.position.add(this.vel);
    }

    /* Methods ==================================================================================== */


}
