package com.example.wiss.io;

import android.util.Log;
import android.view.View;

import com.example.wiss.io.output.methods.GameO;
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
    private GameOManager outputManager = new GameOManager();
    private GameActivity gameActivity = null;
    View.OnTouchListener onTouchListener;
    private int running = 0;

    /**
     * method called to transfer a message in output
     * @param out the message we want to transfer
     * @param param parameters of this message
     * @throws OutputStringDoesNotExistException if the message does not exist this exception is thrown
     */
    public void transferOutput(String out,String param) throws OutputStringDoesNotExistException
    {
        running++;
        outputManager.transferOutput(out,param);
        running--;
    }

    /**
     * transfer message without parameters
     * @param out the message to transfer
     * @throws OutputStringDoesNotExistException
     */

    public void transferOutput(String out) throws OutputStringDoesNotExistException
    {
        transferOutput(out,"");
    }

    /**
     * adding a message to be handled
     * @param out the message's String
     * @param method the method to be called when this message is sent
     * @throws OutputStringAlreadyExistsException
     */

    public void addOutput(String out,GameO method) throws OutputStringAlreadyExistsException
    {
        outputManager.addOutput(out,method);
    }

    /**
     * method to be called in onCreate of GameActivity,
     * this will allow for GameIO to manage inputs and outputs between gameLogic and GameActivity that is passed here
     * @param gameActivity
     */

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
        Log.d("myTag","setting output manager");
        outputManager.setGameActivity(gameActivity);
        Log.d("myTag","setting on touch listener");
        gameActivity.setOnTouchListener(onTouchListener);
        Log.d("myTag","end setting on touch listener");
    }

    public void blockThreadWhileOutputRunning()
    {
        while (isRunning())
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }


    //******************* getters & setters *********************

    public Map<String, GameO> getOutputs() {
        return outputManager.getOutputs();
    }

    public void setOutputs(Map<String, GameO> outputs) {
        outputManager.setOutputs(outputs);
    }

    public GameActivity getGameActivity() {
        return gameActivity;
    }

    public GameOManager getOutputManager() {
        return outputManager;
    }

    public void setOutputManager(GameOManager outputManager) {
        this.outputManager = outputManager;
    }


    public View.OnTouchListener getOnTouchListener() {
        return onTouchListener;
    }

    public void setOnTouchListener(View.OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    /**
     * if there is an output running
     * @return
     */
    public boolean isRunning()
    {
        return running>0;
    }

    /**
     * the number of outputs currently running
     * @return
     */

    public int getRunningCount()
    {
        return running;
    }
    //******************* end of getters & setters *********************
}
