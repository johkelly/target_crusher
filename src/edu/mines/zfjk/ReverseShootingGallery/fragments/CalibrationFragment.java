package edu.mines.zfjk.ReverseShootingGallery.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import edu.mines.zfjk.ReverseShootingGallery.GameplayView;
import edu.mines.zfjk.ReverseShootingGallery.R;
import edu.mines.zfjk.ReverseShootingGallery.filters.AccelLowPassFilter;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: John Kelly
 * Date: 11/18/13
 * Time: 3:19 PM
 */
public class CalibrationFragment extends Fragment implements SensorEventListener, View.OnClickListener {

    private SensorManager sensorManager;
    private Sensor accelSensor;

    private AccelLowPassFilter filter;
    private DecimalFormat formatter;
    private static final Object neutralLock = new Object();
    private float neutralX, neutralY;

    private Handler threadHandler;

    public CalibrationFragment() {
        filter = new AccelLowPassFilter();
        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.getDefault());
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumIntegerDigits(1);
        threadHandler = new Handler();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.calibration, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Button b = (Button) view.findViewById(R.id.calibrate_btn);
        b.setOnClickListener(this);
        ImageView i = (ImageView) view.findViewById(R.id.device_image);
        Integer drawId = null;
        int orientation = getActivity().getResources().getConfiguration().orientation;
        switch (getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) {
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    drawId = R.drawable.device_s_p;
                } else {
                    drawId = R.drawable.device_s;
                }
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    drawId = R.drawable.device_n_p;
                } else {
                    drawId = R.drawable.device_n;
                }
                break;
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    drawId = R.drawable.device_l_p;
                } else {
                    drawId = R.drawable.device_l;
                }
                break;
            case Configuration.SCREENLAYOUT_SIZE_XLARGE:
                if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                    drawId = R.drawable.device_xl_p;
                } else {
                    drawId = R.drawable.device_xl;
                }
                break;
            default:
                break;
        }
        if (drawId != null) {
            i.setImageDrawable(getResources().getDrawable(drawId));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        sensorManager = (SensorManager) activity.getSystemService(Context.SENSOR_SERVICE);
        accelSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SharedPreferences prefs = activity.getSharedPreferences(GameplayView.prefString, Context.MODE_PRIVATE);
        neutralX = prefs.getFloat("neutralX", 0);
        neutralY = prefs.getFloat("neutralY", 0);
    }

    public void registerSensor() {
        sensorManager.registerListener(this, accelSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterSensor() {
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        registerSensor();
        lockOrientation(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterSensor();
        SharedPreferences prefs = getActivity().getSharedPreferences(GameplayView.prefString, Context.MODE_PRIVATE);
        prefs.edit().putFloat("neutralX", neutralX).putFloat("neutralY", neutralY).commit();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        TextView tx = (TextView) getView().findViewById(R.id.tilt_x);
        TextView ty = (TextView) getView().findViewById(R.id.tilt_y);
        filter.put(event.values[1], event.values[0]);
        synchronized (neutralLock) {
            float x = filter.getX() - neutralX;
            float y = filter.getY() - neutralY;
            int orientation = getActivity().getResources().getConfiguration().orientation;
            if (orientation != Configuration.ORIENTATION_PORTRAIT) {
                tx.setText(formatter.format(x));
                ty.setText(formatter.format(y));
            } else {
                tx.setText(formatter.format(y));
                ty.setText(formatter.format(x));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //Do Nothing
    }

    // http://stackoverflow.com/a/14565436
    public static void lockOrientation(Activity activity) {
        Display display = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int rotation = display.getRotation();
        int tempOrientation = activity.getResources().getConfiguration().orientation;
        int orientation = 0;
        switch (tempOrientation) {
            case Configuration.ORIENTATION_LANDSCAPE:
                if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90)
                    orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
                else
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
                break;
            case Configuration.ORIENTATION_PORTRAIT:
                if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_270)
                    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
                else
                    orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT;
        }
        activity.setRequestedOrientation(orientation);
    }

    private void beginCalibration() {
        final ProgressBar pb = (ProgressBar) getView().findViewById(R.id.calibrate_prog);
        final Button b = (Button) getView().findViewById(R.id.calibrate_btn);
        b.setVisibility(View.GONE);
        pb.setIndeterminate(false);
        pb.setVisibility(View.VISIBLE);
        pb.setProgress(0);
        final Runnable neutralTicker = new Runnable() {
            @Override
            public void run() {
                synchronized (neutralLock) {
                    neutralX = filter.getX();
                    neutralY = filter.getY();
                }
                pb.incrementProgressBy(1);
                threadHandler.postDelayed(this, 100);
            }
        };
        Runnable endCalibration = new Runnable() {
            @Override
            public void run() {
                pb.setVisibility(View.GONE);
                b.setVisibility(View.VISIBLE);
                threadHandler.removeCallbacks(neutralTicker);
            }
        };
        threadHandler.postDelayed(neutralTicker, 100);
        threadHandler.postDelayed(endCalibration, 4000);
    }

    @Override
    public void onClick(View view) {
        if (view == getView().findViewById(R.id.calibrate_btn)) {
            beginCalibration();
        }
    }
}
