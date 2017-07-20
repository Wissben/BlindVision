package com.example.wiss.gameGen;

import com.example.wiss.game.GameLogic;
import com.example.wiss.game.SimpleGameLogic;
import com.example.wiss.game.TutorialGameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.io.input.GameITouchDirect;
import com.example.wiss.io.input.GameITouchSwipe;
import com.example.wiss.myapplication.R;
import com.example.wiss.myapplication.WelcomeActivity;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;

import java.util.LinkedList;

/**
 * Created by Sidahmed on 20/07/17.
 */

public class TutorialGameGen extends GameGen {
    // distance from which sound can not be heard (we take the screen diagonal distance)
    double maxDistance = WelcomeActivity.getScreenVec().getAbsValue();


    /* Methods ==================================================================================== */

    @Override
    public GameLogic generateGameLogic()
    {
        // creating player
        Player player = new Player();

        // creating a simple game logic with the sound sources
        return new TutorialGameLogic(player);
    }


    @Override
    public GameIO generateGameIO(GameLogic gc)
    {
        // creating gameIO with direct touch listener
        GameIO gameIO = new GameIO();
        gameIO.setOnTouchListener(new GameITouchDirect(gc));
        return gameIO;
    }


}

