/**
 * Description: Thread object that loops repeatedly, creating an Update()/Draw() loop
 * @author John Kelly, Zach Fleischman
 */

package edu.mines.zfjk.ReverseShootingGallery;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    public boolean running;
    private SurfaceHolder surfaceHolder;
    private GameplayView gameplayView;

    public GameThread(SurfaceHolder sHolder, GameplayView gameplayView) {
        surfaceHolder = sHolder;
        this.gameplayView = gameplayView;
        running = false;
    }

    @Override
    public void run() {
        super.run();
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                gameplayView.manualUpdate();
                gameplayView.manualDraw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
