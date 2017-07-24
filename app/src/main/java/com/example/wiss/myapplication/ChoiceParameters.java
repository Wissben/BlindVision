package com.example.wiss.myapplication;

import com.example.wiss.sound.SequenceSoundManager;

/**
 * Created by ressay on 24/07/17.
 */

public class ChoiceParameters
{
    static private Choiceable choiceable;
    static private SequenceSoundManager sequenceSoundManager;

    public static SequenceSoundManager getSequenceSoundManager() {
        return sequenceSoundManager;
    }

    public static void setSequenceSoundManager(SequenceSoundManager sequenceSoundManager) {
        ChoiceParameters.sequenceSoundManager = sequenceSoundManager;
    }

    public static Choiceable getChoiceable() {
        return choiceable;
    }

    public static void setChoiceable(Choiceable choiceable) {
        ChoiceParameters.choiceable = choiceable;
    }
}
