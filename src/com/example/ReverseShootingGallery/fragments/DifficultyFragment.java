package com.example.ReverseShootingGallery.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.ReverseShootingGallery.GameManager;
import com.example.ReverseShootingGallery.R;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 11/18/13
 * Time: 3:21 PM
 */
public class DifficultyFragment extends Fragment {
	private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	view = inflater.inflate(R.layout.difficulty, container, false);
    	addButtonListeners();
        return view;
    }
    public void addButtonListeners() {
		Button easy = (Button) view.findViewById(R.id.easy_button);
		easy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GameManager.getInstance().setDifficulty(GameManager.EASY);
			}
		});
		Button medium = (Button) view.findViewById(R.id.medium_button);
		medium.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GameManager.getInstance().setDifficulty(GameManager.MEDIUM);
			}
		});
		Button hard = (Button) view.findViewById(R.id.hard_button);
		hard.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GameManager.getInstance().setDifficulty(GameManager.HARD);
			}
		});
	}
}
