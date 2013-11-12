package com.example.ReverseShootingGallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * Based very loosely on:
 * http://www.mindfiresolutions.com/Using-Surface-View-for-Android-1659.php
 * http://www.codeproject.com/Articles/228656/Tilt-Ball-Walkthrough
 */
public class GameplayView extends SurfaceView implements SensorEventListener, SurfaceHolder.Callback, View.OnTouchListener {

    private static final String logString = GameplayView.class.getName()+".log";

    private DrawableTarget target;

    private SensorManager sensorManager;
    private Sensor accelSensor;

    private GameThread gameThread;
    private Handler threadHandler;
    private Runnable shotTimer;
    private long lastShotResume;
    private long shotWaitElapsed;

    private final Context mContext;
    private boolean paused = false;

    public GameplayView(Context context) {
        super(context);
        target = new DrawableTarget(300, 300, R.drawable.target_blue, getResources());
        this.mContext = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        threadHandler = new Handler();
        shotTimer = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, "Shot fired!", Toast.LENGTH_SHORT).show();
                lastShotResume = System.currentTimeMillis();
                threadHandler.postDelayed(this, 5000);
                shotWaitElapsed = 0;
            }
        };
        threadHandler.postDelayed(shotTimer, 5000);
    }

    public void registerSensor(){
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
        c.drawColor(Color.WHITE);
        target.clamp(getWidth(), getHeight());
        target.draw(c);
        if(paused){
            c.drawColor(Color.argb(128, 0,0,0));
        }
    }

    public void gameplayPause(){
        paused = true;
        unregisterSensor();
        target.setVelocity(0,0);
        shotWaitElapsed += (System.currentTimeMillis() - lastShotResume);
        threadHandler.removeCallbacks(shotTimer);
    }

    public void gameplayUnpause(){
        paused = false;
        registerSensor();
        lastShotResume = System.currentTimeMillis();
        threadHandler.postDelayed(shotTimer, 5000-shotWaitElapsed);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        if(v == this){
            if(!paused){
                gameplayPause();
            } else {
                gameplayUnpause();
            }
        }
        return false;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        target.setVelocity(event.values[1], event.values[0]);
        Log.d(logString, event.values[1] + " " + event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Do nothing
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        gameThread = new GameThread(holder, mContext, this);
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
