package com.test.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import static com.test.test.LoginActivity.music_selected;
import static com.test.test.LoginActivity.id;

public class SetPianoActivity extends AppCompatActivity implements SetPianoOctaveActivity.OnFragmentInteractionListener, SetPianoPositionActivity.OnFragmentInteractionListener, SetPianoVerticalActivity.OnFragmentInteractionListener, SetPianoHorizontalActivity.OnFragmentInteractionListener{
    private TextView showid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_piano);
        getSupportActionBar().hide();

        showid = (TextView)findViewById(R.id.id);
        showid.setText(id.getText().toString());

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("옥타브 설정"));
        tabLayout.addTab(tabLayout.newTab().setText("위치 지정"));
        tabLayout.addTab(tabLayout.newTab().setText("가로 길이 설정"));
        tabLayout.addTab(tabLayout.newTab().setText("세로 길이 설정"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setTabTextColors(0xffada9a3, 0xfffffaf7);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final SetPianoPagerAdapter setPianoPagerAdapter = new SetPianoPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(setPianoPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
                    startActivity(new Intent(SetPianoActivity.this, GameActivity.class));
                } else {
                    startActivity(new Intent(SetPianoActivity.this, SelectGenreActivity.class));
                }
                break;
            case R.id.music:
                music.setBackgroundResource(R.drawable.btn_music_entered);
                startActivity(new Intent(SetPianoActivity.this, SelectGenreActivity.class));
                break;
            case R.id.set:
                set.setBackgroundResource(R.drawable.btn_set_entered);
                startActivity(new Intent(SetPianoActivity.this, SetPianoActivity.class));
                break;
            case R.id.back:
                back.setBackgroundResource(R.drawable.btn_back_entered);
                startActivity(new Intent(SetPianoActivity.this, MenuActivity.class));
                break;
            case R.id.home:
                home.setBackgroundResource(R.drawable.btn_home_entered);
                startActivity(new Intent(SetPianoActivity.this, MenuActivity.class));
                break;
        }

        ActivityCompat.finishAffinity(this);
    }

    public void onBackPressed() {
        startActivity(new Intent(SetPianoActivity.this, MenuActivity.class));
        ActivityCompat.finishAffinity(this);
    }
}