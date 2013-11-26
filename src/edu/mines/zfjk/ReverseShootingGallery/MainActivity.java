
/**
 * Description: Entry point for the app.
 *
 * Documentation Statement:
 *  App1 Inherited Requirements:
 *      Uses more than 3 View widgets
 *      Information is passed from the Options activity to the Gameplay activity via the GameManager
 *      Heavy work done off the UI thread
 *      Robust against crashes/hangs
 *
 *  App2 Inherited Requirements:
 *      Uses graphics drawing API
 *
 *  App3 Requirements:
 *      Settings, Help, About: Accessed via the Action Bar Overflow
 *      Back and Up Navigation: Back will undo fragment transactions in the Options activity, or return to the gameplay
 *          activity. Up navigation in the Options activity will return directly to the gameplay activity.
 *      Action Bar: Persistent High Scores action
 *      Fragment Layouts: Options activity layout responds to screen size and orientation with a dynamic, fragment-powered
 *          master/detail fragment pattern.
 *
 *  Outside Links: (Can also be found near relevant code)
 *      http://www.mindfiresolutions.com/Using-Surface-View-for-Android-1659.php
 *      http://www.codeproject.com/Articles/228656/Tilt-Ball-Walkthrough
 *      http://www.ezzylearning.com/tutorial.aspx?tid=1763429
 *      http://stackoverflow.com/a/14565436
 *      http://stackoverflow.com/questions/4310525/android-on-edittext-changed-listener
 *
 *  Point Split: 50/50
 *
 * @author Zachary Fleischman, John Kelly
 */

package edu.mines.zfjk.ReverseShootingGallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements View.OnClickListener {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        findViewById(android.R.id.content).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, GameplayActivity.class);
        startActivity(intent);
    }
}