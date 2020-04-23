package com.example.final_project_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project_quiz.model.Score;
import com.example.final_project_quiz.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textViewName,textViewTotal,textViewCorrect,textViewWrong;
    Button btnExit;
    DatabaseReference databaseReference;
    String name;
    String correct;
    String psw;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        textViewCorrect = findViewById(R.id.textViewRCorrect);
        textViewName = findViewById(R.id.textViewRName);
        textViewTotal = findViewById(R.id.textViewRTotal);
        textViewWrong = findViewById(R.id.textViewRWrong);

        btnExit = findViewById(R.id.buttonFinish);
        btnExit.setOnClickListener(this);


        name = getIntent().getStringExtra("username");
        String total = getIntent().getStringExtra("total");

        correct = getIntent().getStringExtra("correct");
        String wrong = getIntent().getStringExtra("incorrect");
        name = name.toUpperCase();
        textViewWrong.setText(wrong);
        textViewTotal.setText(total);
        textViewName.setText(name);
        textViewCorrect.setText(correct);
        mediaPlayer = MediaPlayer.create(ResultActivity.this,R.raw.s);
        mediaPlayer.start();


    }

    @Override
    public void onClick(View v) {
        name = name.toLowerCase();
        System.out.println(name);
        Score score = new Score(name,Integer.valueOf(correct));
        databaseReference = FirebaseDatabase.getInstance().getReference().child("score").child(name);
        databaseReference.setValue(score);
        Toast.makeText(getApplicationContext(), "Thank you see you next time", Toast.LENGTH_LONG).show();

    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
    }

}
