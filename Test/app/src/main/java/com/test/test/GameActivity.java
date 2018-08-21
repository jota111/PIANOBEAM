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
import android.widget.TextView;
import android.widget.ToggleButton;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import fftpack.RealDoubleFFT;

import static com.test.test.LoginActivity.music_selected;
import static com.test.test.Ball.velocity;
import static com.test.test.Ball.WHITE;
import static com.test.test.DrawGunban.y_piano_upleft;
import static com.test.test.LoginActivity.id;
import static com.test.test.Panel.balls_right;
import static com.test.test.Panel.balls_left;
import static com.test.test.ScoreActivity.GREAT;
import static com.test.test.ScoreActivity.GOOD;
import static com.test.test.ScoreActivity.BAD;
import static com.test.test.ScoreActivity.great_score;
import static com.test.test.ScoreActivity.good_score;
import static com.test.test.ScoreActivity.bad_score;
import static com.test.test.SelectMusicPagerAdapter.music;
import static com.test.test.SelectMusicPagerAdapter.elise;
import static com.test.test.SelectMusicPagerAdapter.nowhere;
import static com.test.test.SelectMusicPagerAdapter.star;
import static com.test.test.SelectMusicPagerAdapter.rabbit;
import static com.test.test.SetPianoOctaveActivity.gunban;

public class GameActivity extends AppCompatActivity {
    public static Panel panel;
    public static ArrayList<ArrayList> answer_right;
    public static ArrayList<ArrayList> answer_left;
    public static RecordAudio record;
    public static int sum_right_count_while; // 오른손 누적 for문 도는 개수
    public static int sum_left_count_while; // 왼손 누적 for문 도는 개수

    private RealDoubleFFT transformer;
    private int blockSize;
    private AudioRecord audioRecord;
    private Canvas background;
    private Paint green_line;
    private ImageView Test;
    private TextView showid;
    private ImageView velocity1;
    private ImageView velocity2;
    private ImageView velocity3;
    private ImageView velocity4;
    private ImageView velocity5;
    private ImageView velocity6;
    private ImageView velocity7;
    private ImageView velocity8;
    private ImageView score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        getSupportActionBar().hide();

        panel = new Panel(this);

        insertProtocol();

        panel.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
        ViewGroup root = (ViewGroup)findViewById(R.id.game);

        root.addView(panel, 2);

        showid = (TextView)findViewById(R.id.id);
        showid.setText(id.getText().toString());

        velocity1 = (ImageView)findViewById(R.id.btn_velocity1);
        velocity2 = (ImageView)findViewById(R.id.btn_velocity2);
        velocity3 = (ImageView)findViewById(R.id.btn_velocity3);
        velocity4 = (ImageView)findViewById(R.id.btn_velocity4);
        velocity5 = (ImageView)findViewById(R.id.btn_velocity5);
        velocity6 = (ImageView)findViewById(R.id.btn_velocity6);
        velocity7 = (ImageView)findViewById(R.id.btn_velocity7);
        velocity8 = (ImageView)findViewById(R.id.btn_velocity8);
        score = (ImageView)findViewById(R.id.score);

        velocity1.setVisibility(View.INVISIBLE);
        velocity2.setVisibility(View.INVISIBLE);
        velocity3.setVisibility(View.INVISIBLE);
        velocity4.setVisibility(View.INVISIBLE);
        velocity5.setVisibility(View.INVISIBLE);
        velocity6.setVisibility(View.INVISIBLE);
        velocity7.setVisibility(View.INVISIBLE);
        velocity8.setVisibility(View.INVISIBLE);
        //score.setVisibility(View.INVISIBLE);

        blockSize = 256;

        y_piano_upleft = y_piano_upleft - 218; // 160pixel = 40dp

        great_score = 0;
        good_score = 0;
        bad_score = 0;

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

