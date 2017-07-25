package com.example.wiss.myapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.wiss.game.GameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.updater.Updater;

public class GameActivity extends BlindActivity {

    Updater updater;
    GameLogic gameLogic;
    GameIO gameIO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        // getting gameLogic and gameIO
        gameLogic = GameParameters.getGameLogic();
        gameIO = GameParameters.getGameIO();

        // starting the updater
        updater = new Updater(100);
        updater.addToUpdate(gameLogic);


        // setting up input/output manager to run on this activity
        gameIO.setGameActivity(this);

        gameLogic.setGameIO(gameIO);
        gameLogic.initialize();
        updater.startUpdating();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(updater.isCancelled()) {
            // to start the thread again we need new instance so we get a copy the current instance
            updater = updater.copy();
            updater.startUpdating();
        }
        updater.resumeUpdating();
    }


    @Override
    protected void onPause() {
        super.onPause();
        updater.pauseUpdating();

    }

    @Override
    protected void onStop()
    {
        super.onStop();
        updater.pauseAndStopUpdating();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updater.cancel();
    }

    public void setOnTouchListener(View.OnTouchListener touchListener)
    {
        this.findViewById(android.R.id.content).setOnTouchListener(touchListener);
    }


    public Updater getUpdater() {
        return updater;
    }

    public GameLogic getGameLogic() {
        return gameLogic;
    }

    public GameIO getGameIO() {
        return gameIO;
    }

    @Override
    public void onBackPressed()
    {

        PauseChoice.pauseGame(this);
    }
}
