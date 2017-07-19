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
    @Override
    public GameLogic generateGameLogic()
    {
        Vector screenVec = WelcomeActivity.getScreenVec();
        SimpleSoundSource simple;
        Player player = new Player();


        LinkedList<SoundSource> soundSources = new LinkedList<>();

        // creating sound sources
        simple = new SimpleSoundSource(200, 500);
        simple.initialise(player, R.raw.snk, screenVec.getAbsValue());
        soundSources.add(simple);

        simple = new SimpleSoundSource(500, 1000);
        simple.initialise(player, R.raw.meza, screenVec.getAbsValue());
        soundSources.add(simple);

        return new SimpleGameLogic(player,soundSources,soundSources.get(0),screenVec.getAbsValue());
    }


    @Override
    public GameIO generateGameIO(GameLogic gc)
    {
        GameIO gameIO = new GameIO();
        gameIO.setOnTouchListener(new GameITouchDirect(gc));
        return gameIO;
    }


}
