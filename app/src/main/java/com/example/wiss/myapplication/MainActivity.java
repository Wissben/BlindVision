package com.example.wiss.myapplication;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;

import com.example.wiss.game.GameLogic;
import com.example.wiss.game.SimpleGameLogic;
import com.example.wiss.io.input.GameITouchDirect;
import com.example.wiss.io.input.GameITouchSwipe;
import com.example.wiss.sound.SoundSourceNotInitialisedException;
import com.example.wiss.sound.SoundUpdater;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;
import com.example.wiss.units.SoundSource;
import com.example.wiss.updater.Updater;

import java.util.LinkedList;

public class MainActivity extends BlindActivity{

        Updater updater;
        LinkedList<SoundSource> soundSources = new LinkedList<>();
        GameLogic gameLogic;
        //This is the handler of the touch :
        private View.OnTouchListener handleTouch;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Log.d("myTag", "creation");
            Vector screenVec = WelcomeActivity.getScreenVec();
            SimpleSoundSource simple;
            Player player = new Player();

            // creating sound sources
            simple = new SimpleSoundSource(200, 500);
            simple.initialise(player, R.raw.snk, screenVec.getAbsValue());
            soundSources.add(simple);

            simple = new SimpleSoundSource(500, 1000);
            simple.initialise(player, R.raw.meza, screenVec.getAbsValue());
            soundSources.add(simple);


        /* Setting the gameLogic and the handleTouch. It is using swipe touch now. */

            int lower = 0;
            int upper = soundSources.size() - 1;
            int r = (int) (Math.random() * (upper - lower)) + lower;

            Log.d("myTag", "creation2");
            this.gameLogic = new SimpleGameLogic(player, soundSources, soundSources.get(r), 2);

            updater = new Updater(100);
            updater.addToUpdate(gameLogic);
            updater.startUpdating();

            this.handleTouch = new GameITouchDirect(gameLogic);
            this.findViewById(android.R.id.content).setOnTouchListener(this.handleTouch);

        }

        @Override
        public void onResume() {
            super.onResume();
            Log.d("myTag", "resuming updater");
            updater.resumeUpdating();
            Log.d("myTag", gameLogic.getPlayer().getPosition() + " is this");
        }


        @Override
        protected void onPause() {
            super.onPause();
            updater.pauseUpdating();

        }

        @Override
        protected void onStop() {
            super.onStop();
            //updater.cancel();
        }






}
