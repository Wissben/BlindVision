package com.example.wiss.io.output;

import com.example.wiss.myapplication.GameActivity;

import java.util.Map;

/**
 * Created by ressay on 17/07/17.
 */

public class GameOManager
{
    public Map<String,GameO> outputs;
    private GameActivity gameActivity = null;

    public void transferOutput(String out,String param) throws OutputStringDoesNotExistException
    {
        if(!outputs.containsKey(out)) throw new OutputStringDoesNotExistException();
        outputs.get(out).output(param,getGameActivity());
    }

    public void addOutput(String out,GameO method) throws OutputStringAlreadyExistsException
    {
        if(outputs.containsKey(out)) throw new OutputStringAlreadyExistsException();
        outputs.put(out,method);
    }

    public Map<String, GameO> getOutputs() {
        return outputs;
    }

    public void setOutputs(Map<String, GameO> outputs) {
        this.outputs = outputs;
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }
}
