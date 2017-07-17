package com.example.wiss.game;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.wiss.myapplication.Vector;

/**
 * Created by Sidahmed on 16/07/17.
 * This class will move the player depending on the player swiping. The swipe is the vector between
 * the initial position of the player finger, and the final position of the player finger.
 */

public class GameITouchSwipe implements View.OnTouchListener {
    protected GameLogic gameLogic;
    protected double coeff;
    /* This represent the finger position before it starts swiping. */
    protected Vector posBeforeSwipe = new Vector(0, 0);


    /* Constructors ============================================================================== */

    public GameITouchSwipe(GameLogic gameLogic,double coeff)
    {
        this.gameLogic = gameLogic;
        this.coeff = coeff;
    }


    /* Methods =================================================================================== */

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.posBeforeSwipe.setX(x);
                this.posBeforeSwipe.setY(y);
                Log.i("TAG", "action down: (" + x + ", " + y + ")");
                break;
            case MotionEvent.ACTION_UP:
                Vector swipeVect = new Vector(this.posBeforeSwipe, new Vector(x, y));
                if(swipeVect.getAbsValue()>0)
                    this.gameLogic.movePlayer(swipeVect.mul(coeff));

//                Log.i("TAG", "posBeforeSwipe " + this.posBeforeSwipe);
                Log.i("TAG", "action up (" + x + ", " + y + ")");
//                Log.i("TAG", "Swipe vector " + swipeVect);
//                Log.i("TAG", "Player position " + this.gameLogic.getPlayer().getPosition());
                break;
        }
        return true;
    }
}
