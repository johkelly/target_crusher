package com.example.ReverseShootingGallery;

import android.app.Activity;
import android.os.Bundle;

public class GameplayActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new GameplayView(this));
    }
}
