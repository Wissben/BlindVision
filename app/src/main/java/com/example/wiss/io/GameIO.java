package com.example.wiss.io;

import android.view.View;

import com.example.wiss.io.output.GameO;
import com.example.wiss.io.output.GameOManager;
import com.example.wiss.io.output.OutputStringAlreadyExistsException;
import com.example.wiss.io.output.OutputStringDoesNotExistException;
import com.example.wiss.myapplication.GameActivity;

import java.util.Map;

/**
 * Created by wiss on 15/07/17.
 */

public class GameIO
{
    private GameOManager outputManager;
    private GameActivity gameActivity = null;
    View.OnTouchListener onTouchListener;

    public void transferOutput(String out,String param) throws OutputStringDoesNotExistException
    {
        outputManager.transferOutput(out,param);
    }

    public void transferOutput(String out) throws OutputStringDoesNotExistException
    {
        transferOutput(out,"");
    }

    public void addOutput(String out,GameO method) throws OutputStringAlreadyExistsException
    {
        outputManager.addOutput(out,method);
    }

    public Map<String, GameO> getOutputs() {
        return outputManager.getOutputs();
    }

    public void setOutputs(Map<String, GameO> outputs) {
        outputManager.setOutputs(outputs);
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        outputManager.setGameActivity(gameActivity);
    }

    public GameOManager getOutputManager() {
        return outputManager;
    }

    public void setOutputManager(GameOManager outputManager) {
        this.outputManager = outputManager;
    }
}
