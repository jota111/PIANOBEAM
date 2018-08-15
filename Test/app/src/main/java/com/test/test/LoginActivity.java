package com.test.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import static com.test.test.DrawGunban.x_piano_upleft;
import static com.test.test.DrawGunban.y_piano_upleft;
import static com.test.test.Gunban.black_vertical;
import static com.test.test.Gunban.count_size_change;
import static com.test.test.Gunban.white_vertical;
import static com.test.test.SetPianoOctaveActivity.gunban;

public class LoginActivity extends AppCompatActivity {
    private static final long DURATION_TIME = 2000L;
    private long prevPressTime = 0L;
    private DatabaseReference databaseReference;
    public static DatabaseReference logindatabaseReference;

    public static boolean music_selected;
    public static boolean to_gameActivity;

    private EditText Id;
    private ImageView login;
    private ImageView join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getSupportActionBar().hide();

        music_selected = false; // 노래가 아직 선택되지 않음
        to_gameActivity = false; // 게임 화면으로 이동하지 않음

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        Id = (EditText)findViewById(R.id.Id);

        login = (ImageView)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

                        while(child.hasNext()) {
                            if(child.next().getKey().equals(Id.getText().toString())) {
                                login.setBackgroundResource(R.drawable.login_entered);

                                Toast.makeText(getApplicationContext(), Id.getText().toString() + "님" + " 환영합니다!", Toast.LENGTH_LONG).show();
                                logindatabaseReference = FirebaseDatabase.getInstance().getReference("users").child(Id.getText().toString());
                                startActivity(new Intent(LoginActivity.this, MenuActivity.class));

                                logindatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        gunban.setOctave(Integer.parseInt(dataSnapshot.child("gunban_octave").getValue().toString()));
                                        x_piano_upleft=Float.parseFloat(dataSnapshot.child("x_piano_upleft").getValue().toString());
                                        y_piano_upleft=Float.parseFloat(dataSnapshot.child("y_piano_upleft").getValue().toString());
                                        black_vertical=Float.parseFloat(dataSnapshot.child("black_vertical").getValue().toString());
                                        white_vertical=Float.parseFloat(dataSnapshot.child("white_vertical").getValue().toString());
                                        count_size_change=Float.parseFloat(dataSnapshot.child("count_size_change").getValue().toString());
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });

                                return;
                            }
                        }

                        Toast.makeText(getApplicationContext(),"존재하지 않는 아이디입니다.",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        join = (ImageView)findViewById(R.id.join);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join.setBackgroundResource(R.drawable.join_entered);
                startActivity(new Intent(getApplicationContext(),JoinActivity.class));
                finish();
            }
        });
    }

    public void moveActivity(View v) {
        ImageView guestplay = (ImageView)findViewById(R.id.guestplay);

        switch(v.getId()) {
            case R.id.guestplay:
                guestplay.setBackgroundResource(R.drawable.guestplay_entered);
                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                break;
        }
    }

    public void onBackPressed() {
        if(System.currentTimeMillis() - prevPressTime <= DURATION_TIME) {
            android.os.Process.killProcess(android.os.Process.myPid());
        } else {
            prevPressTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로가기 버튼을 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }
}