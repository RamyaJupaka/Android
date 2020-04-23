package com.example.final_project_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.final_project_quiz.model.Funtions;
import com.example.final_project_quiz.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SignupActivity extends AppCompatActivity {


    EditText editTextNewName,editTextNewPSW;
    Button btnSubmit;
    DatabaseReference userDatabase;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mediaPlayer = MediaPlayer.create(SignupActivity.this,R.raw.s);
        mediaPlayer.start();

        editTextNewName = findViewById(R.id.editTextNewName);
        editTextNewPSW = findViewById(R.id.editTextNewPsw);

        userDatabase = FirebaseDatabase.getInstance().getReference("user");


        btnSubmit = findViewById(R.id.buttonSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                boolean checkNameIsEmpty, checkPswIsEmpty;
                checkNameIsEmpty = Funtions.isEmpty(editTextNewName,"name");
                checkPswIsEmpty = Funtions.isEmpty(editTextNewPSW,"password");
                if(checkNameIsEmpty && checkPswIsEmpty){
                    String userName = editTextNewName.getText().toString().toLowerCase();
                    String userPSW = editTextNewPSW.getText().toString();
                    User newUser = new User(userName,userPSW);
                    userDatabase.child(userName).setValue(newUser);
                    Toast.makeText(getApplicationContext(),"create account was successfully",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SignupActivity.this,SecondActivity.class);
                    intent.putExtra("username",userName);
                    startActivity(intent);
                }else{
                    // do something
                }

            }
        });

    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
    }

}
