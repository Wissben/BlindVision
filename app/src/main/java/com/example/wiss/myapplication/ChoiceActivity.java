package com.example.wiss.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.wiss.updater.Updater;

public class ChoiceActivity extends BlindActivity {

    Choiceable choice;
    Updater updater;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        choice = ChoiceParameters.getChoiceable();
        updater = new Updater(100);
        updater.addToUpdate(ChoiceParameters.getSequenceSoundManager());
        updater.startUpdating();

        this.findViewById(android.R.id.content).setOnTouchListener(new SwipeChoice(choice));
    }

    @Override
    public void onResume()
    {
        super.onResume();
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
        updater.cancel();
    }

}
