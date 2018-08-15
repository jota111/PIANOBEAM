package com.test.test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Date;
import java.util.Iterator;

import static com.test.test.Gunban.count_size_change;
import static com.test.test.SetPianoOctaveActivity.gunban;
import static com.test.test.Gunban.white_vertical;
import static com.test.test.Gunban.black_vertical;
import static com.test.test.DrawGunban.x_piano_upleft;
import static com.test.test.DrawGunban.y_piano_upleft;

public class JoinActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private EditText ID;
    private EditText Password;
    private EditText Name;
    private ImageView join;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        getSupportActionBar().hide();

        databaseReference  = FirebaseDatabase.getInstance().getReference("users");

        ID = (EditText)findViewById(R.id.id);
        Name = (EditText)findViewById(R.id.name);
        Password = (EditText)findViewById(R.id.password);

        join = (ImageView)findViewById(R.id.join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                join.setBackgroundResource(R.drawable.join_entered);
                databaseReference.addListenerForSingleValueEvent(checkRegister);
            }
        });
    }

    private ValueEventListener checkRegister = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();

            while(child.hasNext()) {
                if(ID.getText().toString().equals(child.next().getKey())) {
                    Toast.makeText(getApplicationContext(), "존재하는 아이디 입니다.", Toast.LENGTH_LONG).show();
                    databaseReference.removeEventListener(this);

                    return;
                }
            }

            makeNewId();
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    void makeNewId() {
        Date date = new Date(System.currentTimeMillis());

        databaseReference.child(ID.getText().toString()).child("가입일").setValue(date.toString());
        databaseReference.child(ID.getText().toString()).child("비밀번호").setValue(Password.getText().toString());
        databaseReference.child(ID.getText().toString()).child("이름").setValue(Name.getText().toString());
        databaseReference.child(ID.getText().toString()).child("x_piano_upleft").setValue(x_piano_upleft);
        databaseReference.child(ID.getText().toString()).child("y_piano_upleft").setValue(y_piano_upleft);
        databaseReference.child(ID.getText().toString()).child("black_vertical").setValue(black_vertical);
        databaseReference.child(ID.getText().toString()).child("white_vertical").setValue(white_vertical);
        databaseReference.child(ID.getText().toString()).child("count_size_change").setValue(count_size_change);
        databaseReference.child(ID.getText().toString()).child("gunban_octave").setValue(gunban.getOctave());

        Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(JoinActivity.this, LoginActivity.class));
        finish();
    }

    public void onBackPressed() {
        startActivity(new Intent(JoinActivity.this, LoginActivity.class));
        JoinActivity.this.finishAffinity();
    }
}