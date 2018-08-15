package com.test.test;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

import static com.test.test.Ball.WHITE;
import static com.test.test.Ball.BLACK;
import static com.test.test.SetPianoOctaveActivity.gunban;
import static com.test.test.DrawGunban.x_piano_upleft;
import static com.test.test.DrawGunban.y_piano_upleft;
import static com.test.test.Panel.balls_right;
import static com.test.test.Panel.balls_left;

class DrawBall extends Thread {
    private SurfaceHolder holder;
    private Paint white_gunban;
    private Paint black_gunban;
    private Paint judge_line;
    private boolean stop;

    public DrawBall(Panel panel) {
        setHolder(panel);
        setColor();
        setStop(false);
    }

    public void setHolder(Panel panel) {
        holder = panel.getHolder();
    }

    public void setColor() {
        white_gunban = new Paint();
        white_gunban.setColor(Color.WHITE);
        black_gunban = new Paint();
        black_gunban.setColor(Color.BLACK);
        judge_line = new Paint();
        judge_line.setColor(Color.GRAY);
    }

    public void setStop(boolean true_or_false) {
        stop = true_or_false;
    }

    @Override
    public void run() {
        while(!stop) {
            Canvas canvas = holder.lockCanvas();

            if(canvas != null) {
                canvas.drawColor(Color.BLACK);
                for(int i = 0; i < 7 * gunban.getOctave(); i++) { // 흰색 건반 그리기
                    float x_white_gunban_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * i;
                    float y_white_gunban_upleft = y_piano_upleft;
                    float x_white_gunban_downright = x_white_gunban_upleft + 47 + gunban.getCountSizeChange();
                    float y_white_gunban_downright = y_piano_upleft + gunban.getWhiteVertical();

                    canvas.drawRect(x_white_gunban_upleft, y_white_gunban_upleft, x_white_gunban_downright, y_white_gunban_downright, white_gunban);
                }

                synchronized(balls_right) { // 흰색 건반에 내려오는 빛 그리기
                    for(Ball ball : balls_right) {
                        if(ball.getType() == WHITE && ball.getGo_down()) {
                            if (ball.getCount_while() > 0) {
                                ball.minusCount_while();
                            } else {
                                ball.drawWhiteGubanLight(canvas);
                            }
                        }
                    }
                }

                synchronized(balls_left) { // 흰색 건반에 내려오는 빛 그리기
                    for(Ball ball : balls_left) {
                        if(ball.getType() == WHITE && ball.getGo_down()) {
                            if (ball.getCount_while() > 0) {
                                ball.minusCount_while();
                            } else {
                                ball.drawWhiteGubanLight(canvas);
                            }
                        }
                    }
                }

                for(int i = 0; i < gunban.getOctave(); i++) { // 검은색 건반 그리기
                    float x_black_gunban1_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (((47 + gunban.getCountSizeChange()) * 3) - (23 + 23 / 47 * gunban.getCountSizeChange()) * 2) / 3;
                    float x_black_gunban1_downright = x_black_gunban1_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_black_gunban1_upleft = y_piano_upleft;
                    float y_black_gunban1_downright = y_piano_upleft + gunban.getBlackVertical();
                    float x_black_gunban2_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + ((47 + gunban.getCountSizeChange()) * 3 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 2) / 3 * 2 + 23 + 23 / 47 * gunban.getCountSizeChange();
                    float x_black_gunban2_downright = x_black_gunban2_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_black_gunban2_upleft = y_piano_upleft;
                    float y_black_gunban2_downright = y_piano_upleft + gunban.getBlackVertical();
                    float x_black_gunban3_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4;
                    float x_black_gunban3_downright = x_black_gunban3_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_black_gunban3_upleft = y_piano_upleft;
                    float y_black_gunban3_downright = y_piano_upleft + gunban.getBlackVertical();
                    float x_black_gunban4_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4 * 2 + 23 + 23 / 47 * gunban.getCountSizeChange();
                    float x_black_gunban4_downright = x_black_gunban4_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_black_gunban4_upleft = y_piano_upleft;
                    float y_black_gunban4_downright = y_piano_upleft + gunban.getBlackVertical();
                    float x_black_gunban5_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4 * 3 + (23 + 23 / 47 * gunban.getCountSizeChange()) * 2;
                    float x_black_gunban5_downright = x_black_gunban5_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_black_gunban5_upleft = y_piano_upleft;
                    float y_black_gunban5_downright = y_piano_upleft + gunban.getBlackVertical();

                    canvas.drawRect(x_black_gunban1_upleft, y_black_gunban1_upleft, x_black_gunban1_downright, y_black_gunban1_downright, black_gunban);
                    canvas.drawRect(x_black_gunban2_upleft, y_black_gunban2_upleft, x_black_gunban2_downright, y_black_gunban2_downright, black_gunban);
                    canvas.drawRect(x_black_gunban3_upleft, y_black_gunban3_upleft, x_black_gunban3_downright, y_black_gunban3_downright, black_gunban);
                    canvas.drawRect(x_black_gunban4_upleft, y_black_gunban4_upleft, x_black_gunban4_downright, y_black_gunban4_downright, black_gunban);
                    canvas.drawRect(x_black_gunban5_upleft, y_black_gunban5_upleft, x_black_gunban5_downright, y_black_gunban5_downright, black_gunban);
                }

                synchronized(balls_right) { // 검은색 건반에 내려오는 빛 그리기
                    for(Ball ball : balls_right) {
                        if(ball.getType() == BLACK && ball.getGo_down()) {
                            if (ball.getCount_while() > 0) {
                                ball.minusCount_while();
                            } else {
                                ball.drawBlackGubanLight(canvas);
                            }
                        }
                    }
                }

                synchronized(balls_left) { // 검은색 건반에 내려오는 빛 그리기
                    for(Ball ball : balls_left) {
                        if(ball.getType() == BLACK && ball.getGo_down()) {
                            if (ball.getCount_while() > 0) {
                                ball.minusCount_while();
                            } else {
                                ball.drawBlackGubanLight(canvas);
                            }
                        }
                    }
                }

                //canvas.drawRect(0, 0, 2000, y_piano_upleft, black_gunban); // 위에 가려주는 부분 그리기
                canvas.drawRect(x_piano_upleft, y_piano_upleft + gunban.getWhiteVertical() - 37, x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 *gunban.getOctave(), y_piano_upleft + gunban.getWhiteVertical() - 30, judge_line); // 흰색 건반 판정선 윗부분 그리기
                canvas.drawRect(x_piano_upleft, y_piano_upleft + gunban.getWhiteVertical() - 7, x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 *gunban.getOctave(), y_piano_upleft + gunban.getWhiteVertical(), judge_line); // 흰색 건반 판정선 아랫부분 그리기

                for(int i = 0; i < gunban.getOctave(); i++) { // 검은색 건반 판정선 위아래 다 그리기
                    float x_judgeline_up_black_gunban1_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (((47 + gunban.getCountSizeChange()) * 3) - (23 + 23 / 47 * gunban.getCountSizeChange()) * 2) / 3;
                    float x_judgeline_up_black_gunban1_downright = x_judgeline_up_black_gunban1_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_up_black_gunban1_upleft = y_piano_upleft + gunban.getBlackVertical() - 37;
                    float y_judgeline_up_black_gunban1_downright = y_piano_upleft + gunban.getBlackVertical() - 30;
                    float x_judgeline_up_black_gunban2_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + ((47 + gunban.getCountSizeChange()) * 3 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 2) / 3 * 2 + 23 + 23 / 47 * gunban.getCountSizeChange();
                    float x_judgeline_up_black_gunban2_downright = x_judgeline_up_black_gunban2_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_up_black_gunban2_upleft = y_piano_upleft + gunban.getBlackVertical() - 37;
                    float y_judgeline_up_black_gunban2_downright = y_piano_upleft + gunban.getBlackVertical() - 30;
                    float x_judgeline_up_black_gunban3_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4;
                    float x_judgeline_up_black_gunban3_downright = x_judgeline_up_black_gunban3_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_up_black_gunban3_upleft = y_piano_upleft + gunban.getBlackVertical() - 37;
                    float y_judgeline_up_black_gunban3_downright = y_piano_upleft + gunban.getBlackVertical() - 30;
                    float x_judgeline_up_black_gunban4_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4 * 2 + 23 + 23 / 47 * gunban.getCountSizeChange();
                    float x_judgeline_up_black_gunban4_downright = x_judgeline_up_black_gunban4_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_up_black_gunban4_upleft = y_piano_upleft + gunban.getBlackVertical() - 37;
                    float y_judgeline_up_black_gunban4_downright = y_piano_upleft + gunban.getBlackVertical() - 30;
                    float x_judgeline_up_black_gunban5_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4 * 3 + (23 + 23 / 47 * gunban.getCountSizeChange()) * 2;
                    float x_judgeline_up_black_gunban5_downright = x_judgeline_up_black_gunban5_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_up_black_gunban5_upleft = y_piano_upleft + gunban.getBlackVertical() - 37;
                    float y_judgeline_up_black_gunban5_downright = y_piano_upleft + gunban.getBlackVertical() - 30;
                    float x_judgeline_down_black_gunban1_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (((47 + gunban.getCountSizeChange()) * 3) - (23 + 23 / 47 * gunban.getCountSizeChange()) * 2) / 3;
                    float x_judgeline_down_black_gunban1_downright = x_judgeline_down_black_gunban1_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_down_black_gunban1_upleft = y_piano_upleft + gunban.getBlackVertical() - 7;
                    float y_judgeline_down_black_gunban1_downright = y_piano_upleft + gunban.getBlackVertical();
                    float x_judgeline_down_black_gunban2_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + ((47 + gunban.getCountSizeChange()) * 3 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 2) / 3 * 2 + 23 + 23 / 47 * gunban.getCountSizeChange();
                    float x_judgeline_down_black_gunban2_downright = x_judgeline_down_black_gunban2_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_down_black_gunban2_upleft = y_piano_upleft + gunban.getBlackVertical() - 7;
                    float y_judgeline_down_black_gunban2_downright = y_piano_upleft + gunban.getBlackVertical();
                    float x_judgeline_down_black_gunban3_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4;
                    float x_judgeline_down_black_gunban3_downright = x_judgeline_down_black_gunban3_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_down_black_gunban3_upleft = y_piano_upleft + gunban.getBlackVertical() - 7;
                    float y_judgeline_down_black_gunban3_downright = y_piano_upleft + gunban.getBlackVertical();
                    float x_judgeline_down_black_gunban4_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4 * 2 + 23 + 23 / 47 * gunban.getCountSizeChange();
                    float x_judgeline_down_black_gunban4_downright = x_judgeline_down_black_gunban4_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_down_black_gunban4_upleft = y_piano_upleft + gunban.getBlackVertical() - 7;
                    float y_judgeline_down_black_gunban4_downright = y_piano_upleft + gunban.getBlackVertical();
                    float x_judgeline_down_black_gunban5_upleft = x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * i + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4 * 3 + (23 + 23 / 47 * gunban.getCountSizeChange()) * 2;
                    float x_judgeline_down_black_gunban5_downright = x_judgeline_down_black_gunban5_upleft + 23 + 0.489361f * gunban.getCountSizeChange();
                    float y_judgeline_down_black_gunban5_upleft = y_piano_upleft + gunban.getBlackVertical() - 7;
                    float y_judgeline_down_black_gunban5_downright = y_piano_upleft + gunban.getBlackVertical();

                    canvas.drawRect(x_judgeline_up_black_gunban1_upleft, y_judgeline_up_black_gunban1_upleft, x_judgeline_up_black_gunban1_downright, y_judgeline_up_black_gunban1_downright, judge_line);
                    canvas.drawRect(x_judgeline_up_black_gunban2_upleft, y_judgeline_up_black_gunban2_upleft, x_judgeline_up_black_gunban2_downright, y_judgeline_up_black_gunban2_downright, judge_line);
                    canvas.drawRect(x_judgeline_up_black_gunban3_upleft, y_judgeline_up_black_gunban3_upleft, x_judgeline_up_black_gunban3_downright, y_judgeline_up_black_gunban3_downright, judge_line);
                    canvas.drawRect(x_judgeline_up_black_gunban4_upleft, y_judgeline_up_black_gunban4_upleft, x_judgeline_up_black_gunban4_downright, y_judgeline_up_black_gunban4_downright, judge_line);
                    canvas.drawRect(x_judgeline_up_black_gunban5_upleft, y_judgeline_up_black_gunban5_upleft, x_judgeline_up_black_gunban5_downright, y_judgeline_up_black_gunban5_downright, judge_line);
                    canvas.drawRect(x_judgeline_down_black_gunban1_upleft, y_judgeline_down_black_gunban1_upleft, x_judgeline_down_black_gunban1_downright, y_judgeline_down_black_gunban1_downright, judge_line);
                    canvas.drawRect(x_judgeline_down_black_gunban2_upleft, y_judgeline_down_black_gunban2_upleft, x_judgeline_down_black_gunban2_downright, y_judgeline_down_black_gunban2_downright, judge_line);
                    canvas.drawRect(x_judgeline_down_black_gunban3_upleft, y_judgeline_down_black_gunban3_upleft, x_judgeline_down_black_gunban3_downright, y_judgeline_down_black_gunban3_downright, judge_line);
                    canvas.drawRect(x_judgeline_down_black_gunban4_upleft, y_judgeline_down_black_gunban4_upleft, x_judgeline_down_black_gunban4_downright, y_judgeline_down_black_gunban4_downright, judge_line);
                    canvas.drawRect(x_judgeline_down_black_gunban5_upleft, y_judgeline_down_black_gunban5_upleft, x_judgeline_down_black_gunban5_downright, y_judgeline_down_black_gunban5_downright, judge_line);
                }

                holder.unlockCanvasAndPost(canvas);
            }
        }
    }
}