package com.example.wiss.io.input;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.wiss.game.GameLogic;
import com.example.wiss.myapplication.Vector;

/**
 * Created by wiss on 16/07/17.
 */

public class GameIAccel  implements SensorEventListener{

    private GameLogic gameLogic;
    private double x,y,z;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    public GameIAccel(GameLogic gameLogic)
    {
        this.gameLogic = gameLogic;
        x=y=z=0;


        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        } else {

            // fai! we dont have an accelerometer!

        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        double x = sensorEvent.values[0];
        double y = 0;
        double z = sensorEvent.values[2];
        this.gameLogic.movePlayer(new Vector(x,z));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
