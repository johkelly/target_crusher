package com.example.ReverseShootingGallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class GameplayActivity extends MenuDisplayingActivity {

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
