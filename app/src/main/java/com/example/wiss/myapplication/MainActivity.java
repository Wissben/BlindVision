package com.example.wiss.myapplication;

import android.app.Activity;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private  SoundMapManager smm;
    static private Vector screenVec = null;
    static private Activity currentActivity = null;
    SoundHandler snk = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(android.R.id.content).setOnTouchListener(handleTouch);
        //Setting the touch listener to the handler described below

        screenVec = getScreenVector();
        smm = new SoundMapManager(getScreenVec().getAbsValue());
        setCurrentActivity(this);
        snk = new SoundHandler(R.raw.snk);

    }

    /**
     * to call in onCreate of activities
     * @param ac
     */

    static void setCurrentActivity(Activity ac)
    {
        currentActivity = ac;
    }

    static Activity getCurrentActivity()
    {
        return currentActivity;
    }

    static Vector getScreenVec()
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


            int x = (int) event.getX();
            int y = (int) event.getY();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;
            int xCenter = width/2;
            int yCenter = height/2;
            float ang =MyMath.getAngleFromCenter(xCenter,yCenter,x,y,width,height);

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                {
                    Log.i("TAG", "action down: (" + x + ", " + y + ")");
                    //

                    //Log.d("TAG","ang= "+ang);
                    snk = smm.produceSoundBetweenPoints(new Vector(xCenter,yCenter),new Vector(x,y),snk);

                    //
                    //Log.i("TAG","limit volume : "+sh.limitVolume);
                    break;
                }
                case MotionEvent.ACTION_MOVE:
                    Log.i("TAG", "moving: (" + x + ", " + y + ")");
                    snk = smm.produceSoundBetweenPoints(new Vector(xCenter,yCenter),new Vector(x,y),snk);
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("TAG", "action up (" + x + ", " + y + ")");
                    snk.pauseSound();
                    break;
            }

            return true;
        }
    };

}
