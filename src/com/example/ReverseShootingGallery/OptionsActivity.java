package com.example.ReverseShootingGallery;


import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class OptionsActivity extends MenuDisplayingActivity implements OptionsListFragment.OptionDetailsFragmentDispatcher {

    public static String[] OPTION_NAMES = {
            "Calibration",
            "Difficulty",
            "Appearance",
            "Data"
    };

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
        	OptionsDetailFragment details = (OptionsDetailFragment) fm.findFragmentByTag("details");
            if (details == null) {
                details = new OptionsDetailFragment();

                fm.beginTransaction().add(R.id.options_detail_fragment_container, details, "details").addToBackStack(null).commit();
                fm.executePendingTransactions();
            }
            details.showDetailsFor(pos);
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
