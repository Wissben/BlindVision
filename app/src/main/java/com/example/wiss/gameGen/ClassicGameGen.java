package com.example.wiss.gameGen;

import com.example.wiss.game.ClassicGameLogic;
import com.example.wiss.game.GameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.io.input.GameITouchDirect;
import com.example.wiss.io.output.OutputStringAlreadyExistsException;
import com.example.wiss.io.output.methods.GameOSimpleSound;
import com.example.wiss.myapplication.R;
import com.example.wiss.sound.SequenceSoundManager;
import com.example.wiss.units.Player;

/**
 * Created by ressay on 23/07/17.
 */

public class ClassicGameGen extends GameGen
{
    @Override
    public GameLogic generateGameLogic()
    {
        Player player = new Player();
        ClassicGameLogic gameLogic = new ClassicGameLogic();
        gameLogic.setPlayer(player);
        return gameLogic;
    }

    @Override
    public GameIO generateGameIO(GameLogic gc) {
        GameIO gameIO = new GameIO();
        gameIO.setOnTouchListener(new GameITouchDirect(gc));


        return gameIO;
    }
}
