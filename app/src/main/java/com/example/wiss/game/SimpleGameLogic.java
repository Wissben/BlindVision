package com.example.wiss.game;

import com.example.wiss.myapplication.Vector;
import com.example.wiss.units.Player;

/**
 * Created by Sidahmed on 15/07/17.
 * This class will be called to update the player position on the map.
 */

public class SimpleGameLogic extends GameLogic {

    /* Constructors =============================================================================== */

    public SimpleGameLogic(Player player){ this.player = player; }

    /* Methods ==================================================================================== */

    /**
     * This will move the player using the vector.
     */
    @Override
    public void movePlayer(Vector vector) {
        Vector finalPosition = vector.add(this.player.getPosition());
        this.player.setPosition(finalPosition);
    }

    /**
     * This will move the player to the specific position in the map.
     */
    @Override
    public void movePlayerToPos(double x, double y) {
        this.player.setPosition(x, y);
    }

    @Override
    protected void update() { }
}