    public void insertProtocol() {
        sum_right_count_while = 0;
        sum_left_count_while = 0;
        music_selected = true; // 노래를 선택했다

        switch(music) {
            case elise:
                insertRightProtocol(R.raw.elise_right, panel);
                insertLeftProtocol(R.raw.elise_left, panel);
                answer_right = insertAnswerProtocol(R.raw.elise_right);
                answer_left = insertAnswerProtocol(R.raw.elise_left);
                break;

            case nowhere:
                insertRightProtocol(R.raw.nowhere_right, panel);
                insertLeftProtocol(R.raw.nowhere_left, panel);
                answer_right = insertAnswerProtocol(R.raw.nowhere_right);
                answer_left = insertAnswerProtocol(R.raw.nowhere_left);
                break;

            case star:
                insertRightProtocol(R.raw.star_right, panel);
                insertLeftProtocol(R.raw.star_left, panel);
                answer_right = insertAnswerProtocol(R.raw.star_right);
                answer_left = insertAnswerProtocol(R.raw.star_left);
                break;

            case rabbit:
                insertRightProtocol(R.raw.rabbit_right, panel);
                insertLeftProtocol(R.raw.rabbit_left, panel);
                answer_right = insertAnswerProtocol(R.raw.rabbit_right);
                answer_left = insertAnswerProtocol(R.raw.rabbit_left);
                break;
        }
    }

    public void insertRightProtocol(int music_name, Panel panel) { // 오른손 프로토콜 입력
        String strBuf = ReadTextAssets(music_name);
        String[] lines = strBuf.split("\n");

        for (String note : lines) {
            panel.balls_right.add(new Ball(panel.getContext(), Integer.parseInt(note.trim())));
        }
    }

    public void insertLeftProtocol(int music_name, Panel panel) { // 왼손 프로토콜 입력
        String strBuf = ReadTextAssets(music_name);
        String[] lines = strBuf.split("\n");

        for (String note : lines) {
            panel.balls_left.add(new Ball(panel.getContext(), Integer.parseInt(note.trim())));
        }
    }

