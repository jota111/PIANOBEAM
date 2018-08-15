package com.test.test;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static com.test.test.SelectMusicPagerAdapter.music;
import static com.test.test.SelectMusicPagerAdapter.elise;
import static com.test.test.LoginActivity.music_selected;
import static com.test.test.SelectMusicPagerAdapter.nowhere;
import static com.test.test.SelectMusicPagerAdapter.star;

public class SelectMusicActivity extends AppCompatActivity {
    public static Panel panel;
    public static ArrayList<ArrayList> answer_right;
    public static ArrayList<ArrayList> answer_left;

    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_music);
        getSupportActionBar().hide();

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        sliderDotspanel = (LinearLayout)findViewById(R.id.SliderDots);
        SelectMusicPagerAdapter viewPagerAdapter = new SelectMusicPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
        dotscount = viewPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for(int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        switch(music) {
            case elise:
                panel = new Panel(this);
                insertRightProtocol(R.raw.test_right, panel);
                insertLeftProtocol(R.raw.test_left, panel);
                answer_right = answerProtocol(R.raw.test_right);
                answer_left = answerProtocol(R.raw.test_left);
                music_selected = true; // 노래를 선택했다
                break;
            case nowhere:
                break;
            case star:
                panel = new Panel(this);
                insertLeftProtocol(R.raw.test_right, panel);
                answerProtocol(R.raw.test_right);
                music_selected = true; // 노래를 선택했다
                break;
        }
    }

    public void moveActivity(View v) {
        ImageView play = (ImageView)findViewById(R.id.play);
        ImageView music = (ImageView)findViewById(R.id.music);
        ImageView set = (ImageView)findViewById(R.id.set);
        ImageView back = (ImageView)findViewById(R.id.back);
        ImageView home = (ImageView)findViewById(R.id.home);

        switch(v.getId()) {
            case R.id.play:
                play.setBackgroundResource(R.drawable.btn_play_entered);

                if(music_selected) {
                    startActivity(new Intent(SelectMusicActivity.this, GameActivity.class));
                } else {
                    startActivity(new Intent(SelectMusicActivity.this, SelectGenreActivity.class));
                }
                break;
            case R.id.music:
                music.setBackgroundResource(R.drawable.btn_music_entered);
                startActivity(new Intent(SelectMusicActivity.this, SelectGenreActivity.class));
                break;
            case R.id.set:
                set.setBackgroundResource(R.drawable.btn_set_entered);
                startActivity(new Intent(SelectMusicActivity.this, SetPianoActivity.class));
                break;
            case R.id.back:
                back.setBackgroundResource(R.drawable.btn_back_entered);
                startActivity(new Intent(SelectMusicActivity.this, SelectGenreActivity.class));
                break;
            case R.id.home:
                home.setBackgroundResource(R.drawable.btn_home_entered);
                startActivity(new Intent(SelectMusicActivity.this, MenuActivity.class));
                break;
        }

        finish();
    }
    public void onBackPressed() {
        startActivity(new Intent(SelectMusicActivity.this, SelectGenreActivity.class));
        SelectMusicActivity.this.finishAffinity();
    }

    public void insertRightProtocol(int music_name, Panel panel) { // 프로토콜 입력
        String strBuf = ReadTextAssets(music_name);
        String[] lines = strBuf.split("\n");

        for (String note : lines) {
            panel.balls_right.add(new Ball(panel.getContext(), Integer.parseInt(note.trim())));
        }
    }

    public void insertLeftProtocol(int music_name, Panel panel) { // 프로토콜 입력
        String strBuf = ReadTextAssets(music_name);
        String[] lines = strBuf.split("\n");

        for (String note : lines) {
            panel.balls_left.add(new Ball(panel.getContext(), Integer.parseInt(note.trim())));
        }
    }

    public String ReadTextAssets(int music_name) {
        String text = null;

        try {
            InputStream is = SelectMusicActivity.this.getResources().openRawResource(music_name);
            byte[] buffer = new byte[is.available()];

            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return text;
    }

    public ArrayList<ArrayList> answerProtocol(int music_name) {
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
}