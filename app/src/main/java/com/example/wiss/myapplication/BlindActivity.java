package com.example.wiss.myapplication;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * All our activities will extend this activity in order to keep track of the current activity
 * (all activities will call this activity's onCreate so the method setCurrentActivity will be called as well)
 */
public abstract class BlindActivity extends AppCompatActivity {

    private static Activity currentActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCurrentActivity(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        setCurrentActivity(this);
    }

    /**
     * to call in onCreate of activities
     *
     * @param ac
     */

    static void setCurrentActivity(Activity ac)
    {
        Log.d("myTag","setting current activity!");
        currentActivity = ac;
    }

    static public Activity getCurrentActivity()
    {
        return currentActivity;
    }
}
