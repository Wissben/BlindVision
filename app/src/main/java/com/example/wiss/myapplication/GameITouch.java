package com.example.wiss.myapplication;

import android.media.Image;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.wiss.units.Player;

import java.util.EventListener;

/**
 * Created by wiss on 15/07/17.
 */

public class GameITouch implements View.OnTouchListener
{
    protected GameLogic gameLogic;
    public GameITouch(GameLogic gameLogic)
    {
        this.gameLogic = gameLogic;
    }

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        this.gameLogic.movePlayerToPos(x, y);

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
}
