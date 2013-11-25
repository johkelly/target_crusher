package com.example.ReverseShootingGallery.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import com.example.ReverseShootingGallery.GameManager;
import com.example.ReverseShootingGallery.R;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 11/24/13
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class GameEndDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    String pName;

    View view;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        view = inflater.inflate(R.layout.game_end, null);
        builder.setView(view)
                .setPositiveButton(R.string.ge_newgame, this);
        builder.setMessage(R.string.ge_title);
        Dialog d = builder.create();
        EditText name = (EditText) view.findViewById(R.id.ge_name);
        TextView score = (TextView) view.findViewById(R.id.ge_score);
        name.setText(GameManager.getInstance().getPlayerName());
        score.setText(String.valueOf(GameManager.getInstance().getScore()));
        return d;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        // Get the text out of the editable text
        GameManager.getInstance().setPlayerName(String.valueOf((((EditText)view.findViewById(R.id.ge_name)).getText())));
        GameManager.getInstance().storeScore();
        GameManager.getInstance().resetGame();
    }
}
