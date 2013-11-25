package com.example.ReverseShootingGallery.fragments;

import android.app.Fragment;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 11/22/13
 * Time: 3:48 PM
 */
public class DetailFragmentFactory {

    private static final String logString = DetailFragmentFactory.class.getName() + ".log";

    public DetailFragmentFactory() {

    }

    public Fragment getDetailFragment(int pos) {
        Fragment f;
        switch (pos) {
            case 0:
                f = new CalibrationFragment();
                break;
            case 1:
                f = new DifficultyFragment();
                break;
            case 2:
                f = new AppearanceFragment();
                break;
            case 3:
                f = new DataFragment();
                break;
            default:
                Log.d(logString, "Requested to create unknown DetailFragment, id: " + pos);
                f = new Fragment();
                break;
        }
        return f;
    }
}
