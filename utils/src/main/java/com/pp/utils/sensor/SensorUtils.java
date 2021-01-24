package com.pp.utils.sensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class SensorUtils {
    /*
     *register listener for sensor
     */
    public static boolean registerSensorListener(Activity activity, int sensorType, SensorEventListener listener, int delay) {
        SensorManager sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor sensor = sensorManager.getDefaultSensor(sensorType);
            sensorManager.registerListener(listener, sensor, delay);
            return true;
        }
        return false;
    }

    /*
     *unregister listener for sensor
     */
    public static void unregisterSensorListener(Activity activity, int sensorType, SensorEventListener listener) {
        SensorManager sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager != null) {
            Sensor sensor = sensorManager.getDefaultSensor(sensorType);
            sensorManager.unregisterListener(listener, sensor);
        }
    }
}
