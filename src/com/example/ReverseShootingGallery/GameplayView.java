package com.example.ReverseShootingGallery;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Based very loosely on:
 * http://www.mindfiresolutions.com/Using-Surface-View-for-Android-1659.php
 * http://www.codeproject.com/Articles/228656/Tilt-Ball-Walkthrough
 */
public class GameplayView extends SurfaceView implements SensorEventListener, SurfaceHolder.Callback, View.OnTouchListener {

    private static final String logString = GameplayView.class.getName() + ".log";

    private DrawableTarget target;
    private DrawableTarget reticle;

    private SensorManager sensorManager;
    private Sensor accelSensor;

    private GameThread gameThread;
    private Handler threadHandler;
    private Runnable shotTimer;
    private long lastShotResume;
    private long shotWaitElapsed;

    private GameManager gameManager;

    private final Context mContext;
    private boolean paused = false;

    public GameplayView(Context context) {
        super(context);
        target = new DrawableTarget(300, 300, R.drawable.target_blue, getResources(), 1.0, 110);
        // initialize position later
        reticle = new DrawableTarget(0, 0, R.drawable.reticle, getResources(), 1.0, 40);
        this.mContext = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);

        this.gameManager = GameManager.getInstance();

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        threadHandler = new Handler();
        shotTimer = new Runnable() {
            @Override
            public void run() {
                // Collision
                if (targetUnderReticle()) {
                    gameManager.targetHit();
                    randTargetPosition();
                    //flash!
                    //bang!
                    Toast.makeText(mContext, "Target Hit!", Toast.LENGTH_SHORT).show();
                } else {
                    gameManager.targetMiss();
                    //sad noise
                    Toast.makeText(mContext, "Target Miss!", Toast.LENGTH_SHORT).show();
                }
                // Game state/update
                if (gameManager.gameOver()) {
                    gameManager.storeScore();
                    gameManager.resetGame(); // TODO: Move to NewGameButton callback
                    randTargetPosition();
                    gameplayPause();
                    //show high score menu
                    //show "play again" button
                } else {
                    invalidate();
                    lastShotResume = System.currentTimeMillis();
                    threadHandler.postDelayed(this, gameManager.shotDelay());
                    shotWaitElapsed = 0;
                }
            }
        };
    }

    public void registerSensor() {
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterSensor() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
    }

    public void manualUpdate() {
        target.update();
    }

    public void manualDraw(Canvas c) {
        c.drawColor(Color.WHITE);
        target.clamp(getWidth(), getHeight());
        target.draw(c);
        reticle.setPosition(this.getWidth() / 2, this.getHeight() / 2);
        reticle.draw(c);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35);
        c.drawText("Score: " + gameManager.getScore(), 5, 35, paint);
        c.drawText("Shots: " + gameManager.getShotsLeft(), 5, 70, paint);

        if (paused) {
            c.drawColor(Color.argb(128, 0, 0, 0));
        }
    }

    private void randTargetPosition() {
        Random r = new Random();
        target.setPosition(r.nextInt() % this.getWidth(), r.nextInt() % this.getHeight());
        //target.setVelocity(0, 0); probably not, but maybe?
        target.clamp(getWidth(), getHeight());
    }

    public void gameplayPause() {
        paused = true;
        unregisterSensor();
        target.setVelocity(0, 0);
        shotWaitElapsed += (System.currentTimeMillis() - lastShotResume);
        threadHandler.removeCallbacks(shotTimer);
    }

    public void gameplayUnpause() {
        paused = false;
        registerSensor();
        lastShotResume = System.currentTimeMillis();
        if(gameManager.newGame){
            shotWaitElapsed = 0;
        }
        threadHandler.postDelayed(shotTimer, gameManager.shotDelay() - shotWaitElapsed);
    }

    private boolean targetUnderReticle() {
        double dist = target.distanceToCenterOf(reticle);
        return dist < target.getCollRadius();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v == this) {
            if (!paused) {
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
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                Log.v(logString, "Failed to join game thread.");
            }

        }
    }
}
