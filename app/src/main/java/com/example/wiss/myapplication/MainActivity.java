package com.example.wiss.myapplication;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

import com.example.wiss.Sound.SimpleSoundSource;
import com.example.wiss.Sound.SoundHandler;
import com.example.wiss.Sound.SoundMapManager;
import com.example.wiss.Sound.SoundSource;
import com.example.wiss.Sound.SoundSourceNotInitialisedException;
import com.example.wiss.Sound.SoundUpdater;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    static private Vector screenVec = null;
    static private Activity currentActivity = null;
    SoundUpdater soundUpdater;
    LinkedList<SoundSource> soundSources = new LinkedList<>();
    Player player = new Player();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(android.R.id.content).setOnTouchListener(handleTouch);
        //Setting the touch listener to the handler described below

        screenVec = getScreenVector();
        setCurrentActivity(this);
        SimpleSoundSource simple;

        Log.d("myTag","first creation!");
        // creating sound sources
        simple = new SimpleSoundSource(200,500);
        simple.initialise(player,R.raw.snk,screenVec.getAbsValue());
        soundSources.add(simple);

        simple = new SimpleSoundSource(500,1000);
        simple.initialise(player,R.raw.soo,screenVec.getAbsValue());
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


    //This is the handler of the touch :
    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {


            float x = event.getX();
            float y = event.getY();
            player.setPosition(x,y);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                {
                    Log.i("TAG", "action down: (" + x + ", " + y + ")");
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                    Log.i("TAG", "moving: (" + x + ", " + y + ")");
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("TAG", "action up (" + x + ", " + y + ")");
                    break;
            }

            return true;
        }
    };

}
