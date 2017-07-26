package com.example.wiss.gameGen;

import com.example.wiss.game.GameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.io.output.OutputStringAlreadyExistsException;
import com.example.wiss.myapplication.GameParameters;

/**
 * class GameGen is what generates Game behaviours
 * so 2 methods need to be implemented in order to generate a new game behaviour
 * a method that creates a gameLogic
 * a method that creates a GameIO
 * Created by ressay on 19/07/17.
 */

public abstract class GameGen
{
    // the next gameGen to be run
    GameGen next = null;
    public void generateGameParams()
    {
        GameLogic gc = generateGameLogic();
        GameIO gameIO = generateGameIO(gc);
        GameParameters.setGameLogic(gc);
        GameParameters.setGameIO(gameIO);
    }

    public GameGen getNext() {
        return next;
    }

    public void setNext(GameGen next) {
        this.next = next;
    }

    public abstract GameLogic generateGameLogic();
    public abstract GameIO generateGameIO(GameLogic gc);
}
