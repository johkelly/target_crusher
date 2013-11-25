package edu.mines.zfjk.ReverseShootingGallery.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import edu.mines.zfjk.ReverseShootingGallery.GameManager;
import edu.mines.zfjk.ReverseShootingGallery.R;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 11/18/13
 * Time: 3:21 PM
 */
public class AppearanceFragment extends Fragment {

	private View view;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	view = inflater.inflate(R.layout.appearance, container, false);
    	addListeners();
        return view;
    }
    
    private void addListeners() {
    	Button pink = (Button) view.findViewById(R.id.pink_color_button);
    	pink.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GameManager.getInstance().setTargetColor(GameManager.PINK);
			}
		});
    	Button blue = (Button) view.findViewById(R.id.blue_color_button);
    	blue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GameManager.getInstance().setTargetColor(GameManager.BLUE);
			}
		});
    	Button rainbow = (Button) view.findViewById(R.id.rainbow_color_button);
    	rainbow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GameManager.getInstance().setTargetColor(GameManager.RAINBOW);
			}
		});
    }
}
