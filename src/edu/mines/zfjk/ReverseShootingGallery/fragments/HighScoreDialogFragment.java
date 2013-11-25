package edu.mines.zfjk.ReverseShootingGallery.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import edu.mines.zfjk.ReverseShootingGallery.GameManager;
import edu.mines.zfjk.ReverseShootingGallery.R;
import edu.mines.zfjk.ReverseShootingGallery.Score;

import java.util.ArrayList;
import java.util.List;

public class HighScoreDialogFragment extends DialogFragment {

    private HSDListener listener;
    private GameManager manager;
    private View view;
    
    private ArrayList<TextView> nameViews, scoreViews, dateViews;

    public interface HSDListener {
        public void onVisible();

        public void onDismiss();
    }

    public HighScoreDialogFragment(){
        manager = GameManager.getInstance();
    }

    public void setListener(HSDListener listener) {
        this.listener = listener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        view = inflater.inflate(R.layout.high_scores, null);
        builder.setView(view);
        builder.setMessage(R.string.hs_title);
        Dialog d = builder.create();
        
        nameViews = new ArrayList<TextView>();scoreViews = new ArrayList<TextView>();dateViews = new ArrayList<TextView>();
        nameViews.add((TextView)view.findViewById(R.id.row1_name));
        nameViews.add((TextView)view.findViewById(R.id.row2_name));
        nameViews.add((TextView)view.findViewById(R.id.row3_name));
        nameViews.add((TextView)view.findViewById(R.id.row4_name));
        nameViews.add((TextView)view.findViewById(R.id.row5_name));
        scoreViews.add((TextView) view.findViewById(R.id.row1_score));
        scoreViews.add((TextView) view.findViewById(R.id.row2_score));
        scoreViews.add((TextView) view.findViewById(R.id.row3_score));
        scoreViews.add((TextView) view.findViewById(R.id.row4_score));
        scoreViews.add((TextView) view.findViewById(R.id.row5_score));
        dateViews.add((TextView) view.findViewById(R.id.row1_date));
        dateViews.add((TextView) view.findViewById(R.id.row2_date));
        dateViews.add((TextView) view.findViewById(R.id.row3_date));
        dateViews.add((TextView) view.findViewById(R.id.row4_date));
        dateViews.add((TextView) view.findViewById(R.id.row5_date));
        
        populateScores();
        // Create the AlertDialog object and return it
        return d;
    }

    private void populateScores() {
        List<Score> scores = manager.getTop5Scores();

        for (int i = 0; i < Math.min(scores.size(), 5); i++) {
        	Score s = scores.get(i);
        	if (s.valid) {
        		nameViews.get(i).setText(s.name);
        		scoreViews.get(i).setTag(s.score);
        		dateViews.get(i).setText(s.date);
        	} else {
        		nameViews.get(i).setText("");
        		scoreViews.get(i).setTag("");
        		scoreViews.get(i).setText("");
        	}
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        listener.onVisible();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        listener.onDismiss();
    }
}
