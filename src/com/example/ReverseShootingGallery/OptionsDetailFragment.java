package com.example.ReverseShootingGallery;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OptionsDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.options_detail, container, false);
    }

	public void showDetailsFor(int pos) {
		TextView tempView =  (TextView) getView().findViewById(R.id.OptionsTempTextView);
		tempView.setText("details for position " + pos);
		// TODO: load in a new fragment for this option
	}
}
