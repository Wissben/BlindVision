package com.example.wiss.gameGen;

import com.example.wiss.game.GameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.myapplication.GameParameters;

/**
 * Created by ressay on 19/07/17.
 */

public abstract class GameGen
{
    public void generateGameParams()
    {
        GameLogic gc = generateGameLogic();
        GameIO gameIO = generateGameIO(gc);
        GameParameters.setGameLogic(gc);
        GameParameters.setGameIO(gameIO);
    }

    public abstract GameLogic generateGameLogic();
    public abstract GameIO generateGameIO(GameLogic gc);
}
