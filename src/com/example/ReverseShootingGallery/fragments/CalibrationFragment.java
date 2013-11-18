package com.example.ReverseShootingGallery.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.example.ReverseShootingGallery.R;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 11/18/13
 * Time: 3:19 PM
 */
public class CalibrationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calibration, container, false);
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState){
//        ProgressBar pb = (ProgressBar) view.findViewById(R.id.calibration_progressBar);
//        pb.setIndeterminate(false);
//        pb.setMax(100);
//        pb.setProgress(50);
//    }
}
