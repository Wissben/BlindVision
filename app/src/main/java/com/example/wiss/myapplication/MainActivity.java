package com.example.wiss.myapplication;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.example.wiss.game.GameITouchSwipe;
import com.example.wiss.game.GameLogic;
import com.example.wiss.game.SimpleGameLogic;
import com.example.wiss.sound.SoundUpdater;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;
import com.example.wiss.sound.SoundSourceNotInitialisedException;
import com.example.wiss.updater.Updater;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    static private Vector screenVec = null;
    static private Activity currentActivity = null;
    Updater updater;
    SoundUpdater soundUpdater;
    LinkedList<SoundSource> soundSources = new LinkedList<>();
    GameLogic gameLogic;
    //This is the handler of the touch :
    private View.OnTouchListener handleTouch;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        screenVec = getScreenVector();
        setCurrentActivity(this);
        SimpleSoundSource simple;
        Player player = new Player();

        // creating sound sources
        simple = new SimpleSoundSource(200,500);
        simple.initialise(player,R.raw.tromp,screenVec.getAbsValue());
        soundSources.add(simple);

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


        /* Setting the gameLogic and the handleTouch. It is using swipe touch now. */

        int lower = 0 ;
        int upper = soundSources.size()-1;
        int r = (int) (Math.random() * (upper - lower)) + lower;

        this.gameLogic = new SimpleGameLogic(player,soundSources,soundSources.get(r),2);

//        this.handleTouch = new GameITouchDirect(this.gameLogic);
        this.handleTouch = new GameITouchSwipe(this.gameLogic,0.3);
        this.findViewById(android.R.id.content).setOnTouchListener(this.handleTouch);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        updater = new Updater(100);
        soundUpdater = new SoundUpdater();
        soundUpdater.addSoundSourcesToUpdate(soundSources);
        updater.addToUpdate(soundUpdater);
        updater.startUpdating();
        Log.d("myTag",gameLogic.getPlayer().getPosition()+ " is this");
    }


    @Override
    protected void onPause() {
        super.onPause();
        int size = soundSources.size();
        updater.cancel();
        for(int i=0;i<size;i++)
            soundSources.get(i).pauseSound();

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        int size = soundSources.size();
        updater.cancel();
        for(int i=0;i<size;i++)
            soundSources.get(i).stopSound();
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
