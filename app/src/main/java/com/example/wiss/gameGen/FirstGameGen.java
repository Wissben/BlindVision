package com.example.wiss.gameGen;

import com.example.wiss.game.GameLogic;
import com.example.wiss.game.SimpleGameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.io.input.GameITouchDirect;
import com.example.wiss.io.output.GameOManager;
import com.example.wiss.myapplication.GameParameters;
import com.example.wiss.myapplication.R;
import com.example.wiss.myapplication.Vector;
import com.example.wiss.myapplication.WelcomeActivity;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;

import java.util.LinkedList;

/**
 * Created by ressay on 19/07/17.
 */

public class FirstGameGen extends GameGen
{
    // distance from which sound can not be heard (we take the screen diagonal distance)
    double maxDistance = WelcomeActivity.getScreenVec().getAbsValue();
    @Override
    public GameLogic generateGameLogic()
    {
        // creating player
        Player player = new Player();


        LinkedList<SoundSource> soundSources = new LinkedList<>();

        // creating sound sources
        SimpleSoundSource simple;
        simple = new SimpleSoundSource(200, 500);
        simple.initialise(player, R.raw.snk, maxDistance);
        soundSources.add(simple);

        simple = new SimpleSoundSource(500, 1000);
        simple.initialise(player, R.raw.meza, maxDistance);
        soundSources.add(simple);

        // creating a simple game logic with the sound sources
        return new SimpleGameLogic(player,soundSources,soundSources.get(0),maxDistance);
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