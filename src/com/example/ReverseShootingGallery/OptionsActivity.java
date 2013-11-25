package com.example.ReverseShootingGallery;


import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import com.example.ReverseShootingGallery.fragments.DetailFragmentFactory;
import com.example.ReverseShootingGallery.fragments.OptionsListFragment;

public class OptionsActivity extends MenuDisplayingActivity implements OptionsListFragment.OptionDetailsFragmentDispatcher {

    // private String logString = this.getClass().getSimpleName() + ".log";

    private int displayingDetailsFor = -1;

    public static String[] OPTION_NAMES = {
            "Calibration",
            "Difficulty",
            "Appearance",
            "Data"
    };

    private DetailFragmentFactory fragFactory;

    public OptionsActivity() {
        fragFactory = new DetailFragmentFactory();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load an appropriate layout
        setContentView(R.layout.options_listing);
        // Single fragment layout
        if (findViewById(R.id.solo_options_fragment_container) != null) {
            Fragment frag;
            frag = new OptionsListFragment();
            // Restore a "stack" consisting of the list view...
            getFragmentManager().beginTransaction().add(R.id.solo_options_fragment_container, frag, "options_list_fragment").commit();
            getFragmentManager().executePendingTransactions();
            /// ... and the details view, if needed
            if (savedInstanceState != null) {
                int optionIndex = savedInstanceState.getInt("show");
                if(optionIndex != -1){
                    displayDetailsFor(optionIndex);
                }
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);
        b.putInt("show", displayingDetailsFor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.removeItem(R.id.action_options);
        return true;
    }

    /**
     * Dispatch a DetailsFragment according to the current layout
     *
     * @param pos index of the Equipment object to display details for
     */
    @Override
    public void displayDetailsFor(int pos) {
        displayingDetailsFor = pos;
        FragmentManager fm = getFragmentManager();
        // Multi pane layout with multiple fragments in layout
        if (findViewById(R.id.solo_options_fragment_container) == null) {
            ((OptionsListFragment) fm.findFragmentById(R.id.options_list_fragment)).getListView().setItemChecked(pos, true);
            Fragment f = fragFactory.getDetailFragment(pos);
            fm.beginTransaction().replace(R.id.options_detail_fragment_container, f, "details").commit();
        }
        // Single pane layout with multiple activities
        else {
            Fragment details = fragFactory.getDetailFragment(pos);
            fm.beginTransaction().replace(R.id.solo_options_fragment_container, details).addToBackStack(null).commit();
            fm.executePendingTransactions();
        }
    }
}
