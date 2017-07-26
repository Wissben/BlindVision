package com.example.wiss.io.output;

import com.example.wiss.io.output.methods.GameO;
import com.example.wiss.myapplication.GameActivity;
import com.example.wiss.updater.Updatable;
import com.example.wiss.updater.Updater;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Created by ressay on 17/07/17.
 */

public class GameOManager implements Updatable
{
    public Map<String,GameO> outputs = new HashMap<>();
    private GameActivity gameActivity = null;
    Updater updater = null;
    LinkedList<GameO> running = new LinkedList<>();
    LinkedList<String> params = new LinkedList<>();
    private boolean ended = false;

    public void transferOutput(String out,String param) throws OutputStringDoesNotExistException
    {
        if(!outputs.containsKey(out)) throw new OutputStringDoesNotExistException();
        running.add(outputs.get(out));
        params.add(param);
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

    public void setGameActivity(GameActivity gameActivity)
    {
        this.gameActivity = gameActivity;
        setUpdater(gameActivity.getUpdater());
    }

    public Updater getUpdater() {
        return updater;
    }

    public void setUpdater(Updater updater) {
        this.updater = updater;
        updater.addToUpdate(this);
    }

    @Override
    public void update()
    {
        int size = running.size();
        for(int i=0;i<size;i++)
            running.get(i).output(params.get(i),getGameActivity());
        running.clear();
        params.clear();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void stop() {
        ended = true;
    }

    @Override
    public boolean hasEnded() {
        return ended;
    }

    @Override
    public boolean isPaused() {
        return false;
    }

    public int getCountRunning()
    {
        return running.size();
    }

    public boolean isRunning()
    {
        return getCountRunning() > 0;
    }
}
