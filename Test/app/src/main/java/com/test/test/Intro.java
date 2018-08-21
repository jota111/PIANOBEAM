package com.test.test;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class Intro extends AppCompatActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro);
        getSupportActionBar().hide();
        setDisplay();

        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(Intro.this, "권한 허가", Toast.LENGTH_SHORT).show();
                Handler handler = new Handler();

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(Intro.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, 1200);
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                Toast.makeText(Intro.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                ActivityCompat.finishAffinity(Intro.this);
                System.runFinalization();
                System.exit(0);
            }
        };

        TedPermission.with(this).setPermissionListener(permissionlistener).setRationaleMessage("음 인식을 위해 마이크 권한이 필요합니다.").setDeniedMessage("권한이 있어야 앱이 정상적으로 작동합니다.").setPermissions(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE).check();
    }

    public void setDisplay() {
        getWindowManager().getDefaultDisplay().getMetrics(new DisplayMetrics());
        int android_x_size = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getWidth();
        int android_y_size = ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getHeight();
        ImageView iv_intro = (ImageView)findViewById(R.id.intro);

        if (android_x_size == 1280 && android_y_size == 720) {
            iv_intro.setBackgroundResource(R.drawable.intro_720);
        } else if (android_x_size == 1920 && android_y_size == 1080) {
            iv_intro.setBackgroundResource(R.drawable.intro_1080);
        } else if (android_x_size == 2560 && android_y_size == 1440) {
            iv_intro.setBackgroundResource(R.drawable.intro_720); // 내 폰만 이럼
            //iv_intro.setBackgroundResource(R.drawable.intro_1440);
        }
    }
}