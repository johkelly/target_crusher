package com.example.ReverseShootingGallery;


import android.app.Activity;
import android.os.Bundle;

public class OptionsActivity extends Activity {
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
}
