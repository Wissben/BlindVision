package com.example.wiss.myapplication;

/**
 * Created by ressay on 24/07/17.
 */

public class ChoiceParameters
{
    static private Choiceable choiceable;

    public static Choiceable getChoiceable() {
        return choiceable;
    }

    public static void setChoiceable(Choiceable choiceable) {
        ChoiceParameters.choiceable = choiceable;
    }
}
