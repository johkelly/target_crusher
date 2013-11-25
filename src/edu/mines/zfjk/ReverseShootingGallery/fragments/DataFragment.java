package edu.mines.zfjk.ReverseShootingGallery.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.mines.zfjk.ReverseShootingGallery.GameManager;
import edu.mines.zfjk.ReverseShootingGallery.R;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 11/18/13
 * Time: 3:21 PM
 */

//Used edit text pattern from http://stackoverflow.com/questions/4310525/android-on-edittext-changed-listener

public class DataFragment extends Fragment {

	private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	view = inflater.inflate(R.layout.data, container, false);
    	setup();
        return view;
    }
    
    private void setup() {
    	Button reset = (Button) view.findViewById(R.id.reset_scores_button);
		reset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				GameManager.getInstance().resetScores();
			}
		});
		
		EditText nameEdit = (EditText) view.findViewById(R.id.player_name_edit_text);
		nameEdit.setText(GameManager.getInstance().getPlayerName());
		nameEdit.addTextChangedListener(new TextWatcher(){
	        public void afterTextChanged(Editable newName) {
	        	GameManager.getInstance().setPlayerName(String.valueOf(newName));
	        }
	        public void beforeTextChanged(CharSequence s, int start, int count, int after){}
	        public void onTextChanged(CharSequence s, int start, int before, int count){}
	    }); 
		
    }
    
}
