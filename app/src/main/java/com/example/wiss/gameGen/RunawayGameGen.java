package com.example.wiss.gameGen;

import com.example.wiss.game.GameLogic;
import com.example.wiss.game.RunawayGameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.io.input.GameITouchDirect;
import com.example.wiss.units.Player;

/**
 * Created by wiss on 26/07/17.
 */

public class RunawayGameGen extends GameGen {
    @Override
    public GameLogic generateGameLogic() {
        Player player = new Player();
        RunawayGameLogic gameLogic = new RunawayGameLogic();
        gameLogic.setPlayer(player);
        return gameLogic;    }

    @Override
    public GameIO generateGameIO(GameLogic gc) {
        GameIO gameIO = new GameIO();
        gameIO.setOnTouchListener(new GameITouchDirect(gc));
        // the outputs of classic gameGen are going to be set up inside the classicGameLogic
        return gameIO;
    }
}
