package com.example.wiss.gameGen;

import java.util.LinkedList;

/**
 * Created by ressay on 26/07/17.
 */

public class SequenceGameManager
{
    public static GameGen generateSequence(GameGen... gameGens)
    {
        if(gameGens.length == 0) return null;
        GameGen gameGen = gameGens[0];
        for(int i=1;i<gameGens.length;i++)
        {
            gameGen.setNext(gameGens[i]);
            gameGen = gameGen.getNext();
        }
        return gameGens[0];
    }
}
