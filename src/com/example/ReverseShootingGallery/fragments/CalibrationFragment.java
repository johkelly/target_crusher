package com.example.ReverseShootingGallery.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.ReverseShootingGallery.R;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 11/18/13
 * Time: 3:19 PM
 */
public class CalibrationFragment extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelSensor;

    public CalibrationFragment(Context context) {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    public void registerSensor() {
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterSensor() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calibration, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ImageView i = (ImageView) view.findViewById(R.id.device_image);
        Integer drawId = null;
        switch (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                drawId = R.drawable.device_s;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                drawId = R.drawable.device_n;
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                drawId = R.drawable.device_l;
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                drawId = R.drawable.device_xl;
                break;
            default:
                break;
        }
        if (drawId != null) {
            i.setImageDrawable(getResources().getDrawable(drawId));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        registerSensor();
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterSensor();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
