package com.test.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import java.util.ArrayList;

import fftpack.RealDoubleFFT;

import static com.test.test.ScoreActivity.GREAT;
import static com.test.test.ScoreActivity.GOOD;
import static com.test.test.ScoreActivity.BAD;
import static com.test.test.ScoreActivity.great_score;
import static com.test.test.ScoreActivity.good_score;
import static com.test.test.ScoreActivity.bad_score;
import static com.test.test.SelectMusicActivity.panel;
import static com.test.test.SelectMusicActivity.answer_right;
import static com.test.test.SelectMusicActivity.answer_left;
import static com.test.test.DrawGunban.y_piano_upleft;
import static com.test.test.Panel.balls_right;
import static com.test.test.Panel.balls_left;
import static com.test.test.Ball.sum_right_count_while;
import static com.test.test.Ball.sum_left_count_while;
import static com.test.test.Ball.WHITE;
import static com.test.test.Ball.velocity;
import static com.test.test.SetPianoOctaveActivity.gunban;

public class GameActivity extends AppCompatActivity {
    private RealDoubleFFT transformer;
    private int blockSize;
    private AudioRecord audioRecord;
    public static RecordAudio record;

    private Canvas background;
    private Paint green_line;
    private ImageView Test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getSupportActionBar().hide();

        blockSize = 256;

        y_piano_upleft = y_piano_upleft - 204; // 160pixel = 40dp
        great_score = 0;
        good_score = 0;
        bad_score = 0;

        panel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
        ViewGroup root = (ViewGroup)findViewById(R.id.game);
        root.addView(panel, 2);

        ToggleButton play_or_stop = (ToggleButton)root.findViewById(R.id.btn_play_or_stop);
        play_or_stop.setChecked(true);

        Bitmap bitmap = Bitmap.createBitmap(256, 100, Bitmap.Config.ARGB_8888);
        background = new Canvas(bitmap);
        green_line = new Paint();
        green_line.setColor(Color.GREEN);
        Test = (ImageView)findViewById(R.id.test);
        Test.setImageBitmap(bitmap);

