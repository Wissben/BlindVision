package com.example.wiss.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ChoiceActivity extends AppCompatActivity {

    Choiceable choice;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        choice = ChoiceParameters.getChoiceable();

        this.findViewById(android.R.id.content).setOnTouchListener(new SwipeChoice(choice));
    }
}
