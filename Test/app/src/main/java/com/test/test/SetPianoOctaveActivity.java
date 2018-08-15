package com.test.test;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import static com.test.test.DrawGunban.y_piano_upleft;

public class SetPianoOctaveActivity extends android.support.v4.app.Fragment {
    public static Gunban gunban = new Gunban();
    public static DrawGunban draw_piano;
    View view;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) { // 현재 보이고 있으면 새로고침
        super.setUserVisibleHint(isVisibleToUser);

        if(isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.set_octave, container, false);
        draw_piano = (DrawGunban)view.findViewById(R.id.piano);
        draw_piano.invalidate();

        final ImageView octave2 = (ImageView)view.findViewById(R.id.btn_octave2);
        final ImageView octave3 = (ImageView)view.findViewById(R.id.btn_octave3);
        final ImageView octave4 = (ImageView)view.findViewById(R.id.btn_octave4);
        final ImageView octave5 = (ImageView)view.findViewById(R.id.btn_octave5);
        final ImageView octave6 = (ImageView)view.findViewById(R.id.btn_octave6);
        final ImageView octave7 = (ImageView)view.findViewById(R.id.btn_octave7);

        octave2.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                octave2.setBackgroundResource(R.drawable.octave2_entered);
                octave3.setBackgroundResource(R.drawable.octave3);
                octave4.setBackgroundResource(R.drawable.octave4);
                octave5.setBackgroundResource(R.drawable.octave5);
                octave6.setBackgroundResource(R.drawable.octave6);
                octave7.setBackgroundResource(R.drawable.octave7);
                gunban.setOctave(2);
                draw_piano.invalidate();

            }
        });

        octave3.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0){
                octave2.setBackgroundResource(R.drawable.octave2);
                octave3.setBackgroundResource(R.drawable.octave3_entered);
                octave4.setBackgroundResource(R.drawable.octave4);
                octave5.setBackgroundResource(R.drawable.octave5);
                octave6.setBackgroundResource(R.drawable.octave6);
                octave7.setBackgroundResource(R.drawable.octave7);
                gunban.setOctave(3);
                draw_piano.invalidate();
            }
        });

        octave4.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                octave2.setBackgroundResource(R.drawable.octave2);
                octave3.setBackgroundResource(R.drawable.octave3);
                octave4.setBackgroundResource(R.drawable.octave4_entered);
                octave5.setBackgroundResource(R.drawable.octave5);
                octave6.setBackgroundResource(R.drawable.octave6);
                octave7.setBackgroundResource(R.drawable.octave7);
                gunban.setOctave(4);
                draw_piano.invalidate();
            }
        });

        octave5.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                octave2.setBackgroundResource(R.drawable.octave2);
                octave3.setBackgroundResource(R.drawable.octave3);
                octave4.setBackgroundResource(R.drawable.octave4);
                octave5.setBackgroundResource(R.drawable.octave5_entered);
                octave6.setBackgroundResource(R.drawable.octave6);
                octave7.setBackgroundResource(R.drawable.octave7);
                gunban.setOctave(5);
                draw_piano.invalidate();
            }
        });

        octave6.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                octave2.setBackgroundResource(R.drawable.octave2);
                octave3.setBackgroundResource(R.drawable.octave3);
                octave4.setBackgroundResource(R.drawable.octave4);
                octave5.setBackgroundResource(R.drawable.octave5);
                octave6.setBackgroundResource(R.drawable.octave6_entered);
                octave7.setBackgroundResource(R.drawable.octave7);
                gunban.setOctave(6);
                draw_piano.invalidate();
            }
        });

        octave7.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                octave2.setBackgroundResource(R.drawable.octave2);
                octave3.setBackgroundResource(R.drawable.octave3);
                octave4.setBackgroundResource(R.drawable.octave4);
                octave5.setBackgroundResource(R.drawable.octave5);
                octave6.setBackgroundResource(R.drawable.octave6);
                octave7.setBackgroundResource(R.drawable.octave7_entered);
                gunban.setOctave(7);
                draw_piano.invalidate();
            }
        });

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this.getContext());

        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        if(isFirstRun) {
            setDisplay();
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }
        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }

    public void setDisplay() {
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        int android_x_size = ((WindowManager)getActivity().getSystemService(getActivity().WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        int android_y_size = ((WindowManager)getActivity().getSystemService(getActivity().WINDOW_SERVICE)).getDefaultDisplay().getHeight();

        if(android_x_size == 1280  && android_y_size == 720) {
            y_piano_upleft = 100;
        } else if(android_x_size == 1920  && android_y_size == 1080) {
            y_piano_upleft = 200;
        } else if(android_x_size == 2560  && android_y_size == 1440) {
            y_piano_upleft = 400;
        } else if(android_x_size == 2560  && android_y_size == 1660) {
            y_piano_upleft = 500;
        }
    }
}