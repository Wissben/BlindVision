package com.example.wiss.myapplication;

import com.example.wiss.game.GameLogic;
import com.example.wiss.io.GameIO;

/**
 * Created by ressay on 19/07/17.
 */

public class GameParameters
{
    static private GameLogic gameLogic;
    static private GameIO gameIO;

    public static GameLogic getGameLogic() {
        return gameLogic;
    }

    public static void setGameLogic(GameLogic gameLogic) {
        GameParameters.gameLogic = gameLogic;
    }

    public static GameIO getGameIO() {
        return gameIO;
    }

    public static void setGameIO(GameIO gameIO) {
        GameParameters.gameIO = gameIO;
    }
}
