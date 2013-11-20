package com.example.ReverseShootingGallery.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.ReverseShootingGallery.R;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 11/18/13
 * Time: 3:21 PM
 */
public class AppearanceFragment extends Fragment {

    public AppearanceFragment(Context context){

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.appearance, container, false);
    }
}
