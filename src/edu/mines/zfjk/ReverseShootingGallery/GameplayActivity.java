package edu.mines.zfjk.ReverseShootingGallery;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * @author zachary fleischman and john kelly
 *         <p/>
 *         Functionality:
 *         This intermediate submission implements the following functionalities as specified in the design document:
 *         1. App tracks orientation of device and moves target on screen appropriately
 *         2. User may tap the screen to pause the game (stop tracking motion)
 *         3. User may navigate to empty activities, which serve as place holders for the Options, Help, and About activities.
 */

public class GameplayActivity extends MenuDisplayingActivity {

    GameplayView gameView;

    GameManager gameManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameplayView(this);
        setContentView(gameView);
        gameView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                gameView.updateScale();
                gameView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
        gameManager = GameManager.getInstance();
        SharedPreferences prefs = getSharedPreferences(GameManager.PREFS_KEY, Context.MODE_PRIVATE);
        gameManager.getStashedValues(prefs);
    }

    @Override
    public void onResume() {
        super.onResume();
        gameView.gameplayUnpause();
        gameView.updateColor();
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
