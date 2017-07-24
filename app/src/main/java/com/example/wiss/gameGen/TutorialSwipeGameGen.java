package com.example.wiss.gameGen;

import com.example.wiss.game.GameLogic;
import com.example.wiss.game.TutorialGameLogic;
import com.example.wiss.game.TutorialSwipeGameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.io.input.GameITouchDirect;
import com.example.wiss.io.input.GameITouchSwipe;
import com.example.wiss.units.Player;

/**
 * Created by no_one on 24/07/17.
 */

public class TutorialSwipeGameGen extends GameGen {

    @Override
    public GameLogic generateGameLogic()
    {
        // creating player
        Player player = new Player();

        // creating a simple game logic with the sound sources
        return new TutorialSwipeGameLogic(player);
    }


    @Override
    public GameIO generateGameIO(GameLogic gc)
    {
        // creating gameIO with swipe touch listener
        GameIO gameIO = new GameIO();
        gameIO.setOnTouchListener(new GameITouchSwipe(gc, 0.3));
        return gameIO;
    }
}
