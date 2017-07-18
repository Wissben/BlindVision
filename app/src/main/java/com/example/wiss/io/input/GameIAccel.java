package com.example.wiss.io.input;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.wiss.game.GameLogic;
import com.example.wiss.myapplication.Vector;

/**
 * Created by wiss on 16/07/17.
 */

public class GameIAccel implements SensorEventListener {

    private GameLogic gameLogic;
    private double x, y, z;
    private SensorManager sensorManager;
    private Sensor accelerometer;

    public GameIAccel(GameLogic gameLogic, Context context) {
        this.gameLogic = gameLogic;
        x = y = z = 0;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {

            // fail we dont have an accelerometer!

        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        double x = -1*sensorEvent.values[0];
        double y = 0;
        double z = -1*sensorEvent.values[2];

        if(this.gameLogic.getPlayer().getPosition().getX()<0 && this.gameLogic.getPlayer().getPosition().getX()>1000)
            x=0;
        if(this.gameLogic.getPlayer().getPosition().getY()<0 && this.gameLogic.getPlayer().getPosition().getY()>1900)
            z=0;

        Vector acc = new Vector(x, z);
        Log.d("TAG", "onSensorChanged BEFORE: " + x + "/" + y);
        Vector diff = this.gameLogic.getPlayer().getPosition().add(new Vector(x, z));
        /**
         For testing the x z changing value only, not the proper way to set the player position
         THIS REALLY NEED TO BE ADJUSTED !
         */
        this.gameLogic.getPlayer().setVelAccel(new Vector(0, 0), acc);
        this.gameLogic.getPlayer().updatePosition();
        Log.d("TAG", "onSensorChanged: " + this.gameLogic.getPlayer().getPosition().getX() + "/" + this.gameLogic.getPlayer().getPosition().getY());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
