package com.example.wiss.gameGen;

import com.example.wiss.game.GameLogic;
import com.example.wiss.game.SimpleGameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.io.input.GameITouchDirect;
import com.example.wiss.io.output.GameOManager;
import com.example.wiss.io.output.OutputStringAlreadyExistsException;
import com.example.wiss.io.output.methods.GameOSimpleSound;
import com.example.wiss.io.output.methods.GameOWin;
import com.example.wiss.myapplication.GameParameters;
import com.example.wiss.myapplication.R;
import com.example.wiss.myapplication.Vector;
import com.example.wiss.myapplication.WelcomeActivity;
import com.example.wiss.sound.SequenceSoundManager;
import com.example.wiss.sound.SoundName;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;

import java.util.LinkedList;

/**
 * class GameGen is what generates Game behaviours
 * so 2 methods need to be implemented in order to generate a new game behaviour
 * a method that creates a gameLogic
 * a method that creates a GameIO
 * this is a simple implementation of GameGen classes that creates a test GameLogic
 * Created by ressay on 19/07/17.
 */

public class FirstGameGen extends GameGen
{
    // distance from which sound can not be heard (we take the screen diagonal distance)
    double maxDistance = WelcomeActivity.getScreenVec().getAbsValue();
    int r = 0;
    @Override
    public GameLogic generateGameLogic()
    {
        // creating player
        Player player = new Player();


        LinkedList<SimpleSoundSource> soundSources = new LinkedList<>();

        // creating sound sources
        SimpleSoundSource simple;
        simple = new SimpleSoundSource(100, 1000);
        simple.initialise(player, R.raw.bearsound, maxDistance);
        soundSources.add(simple);

        simple = new SimpleSoundSource(500, 1000);
        simple.initialise(player, R.raw.cowsound, maxDistance);
        soundSources.add(simple);

        // creating a simple game logic with the sound sources
        return new SimpleGameLogic(player,soundSources,soundSources.get(r),50);
    }


    @Override
    public GameIO generateGameIO(GameLogic gc)
    {
        // creating gameIO with direct touch listener
        GameIO gameIO = new GameIO();
        gameIO.setOnTouchListener(new GameITouchDirect(gc));

        // creating output one, look for target
        SequenceSoundManager sequence = new SequenceSoundManager();
        sequence.addSounds(R.raw.lookfor,R.raw.bear);

        try {
            gameIO.addOutput("targetTask",new GameOSimpleSound(sequence));
            gameIO.addOutput("win",new GameOWin(R.raw.youwin));
        } catch (OutputStringAlreadyExistsException e) {
            e.printStackTrace();
        }

        return gameIO;
    }


}
