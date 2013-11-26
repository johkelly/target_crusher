/**
 * Description: Activity for browsing and selecting sub-option items
 * @author John Kelly, Zach Fleischman
 */

package edu.mines.zfjk.ReverseShootingGallery;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import edu.mines.zfjk.ReverseShootingGallery.fragments.DetailFragmentFactory;
import edu.mines.zfjk.ReverseShootingGallery.fragments.OptionsListFragment;

public class OptionsActivity extends MenuDisplayingActivity implements OptionsListFragment.OptionDetailsFragmentDispatcher {

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
        //getFragmentManager().popBackStack();
        getFragmentManager().executePendingTransactions();
        // Load an appropriate layout
        setContentView(R.layout.options_listing);
        // Single fragment layout
        if (findViewById(R.id.solo_options_fragment_container) != null) {
            // The list fragment is not yet present
            if (getFragmentManager().findFragmentByTag("options_list_fragment") == null) {
                Fragment frag;
                frag = new OptionsListFragment();
                // Restore a "stack" consisting of the list view.
                getFragmentManager().beginTransaction().add(R.id.solo_options_fragment_container, frag, "options_list_fragment").commit();
                getFragmentManager().executePendingTransactions();
            } // else: do nothing, the list fragment is already present in the layout //
        }
        // Pop the details view back stack entry if it exists
        else {
            getFragmentManager().popBackStack("details_back", FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        GameManager m = GameManager.getInstance();
        m.stashValues(getSharedPreferences(GameManager.PREFS_KEY, Context.MODE_PRIVATE));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.removeItem(R.id.action_options);
        return true;
    }

    @Override
    public void displayDetailsFor(int pos) {
        FragmentManager fm = getFragmentManager();
        // Single pane layout with multiple fragments
        if (findViewById(R.id.solo_options_fragment_container) != null) {
            Fragment details = fragFactory.getDetailFragment(pos);
            fm.beginTransaction().replace(R.id.solo_options_fragment_container, details).addToBackStack("details_back").commit();
        }
        // Multi pane layout with multiple fragments in layout
        else {
            Fragment details = fragFactory.getDetailFragment(pos);
            fm.beginTransaction().replace(R.id.options_detail_fragment_container, details, "details").commit();
        }
        fm.executePendingTransactions();
    }
}
