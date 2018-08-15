package com.test.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import static com.test.test.LoginActivity.music_selected;
import static com.test.test.LoginActivity.to_gameActivity;

public class MenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        getSupportActionBar().hide();

        to_gameActivity = false;
    }

    public void moveActivity(View v) {
        ImageView music = (ImageView)findViewById(R.id.music);
        ImageView set = (ImageView)findViewById(R.id.set);
        ImageView play = (ImageView)findViewById(R.id.play);
        ImageView faq = (ImageView)findViewById(R.id.faq);
        ImageView howtoplay = (ImageView)findViewById(R.id.howtoplay);
        ImageView back = (ImageView)findViewById(R.id.back);

        switch(v.getId()) {
            case R.id.music:
                music.setBackgroundResource(R.drawable.btn_start_music_entered);
                startActivity(new Intent(MenuActivity.this, SelectGenreActivity.class));
                break;
            case R.id.set:
                set.setBackgroundResource(R.drawable.btn_start_set_entered);
                startActivity(new Intent(MenuActivity.this, SetPianoActivity.class));
                break;
            case R.id.play:
                play.setBackgroundResource(R.drawable.btn_start_play_entered);

                if(music_selected) {
                    startActivity(new Intent(MenuActivity.this, GameActivity.class));
                } else {
                    to_gameActivity = true;
                    startActivity(new Intent(MenuActivity.this, SelectGenreActivity.class));
                }
                break;
            case R.id.faq:
                faq.setBackgroundResource(R.drawable.btn_start_faq_entered);
                startActivity(new Intent(MenuActivity.this, QuestionActivity.class));
                break;
            case R.id.howtoplay:
                howtoplay.setBackgroundResource(R.drawable.btn_start_howtoplay_entered);
                startActivity(new Intent(MenuActivity.this, HowToPlayActivity.class));
                break;
            case R.id.back:
                back.setBackgroundResource(R.drawable.btn_back_entered);
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                break;
        }
    }

    public void onBackPressed() {
        startActivity(new Intent(MenuActivity.this, LoginActivity.class));
        finish();
    }
}