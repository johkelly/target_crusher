package com.example.ReverseShootingGallery;


import edu.mines.zfjk.EquipmentCheckout.DetailFragment;
import edu.mines.zfjk.EquipmentCheckout.FragmentManager;
import edu.mines.zfjk.EquipmentCheckout.Override;
import edu.mines.zfjk.EquipmentCheckout.R;
import android.app.Activity;
import android.os.Bundle;

public class OptionsActivity extends Activity implements OptionDetailsFragmentDispatcher {
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
    /**
     * Dispatch a DetailsFragment according to the current layout
     * @param pos index of the Equipment object to display details for
     */
    @Override
    public void displayDetailsFor(int pos) {
        if (emc.getAllObjects().size() <= pos) return;
        FragmentManager fm = getFragmentManager();
        // Multi pane layout with multiple fragments in layout
        if (findViewById(R.id.solo_options_fragment_container) == null) {
        	OptionsDetailFragment details = (OptionsDetailFragment) fm.findFragmentByTag("details");
            if (details == null) {
                details = new OptionsDetailFragment();

                fm.beginTransaction().add(R.id.OptionsDetailFragment, details, "details").addToBackStack(null).commit();
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
