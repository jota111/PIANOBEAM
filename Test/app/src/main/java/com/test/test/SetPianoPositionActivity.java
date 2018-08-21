package com.test.test;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import static com.test.test.DrawGunban.x_piano_upleft;
import static com.test.test.DrawGunban.y_piano_upleft;
import static com.test.test.LoginActivity.logindatabaseReference;
import static com.test.test.SetPianoOctaveActivity.gunban;

public class SetPianoPositionActivity extends Fragment {
    private View view;
    private DrawGunban draw_gunban;

    @Override
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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.set_position, container, false);
        draw_gunban = (DrawGunban)view.findViewById(R.id.piano);
        draw_gunban.invalidate();

        view.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        x_piano_upleft = event.getX() - (47 + gunban.getCountSizeChange()) * 7 * gunban.getOctave() / 2;
                        y_piano_upleft = event.getY();
                        draw_gunban.invalidate();
                        break;
                }

                logindatabaseReference.child("x_piano_upleft").setValue(x_piano_upleft);
                logindatabaseReference.child("y_piano_upleft").setValue(y_piano_upleft);

                return true;
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}