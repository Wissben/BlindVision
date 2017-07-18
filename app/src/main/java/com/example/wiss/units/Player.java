package com.example.wiss.units;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.example.wiss.myapplication.MainActivity;
import com.example.wiss.myapplication.R;
import com.example.wiss.myapplication.Vector;

/**
 * Created by no_one on 12/07/17.
 */

public class Player extends Unit {

    private Vector vel = new Vector(0, 0);
    private Vector acc = new Vector(0, 0);
    private ImageView icon ;
    private Activity currentAct ;
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

    public Player(Vector pos , Vector vel , Vector acc ,Activity act)
    {
        super();
        this.icon =(ImageView) act.findViewById(R.id.imageView);
        this.position=pos;
        this.vel=vel;
        this.acc=acc;
        this.currentAct=act;
    }


    /* Methods ==================================================================================== */


    public void setVelAccel(Vector vel, Vector acc) {
        this.vel = vel;
        this.acc = acc;
    }

    public void updatePosition() {
        this.vel.add(this.acc);
        this.position.add(this.vel);
        this.icon.setX((float)this.getPosition().getX());
        this.icon.setY((float)this.getPosition().getY());
    }


    //Getters and setters BEGIN
    public Vector getVel() {
        return vel;
    }

    public void setVel(Vector vel) {
        this.vel = vel;
    }

    public Vector getAcc() {
        return acc;
    }

    public void setAcc(Vector acc) {
        this.acc = acc;
    }

    public ImageView getIcon() {
        return icon;
    }

    public void setIcon(ImageView icon) {
        this.icon = icon;
    }

    public Activity getCurrentAct() {
        return currentAct;
    }

    public void setCurrentAct(Activity currentAct) {
        this.currentAct = currentAct;
    }
//Getters and setters END


}
