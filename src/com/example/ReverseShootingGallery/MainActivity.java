package com.example.ReverseShootingGallery;


import android.os.Bundle;
/**
 * 
 * @author zachary fleischman and john kelly
 *
 * Functionality:
 * 	This intermediate submission implements the following functionalities as specified in the design document:
 * 		1. App tracks orientation of device and moves target on screen appropriately
 * 		2. User may tap the screen to pause the game (stop tracking motion)
 * 		3. User may navigate to empty activities, which serve as place holders for the Options, Help, and About activities.
 */

public class MainActivity extends MenuDisplayingActivity {

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
