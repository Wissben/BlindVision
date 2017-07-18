package com.example.wiss.myapplication;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Sidahmed on 17/07/17.
 * This class receive in the constructor the class that want to give a choice to the player.
 * Then, this class will listen for the player swiping, then call one of the methods of the
 * 'Choiceable' interface in the class that wanted to make a choice.
 */

public class SwipeChoice implements View.OnTouchListener {
    protected Vector posBeforeSwipe = new Vector(0, 0);
    /* This is generally the class that called 'SwipeChoice'. */
    protected Choiceable choiceClass;


    /* Constructors =============================================================================== */

    /**
     * @param choiceClass this is the class that want to make a choice.
     */
    public SwipeChoice(Choiceable choiceClass) { this.choiceClass = choiceClass; }


    /* Methods ==================================================================================== */

    @Override
    public boolean onTouch(View view, MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.posBeforeSwipe.setX(x);
                this.posBeforeSwipe.setY(y);
                break;
            case MotionEvent.ACTION_UP:
                Vector swipeVect = new Vector(this.posBeforeSwipe, new Vector(x, y));
                swipeVect.setY( -swipeVect.getY());

                double angle = swipeVect.getAngle();
                Log.i("ANGLE", String.valueOf(angle));

                /* Will call the right method depending on the player swipe direction.
                   To avoid errors, there is PI/3 marge in the two corners of the swipe. */
                if ( (angle <= Math.PI/6 && angle >= 0) || (angle >= Math.PI*11/6))
                    this.choiceClass.right();
                else if (angle >= Math.PI/3 && angle <= Math.PI*2/3)
                    this.choiceClass.up();
                else if (angle >= Math.PI*5/6 && angle <= Math.PI*7/6)
                    this.choiceClass.left();
                else if (angle >= Math.PI*4/3 && angle < Math.PI*5/3)
                    this.choiceClass.down();
                break;
        }
        return true;
    }
}
