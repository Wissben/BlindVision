package com.example.wiss.myapplication;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import com.example.wiss.myapplication.MyMath;
import com.example.wiss.myapplication.SoundHandler;

public class MainActivity extends AppCompatActivity {

    private  SoundHandler sh;
    static private Vector screenVec = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.findViewById(android.R.id.content).setOnTouchListener(handleTouch);
        //Setting the touch listener to the handler described below
        sh = new SoundHandler(R.raw.soo,getApplicationContext());
        screenVec = getScreenVector();

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

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                {
                    Log.i("TAG", "action down: (" + x + ", " + y + ")");
                    //
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    int height = size.y;
                    int xCenter = width/2;
                    int yCenter = height/2;
                    float ang =MyMath.getAngleFromCenter(xCenter,yCenter,x,y,width,height);
                    //Log.d("TAG","ang= "+ang);
                    sh.playSound(ang);

                    //
                    //Log.i("TAG","limit volume : "+sh.limitVolume);
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
