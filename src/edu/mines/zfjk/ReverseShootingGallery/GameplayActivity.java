/**
 * Description: Primary activity for gameplay
 * @author John Kelly, Zach Fleischman
 */

package edu.mines.zfjk.ReverseShootingGallery;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewTreeObserver;

public class GameplayActivity extends MenuDisplayingActivity {

    private GameplayView gameView;

    private GameManager gameManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameplayView(this);
        setContentView(gameView);
        gameView.updateColor();
        scheduleScaleUpdate();
        gameManager = GameManager.getInstance();
        SharedPreferences prefs = getSharedPreferences(GameManager.PREFS_KEY, Context.MODE_PRIVATE);
        gameManager.getStashedValues(prefs);
    }

    @Override
    public void onResume() {
        super.onResume();
        gameView.gameplayUnpause();
        gameView.updateColor();
        scheduleScaleUpdate();
    }

    /**
     * Deploys an event that listens for the view to be properly inflated.
     * The event, or this function, will both update the scale of the drawable game objects.
     */
    private void scheduleScaleUpdate() {
        if (gameView.getHeight() == 0) {
            gameView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    gameView.updateScale();
                    gameView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        } else {
            gameView.updateScale();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        gameView.gameplayPause();
        SharedPreferences prefs = getSharedPreferences(GameManager.PREFS_KEY, Context.MODE_PRIVATE);
        gameManager.stashValues(prefs);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_scores) {
            gameView.gameplayPause();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDismiss() {
        gameView.gameplayUnpause();
    }
}
