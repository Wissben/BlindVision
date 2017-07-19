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

        gameLogic = GameParameters.getGameLogic();
        gameIO = GameParameters.getGameIO();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        Log.d("myTag","resuming updater");
        updater.resumeUpdating();
        Log.d("myTag",gameLogic.getPlayer().getPosition()+ " is this");
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
        //updater.cancel();
    }

}
