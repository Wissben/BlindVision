package com.example.wiss.game;

import com.example.wiss.myapplication.Vector;
import com.example.wiss.units.Player;

/**
 * Created by wiss on 15/07/17.
 * This class will update the units positions on the map.
 * In a simple case, we only need to change the player position, but we created this class
 * in case we wanna move other units.
 */

public abstract class GameLogic extends Thread
{
    Player player;
    GameIO gameIO;


    /* Abstract Methods ========================================================================== */

    /**
     * This will move the player using the vector.
     */
    public void movePlayer(Vector vector)
    {
        Vector v = player.getPosition().copy();
        movePlayerToPos(v.add(vector));
    }

    /**
     * same method as movePlayerToPos with different parameters
     * @param vector
     */

    public void movePlayerToPos(Vector vector)
    {
        movePlayerToPos(vector.getX(),vector.getY());
    }


    /**
     * This will move the player to the specific position in the map.
     */
    public abstract void movePlayerToPos(double x, double y);


    /**
     * Don't know where this will be used yet.
     */
    protected abstract void update();


    /* Methods ==================================================================================== */


    public void startUpdating() { this.start(); }

    public void run()
    {
        while(true)
        {
            this.update();
        }
    }


    /* Setters & Getters ========================================================================== */

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public GameIO getGameIO() {
        return gameIO;
    }

    public void setGameIO(GameIO gameIO) {
        this.gameIO = gameIO;
    }
}
