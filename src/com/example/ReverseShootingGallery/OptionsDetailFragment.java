package com.example.ReverseShootingGallery;

import android.app.Fragment;
import android.widget.TextView;

public class OptionsDetailFragment extends Fragment {
	public void showDetailsFor(int pos) {
		TextView tempView =  (TextView) getView().findViewById(R.id.OptionsTempTextView);
		tempView.setText("details for position " + pos);
		// TODO: load in a new fragment for this option
	}
}
