package com.example.ReverseShootingGallery;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created with IntelliJ IDEA.
 * Based on http://www.mindfiresolutions.com/Using-Surface-View-for-Android-1659.php
 */
public class GameThread extends Thread {
    public boolean running;
    Canvas canvas;
    SurfaceHolder surfaceHolder;
    Context context;
    GameplayView gameplayView;

    public GameThread(SurfaceHolder sHolder, Context ctxt, GameplayView gameplayView){
        surfaceHolder = sHolder;
        context = ctxt;
        this.gameplayView = gameplayView;
        running = false;
    }

    @Override
    public void run(){
        super.run();
        while(running){
            canvas = surfaceHolder.lockCanvas();
            if(canvas != null){
                gameplayView.manualUpdate();
                gameplayView.manualDraw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
