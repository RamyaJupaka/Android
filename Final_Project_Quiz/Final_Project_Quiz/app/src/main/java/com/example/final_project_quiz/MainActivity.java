package com.example.final_project_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project_quiz.model.Funtions;
import com.example.final_project_quiz.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.Instant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ValueEventListener {

    EditText editTextName, editTextPsw;
    Button buttonStart, buttonSignup;

    MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextPsw = findViewById(R.id.editTextPsw);

        buttonStart = findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(this);

        buttonSignup = findViewById(R.id.buttonRegister);
        buttonSignup.setOnClickListener(this);

        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.s);
        mediaPlayer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
    }


    @Override
    public void onClick(View v) {

        int btnId = v.getId();

        switch (btnId){
            case R.id.buttonStart:
                login();


                break;
            case R.id.buttonRegister:
                 signup();
                break;
        }
    }

    private void signup() {
        Intent intent = new Intent(this,SignupActivity.class);
        startActivity(intent);
    }

    private void login() {
        String name = editTextName.getText().toString();


        //System.out.println(name);

        DatabaseReference userDatabase = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("user")
                .child(name);

        userDatabase.addValueEventListener(this);
    }



    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        //System.out.println("hello");  check this function is called
        boolean checkNameIsEmpty, checkPswIsEmpty;
        checkNameIsEmpty = Funtions.isEmpty(editTextName,"name");
        checkPswIsEmpty = Funtions.isEmpty(editTextPsw,"password");


        if(checkNameIsEmpty && checkPswIsEmpty) {
            if (dataSnapshot.exists()) {

                //System.out.println("heel");// check this
                String name = dataSnapshot.child("name").getValue().toString().trim();
                name = name.toLowerCase();
                // System.out.println(name); check the valuable of name

                String psw = dataSnapshot.child("password").getValue().toString().trim();
                psw = psw.toLowerCase();


                //System.out.println(psw);


                if (editTextName.getText().toString().equalsIgnoreCase(name)) {
                    if (editTextPsw.getText().toString().equalsIgnoreCase(psw)) {
                        Intent intent = new Intent(this, SecondActivity.class);
                        intent.putExtra("username",editTextName.getText().toString());
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "check your password", Toast.LENGTH_LONG).show();
                        editTextPsw.setText(null);
                        editTextPsw.findFocus();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "check your name", Toast.LENGTH_LONG).show();
                    editTextName.setText(null);
                    editTextName.findFocus();
                }
            } else {
                Toast.makeText(getApplicationContext(), "User do not exist", Toast.LENGTH_LONG).show();
            }
        }else{
            // do something
        }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }



}
