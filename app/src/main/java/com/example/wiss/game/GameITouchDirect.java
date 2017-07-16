package com.example.wiss.game;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by wiss on 15/07/17.
 * This class will handle the player touch on the screen, and it will move the player to that
 * specific position on the map.
 */

public class GameITouchDirect implements View.OnTouchListener
{
    protected GameLogic gameLogic;


    /* Constructors ============================================================================== */

    public GameITouchDirect(GameLogic gameLogic) { this.gameLogic = gameLogic; }


    /* Methods =================================================================================== */

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        this.gameLogic.movePlayerToPos(x, y);

        return true;
    }
}
