package com.test.test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import static com.test.test.Gunban.black_vertical;
import static com.test.test.Gunban.count_size_change;
import static com.test.test.Gunban.white_vertical;
import static com.test.test.LoginActivity.logindatabaseReference;
import static com.test.test.SetPianoOctaveActivity.gunban;

public class SetPianoVerticalActivity extends Fragment {
    private View view;
    private Timer timer_plus_white;
    private Timer timer_minus_white;
    private Timer timer_plus_black;
    private Timer timer_minus_black;
    private TimerTask timerTask_plus_white;
    private TimerTask timerTask_minus_white;
    private TimerTask timerTask_plus_black;
    private TimerTask timerTask_minus_black;

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
        view = inflater.inflate(R.layout.set_vertical, container, false);
        draw_gunban = (DrawGunban)view.findViewById(R.id.piano);
        draw_gunban.invalidate();

        final ImageView plus_white_vertical = (ImageView)view.findViewById(R.id.btn_plus_white_vertical);
        final ImageView minus_white_vertical = (ImageView)view.findViewById(R.id.btn_minus_white_vertical);
        final ImageView plus_black_vertical = (ImageView)view.findViewById(R.id.btn_plus_black_vertical);
        final ImageView minus_black_vertical = (ImageView)view.findViewById(R.id.btn_minus_black_vertical);
        final ImageView finish = (ImageView)view.findViewById(R.id.btn_finish);

        timer_plus_white = new Timer(true);
        timer_minus_white = new Timer(true);
        timer_plus_black = new Timer(true);
        timer_minus_black = new Timer(true);

        timerTask_plus_white = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        gunban.plusWhiteVertical();
                        draw_gunban.invalidate();
                    }
                });
            }

            @Override
            public boolean cancel() {
                return super.cancel();
            }
        };

        timerTask_minus_white = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gunban.minusWhiteVertical();
                        draw_gunban.invalidate();
                    }
                });
            }

            @Override
            public boolean cancel() {
                return super.cancel();
            }
        };

        timerTask_plus_black = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gunban.plusBlackVertical();
                        draw_gunban.invalidate();
                    }
                });
            }

            @Override
            public boolean cancel() {
                return super.cancel();
            }
        };

        timerTask_minus_black = new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        gunban.minusBlackVertical();
                        draw_gunban.invalidate();
                    }
                });
            }

            @Override
            public boolean cancel() {
                return super.cancel();
            }
        };

        plus_white_vertical.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        plus_white_vertical.setBackgroundResource(R.drawable.btn_plus_entered);
                        minus_white_vertical.setBackgroundResource(R.drawable.btn_minus);
                        plus_black_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_black_vertical.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_plus_white == null) {
                            timer_plus_white = new Timer(true);

                            timerTask_plus_white = new TimerTask() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gunban.plusWhiteVertical();
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
                        timer_plus_white.schedule(timerTask_plus_white, 0, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        plus_white_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_white_vertical.setBackgroundResource(R.drawable.btn_minus);
                        plus_black_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_black_vertical.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_plus_white != null) {
                            timer_plus_white.cancel();
                            timer_plus_white = null;
                        }

                        break;
                }

                logindatabaseReference.child("white_vertical").setValue(white_vertical);

                return true;
            }
        });

        minus_white_vertical.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        plus_white_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_white_vertical.setBackgroundResource(R.drawable.btn_minus_entered);
                        plus_black_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_black_vertical.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_minus_white == null) {
                            timer_minus_white = new Timer(true);
                            timerTask_minus_white = new TimerTask() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gunban.minusWhiteVertical();
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

                        timer_minus_white.schedule(timerTask_minus_white, 0, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        plus_white_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_white_vertical.setBackgroundResource(R.drawable.btn_minus);
                        plus_black_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_black_vertical.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_minus_white != null) {
                            timer_minus_white.cancel();
                            timer_minus_white = null;
                        }

                        break;
                }

                logindatabaseReference.child("white_vertical").setValue(white_vertical);

                return true;
            }
        });

        plus_black_vertical.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        plus_white_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_white_vertical.setBackgroundResource(R.drawable.btn_minus);
                        plus_black_vertical.setBackgroundResource(R.drawable.btn_plus_entered);
                        minus_black_vertical.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_plus_black == null) {
                            timer_plus_black = new Timer(true);
                            timerTask_plus_black = new TimerTask() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gunban.plusBlackVertical();
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

                        timer_plus_black.schedule(timerTask_plus_black, 0, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        plus_white_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_white_vertical.setBackgroundResource(R.drawable.btn_minus);
                        plus_black_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_black_vertical.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_plus_black != null) {
                            timer_plus_black.cancel();
                            timer_plus_black = null;
                        }

                        break;
                }

                logindatabaseReference.child("black_vertical").setValue(black_vertical);

                return true;
            }
        });

        minus_black_vertical.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        plus_white_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_white_vertical.setBackgroundResource(R.drawable.btn_minus);
                        plus_black_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_black_vertical.setBackgroundResource(R.drawable.btn_minus_entered);

                        if(timer_minus_black == null) {
                            timer_minus_black = new Timer(true);
                            timerTask_minus_black = new TimerTask() {
                                @Override
                                public void run() {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            gunban.minusBlackVertical();
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

                        timer_minus_black.schedule(timerTask_minus_black, 0, 100);
                        break;
                    case MotionEvent.ACTION_UP:
                        plus_white_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_white_vertical.setBackgroundResource(R.drawable.btn_minus);
                        plus_black_vertical.setBackgroundResource(R.drawable.btn_plus);
                        minus_black_vertical.setBackgroundResource(R.drawable.btn_minus);

                        if(timer_minus_black != null) {
                            timer_minus_black.cancel();
                            timer_minus_black=null;
                        }

                        break;
                }

                logindatabaseReference.child("black_vertical").setValue(black_vertical);

                return true;
            }
        });

        finish.setOnClickListener(new ImageView.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish.setBackgroundResource(R.drawable.finish_entered);
                arg0.getContext().startActivity(new Intent(arg0.getContext(), SelectGenreActivity.class));
            }
        });

        return view;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
    }
}