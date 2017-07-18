package com.example.wiss.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class WelcomeActivity extends AppCompatActivity implements Choiceable {
    public View.OnTouchListener handleSwipe;
    /* We put the static objects here (like gameLogic and stuff). */


    /* Methods =================================================================================== */

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /* We tell the player the available choices before using the next instructions. */

        this.handleSwipe = new SwipeChoice(this);
        this.findViewById(android.R.id.content).setOnTouchListener(this.handleSwipe);
    }


    @Override
    public void up()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        Log.i("CHOICE", "The player choosed up");
    }

    @Override
    public void down() {
        Log.i("CHOICE", "The player choosed down");
    }

    @Override
    public void left() {
        Log.i("CHOICE", "The player choosed left");
    }

    @Override
    public void right() {
        Log.i("CHOICE", "The player choosed right");
    }
}
