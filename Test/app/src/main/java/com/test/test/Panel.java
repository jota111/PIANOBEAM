package com.test.test;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

class Panel extends SurfaceView implements SurfaceHolder.Callback {
    public static int surface_width;
    public static int surface_height;

    public static List<Ball> balls_right = new ArrayList<>();
    public static List<Ball> balls_left = new ArrayList<>();
    public DrawBall thread = new DrawBall(this);

    public Panel(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if(thread.isAlive()){
            thread.interrupt();
        }

        thread = new DrawBall(this);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        surface_width = width;
        surface_height = height;
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
}