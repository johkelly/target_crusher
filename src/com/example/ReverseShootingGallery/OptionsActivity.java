package com.example.ReverseShootingGallery;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import com.example.ReverseShootingGallery.fragments.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class OptionsActivity extends MenuDisplayingActivity implements OptionsListFragment.OptionDetailsFragmentDispatcher {

    private String logString = this.getClass().getSimpleName()+".log";

    private int displayingDetailsFor = -1;

    public static String[] OPTION_NAMES = {
            "Calibration",
            "Difficulty",
            "Appearance",
            "Data"
    };

    private static Map<Integer, Constructor<? extends Fragment>> detailFragments;
    static {
        detailFragments = new HashMap<Integer, Constructor<? extends Fragment>>();
        detailFragments.put(0, (Constructor<? extends Fragment>) CalibrationFragment.class.getConstructors()[0]);
        detailFragments.put(1, (Constructor<? extends Fragment>) DifficultyFragment.class.getConstructors()[0]);
        detailFragments.put(2, (Constructor<? extends Fragment>) AppearanceFragment.class.getConstructors()[0]);
        detailFragments.put(3, (Constructor<? extends Fragment>) DataFragment.class.getConstructors()[0]);
        detailFragments.put(-1, (Constructor<? extends Fragment>) OptionsListFragment.class.getConstructors()[0]);
    }

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int ctorIndex = -1;
        if(savedInstanceState != null){
            ctorIndex = (savedInstanceState.containsKey("show")? savedInstanceState.getInt("show") : -1);
        }
        setContentView(R.layout.options_listing);
        if (findViewById(R.id.solo_options_fragment_container) != null) {
            Constructor<? extends Fragment> ctor = detailFragments.get(ctorIndex);
            try {
                Fragment frag = ctor.newInstance(this);
                getFragmentManager().beginTransaction().add(R.id.solo_options_fragment_container, frag, "options_list_fragment").commit();
                getFragmentManager().executePendingTransactions();
            } catch (InstantiationException e) {
                Log.e(logString, "Failed to instantiate " + detailFragments.get(ctorIndex) + " at position " + ctorIndex);
                Log.e(logString, e.getMessage());
            } catch (IllegalAccessException e) {
                Log.e(logString, "Illegal access of " + detailFragments.get(ctorIndex) + " at position " + ctorIndex);
                Log.e(logString, e.getMessage());
            } catch (InvocationTargetException e) {
                Log.e(logString, "Reflection invocation failed for " + detailFragments.get(ctorIndex) + " at position " + ctorIndex);
                Log.e(logString, e.getMessage());
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle b){
        super.onSaveInstanceState(b);
        b.putInt("show", displayingDetailsFor);
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
        displayingDetailsFor = pos;
        FragmentManager fm = getFragmentManager();
        // Multi pane layout with multiple fragments in layout
        if (findViewById(R.id.solo_options_fragment_container) == null) {
            ((OptionsListFragment)fm.findFragmentById(R.id.options_list_fragment)).getListView().setItemChecked(pos, true);
            Fragment f = makeDetailsFragment(pos);
            fm.beginTransaction().replace(R.id.options_detail_fragment_container, f, "details").commit();
        }
        // Single pane layout with multiple activities
        else {
            OptionsDetailFragment details = new OptionsDetailFragment();

            fm.beginTransaction().replace(R.id.solo_options_fragment_container, details).addToBackStack(null).commit();
            fm.executePendingTransactions();
            details.showDetailsFor(pos);
        }
    }

    private Fragment makeDetailsFragment(int pos){
        try {
            // Attempt to instantiate an object from the stored ctor
            Fragment f = detailFragments.get(pos).newInstance(this);
            return f;
        } catch (InstantiationException e) {
            Log.e(logString, "Failed to instantiate " + detailFragments.get(pos) + " at position " + pos);
            Log.e(logString, e.getMessage());
        } catch (IllegalAccessException e) {
            Log.e(logString, "Illegal access of " + detailFragments.get(pos) + " at position " + pos);
            Log.e(logString, e.getMessage());
        } catch (InvocationTargetException e) {
            Log.e(logString, "Reflection invocation failed for " + detailFragments.get(pos) + " at position " + pos);
            Log.e(logString, e.getMessage());
        }
        return null;
    }
}