        int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
        int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
        int bufferSize = AudioRecord.getMinBufferSize(8000, channelConfiguration, audioEncoding);
        audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, 8000, channelConfiguration, audioEncoding, bufferSize);

        transformer = new RealDoubleFFT(blockSize);

        record = new RecordAudio();
        record.execute();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void clickPlayOrStop(View v) {
        boolean is_on = ((ToggleButton)v).isChecked();
        ToggleButton play_or_stop = (ToggleButton)findViewById(R.id.btn_play_or_stop);

        if(is_on) {
            play_or_stop.setBackgroundResource(R.drawable.start);
            panel.thread = new DrawBall(panel);
            panel.thread.start();
        } else {
            play_or_stop.setBackgroundResource(R.drawable.stop);
            panel.thread.setStop(true);
        }
    }

    /*public void insertCameraProtocol(String str2, Panel panel) { // 프로토콜 입력
        String[] lines = str2.split("\n");

        for (String note : lines) {
            balls_right.add(new Ball(panel.getContext(), Integer.parseInt(note.trim())));
        }
    }*/

    public void clickVelocity(View v) {
        ImageView velocity1 = (ImageView)findViewById(R.id.btn_velocity1);
        ImageView velocity2 = (ImageView)findViewById(R.id.btn_velocity2);
        ImageView velocity3 = (ImageView)findViewById(R.id.btn_velocity3);
        ImageView velocity4 = (ImageView)findViewById(R.id.btn_velocity4);
        ImageView velocity5 = (ImageView)findViewById(R.id.btn_velocity5);
        ImageView velocity6 = (ImageView)findViewById(R.id.btn_velocity6);
        ImageView velocity7 = (ImageView)findViewById(R.id.btn_velocity7);
        ImageView velocity8 = (ImageView)findViewById(R.id.btn_velocity8);

        switch (v.getId()) {
            case R.id.btn_velocity1:
                velocity1.setBackgroundResource(R.drawable.btn_velocity1_entered);
                velocity2.setBackgroundResource(R.drawable.btn_velocity2);
                velocity3.setBackgroundResource(R.drawable.btn_velocity3);
                velocity4.setBackgroundResource(R.drawable.btn_velocity4);
                velocity5.setBackgroundResource(R.drawable.btn_velocity5);
                velocity6.setBackgroundResource(R.drawable.btn_velocity6);
                velocity7.setBackgroundResource(R.drawable.btn_velocity7);
                velocity8.setBackgroundResource(R.drawable.btn_velocity8);
                velocity = 1f;
                break;
            case R.id.btn_velocity2:
                velocity1.setBackgroundResource(R.drawable.btn_velocity1);
                velocity2.setBackgroundResource(R.drawable.btn_velocity2_entered);
                velocity3.setBackgroundResource(R.drawable.btn_velocity3);
                velocity4.setBackgroundResource(R.drawable.btn_velocity4);
                velocity5.setBackgroundResource(R.drawable.btn_velocity5);
                velocity6.setBackgroundResource(R.drawable.btn_velocity6);
                velocity7.setBackgroundResource(R.drawable.btn_velocity7);
                velocity8.setBackgroundResource(R.drawable.btn_velocity8);
                velocity = 1.5f;
                break;
            case R.id.btn_velocity3:
                velocity1.setBackgroundResource(R.drawable.btn_velocity1);
                velocity2.setBackgroundResource(R.drawable.btn_velocity2);
                velocity3.setBackgroundResource(R.drawable.btn_velocity3_entered);
                velocity4.setBackgroundResource(R.drawable.btn_velocity4);
                velocity5.setBackgroundResource(R.drawable.btn_velocity5);
                velocity6.setBackgroundResource(R.drawable.btn_velocity6);
                velocity7.setBackgroundResource(R.drawable.btn_velocity7);
                velocity8.setBackgroundResource(R.drawable.btn_velocity8);
                velocity = 2f;
                break;
            case R.id.btn_velocity4:
                velocity1.setBackgroundResource(R.drawable.btn_velocity1);
                velocity2.setBackgroundResource(R.drawable.btn_velocity2);
                velocity3.setBackgroundResource(R.drawable.btn_velocity3);
                velocity4.setBackgroundResource(R.drawable.btn_velocity4_entered);
                velocity5.setBackgroundResource(R.drawable.btn_velocity5);
                velocity6.setBackgroundResource(R.drawable.btn_velocity6);
                velocity7.setBackgroundResource(R.drawable.btn_velocity7);
                velocity8.setBackgroundResource(R.drawable.btn_velocity8);
                velocity = 2.5f;
                break;
            case R.id.btn_velocity5:
                velocity1.setBackgroundResource(R.drawable.btn_velocity1);
                velocity2.setBackgroundResource(R.drawable.btn_velocity2);
                velocity3.setBackgroundResource(R.drawable.btn_velocity3);
                velocity4.setBackgroundResource(R.drawable.btn_velocity4);
                velocity5.setBackgroundResource(R.drawable.btn_velocity5_entered);
                velocity6.setBackgroundResource(R.drawable.btn_velocity6);
                velocity7.setBackgroundResource(R.drawable.btn_velocity7);
                velocity8.setBackgroundResource(R.drawable.btn_velocity8);
                velocity = 3f;
                break;
            case R.id.btn_velocity6:
                velocity1.setBackgroundResource(R.drawable.btn_velocity1);
                velocity2.setBackgroundResource(R.drawable.btn_velocity2);
                velocity3.setBackgroundResource(R.drawable.btn_velocity3);
                velocity4.setBackgroundResource(R.drawable.btn_velocity4);
                velocity5.setBackgroundResource(R.drawable.btn_velocity5);
                velocity6.setBackgroundResource(R.drawable.btn_velocity6_entered);
                velocity7.setBackgroundResource(R.drawable.btn_velocity7);
                velocity8.setBackgroundResource(R.drawable.btn_velocity8);
                velocity = 3.5f;
                break;
            case R.id.btn_velocity7:
                velocity1.setBackgroundResource(R.drawable.btn_velocity1);
                velocity2.setBackgroundResource(R.drawable.btn_velocity2);
                velocity3.setBackgroundResource(R.drawable.btn_velocity3);
                velocity4.setBackgroundResource(R.drawable.btn_velocity4);
                velocity5.setBackgroundResource(R.drawable.btn_velocity5);
                velocity6.setBackgroundResource(R.drawable.btn_velocity6);
                velocity7.setBackgroundResource(R.drawable.btn_velocity7_entered);
                velocity8.setBackgroundResource(R.drawable.btn_velocity8);
                velocity = 4f;
                break;
            case R.id.btn_velocity8:
                velocity1.setBackgroundResource(R.drawable.btn_velocity1);
                velocity2.setBackgroundResource(R.drawable.btn_velocity2);
                velocity3.setBackgroundResource(R.drawable.btn_velocity3);
                velocity4.setBackgroundResource(R.drawable.btn_velocity4);
                velocity5.setBackgroundResource(R.drawable.btn_velocity5);
                velocity6.setBackgroundResource(R.drawable.btn_velocity6);
                velocity7.setBackgroundResource(R.drawable.btn_velocity7);
                velocity8.setBackgroundResource(R.drawable.btn_velocity8_entered);
                velocity = 4.5f;
                break;
        }

        changeVelocity(velocity);
        panel.invalidate();
    }

    public void changeVelocity(float velocity) {
        float percentage;

        sum_right_count_while = 0;
        sum_left_count_while = 0;

        synchronized(balls_right) { // 검은색 건반에 내려오는 빛 그리기
            for(Ball ball : balls_right) {
                if(velocity == 1) {
                    percentage = 0.99f;
                } else if(velocity == 1.5) {
                    percentage = 0.66f;
                } else if(velocity == 2) {
                    percentage = 0.5f;
                } else if(velocity == 2.5) {
                    percentage = 0.4f;
                } else if(velocity == 3) {
                    percentage = 0.34f;
                } else if(velocity == 3.5) {
                    percentage = 0.29f;
                } else if(velocity == 4) {
                    percentage = 0.26f;
                } else {
                    percentage = 0.22f;
                }

                if(ball.getProtocol() / 10000000 == 1) { // 오른손이라면, 오른손 쉼표도 해당
                    if(ball.getProtocol() % 10 != 1) { // 동시에 치는 음이 아니라면
                        sum_right_count_while += (ball.getProtocol() % 10000) * percentage;
                    }

                    ball.changeCount_while(sum_right_count_while); // 오른손 누적 for문 도는 개수에 추가
                    sum_right_count_while = sum_right_count_while + 2;

                } else { // 왼손이라면, 왼손 쉼표도 해당
                    if(ball.getProtocol() % 10 != 1) { // 동시에 치는 음이 아니라면
                        sum_left_count_while += (ball.getProtocol() % 10000) * percentage;
                    }

                    ball.changeCount_while(sum_left_count_while); // 왼손 누적 for문 도는 개수에 추가
                    sum_left_count_while = sum_left_count_while + 2;
                }

                if(!ball.getGo_down())
                    ball.setGo_down(true);

                ball.changeLength_boundary(ball.getLength());
                ball.setY(ball.getProtocol());
            }
        }
    }

    private class RecordAudio extends AsyncTask<Void, double[], Void> {
        ArrayList<Integer> temp_right;
        ArrayList<Integer> temp_left;
        int count_right;
        int count_left;
        int combo;

        @Override
        protected Void doInBackground(Void... params) {
            temp_right = new ArrayList<>();
            temp_left = new ArrayList<>();
            count_right = 0;
            count_left = 0;
            combo = 0;

            try {
                short[] buffer = new short[blockSize];
                double[] toTransform = new double[blockSize];

                audioRecord.startRecording();

                while(true) {
                    int bufferReadResult = audioRecord.read(buffer, 0, blockSize);

                    for(int i = 0; i < blockSize && i < bufferReadResult; i++) {
                        toTransform[i] = (double) buffer[i] / Short.MAX_VALUE;
                    }

                    transformer.ft(toTransform);
                    publishProgress(toTransform);

                    float y_last_ball = balls_right.get(balls_right.size() - 1).getY(); // 마지막으로 내려오는 막대의 y좌표

                    if(y_last_ball >= y_piano_upleft + gunban.getWhiteVertical() - 1) { // 마지막 빛 막대가 판정선을 지나가면
                        for(int i = 0; i < balls_right.size(); i++) {
                            switch (balls_right.get(i).getCorrect()) {
                                case GREAT:
                                    great_score++;
                                    break;
                                case GOOD:
                                    good_score++;
                                    break;
                                case BAD:
                                    bad_score++;
                                    break;
                            }
                        }

                        for(int i = 0; i < balls_left.size(); i++) {
                            switch (balls_left.get(i).getCorrect()) {
                                case GREAT:
                                    great_score++;
                                    break;
                                case GOOD:
                                    good_score++;
                                    break;
                                case BAD:
                                    bad_score++;
                                    break;
                            }
                        }
                        break;
                    }
                }

                audioRecord.stop();
                startActivity(new Intent(GameActivity.this, ScoreActivity.class));
                record.cancel(true);
                ActivityCompat.finishAffinity(GameActivity.this);
                System.runFinalization();
                System.exit(0);
            } catch (Throwable t) {
                Log.e("AudioRecord", "Recording Failed");
            }

            y_piano_upleft = y_piano_upleft + 204; // 160pixel = 40dp, 보정

            return null;
        }

        @Override
        protected void onProgressUpdate(double[]... toTransform) {
            background.drawColor(Color.BLACK);

            for(int i = 0; i < toTransform[0].length; i++) {
                background.drawLine(i, (int)(100 - (toTransform[0][i] * 10)), i, 100, green_line);
            }

            Test.invalidate();

            if(count_right != balls_right.size()) { // 마지막에 스코어 화면으로 넘어가기 위해 필수적 조건
                float black_or_white;

                if(balls_right.get(count_right).getType() == WHITE) { // 흰 건반이라면
                    black_or_white = gunban.getWhiteVertical();
                } else { // 검은 건반이라면
                    black_or_white = gunban.getBlackVertical();
                }

                float y_ball = balls_right.get(count_right).getY(); // 내려오는 막대의 y좌표

                if(y_ball >= y_piano_upleft + black_or_white - balls_right.get(count_right).getLength() - 30 && y_ball < y_piano_upleft + black_or_white - 30) { // 빛 막대가 판정선을 지나고 있는 동안
                    for(int i = 0; i < toTransform[0].length; i++) {
                        int downy = (int)(100 - (toTransform[0][i] * 10));

                        if(downy < 20) { // 높이 올라오면
                            temp_right.add(i);

                            if(balls_right.get(count_right).getCorrect() == BAD) {
                                int init = 0; // 사용자가 친 음의 특징값 하나가 정답지에 있는지

                                if(!balls_right.get(count_right).getSame()) { // 동시에 치는 음이 아니면
                                    for(int j = 0; j < answer_right.get(count_right).size(); j++) {
                                        for(int k = 0; k < temp_right.size(); k++) { // 사용자가 친 음과 비교
                                            if(answer_right.get(count_right).get(j) == temp_right.get(k)) { // 하나씩 비교, 존재한다면
                                                init++;

                                                break;
                                            }
                                        }
                                    }

                                    if(init == answer_right.get(count_right).size()) { // 오른손 정답지와 개수 비교
                                        if(y_ball >= y_piano_upleft + black_or_white - balls_right.get(count_right).getLength() - 30 && y_ball < y_piano_upleft + black_or_white - balls_right.get(count_right).getLength() / 3 - 30) { // 전체 음의 1/3 안에 치면
                                            balls_right.get(count_right).setCorrect(GREAT);

                                        } else if(y_ball >= y_piano_upleft + black_or_white - balls_right.get(count_right).getLength() / 3 - 30 && y_ball < y_piano_upleft + black_or_white - 30) { // 그 이후에 치면
                                            balls_right.get(count_right).setCorrect(GOOD);
                                        }

                                        combo++;
                                        showCombo(combo);
                                    }
                                } else { // 동시에 치는 음이면
                                    for(int j = 0; j < balls_right.get(count_right).getSame_num(); j++) {
                                        for(int k = 0; k < answer_right.get(count_right).size(); k++) {
                                            for(int l = 0; l < temp_right.size(); l++) { // 사용자가 친 음과 비교
                                                if(answer_right.get(count_right + j).get(k) == temp_right.get(l)) { // 하나씩 비교, 존재한다면
                                                    init++;

                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    int temp_sum = 0; // 동시에 친 음들의 특징값들의 개수의 합

                                    for(int j = 0; j < balls_right.get(count_right).getSame_num(); j++) {
                                        temp_sum += answer_right.get(count_right + j).size();
                                    }

                                    if(init == temp_sum) { // 동시에 친 음이 다 맞는 경우만 판단 가능
                                        for(int j = 0; j < balls_right.get(count_right).getSame_num(); j++) {
                                            balls_right.get(count_right + j).setCorrect(GREAT);
                                        }

                                        combo = combo + balls_right.get(count_right).getSame_num();
                                        showCombo(combo);
                                    }
                                }
                            }
                        }
                    }
                }

                else if(y_ball >= y_piano_upleft + black_or_white - 30) {
                    if(!balls_right.get(count_right).getSame()) { // 동시에 치는 음이 아니면
                        count_right++;
                    } else { // 동시에 치는 음이면
                        count_right = count_right + balls_right.get(count_right).getSame_num();
                    }

                    temp_right.clear();
                }
            }

            if(count_left != balls_left.size()) { // 마지막에 스코어 화면으로 넘어가기 위해 필수적 조건
                float black_or_white;

                if(balls_left.get(count_left).getType() == WHITE) { // 흰 건반이라면
                    black_or_white = gunban.getWhiteVertical();
                } else { // 검은 건반이라면
                    black_or_white = gunban.getBlackVertical();
                }

                float y_ball = balls_left.get(count_left).getY(); // 내려오는 막대의 y좌표

                if(y_ball >= y_piano_upleft + black_or_white - balls_left.get(count_left).getLength() - 30 && y_ball < y_piano_upleft + black_or_white - 30) { // 빛 막대가 판정선을 지나고 있는 동안
                    for(int i = 0; i < toTransform[0].length; i++) {
                        int downy = (int)(100 - (toTransform[0][i] * 10));

                        if(downy < 20) { // 높이 올라오면
                            Log.i("특징값", "2222222222222222  " + i);
                            temp_left.add(i);

                            if(balls_left.get(count_left).getCorrect() == BAD) {
                                int init = 0; // 사용자가 친 음의 특징값 하나가 정답지에 있는지

                                if(!balls_left.get(count_left).getSame()) { // 동시에 치는 음이 아니면
                                    for(int j = 0; j < answer_left.get(count_left).size(); j++) {
                                        for(int k = 0; k < temp_left.size(); k++) { // 사용자가 친 음과 비교
                                            if(answer_left.get(count_left).get(j) == temp_left.get(k)) { // 하나씩 비교, 존재한다면
                                                init++;

                                                break;
                                            }
                                        }
                                    }

                                    if(init == answer_left.get(count_left).size()) {
                                        if(y_ball >= y_piano_upleft + black_or_white - balls_left.get(count_left).getLength() - 30 && y_ball < y_piano_upleft + black_or_white - balls_left.get(count_left).getLength() / 3 - 30) {
                                            balls_left.get(count_left).setCorrect(GREAT);
                                            combo++;
                                            showCombo(combo);

                                        } else if(y_ball >= y_piano_upleft + black_or_white - balls_left.get(count_left).getLength() / 3 - 30 && y_ball < y_piano_upleft + black_or_white - 30) {
                                            balls_left.get(count_left).setCorrect(GOOD);
                                        }
                                    }
                                } else { // 동시에 치는 음이면
                                    for(int j = 0; j < balls_left.get(count_left).getSame_num(); j++) {
                                        for(int k = 0; k < answer_left.get(count_left).size(); k++) {
                                            for(int l = 0; l < temp_left.size(); l++) { // 사용자가 친 음과 비교
                                                if(answer_left.get(count_left + j).get(k) == temp_left.get(l)) { // 하나씩 비교, 존재한다면
                                                    init++;

                                                    break;
                                                }
                                            }
                                        }
                                    }

                                    int temp_sum = 0; // 동시에 친 음들의 특징값들의 개수의 합

                                    for(int j = 0; j < balls_left.get(count_left).getSame_num(); j++) {
                                        temp_sum += answer_left.get(count_left + j).size();
                                    }

                                    if(init == temp_sum) {
                                        for(int j = 0; j < balls_left.get(count_left).getSame_num(); j++) {
                                            balls_left.get(count_left + j).setCorrect(GREAT);
                                        }

                                        combo = combo + balls_left.get(count_left).getSame_num();
                                        showCombo(combo);
                                    }
                                }
                            }
                        }
                    }
                } else if(y_ball >= y_piano_upleft + black_or_white - 30) {
                    if(!balls_left.get(count_left).getSame()) { // 동시에 치는 음이 아니면
                        count_left++;
                    } else { // 동시에 치는 음이면
                        count_left = count_left + balls_left.get(count_left).getSame_num();
                    }

                    temp_left.clear();
                }
            }
        }
    }

    public void showCombo(int combo) {
        ImageView combo1 = (ImageView)findViewById(R.id.c_img1);
        ImageView combo2 = (ImageView)findViewById(R.id.c_img2);
        ImageView combo3 = (ImageView)findViewById(R.id.c_img3);

        int combo100 = combo / 100; // 콤보 100자릿수
        int combo010 = (combo % 100) / 10; // 콤보 10자릿수
        int combo001 = combo % 10; // 콤보 1자릿수

        switch(combo100) {
            case 0:
                combo1.setBackgroundResource(R.drawable.green0);
                break;
            case 1:
                combo1.setBackgroundResource(R.drawable.green1);
                break;
            case 2:
                combo1.setBackgroundResource(R.drawable.green2);
                break;
            case 3:
                combo1.setBackgroundResource(R.drawable.green3);
                break;
            case 4:
                combo1.setBackgroundResource(R.drawable.green4);
                break;
            case 5:
                combo1.setBackgroundResource(R.drawable.green5);
                break;
            case 6:
                combo1.setBackgroundResource(R.drawable.green6);
                break;
            case 7:
                combo1.setBackgroundResource(R.drawable.green7);
                break;
            case 8:
                combo1.setBackgroundResource(R.drawable.green8);
                break;
            case 9:
                combo1.setBackgroundResource(R.drawable.green9);
                break;
            default:
                break;
        }

        switch(combo010) {
            case 0:
                combo2.setBackgroundResource(R.drawable.blue0);
                break;
            case 1:
                combo2.setBackgroundResource(R.drawable.blue1);
                break;
            case 2:
                combo2.setBackgroundResource(R.drawable.blue2);
                break;
            case 3:
                combo2.setBackgroundResource(R.drawable.blue3);
                break;
            case 4:
                combo2.setBackgroundResource(R.drawable.blue4);
                break;
            case 5:
                combo2.setBackgroundResource(R.drawable.blue5);
                break;
            case 6:
                combo2.setBackgroundResource(R.drawable.blue6);
                break;
            case 7:
                combo2.setBackgroundResource(R.drawable.blue7);
                break;
            case 8:
                combo2.setBackgroundResource(R.drawable.blue8);
                break;
            case 9:
                combo2.setBackgroundResource(R.drawable.blue9);
                break;
            default:
                break;
        }

        switch(combo001) {
            case 0:
                combo3.setBackgroundResource(R.drawable.red0);
                break;
            case 1:
                combo3.setBackgroundResource(R.drawable.red1);
                break;
            case 2:
                combo3.setBackgroundResource(R.drawable.red2);
                break;
            case 3:
                combo3.setBackgroundResource(R.drawable.red3);
                break;
            case 4:
                combo3.setBackgroundResource(R.drawable.red4);
                break;
            case 5:
                combo3.setBackgroundResource(R.drawable.red5);
                break;
            case 6:
                combo3.setBackgroundResource(R.drawable.red6);
                break;
            case 7:
                combo3.setBackgroundResource(R.drawable.red7);
                break;
            case 8:
                combo3.setBackgroundResource(R.drawable.red8);
                break;
            case 9:
                combo3.setBackgroundResource(R.drawable.red9);
                break;
            default:
                break;
        }
    }

    public void moveActivity(View v) {
        ImageView music = (ImageView)findViewById(R.id.music);
        ImageView set = (ImageView)findViewById(R.id.set);
        ImageView back = (ImageView)findViewById(R.id.back);
        ImageView home = (ImageView)findViewById(R.id.home);

        switch(v.getId()) {
            case R.id.music:
                music.setBackgroundResource(R.drawable.btn_music_entered);
                startActivity(new Intent(GameActivity.this, SelectGenreActivity.class));
                record.cancel(true);
                ActivityCompat.finishAffinity(GameActivity.this);
                System.runFinalization();
                System.exit(0);
                break;
            case R.id.set:
                set.setBackgroundResource(R.drawable.btn_set_entered);
                startActivity(new Intent(GameActivity.this, SetPianoActivity.class));
                record.cancel(true);
                ActivityCompat.finishAffinity(GameActivity.this);
                System.runFinalization();
                System.exit(0);
                break;
            case R.id.back:
                back.setBackgroundResource(R.drawable.btn_back_entered);
                startActivity(new Intent(GameActivity.this, SelectMusicActivity.class));
                record.cancel(true);
                ActivityCompat.finishAffinity(GameActivity.this);
                System.runFinalization();
                System.exit(0);
                break;
            case R.id.home:
                home.setBackgroundResource(R.drawable.btn_home_entered);
                startActivity(new Intent(GameActivity.this, MenuActivity.class));
                record.cancel(true);
                ActivityCompat.finishAffinity(GameActivity.this);
                System.runFinalization();
                System.exit(0);
                break;
        }

        record.cancel(true);
        ActivityCompat.finishAffinity(this);
        System.runFinalization();
        System.exit(0);
        y_piano_upleft = y_piano_upleft + 204; // 160pixel = 40dp
    }

    public void onBackPressed() {
        startActivity(new Intent(GameActivity.this, SelectMusicActivity.class));
        record.cancel(true);
        ActivityCompat.finishAffinity(this);
        System.runFinalization();
        System.exit(0);
    }
}