package com.example.ReverseShootingGallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created with IntelliJ IDEA.
 * Based on http://www.mindfiresolutions.com/Using-Surface-View-for-Android-1659.php
 */
public class GameplayView extends SurfaceView implements SensorEventListener, SurfaceHolder.Callback {

    private static final String logString = GameplayView.class.getName()+".log";

    private DrawableTarget target;

    private SensorManager sensorManager;
    private Sensor accelSensor;

    private GameThread gameThread;

    private Context context;

    public GameplayView(Context context) {
        super(context);
        target = new DrawableTarget(300, 300, R.drawable.target_blue, getResources());
        this.context = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
    }

    public void registerSensor(){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterSensor(){
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDraw(Canvas c){
        super.onDraw(c);
    }

    public void manualUpdate(){
        target.update();
    }

    public void manualDraw(Canvas c){
        c.drawColor(Color.BLACK);
        target.clamp(getWidth(), getHeight());
        target.draw(c);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        target.setVelocity(event.values[1], event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Do nothing
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(holder, context, this);
        gameThread.running = true;
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Do Nothing
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        gameThread.running = false;
        boolean retry = true;
        while(retry){
            try{
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.v(logString, "Failed to join game thread.");
            }

        }
    }
}
