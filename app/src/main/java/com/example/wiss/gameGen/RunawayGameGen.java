package com.example.wiss.gameGen;

import com.example.wiss.game.GameLogic;
import com.example.wiss.game.RunawayGameLogic;
import com.example.wiss.io.GameIO;
import com.example.wiss.io.input.GameITouchDirect;
import com.example.wiss.io.output.OutputStringAlreadyExistsException;
import com.example.wiss.io.output.methods.GameOEnd;
import com.example.wiss.io.output.methods.GameOLookFor;
import com.example.wiss.myapplication.MyMath;
import com.example.wiss.myapplication.R;
import com.example.wiss.myapplication.WelcomeActivity;
import com.example.wiss.sound.SoundName;
import com.example.wiss.units.Player;
import com.example.wiss.units.SimpleSoundSource;

import java.util.LinkedList;

/**
 * Created by wiss on 26/07/17.
 */

public class RunawayGameGen extends GameGen {

    private LinkedList<SimpleSoundSource> enemies;
    private int enemies_number=2;
    private Player player;

    @Override
    public GameLogic generateGameLogic() {
        this.player = new Player();
        this.generateRandomEnemies();
        RunawayGameLogic gameLogic = new RunawayGameLogic(this.enemies,50);
        gameLogic.setPlayer(player);
        return gameLogic;    }

    @Override
    public GameIO generateGameIO(GameLogic gc) {
        GameIO gameIO = new GameIO();
        gameIO.setOnTouchListener(new GameITouchDirect(gc));

        try {

            // output in case of win
            if(getNext() != null)
            {
                // gameOEnd will run the soundSequence and then ask player if he wants to continue to next game
                GameOEnd gameOEnd = new GameOEnd(R.raw.youwin,R.raw.transitionnext);
                gameOEnd.setGameGen(getNext());
                gameIO.addOutput("win",gameOEnd);
            }
            else
                gameIO.addOutput("win",new GameOEnd(R.raw.youwin,R.raw.backtowelcome));

            // output in case of loss
            GameOEnd end = new GameOEnd(R.raw.lose);
            end.setGameGen(new RunawayGameGen());
            gameIO.addOutput("lost",end);

        } catch (OutputStringAlreadyExistsException e) {
            e.printStackTrace();
        }
        return gameIO;
    }

    public void generateRandomEnemies()
    {
        this.enemies = new LinkedList<>();

        for (int i = 0; i < enemies_number; i++) {
            this.enemies.add(new SimpleSoundSource());
            int[] allIDs = SoundName.getAllSoundsID();
            int randomAnimal = MyMath.random(0, allIDs.length - 1);
            this.enemies.get(i).initialise(player, allIDs[randomAnimal], WelcomeActivity.getScreenVec().getAbsValue());
        }
    }
}
