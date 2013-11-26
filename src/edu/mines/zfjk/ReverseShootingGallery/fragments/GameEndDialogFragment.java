/**
 * Description: Fragment with layout and logic for viewing a high score at the end of a game, and saving the score
 * under a given name then starting a new game.
 * @author John Kelly
 */

package edu.mines.zfjk.ReverseShootingGallery.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import edu.mines.zfjk.ReverseShootingGallery.GameManager;
import edu.mines.zfjk.ReverseShootingGallery.R;

public class GameEndDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public interface NewGameListener {
        public void newGame();
    }

    public NewGameListener listener;

    private View view;

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
        GameManager.getInstance().setPlayerName(String.valueOf((((EditText) view.findViewById(R.id.ge_name)).getText())));
        GameManager.getInstance().storeScore();
        GameManager.getInstance().resetGame();
        listener.newGame();
    }
}
