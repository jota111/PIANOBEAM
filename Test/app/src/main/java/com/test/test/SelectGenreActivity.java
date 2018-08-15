package com.test.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import static com.test.test.LoginActivity.music_selected;
import static com.test.test.LoginActivity.to_gameActivity;

public class SelectGenreActivity extends AppCompatActivity {
    public static final int classic_selected = 0; // 클래식 선택
    public static final int kpop_selected = 1; // 가요 선택
    public static final int popsong_selected = 2; // 팝송 선택
    public static final int ost_selected = 3; // OST 선택
    public static final int childrensong_selected = 4; // 동요 선택
    public static int genre;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_genre);
        getSupportActionBar().hide();

        if(!music_selected && to_gameActivity) { // 노래가 아직 선택되지 않았고, 게임 시작을 눌렀을 경우
            AlertDialog.Builder dlg = new AlertDialog.Builder(SelectGenreActivity.this);
            dlg.setMessage("게임을 시작하려면 먼저 노래를 선택해주세요.");
            dlg.setPositiveButton("확인", null);
            dlg.show();
        }
    }

    public void selectGenre(View v) {
        ImageView classic = (ImageView)findViewById(R.id.classic);
        ImageView kpop = (ImageView)findViewById(R.id.kpop);
        ImageView popsong = (ImageView)findViewById(R.id.popsong);
        ImageView ost = (ImageView)findViewById(R.id.ost);
        ImageView childrensong = (ImageView)findViewById(R.id.childrensong);
        ImageView camera = (ImageView)findViewById(R.id.camera);

        switch(v.getId()) {
            case R.id.classic:
                classic.setBackgroundResource(R.drawable.btn_classic_entered);
                genre = classic_selected;
                startActivity(new Intent(SelectGenreActivity.this, SelectMusicActivity.class));
                break;
            case R.id.kpop:
                kpop.setBackgroundResource(R.drawable.btn_kpop_entered);
                genre = kpop_selected;
                startActivity(new Intent(SelectGenreActivity.this, SelectMusicActivity.class));
                break;
            case R.id.popsong:
                popsong.setBackgroundResource(R.drawable.btn_popsong_entered);
                genre = popsong_selected;
                startActivity(new Intent(SelectGenreActivity.this, SelectMusicActivity.class));
                break;
            case R.id.ost:
                ost.setBackgroundResource(R.drawable.btn_ost_entered);
                genre = ost_selected;
                startActivity(new Intent(SelectGenreActivity.this, SelectMusicActivity.class));
                break;
            case R.id.childrensong:
                childrensong.setBackgroundResource(R.drawable.btn_childrensong_entered);
                genre = childrensong_selected;
                startActivity(new Intent(SelectGenreActivity.this, SelectMusicActivity.class));
                break;
            case R.id.camera:
                camera.setBackgroundResource(R.drawable.btn_camera_entered);
                startActivity(new Intent(SelectGenreActivity.this, CameraActivity.class));
                break;
        }

        finish();
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
                    startActivity(new Intent(SelectGenreActivity.this, GameActivity.class));
                } else {
                    startActivity(new Intent(SelectGenreActivity.this, SelectGenreActivity.class));
                }
                break;
            case R.id.music:
                music.setBackgroundResource(R.drawable.btn_music_entered);
                startActivity(new Intent(SelectGenreActivity.this, SelectGenreActivity.class));
                break;
            case R.id.set:
                set.setBackgroundResource(R.drawable.btn_set_entered);
                startActivity(new Intent(SelectGenreActivity.this, SetPianoActivity.class));
                break;
            case R.id.back:
                back.setBackgroundResource(R.drawable.btn_back_entered);
                startActivity(new Intent(SelectGenreActivity.this, MenuActivity.class));
                break;
            case R.id.home:
                home.setBackgroundResource(R.drawable.btn_home_entered);
                startActivity(new Intent(SelectGenreActivity.this, MenuActivity.class));
                break;
        }

        finish();
    }

    public void onBackPressed() {
        startActivity(new Intent(SelectGenreActivity.this, MenuActivity.class));
        SelectGenreActivity.this.finishAffinity();
    }
}