package com.test.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import static com.test.test.DrawGunban.y_piano_upleft;
import static com.test.test.LoginActivity.id;

public class ScoreActivity extends AppCompatActivity {
    public static final int GREAT = 100;
    public static final int GOOD = 200;
    public static final int BAD = 300;
    public static int great_score;
    public static int good_score;
    public static int bad_score;

    private TextView showid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.score);
        getSupportActionBar().hide();

        showid = (TextView)findViewById(R.id.id);
        showid.setText(id.getText().toString());

        int great_score_hun = great_score / 100;
        int great_score_ten = (great_score / 10) % 10;
        int great_score_one = great_score % 10;

        int good_score_hun = good_score / 100;
        int good_score_ten = (good_score / 10) % 10;
        int good_score_one = good_score % 10;

        int bad_score_hun = bad_score / 100;
        int bad_score_ten = (bad_score / 10) % 10;
        int bad_score_one = bad_score % 10;

        float record_score = (((great_score * 2) + good_score) - bad_score * 2) * (100 / (float)(great_score + good_score + bad_score));
        int record_score_hun = (((int)record_score + 200) / 4) / 100;
        int record_score_ten = ((((int)record_score + 200) / 4) / 10) % 10;
        int record_score_one = (((int)record_score + 200) / 4) % 10;

        ImageView score = (ImageView)findViewById(R.id.score_font);
        score.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));

        ImageView rank = (ImageView)findViewById(R.id.rank);
        rank.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));

        ImageView great = (ImageView)findViewById(R.id.great);
        great.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView greatnum1 = (ImageView)findViewById(R.id.greatnum1);
        greatnum1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView greatnum2 = (ImageView)findViewById(R.id.greatnum2);
        greatnum2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView greatnum3 = (ImageView)findViewById(R.id.greatnum3);
        greatnum3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));

        ImageView good = (ImageView)findViewById(R.id.good);
        good.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView goodnum1 = (ImageView)findViewById(R.id.goodnum1);
        goodnum1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView goodnum2 = (ImageView)findViewById(R.id.goodnum2);
        goodnum2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView goodnum3 = (ImageView)findViewById(R.id.goodnum3);
        goodnum3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));

        ImageView bad = (ImageView)findViewById(R.id.bad);
        bad.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView badnum1 = (ImageView)findViewById(R.id.badnum1);
        badnum1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView badnum2 = (ImageView)findViewById(R.id.badnum2);
        badnum2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView badnum3 = (ImageView)findViewById(R.id.badnum3);
        badnum3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));

        ImageView record = (ImageView)findViewById(R.id.record);
        record.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView recordnum1 = (ImageView)findViewById(R.id.recordnum1);
        recordnum1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView recordnum2 = (ImageView)findViewById(R.id.recordnum2);
        recordnum2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));
        ImageView recordnum3 = (ImageView)findViewById(R.id.record_num3);
        recordnum3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.fadein3));

        switch(great_score_hun) {
            case 0:
                greatnum1.setImageResource(R.drawable.green0);
                break;
            case 1:
                greatnum1.setImageResource(R.drawable.green1);
                break;
            case 2:
                greatnum1.setImageResource(R.drawable.green2);
                break;
            case 3:
                greatnum1.setImageResource(R.drawable.green3);
                break;
            case 4:
                greatnum1.setImageResource(R.drawable.green4);
                break;
            case 5:
                greatnum1.setImageResource(R.drawable.green5);
                break;
            case 6:
                greatnum1.setImageResource(R.drawable.green6);
                break;
            case 7:
                greatnum1.setImageResource(R.drawable.green7);
                break;
            case 8:
                greatnum1.setImageResource(R.drawable.green8);
                break;
            case 9:
                greatnum1.setImageResource(R.drawable.green9);
                break;
            default:
        }

        switch(great_score_ten) {
            case 0:
                greatnum2.setImageResource(R.drawable.green0);
                break;
            case 1:
                greatnum2.setImageResource(R.drawable.green1);
                break;
            case 2:
                greatnum2.setImageResource(R.drawable.green2);
                break;
            case 3:
                greatnum2.setImageResource(R.drawable.green3);
                break;
            case 4:
                greatnum2.setImageResource(R.drawable.green4);
                break;
            case 5:
                greatnum2.setImageResource(R.drawable.green5);
                break;
            case 6:
                greatnum2.setImageResource(R.drawable.green6);
                break;
            case 7:
                greatnum2.setImageResource(R.drawable.green7);
                break;
            case 8:
                greatnum2.setImageResource(R.drawable.green8);
                break;
            case 9:
                greatnum2.setImageResource(R.drawable.green9);
                break;
            default:
        }

        switch(great_score_one) {
            case 0:
                greatnum3.setImageResource(R.drawable.green0);
                break;
            case 1:
                greatnum3.setImageResource(R.drawable.green1);
                break;
            case 2:
                greatnum3.setImageResource(R.drawable.green2);
                break;
            case 3:
                greatnum3.setImageResource(R.drawable.green3);
                break;
            case 4:
                greatnum3.setImageResource(R.drawable.green4);
                break;
            case 5:
                greatnum3.setImageResource(R.drawable.green5);
                break;
            case 6:
                greatnum3.setImageResource(R.drawable.green6);
                break;
            case 7:
                greatnum3.setImageResource(R.drawable.green7);
                break;
            case 8:
                greatnum3.setImageResource(R.drawable.green8);
                break;
            case 9:
                greatnum3.setImageResource(R.drawable.green9);
                break;
            default:
        }

        switch(good_score_hun) {
            case 0:
                goodnum1.setImageResource(R.drawable.blue0);
                break;
            case 1:
                goodnum1.setImageResource(R.drawable.blue1);
                break;
            case 2:
                goodnum1.setImageResource(R.drawable.blue2);
                break;
            case 3:
                goodnum1.setImageResource(R.drawable.blue3);
                break;
            case 4:
                goodnum1.setImageResource(R.drawable.blue4);
                break;
            case 5:
                goodnum1.setImageResource(R.drawable.blue5);
                break;
            case 6:
                goodnum1.setImageResource(R.drawable.blue6);
                break;
            case 7:
                goodnum1.setImageResource(R.drawable.blue7);
                break;
            case 8:
                goodnum1.setImageResource(R.drawable.blue8);
                break;
            case 9:
                goodnum1.setImageResource(R.drawable.blue9);
                break;
            default:
        }

        switch(good_score_ten) {
            case 0:
                goodnum2.setImageResource(R.drawable.blue0);
                break;
            case 1:
                goodnum2.setImageResource(R.drawable.blue1);
                break;
            case 2:
                goodnum2.setImageResource(R.drawable.blue2);
                break;
            case 3:
                goodnum2.setImageResource(R.drawable.blue3);
                break;
            case 4:
                goodnum2.setImageResource(R.drawable.blue4);
                break;
            case 5:
                goodnum2.setImageResource(R.drawable.blue5);
                break;
            case 6:
                goodnum2.setImageResource(R.drawable.blue6);
                break;
            case 7:
                goodnum2.setImageResource(R.drawable.blue7);
                break;
            case 8:
                goodnum2.setImageResource(R.drawable.blue8);
                break;
            case 9:
                goodnum2.setImageResource(R.drawable.blue9);
                break;
            default:
        }

        switch(good_score_one) {
            case 0:
                goodnum3.setImageResource(R.drawable.blue0);
                break;
            case 1:
                goodnum3.setImageResource(R.drawable.blue1);
                break;
            case 2:
                goodnum3.setImageResource(R.drawable.blue2);
                break;
            case 3:
                goodnum3.setImageResource(R.drawable.blue3);
                break;
            case 4:
                goodnum3.setImageResource(R.drawable.blue4);
                break;
            case 5:
                goodnum3.setImageResource(R.drawable.blue5);
                break;
            case 6:
                goodnum3.setImageResource(R.drawable.blue6);
                break;
            case 7:
                goodnum3.setImageResource(R.drawable.blue7);
                break;
            case 8:
                goodnum3.setImageResource(R.drawable.blue8);
                break;
            case 9:
                goodnum3.setImageResource(R.drawable.blue9);
                break;
            default:
        }

        switch(bad_score_hun) {
            case 0:
                badnum1.setImageResource(R.drawable.red0);
                break;
            case 1:
                badnum1.setImageResource(R.drawable.red1);
                break;
            case 2:
                badnum1.setImageResource(R.drawable.red2);
                break;
            case 3:
                badnum1.setImageResource(R.drawable.red3);
                break;
            case 4:
                badnum1.setImageResource(R.drawable.red4);
                break;
            case 5:
                badnum1.setImageResource(R.drawable.red5);
                break;
            case 6:
                badnum1.setImageResource(R.drawable.red6);
                break;
            case 7:
                badnum1.setImageResource(R.drawable.red7);
                break;
            case 8:
                badnum1.setImageResource(R.drawable.red8);
                break;
            case 9:
                badnum1.setImageResource(R.drawable.red9);
                break;
            default:
        }

        switch(bad_score_ten) {
            case 0:
                badnum2.setImageResource(R.drawable.red0);
                break;
            case 1:
                badnum2.setImageResource(R.drawable.red1);
                break;
            case 2:
                badnum2.setImageResource(R.drawable.red2);
                break;
            case 3:
                badnum2.setImageResource(R.drawable.red3);
                break;
            case 4:
                badnum2.setImageResource(R.drawable.red4);
                break;
            case 5:
                badnum2.setImageResource(R.drawable.red5);
                break;
            case 6:
                badnum2.setImageResource(R.drawable.red6);
                break;
            case 7:
                badnum2.setImageResource(R.drawable.red7);
                break;
            case 8:
                badnum2.setImageResource(R.drawable.red8);
                break;
            case 9:
                badnum2.setImageResource(R.drawable.red9);
                break;
            default:
        }

        switch(bad_score_one) {
            case 0:
                badnum3.setImageResource(R.drawable.red0);
                break;
            case 1:
                badnum3.setImageResource(R.drawable.red1);
                break;
            case 2:
                badnum3.setImageResource(R.drawable.red2);
                break;
            case 3:
                badnum3.setImageResource(R.drawable.red3);
                break;
            case 4:
                badnum3.setImageResource(R.drawable.red4);
                break;
            case 5:
                badnum3.setImageResource(R.drawable.red5);
                break;
            case 6:
                badnum3.setImageResource(R.drawable.red6);
                break;
            case 7:
                badnum3.setImageResource(R.drawable.red7);
                break;
            case 8:
                badnum3.setImageResource(R.drawable.red8);
                break;
            case 9:
                badnum3.setImageResource(R.drawable.red9);
                break;
            default:
        }

        switch(record_score_hun) {
            case 0:
                recordnum1.setImageResource(R.drawable.yellow0);
                break;
            case 1:
                recordnum1.setImageResource(R.drawable.yellow1);
                break;
            case 2:
                recordnum1.setImageResource(R.drawable.yellow2);
                break;
            case 3:
                recordnum1.setImageResource(R.drawable.yellow3);
                break;
            case 4:
                recordnum1.setImageResource(R.drawable.yellow4);
                break;
            case 5:
                recordnum1.setImageResource(R.drawable.yellow5);
                break;
            case 6:
                recordnum1.setImageResource(R.drawable.yellow6);
                break;
            case 7:
                recordnum1.setImageResource(R.drawable.yellow7);
                break;
            case 8:
                recordnum1.setImageResource(R.drawable.yellow8);
                break;
            case 9:
                recordnum1.setImageResource(R.drawable.yellow9);
                break;
            default:
        }

        switch(record_score_ten) {
            case 0:
                recordnum2.setImageResource(R.drawable.yellow0);
                break;
            case 1:
                recordnum2.setImageResource(R.drawable.yellow1);
                break;
            case 2:
                recordnum2.setImageResource(R.drawable.yellow2);
                break;
            case 3:
                recordnum2.setImageResource(R.drawable.yellow3);
                break;
            case 4:
                recordnum2.setImageResource(R.drawable.yellow4);
                break;
            case 5:
                recordnum2.setImageResource(R.drawable.yellow5);
                break;
            case 6:
                recordnum2.setImageResource(R.drawable.yellow6);
                break;
            case 7:
                recordnum2.setImageResource(R.drawable.yellow7);
                break;
            case 8:
                recordnum2.setImageResource(R.drawable.yellow8);
                break;
            case 9:
                recordnum2.setImageResource(R.drawable.yellow9);
                break;
            default:
        }

        switch(record_score_one) {
            case 0:
                recordnum3.setImageResource(R.drawable.yellow0);
                break;
            case 1:
                recordnum3.setImageResource(R.drawable.yellow1);
                break;
            case 2:
                recordnum3.setImageResource(R.drawable.yellow2);
                break;
            case 3:
                recordnum3.setImageResource(R.drawable.yellow3);
                break;
            case 4:
                recordnum3.setImageResource(R.drawable.yellow4);
                break;
            case 5:
                recordnum3.setImageResource(R.drawable.yellow5);
                break;
            case 6:
                recordnum3.setImageResource(R.drawable.yellow6);
                break;
            case 7:
                recordnum3.setImageResource(R.drawable.yellow7);
                break;
            case 8:
                recordnum3.setImageResource(R.drawable.yellow8);
                break;
            case 9:
                recordnum3.setImageResource(R.drawable.yellow9);
                break;
            default:
        }

        if(record_score > 150) {
            rank.setImageResource(R.drawable.rank_s);
        } else if(record_score <= 150 && record_score > 100) {
            rank.setImageResource(R.drawable.rank_a);
        } else if(record_score <= 100 && record_score > 50) {
            rank.setImageResource(R.drawable.rank_b);
        } else if(record_score <= 50 && record_score > 0) {
            rank.setImageResource(R.drawable.rank_c);
        } else {
            rank.setImageResource(R.drawable.rank_d);
        }
    }

    public void moveActivity(View v) {
        ImageView play = (ImageView)findViewById(R.id.play);
        ImageView music = (ImageView)findViewById(R.id.music);
        ImageView set = (ImageView)findViewById(R.id.set);
        ImageView back = (ImageView)findViewById(R.id.back);
        ImageView home = (ImageView)findViewById(R.id.home);
        ImageView replay = (ImageView)findViewById(R.id.replay);
        ImageView home2 = (ImageView)findViewById(R.id.home2);

        switch(v.getId()) {
            case R.id.play:
                play.setBackgroundResource(R.drawable.btn_play_entered);
                startActivity(new Intent(ScoreActivity.this, GameActivity.class));
                break;
            case R.id.music:
                music.setBackgroundResource(R.drawable.btn_music_entered);
                startActivity(new Intent(ScoreActivity.this, SelectGenreActivity.class));
                break;
            case R.id.set:
                set.setBackgroundResource(R.drawable.btn_set_entered);
                startActivity(new Intent(ScoreActivity.this, SetPianoActivity.class));
                break;
            case R.id.back:
                back.setBackgroundResource(R.drawable.btn_back_entered);
                startActivity(new Intent(ScoreActivity.this, GameActivity.class));
                break;
            case R.id.home:
                home.setBackgroundResource(R.drawable.btn_home_entered);
                startActivity(new Intent(ScoreActivity.this, MenuActivity.class));
                break;
            case R.id.replay:
                replay.setBackgroundResource(R.drawable.replay_entered);
                startActivity(new Intent(ScoreActivity.this, GameActivity.class));
                break;
            case R.id.home2:
                home2.setBackgroundResource(R.drawable.home_entered);
                startActivity(new Intent(ScoreActivity.this, MenuActivity.class));
                break;
        }

        ActivityCompat.finishAffinity(this);
    }

    public void onBackPressed() {
        startActivity(new Intent(ScoreActivity.this, GameActivity.class));
        ActivityCompat.finishAffinity(this);
    }
}