    public String ReadTextAssets(int music_name) {
        String text = null;

        try {
            InputStream is = GameActivity.this.getResources().openRawResource(music_name);
            byte[] buffer = new byte[is.available()];

            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    public ArrayList<ArrayList> insertAnswerProtocol(int music_name) {
        String strBuf = ReadTextAssets(music_name);
        String[] lines = strBuf.split("\n");
        ArrayList<ArrayList> answer = new ArrayList<>();

        for(String note : lines) {
            ArrayList<Integer> temp = new ArrayList<>();

            if(Integer.parseInt(note.trim()) % 1000000 >= 100000) { // 흰건반
                switch((Integer.parseInt(note.trim()) / 1000000) % 10) { // 옥타브
                    case 1:
                        switch((Integer.parseInt(note.trim()) / 100000) % 10) {
                            case 1:
                                temp.add(7);temp.add(8);temp.add(15);
                                break;
                            case 2:
                                temp.add(10);temp.add(13);temp.add(14);
                                break;
                            case 3:
                                temp.add(15);temp.add(16);
                                break;
                            case 4:
                                temp.add(21);temp.add(22);
                                break;
                            case 5:
                                temp.add(17);temp.add(44);
                                break;
                            case 6:
                                temp.add(13);temp.add(14);
                                break;
                            case 7:
                                temp.add(15);temp.add(16);
                                break;
                        }
                        break;
                    case 2:
                        switch((Integer.parseInt(note.trim()) / 100000) % 10) {
                            case 1:
                                temp.add(7);temp.add(15);temp.add(16);
                                break;
                            case 2:
                                temp.add(9);temp.add(10);
                                break;
                            case 3:
                                temp.add(21);temp.add(22);
                                break;
                            case 4:
                                temp.add(21);temp.add(22);
                                break;
                            case 5:
                                temp.add(11);temp.add(25);temp.add(26);
                                break;
                            case 6:
                                temp.add(13);temp.add(14);
                                break;
                            case 7:
                                temp.add(16);temp.add(31);
                                break;
                        }
                        break;
                    case 3:
                        switch((Integer.parseInt(note.trim()) / 100000) % 10) {
                            case 1:
                                temp.add(89);
                                break;
                            case 2:
                                temp.add(17);temp.add(18);
                                break;
                            case 3:
                                temp.add(20);temp.add(21);
                                break;
                            case 4:
                                temp.add(21);temp.add(22);
                                break;
                            case 5:
                                temp.add(23);temp.add(25);
                                break;
                            case 6:
                                temp.add(27);temp.add(28);
                                break;
                            case 7:
                                temp.add(31);temp.add(32);
                                break;
                        }
                        break;
                    case 4:
                        switch((Integer.parseInt(note.trim()) / 100000) % 10) {
                            case 1:
                                temp.add(33);temp.add(34);
                                break;
                            case 2:
                                temp.add(37);temp.add(38);
                                break;
                            case 3:
                                temp.add(41);temp.add(42);
                                break;
                            case 4:
                                temp.add(43);temp.add(44);
                                break;
                            case 5:
                                temp.add(49);temp.add(50);
                                break;
                            case 6:
                                temp.add(55);temp.add(56);
                                break;
                            case 7:
                                temp.add(62);temp.add(63);
                                break;
                        }
                        break;
                    case 5:
                        switch((Integer.parseInt(note.trim()) / 100000) % 10) {
                            case 1:
                                temp.add(65);temp.add(67);
                                break;
                            case 2:
                                temp.add(76);
                                break;
                            case 3:
                                temp.add(84);
                                break;
                            case 4:
                                temp.add(90);
                                break;
                            case 5:
                                temp.add(99);
                                break;
                            case 6:
                                temp.add(113);
                                break;
                            case 7:
                                temp.add(126);
                                break;
                        }
                        break;
                }
            } else { // 검은 건반
                switch((Integer.parseInt(note.trim()) / 1000000) % 10) {
                    case 1:
                        switch((Integer.parseInt(note.trim()) / 10000) % 10) {
                            case 1:
                                temp.add(8);temp.add(11);temp.add(14);
                                break;
                            case 2:
                                temp.add(13);temp.add(14);
                                break;
                            case 3:
                                temp.add(17);temp.add(18);
                                break;
                            case 4:
                                temp.add(11);temp.add(13);
                                break;
                            case 5:
                                temp.add(14);temp.add(16);
                                break;
                        }
                        break;
                    case 2:
                        switch((Integer.parseInt(note.trim()) / 10000) % 10) {
                            case 1:
                                temp.add(17);temp.add(18);
                                break;
                            case 2:
                                temp.add(9);temp.add(10);
                                break;
                            case 3:
                                temp.add(11);temp.add(12);
                                break;
                            case 4:
                                temp.add(13);temp.add(14);
                                break;
                            case 5:
                                temp.add(13);
                                break;
                        }
                        break;
                    case 3:
                        switch((Integer.parseInt(note.trim()) / 10000) % 10) {
                            case 1:
                                temp.add(89);
                                break;
                            case 2:
                                temp.add(19);
                                break;
                            case 3:
                                temp.add(23);temp.add(24);
                                break;
                            case 4:
                                temp.add(25);temp.add(26);
                                break;
                            case 5:
                                temp.add(30);temp.add(60);
                                break;
                        }
                        break;
                    case 4:
                        switch((Integer.parseInt(note.trim()) / 10000) % 10) {
                            case 1:
                                temp.add(35);temp.add(36);
                                break;
                            case 2:
                                temp.add(39);temp.add(40);
                                break;
                            case 3:
                                temp.add(45);temp.add(47);
                                break;
                            case 4:
                                temp.add(53);
                                break;
                            case 5:
                                temp.add(59);temp.add(60);
                                break;
                        }
                        break;
                    case 5:
                        switch((Integer.parseInt(note.trim()) / 10000) % 10) {
                            case 1:
                                temp.add(70);temp.add(71);
                                break;
                            case 2:
                                temp.add(79);temp.add(80);
                                break;
                            case 3:
                                temp.add(94);
                                break;
                            case 4:
                                temp.add(105);
                                break;
                            case 5:
                                temp.add(118);
                                break;
                        }
                        break;
                }
            }

            answer.add(temp);
        }

        return answer;
    }

    public void clickPlayOrStop(View v) {
        boolean is_on = ((ToggleButton)v).isChecked();
        ToggleButton play_or_stop = (ToggleButton)findViewById(R.id.btn_play_or_stop);

        if(is_on) {
            play_or_stop.setBackgroundResource(R.drawable.play2);
            velocity1.setVisibility(View.INVISIBLE);
            velocity2.setVisibility(View.INVISIBLE);
            velocity3.setVisibility(View.INVISIBLE);
            velocity4.setVisibility(View.INVISIBLE);
            velocity5.setVisibility(View.INVISIBLE);
            velocity6.setVisibility(View.INVISIBLE);
            velocity7.setVisibility(View.INVISIBLE);
            velocity8.setVisibility(View.INVISIBLE);
            //score.setVisibility(View.INVISIBLE);

            panel.thread = new DrawBall(panel);
            panel.thread.start();
        } else {
            play_or_stop.setBackgroundResource(R.drawable.stop);
            velocity1.setVisibility(View.VISIBLE);
            velocity2.setVisibility(View.VISIBLE);
            velocity3.setVisibility(View.VISIBLE);
            velocity4.setVisibility(View.VISIBLE);
            velocity5.setVisibility(View.VISIBLE);
            velocity6.setVisibility(View.VISIBLE);
            velocity7.setVisibility(View.VISIBLE);
            velocity8.setVisibility(View.VISIBLE);
            //score.setVisibility(View.VISIBLE);

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

        sum_right_count_while = 0;
        sum_left_count_while = 0;

        for(Ball ball : balls_right) {
            if (ball.getCount_while() > 0) {
                ball.setCount_while(ball.getProtocol(), velocity);
            }
        }

        for(Ball ball : balls_left) {
            if (ball.getCount_while() > 0) {
                ball.setCount_while(ball.getProtocol(), velocity);
            }
        }

        panel.invalidate();
    }

    private class RecordAudio extends AsyncTask<Void, double[], Void> {
        ArrayList<Integer> temp_right;
        ArrayList<Integer> temp_left;
        int count_right;
        int count_left;
        //int combo;

        @Override
        protected Void doInBackground(Void... params) {
            temp_right = new ArrayList<>();
            temp_left = new ArrayList<>();
            count_right = 0;
            count_left = 0;
            //combo = 0;

            try {
                short[] buffer = new short[blockSize];
                double[] toTransform = new double[blockSize];

                audioRecord.startRecording();

                while(true) {
                    int bufferReadResult = audioRecord.read(buffer, 0, blockSize);

                    for (int i = 0; i < blockSize && i < bufferReadResult; i++) {
                        toTransform[i] = (double) buffer[i] / Short.MAX_VALUE;
                    }

                    transformer.ft(toTransform);
                    publishProgress(toTransform);

                    float y_last_ball = balls_right.get(balls_right.size() - 1).getY(); // 마지막으로 내려오는 막대의 y좌표

                    if (y_last_ball >= y_piano_upleft + gunban.getWhiteVertical() - 1) { // 마지막 빛 막대가 판정선을 지나가면
                        for (int i = 0; i < balls_right.size(); i++) {
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

                        for (int i = 0; i < balls_left.size(); i++) {
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
            } catch (Throwable t) {
                Log.e("AudioRecord", "Recording Failed");
            }

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

                                if(!balls_right.get(count_right).getTogether()) { // 동시에 치는 음이 아니면
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

                                        //combo++;
                                        //showCombo(combo);
                                    }
                                } else { // 동시에 치는 음이면
                                    for(int j = 0; j < balls_right.get(count_right).getTogether_num(); j++) {
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

                                    for(int j = 0; j < balls_right.get(count_right).getTogether_num(); j++) {
                                        temp_sum += answer_right.get(count_right + j).size();
                                    }

                                    if(init == temp_sum) { // 동시에 친 음이 다 맞는 경우만 판단 가능
                                        for(int j = 0; j < balls_right.get(count_right).getTogether_num(); j++) {
                                            balls_right.get(count_right + j).setCorrect(GREAT);
                                        }

                                        //combo = combo + balls_right.get(count_right).getTogether_num();
                                        //showCombo(combo);
                                    }
                                }
                            }
                        }
                    }
                }

                else if(y_ball >= y_piano_upleft + black_or_white - 30) {
                    if(!balls_right.get(count_right).getTogether()) { // 동시에 치는 음이 아니면
                        count_right++;
                    } else { // 동시에 치는 음이면
                        count_right = count_right + balls_right.get(count_right).getTogether_num();
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

                                if(!balls_left.get(count_left).getTogether()) { // 동시에 치는 음이 아니면
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
                                            //combo++;
                                            //showCombo(combo);

                                        } else if(y_ball >= y_piano_upleft + black_or_white - balls_left.get(count_left).getLength() / 3 - 30 && y_ball < y_piano_upleft + black_or_white - 30) {
                                            balls_left.get(count_left).setCorrect(GOOD);
                                        }
                                    }
                                } else { // 동시에 치는 음이면
                                    for(int j = 0; j < balls_left.get(count_left).getTogether_num(); j++) {
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

                                    for(int j = 0; j < balls_left.get(count_left).getTogether_num(); j++) {
                                        temp_sum += answer_left.get(count_left + j).size();
                                    }

                                    if(init == temp_sum) {
                                        for(int j = 0; j < balls_left.get(count_left).getTogether_num(); j++) {
                                            balls_left.get(count_left + j).setCorrect(GREAT);
                                        }

                                        //combo = combo + balls_left.get(count_left).getTogether_num();
                                        //showCombo(combo);
                                    }
                                }
                            }
                        }
                    }
                } else if(y_ball >= y_piano_upleft + black_or_white - 30) {
                    if(!balls_left.get(count_left).getTogether()) { // 동시에 치는 음이 아니면
                        count_left++;
                    } else { // 동시에 치는 음이면
                        count_left = count_left + balls_left.get(count_left).getTogether_num();
                    }

                    temp_left.clear();
                }
            }
        }
    }

    /*public void showCombo(int combo) {
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
    }*/

    public void moveActivity(View v) {
        panel.balls_right.clear();
        panel.balls_left.clear();
        answer_right.clear();
        answer_left.clear();

        audioRecord.stop();
        record.cancel(true);

        ImageView play = (ImageView)findViewById(R.id.play);
        ImageView music = (ImageView)findViewById(R.id.music);
        ImageView set = (ImageView)findViewById(R.id.set);
        ImageView back = (ImageView)findViewById(R.id.back);
        ImageView home = (ImageView)findViewById(R.id.home);

        switch(v.getId()) {
            case R.id.play:
                play.setBackgroundResource(R.drawable.btn_play_entered);
                startActivity(new Intent(GameActivity.this, GameActivity.class));
                break;
            case R.id.music:
                music.setBackgroundResource(R.drawable.btn_music_entered);
                startActivity(new Intent(GameActivity.this, SelectGenreActivity.class));
                break;
            case R.id.set:
                set.setBackgroundResource(R.drawable.btn_set_entered);
                startActivity(new Intent(GameActivity.this, SetPianoActivity.class));
                break;
            case R.id.back:
                back.setBackgroundResource(R.drawable.btn_back_entered);
                startActivity(new Intent(GameActivity.this, SelectMusicActivity.class));
                break;
            case R.id.home:
                home.setBackgroundResource(R.drawable.btn_home_entered);
                startActivity(new Intent(GameActivity.this, MenuActivity.class));
                break;
            case R.id.score:
                score.setBackgroundResource(R.drawable.score2_entered);
                startActivity(new Intent(GameActivity.this, ScoreActivity.class));
                break;
        }

        ActivityCompat.finishAffinity(this);
        y_piano_upleft = y_piano_upleft + 218; // 160pixel = 40dp
    }

    public void onBackPressed() {
        panel.balls_right.clear();
        panel.balls_left.clear();
        answer_right.clear();
        answer_left.clear();

        audioRecord.stop();
        record.cancel(true);

        startActivity(new Intent(GameActivity.this, SelectMusicActivity.class));
        ActivityCompat.finishAffinity(this);

        y_piano_upleft = y_piano_upleft + 218; // 160pixel = 40dp
    }
}