package com.test.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import static com.test.test.LoginActivity.music_selected;

public class QuestionActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        getSupportActionBar().hide();
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
                    startActivity(new Intent(QuestionActivity.this, GameActivity.class));
                } else {
                    startActivity(new Intent(QuestionActivity.this, SelectGenreActivity.class));
                }
                break;
            case R.id.music:
                music.setBackgroundResource(R.drawable.btn_music_entered);
                startActivity(new Intent(QuestionActivity.this, SelectGenreActivity.class));
                break;
            case R.id.set:
                set.setBackgroundResource(R.drawable.btn_set_entered);
                startActivity(new Intent(QuestionActivity.this, SetPianoActivity.class));
                break;
            case R.id.back:
                back.setBackgroundResource(R.drawable.btn_back_entered);
                startActivity(new Intent(QuestionActivity.this, MenuActivity.class));
                break;
            case R.id.home:
                home.setBackgroundResource(R.drawable.btn_home_entered);
                startActivity(new Intent(QuestionActivity.this, MenuActivity.class));
                break;
        }

        ActivityCompat.finishAffinity(this);
    }

    public void onBackPressed() {
        startActivity(new Intent(QuestionActivity.this, MenuActivity.class));
        ActivityCompat.finishAffinity(this);
    }
}