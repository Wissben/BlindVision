package com.example.wiss.myapplication;

/**
 * Created by Sidahmed on 17/07/17.
 * This interface is implemented by the classes that have to give a choice to the player.
 * When a choice is done using the class 'SwipeChoice', the class 'SwipeChoice' will call one of these
 * methods in the calling class (the class that called 'SwipeChoice') depending on the player swipe.
 */

public interface Choiceable {
    public void up();
    public void down();
    public void left();
    public void right();
}
