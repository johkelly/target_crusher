package com.example.ReverseShootingGallery.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import com.example.ReverseShootingGallery.R;

/**
 * Created with IntelliJ IDEA.
 * User: jack
 * Date: 11/24/13
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class HighScoreDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.hs_title);
        // Create the AlertDialog object and return it
        return builder.create();
    }
}
