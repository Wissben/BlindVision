package com.example.wiss.gameGen;

import android.util.Log;

import com.example.wiss.game.GameLogic;
import com.example.wiss.game.SimpleGameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.io.input.GameITouchDirect;
import com.example.wiss.io.output.OutputStringAlreadyExistsException;
import com.example.wiss.io.output.methods.GameOLookFor;
import com.example.wiss.io.output.methods.GameOEnd;
import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.myapplication.MyMath;
import com.example.wiss.myapplication.R;
import com.example.wiss.myapplication.Vector;
import com.example.wiss.myapplication.WelcomeActivity;
import com.example.wiss.sound.SoundName;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;

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
    Vector screenVec = WelcomeActivity.getScreenVec();
    double maxDistance = screenVec.getAbsValue();
    // number of sound sources in the game
    int numSources = 2;
    int[] possibleSounds;

    public FirstGameGen(int numSources)
    {
        this.numSources = numSources;
    }

    public FirstGameGen(int numSources,int[] possibleSounds)
    {
        this.numSources = numSources;
        this.possibleSounds = possibleSounds;
    }


    @Override
    public GameLogic generateGameLogic()
    {
        int limit=10000;
        // creating player
        Vector screen = WelcomeActivity.getScreenVec();
        double limitX = screen.getX();
        double limitY = screen.getY();

        Player player = new Player();


        LinkedList<SimpleSoundSource> soundSources = new LinkedList<>();
        int[] allSounds = SoundName.getAllSoundsID();
        LinkedList<Integer> selectedSounds = new LinkedList<>();
        int r = 0;
        for(int i=0 ; i<numSources;i++)
        {
            double x = MyMath.random(0,limitX);
            double y = MyMath.random(0,limitY);
            SimpleSoundSource simple = new SimpleSoundSource(x, y);
            int count = 0;
            do {
                r = MyMath.random(0,allSounds.length-1);
                count++;
            } while (selectedSounds.contains(r) && count < limit);
            if(count<limit)
            selectedSounds.add(r);
            else break; // if after so many tries we didn't find unused sound, we content our selves with the selected ones
            Log.d("gameGen","size is " + allSounds.length + " r is " + r);
            simple.initialise(player,allSounds[r], screen.getAbsValue() );
            soundSources.add(simple);
        }
        Log.d("gameGen","going to sound sources");
        r = MyMath.random(0,soundSources.size()-1);
        // creating a simple game logic with the sound sources
        return new SimpleGameLogic(player,soundSources,soundSources.get(r),GameActivity.getFavoredCatchingDistance());
    }


    @Override
    public GameIO generateGameIO(GameLogic gc)
    {
        // creating gameIO with direct touch listener
        GameIO gameIO = new GameIO();
        gameIO.setOnTouchListener(new GameITouchDirect(gc));

        // adding possible outputs to SimpleGameLogic
        try {
            gameIO.addOutput("lookFor",new GameOLookFor());
            gameIO.addOutput("win",new GameOEnd(R.raw.youwin,R.raw.backtowelcome));
        } catch (OutputStringAlreadyExistsException e) {
            e.printStackTrace();
        }

        return gameIO;
    }


}
