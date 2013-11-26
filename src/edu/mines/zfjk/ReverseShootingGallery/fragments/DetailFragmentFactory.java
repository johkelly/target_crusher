/**
 * Description: Factory for generating detail fragments for the options screen based on a list position
 * @author John Kelly
 */

package edu.mines.zfjk.ReverseShootingGallery.fragments;

import android.app.Fragment;
import android.util.Log;

public class DetailFragmentFactory {

    private static final String logString = DetailFragmentFactory.class.getName() + ".log";

    /**
     * Produce a Fragment associated with a given position index
     *
     * @param pos Index of the listing in the Options menu
     * @return Fragment of the proper type for the given {@code pos}
     */
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
                Log.w(logString, "Requested to create unknown DetailFragment, id: " + pos);
                f = new Fragment();
                break;
        }
        return f;
    }
}
