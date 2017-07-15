package com.example.wiss.myapplication;

import com.example.wiss.units.Player;

/**
 * Created by wiss on 15/07/17.
 */

public abstract class GameLogic extends Thread
{
    Player player;
    GameIO gameIO;

    public abstract void movePlayer(double x, double y);
    protected abstract void update();

    public void movePlayer(Vector position)
    {
        movePlayer(position.getX(),position.getY());
    }


    public void startUpdating()
    {
        this.start();
    }

    public void run()
    {
        while(true)
        {
            this.update();
        }
    }

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
