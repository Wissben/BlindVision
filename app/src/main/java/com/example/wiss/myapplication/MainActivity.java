package com.example.wiss.myapplication;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.example.wiss.game.GameITouchDirect;
import com.example.wiss.game.GameITouchSwipe;
import com.example.wiss.game.GameLogic;
import com.example.wiss.game.SimpleGameLogic;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;
import com.example.wiss.sound.SoundSourceNotInitialisedException;
import com.example.wiss.sound.SoundUpdater;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    static private Vector screenVec = null;
    static private Activity currentActivity = null;
    SoundUpdater soundUpdater;
    LinkedList<SoundSource> soundSources = new LinkedList<>();
    Player player = new Player();
    GameLogic gameLogic;
    //This is the handler of the touch :
    private View.OnTouchListener handleTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        screenVec = getScreenVector();
        setCurrentActivity(this);
        SimpleSoundSource simple;

        Log.d("myTag","first creation!");
        // creating sound sources
        simple = new SimpleSoundSource(200,500);
        simple.initialise(player,R.raw.tromp,screenVec.getAbsValue());
        soundSources.add(simple);

//        simple = new SimpleSoundSource(500,1000);
//        simple.initialise(player,R.raw.soo,screenVec.getAbsValue());
//        soundSources.add(simple);

        simple = new SimpleSoundSource(500,1000);
        simple.initialise(player,R.raw.meza,screenVec.getAbsValue());
        soundSources.add(simple);

        // setting up SoundManagers
        for(int i=0;i<soundSources.size();i++)
            try {
                soundSources.get(i).setupSoundManager();
            } catch (SoundSourceNotInitialisedException e) {
                e.printStackTrace();
            }

        Log.d("myTag","updater creation!");

        // adding sound sources to updater in order to get updated automatically in separate thread
        soundUpdater = new SoundUpdater();
        soundUpdater.addSoundSourcesToUpdate(soundSources);
        soundUpdater.startUpdating();

        Log.d("myTag","starting updater!");

        /* Setting the gameLogic and the handleTouch. It is using swipe touch now. */
        this.gameLogic = new SimpleGameLogic(this.player);
//        this.handleTouch = new GameITouchDirect(this.gameLogic);
        this.handleTouch = new GameITouchSwipe(this.gameLogic);
        this.findViewById(android.R.id.content).setOnTouchListener(this.handleTouch);
    }


    @Override
    protected void onPause() {
        super.onPause();
        this.onDestroy();
    }

    /**
     * to call in onCreate of activities
     * @param ac
     */

    static void setCurrentActivity(Activity ac)
    {
        currentActivity = ac;
    }

    static public Activity getCurrentActivity()
    {
        return currentActivity;
    }

    static public Vector getScreenVec()
    {
        return screenVec;
    }

    Vector getScreenVector()
    {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return new Vector(size.x,size.y);
    }

}
