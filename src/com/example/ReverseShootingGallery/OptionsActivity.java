package com.example.ReverseShootingGallery;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.example.ReverseShootingGallery.fragments.*;

import java.util.HashMap;
import java.util.Map;

public class OptionsActivity extends MenuDisplayingActivity implements OptionsListFragment.OptionDetailsFragmentDispatcher {

    private String logString = this.getClass().getSimpleName()+".log";

    public static String[] OPTION_NAMES = {
            "Calibration",
            "Difficulty",
            "Appearance",
            "Data"
    };

    private static Map<Integer, Class<? extends Fragment>> detailFragments;
    static {
        detailFragments = new HashMap<Integer, Class<? extends Fragment>>();
        detailFragments.put(0, CalibrationFragment.class);
        detailFragments.put(1, DifficultyFragment.class);
        detailFragments.put(2, AppearanceFragment.class);
        detailFragments.put(3, DataFragment.class);
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.options_listing);
        if (findViewById(R.id.solo_options_fragment_container) != null) {
            OptionsListFragment optionsList = (OptionsListFragment) new OptionsListFragment();
            getFragmentManager().beginTransaction().add(R.id.solo_options_fragment_container, optionsList, "options_list_fragment").commit();
            getFragmentManager().executePendingTransactions();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        menu.removeItem(R.id.action_options);
        return true;
    }

    /**
     * Dispatch a DetailsFragment according to the current layout
     * @param pos index of the Equipment object to display details for
     */
    @Override
    public void displayDetailsFor(int pos) {
        FragmentManager fm = getFragmentManager();
        // Multi pane layout with multiple fragments in layout
        if (findViewById(R.id.solo_options_fragment_container) == null) {
            try {
                Fragment f = detailFragments.get(pos).newInstance();
                fm.beginTransaction().replace(R.id.options_detail_fragment_container, f, "details").commit();
            } catch (InstantiationException e) {
                Log.e(logString, "Failed to instantiate " + detailFragments.get(pos) + " at position " + pos);
                Log.e(logString, e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e(logString, "Illegal access of " + detailFragments.get(pos) + " at position " + pos);
                Log.e(logString, e.getMessage());
            }
        }
        // Single pane layout with multiple activities
        else {
            OptionsDetailFragment details = new OptionsDetailFragment();

            fm.beginTransaction().replace(R.id.solo_options_fragment_container, details).addToBackStack(null).commit();
            fm.executePendingTransactions();
            details.showDetailsFor(pos);
        }
    }
}
