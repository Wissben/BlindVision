package com.example.wiss.game;

import java.util.Map;

/**
 * Created by ressay on 17/07/17.
 */

public class GameOManager
{
    Map<String,GameO> outputs;
    public void transferOutput(String out,String param) throws OutputStringDoesNotExistException
    {
        if(!outputs.containsKey(out)) throw new OutputStringDoesNotExistException();
        outputs.get(out).applyParam(param);
    }

    public void addOutput(String out,GameO method) throws OutputStringAlreadyExistsException
    {
        if(outputs.containsKey(out)) throw new OutputStringAlreadyExistsException();
        outputs.put(out,method);
    }
}
