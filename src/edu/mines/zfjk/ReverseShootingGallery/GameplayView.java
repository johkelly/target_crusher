/**
 * Description: This is where the magic (i.e. gameplay) happens
 * @author John Kelly, Zach Fleischman
 * @see <a href="http://www.mindfiresolutions.com/Using-Surface-View-for-Android-1659.php">Using SurfaceView</a>
 * @see <a href="http://www.codeproject.com/Articles/228656/Tilt-Ball-Walkthrough">App demonstrating Acceleration Sensor</a>
 */

package edu.mines.zfjk.ReverseShootingGallery;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import edu.mines.zfjk.ReverseShootingGallery.fragments.GameEndDialogFragment;

import java.util.Random;

public class GameplayView extends SurfaceView implements SensorEventListener, SurfaceHolder.Callback, View.OnTouchListener, GameEndDialogFragment.NewGameListener, GameManager.GameManagerListener {

    private static final String logString = GameplayView.class.getName() + ".log";
    public static final String prefString = "sensorPrefs";

    private DrawableTarget target;
    private DrawableTarget reticle;

    private SensorManager sensorManager;
    private Sensor accelSensor;

    private GameThread gameThread;
    private Handler threadHandler;
    private Runnable shotTimer;
    private long lastShotResume;
    private long shotWaitElapsed;
    private double neutralX, neutralY;
    private int bgColor = Color.WHITE;

    private GameManager gameManager;

    private final Context mContext;
    private boolean paused = false;

    public GameplayView(Context context) {
        super(context);

        this.gameManager = GameManager.getInstance();
        gameManager.listener = this;

        //will instantiate target as a new DrawableTarget.
        updateColor();

        // initialize position later
        reticle = new DrawableTarget(0, 0, R.drawable.reticle, getResources(), 1.0, 40);
        this.mContext = context;
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        setOnTouchListener(this);

        //sensory stuffs
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        threadHandler = new Handler();
        shotTimer = new Runnable() {
            @Override
            public void run() {

                // Visual indication of shot
                bgColor = Color.BLACK;
                threadHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bgColor = Color.WHITE;
                    }
                }, 100);
                // Collision
                if (targetUnderReticle()) {
                    gameManager.targetHit();
                    target.setExploding(true);
                    threadHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            target.setExploding(false);
                            randTargetPosition();
                        }
                    }, 500);
                } else {
                    gameManager.targetMiss();
                }
                // Game state/update
                if (gameManager.gameOver()) {
                    // New game end fragment dialog
                    GameEndDialogFragment f = new GameEndDialogFragment();
                    f.listener = GameplayView.this;
                    ((Activity) mContext).getFragmentManager().beginTransaction().add(f, "end").addToBackStack("end").commit();
                    randTargetPosition();
                    gameplayPause();
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

    /**
     * Draws onto the provided canvas the game objects and gameplay information text
     *
     * @param c Canvas to draw onto
     */
    public void manualDraw(Canvas c) {
        // Fill canvas with the background color each frame
        c.drawColor(bgColor);
        // Clamp and draw the target
        target.clamp(getWidth(), getHeight());
        target.draw(c);
        // Forcibly the center the reticle and draw it
        reticle.setPosition(this.getWidth() / 2, this.getHeight() / 2);
        reticle.draw(c);
        // Black text for the gameplay information
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35);
        c.drawText("Score: " + gameManager.getScore(), 5, 35, paint);
        c.drawText("Shots: " + gameManager.getShotsLeft(), 5, 70, paint);
        // If paused, gray out (dim) the canvas
        if (paused) {
            c.drawColor(Color.argb(128, 0, 0, 0));
        }
    }

    /**
     * Set the target to be at a random valid position.
     */
    private void randTargetPosition() {
        Random r = new Random();
        target.setPosition(r.nextInt() % this.getWidth(), r.nextInt() % this.getHeight());
        target.clamp(getWidth(), getHeight());
    }

    /**
     * Unregistor sensors to reduce power draw, quiet the target, stash the shot delay, and cancel the shot callback
     */
    public void gameplayPause() {
        paused = true;
        unregisterSensor();
        target.setNoisy(false);
        target.setVelocity(0, 0);
        shotWaitElapsed += (System.currentTimeMillis() - lastShotResume);
        threadHandler.removeCallbacks(shotTimer);
    }

    /**
     * Register sensors for gameplay, get the target moving again, restore shot delay, and resume gameplay state
     */
    public void gameplayUnpause() {
        paused = false;
        target.setNoisy(true);
        registerSensor();
        lastShotResume = System.currentTimeMillis();
        if (gameManager.newGame) {
            shotWaitElapsed = 0;
        }
        setNeutrals();
        threadHandler.postDelayed(shotTimer, gameManager.shotDelay() - shotWaitElapsed);
    }

    private void setNeutrals() {
        SharedPreferences shared = mContext.getSharedPreferences(prefString, Context.MODE_PRIVATE);
        neutralX = shared.getFloat("neutralX", 0);
        neutralY = shared.getFloat("neutralY", 0);
    }

    /**
     * Whether or not the any part of the target is under the center of the reticle (i.e. visually hittable)
     *
     * @return {@code true} if the target is in such a position, {@code false} otherwise
     */
    private boolean targetUnderReticle() {
        double dist = target.distanceToCenterOf(reticle);
        return dist < target.getCollRadius();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // React to touching this view, and let the event propogate
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
        target.setVelocity(event.values[1] - neutralX, event.values[0] - neutralY);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do nothing: Intentionally blank
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // Spin up a GameThread
        gameThread = new GameThread(holder, this);
        gameThread.running = true;
        gameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Do Nothing: Intentionally blank
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
                Log.w(logString, "Failed to join game thread.");
            }

        }
    }

    @Override
    public void newGame() {
        gameplayUnpause();
    }

    @Override
    public void updateColor() {
        switch (gameManager.getTargetColor()) {
            case GameManager.PINK:
                target = new DrawableTarget(300, 300, R.drawable.target_pink, getResources(), 1.0, 1.0 * 110);
                break;
            case GameManager.BLUE:
                target = new DrawableTarget(300, 300, R.drawable.target_blue, getResources(), 1.0, 1.0 * 110);
                break;
            case GameManager.RAINBOW:
                target = new DrawableTarget(300, 300, R.drawable.target_rainbow, getResources(), 1.0, 1.0 * 110);
                break;
        }
        target.setNoisy(true);
    }

    /**
     * Apply some magic numbers to scale the drawable objects to fit nicely on screen.
     * Each drawable object is scaled to fit into 1/3 the dimension of the most constricted screen dimension
     */
    public void updateScale() {
        double sy = (getResources().getDrawable(R.drawable.target_blue).getIntrinsicHeight()) / (getHeight() / 3.0);
        double sx = (getResources().getDrawable(R.drawable.target_blue).getIntrinsicWidth()) / (getWidth() / 3.0);
        double s = Math.max(sy, sx);
        s = 1.0 / s;
        target.setScale(s);
        reticle.setScale(s);
    }
}
