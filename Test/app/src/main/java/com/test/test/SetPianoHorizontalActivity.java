package com.test.test;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import static com.test.test.Gunban.count_size_change;
import static com.test.test.LoginActivity.logindatabaseReference;
import static com.test.test.SetPianoOctaveActivity.gunban;

public class SetPianoHorizontalActivity extends Fragment {
    private View view;
    private Timer timer_plus;
    private Timer timer_minus;
    private TimerTask timerTask_plus;
    private TimerTask timerTask_minus;
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

    @SuppressLint("ClickableViewAccessibility")

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.set_horizontal, container, false);
        draw_gunban = (DrawGunban)view.findViewById(R.id.piano);
        draw_gunban.invalidate();

        final ImageView plus_horizontal = (ImageView)view.findViewById(R.id.btn_plus_horizontal);
        final ImageView minus_horizontal = (ImageView)view.findViewById(R.id.btn_minus_horizontal);

        timer_plus = new Timer(true);
        timer_minus = new Timer(true);

        timerTask_plus = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gunban.plusCountSizeChange();
                        //logindatabaseReference.child("count_size_change").setValue(count_size_change);
                        draw_gunban.invalidate();
                    }
                });
            }

            @Override
            public boolean cancel() {
                return super.cancel();
            }
        };

        timerTask_minus = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gunban.minusCountSizeChange();
                        //logindatabaseReference.child("count_size_change").setValue(count_size_change);
                        draw_gunban.invalidate();
                    }
                });
            }

            @Override
            public boolean cancel() {
                return super.cancel();
            }
        };

        plus_horizontal.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        plus_horizontal.setBackgroundResource(R.drawable.btn_plus_entered);
                        minus_horizontal.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_plus == null) {
                            timer_plus = new Timer(true);
                            timerTask_plus = new TimerTask() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gunban.plusCountSizeChange();
                                            draw_gunban.invalidate();
                                        }
                                    });
                                }

                                @Override
                                public boolean cancel() {
                                    return super.cancel();
                                }
                            };
                        }

                        timer_plus.schedule(timerTask_plus, 0, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        plus_horizontal.setBackgroundResource(R.drawable.btn_plus);
                        minus_horizontal.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_plus != null) {
                            timer_plus.cancel();
                            timer_plus = null;
                        }

                        break;
                }

                logindatabaseReference.child("count_size_change").setValue(count_size_change);

                return true;
            }
        });

        minus_horizontal.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        plus_horizontal.setBackgroundResource(R.drawable.btn_plus);
                        minus_horizontal.setBackgroundResource(R.drawable.btn_minus_entered);

                        if(timer_minus == null) {
                            timer_minus = new Timer(true);
                            timerTask_minus = new TimerTask() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gunban.minusCountSizeChange();
                                            draw_gunban.invalidate();
                                        }
                                    });
                                }

                                @Override
                                public boolean cancel() {
                                    return super.cancel();
                                }
                            };
                        }

                        timer_minus.schedule(timerTask_minus, 0, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        plus_horizontal.setBackgroundResource(R.drawable.btn_plus);
                        minus_horizontal.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_minus != null) {
                            timer_minus.cancel();
                            timer_minus = null;
                        }
                        break;
                }

                logindatabaseReference.child("count_size_change").setValue(count_size_change);

                return true;
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}