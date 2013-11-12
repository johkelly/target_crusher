package com.example.ReverseShootingGallery;

import android.app.Activity;
import android.os.Bundle;

public class GameplayActivity extends Activity {

    GameplayView gameView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameplayView(this);
        setContentView(gameView);
    }

    @Override
    public void onResume(){
        super.onResume();
        gameView.gameplayUnpause();
    }

    @Override
    public void onPause(){
        super.onPause();
        gameView.gameplayPause();
    }
}
