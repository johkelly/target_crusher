package com.example.ReverseShootingGallery.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.ReverseShootingGallery.GameManager;
import com.example.ReverseShootingGallery.R;
import com.example.ReverseShootingGallery.Score;

import java.util.List;

public class HighScoreDialogFragment extends DialogFragment {

    private final HSDListener listener;
    private GameManager manager;
    private View view;

    public interface HSDListener {
        public void onVisible();

        public void onDismiss();
    }

    public HighScoreDialogFragment(HSDListener listener) {
        this.listener = listener;
        manager = GameManager.getInstance();
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
        populateScores();
        // Create the AlertDialog object and return it
        return d;
    }

    private void populateScores() {
        List<Score> scores = manager.getTop5Scores();

        ((TextView) view.findViewById(R.id.row1_name)).setText(scores.get(0).name);
        ((TextView) view.findViewById(R.id.row1_score)).setText(scores.get(0).score);
        ((TextView) view.findViewById(R.id.row1_date)).setText(scores.get(0).date);

        ((TextView) view.findViewById(R.id.row2_name)).setText(scores.get(1).name);
        ((TextView) view.findViewById(R.id.row2_score)).setText(scores.get(1).score);
        ((TextView) view.findViewById(R.id.row2_date)).setText(scores.get(1).date);

        ((TextView) view.findViewById(R.id.row3_name)).setText(scores.get(2).name);
        ((TextView) view.findViewById(R.id.row3_score)).setText(scores.get(2).score);
        ((TextView) view.findViewById(R.id.row3_date)).setText(scores.get(2).date);

        ((TextView) view.findViewById(R.id.row4_name)).setText(scores.get(3).name);
        ((TextView) view.findViewById(R.id.row4_score)).setText(scores.get(3).score);
        ((TextView) view.findViewById(R.id.row4_date)).setText(scores.get(3).date);

        ((TextView) view.findViewById(R.id.row5_name)).setText(scores.get(4).name);
        ((TextView) view.findViewById(R.id.row5_score)).setText(scores.get(4).score);
        ((TextView) view.findViewById(R.id.row5_date)).setText(scores.get(4).date);
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
