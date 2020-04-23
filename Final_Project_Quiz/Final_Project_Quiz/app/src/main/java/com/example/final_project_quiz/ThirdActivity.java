package com.example.final_project_quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.final_project_quiz.model.Questions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ThirdActivity extends AppCompatActivity  {

    TextView textViewPlayName, textViewCaName, textViewQuestion;
    DatabaseReference databaseReference;
    Button b1, b2,b3,b4;
    String name;
    int totalQuestion = 0;
    int correct = 0;
    int wrong = 0;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        mediaPlayer = MediaPlayer.create(ThirdActivity.this,R.raw.s);
        mediaPlayer.start();

        initialized();


    }

    public static void displayCategory (String category, TextView textView){

        textView.setText(category);
    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
    }

    private void initialized() {
        textViewPlayName = findViewById(R.id.textViewPlayName);
        textViewCaName = findViewById(R.id.textViewCaName);
        textViewQuestion = findViewById(R.id.textViewQuestions);
        b1 = findViewById(R.id.btnOption1);
        b2 = findViewById(R.id.btnOption2);
        b3 = findViewById(R.id.btnOption3);
        b4 = findViewById(R.id.btnOption4);


        name = getIntent().getStringExtra("usename");
        name = name.toUpperCase();
        textViewPlayName.setText("Good Luck " + name);

        int radioId = getIntent().getIntExtra("type",0);
        switch (radioId){
            case R.id.radioButtonCategory1:
                displayCategory("General Knowledge", textViewCaName);
                getdata("generalknowledge");
                break;
            case R.id.radioButtonCategory2:
                displayCategory("Politics", textViewCaName);
                 getdata("polictics");
                break;
            case R.id.radioButtonCategory3:
                displayCategory("Mathamatics",textViewCaName);
                getdata("mathematics");
                break;
            case R.id.radioButtonCategory4:
                displayCategory("Movies",textViewCaName);
                getdata("movies");
                break;

            case R.id.radioButtonCategory5:
                displayCategory("Novels",textViewCaName);
                getdata("novels");
                break;
        }
    }

    private void getdata(final String table) {
        totalQuestion++;
        if(totalQuestion >5){
            totalQuestion = totalQuestion -1;
            Intent intent = new Intent(ThirdActivity.this,ResultActivity.class);
            intent.putExtra("total", String.valueOf(totalQuestion));
            intent.putExtra("correct",String.valueOf(correct));
            intent.putExtra("incorrect",String.valueOf(wrong));
            intent.putExtra("username",name);
            startActivity(intent);


        }else{

            databaseReference = FirebaseDatabase.getInstance().getReference().child(table).child(String.valueOf(totalQuestion));
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    final Questions questions = dataSnapshot.getValue(Questions.class);
                    textViewQuestion.setText(questions.getQuestion());
                    System.out.println(questions.getAnswer());
                    b1.setText(questions.getOption1());
                    b2.setText(questions.getOption2());
                    b3.setText(questions.getOption3());
                    b4.setText(questions.getOption4());

                    b1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b1.getText().toString().equals(questions.getAnswer())){
                                Toast.makeText(getApplicationContext(), "CORRECT", Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(()->{
                                    correct++;
                                    getdata(table);

                                },2000);

                            }else{
                                Toast.makeText(getApplicationContext(), "WRONG, answer is: " + questions.getAnswer() , Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(()->{
                                    wrong++;
                                    getdata(table);

                                },2000);

                            }
                        }
                    });

                    b2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b2.getText().toString().equals(questions.getAnswer())){
                                Toast.makeText(getApplicationContext(), "CORRECT", Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(()->{
                                    correct++;
                                    getdata(table);

                                },2000);

                            }else{
                                Toast.makeText(getApplicationContext(), "WRONG, answer is: " + questions.getAnswer() , Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(()->{
                                    wrong++;
                                    getdata(table);

                                },2000);

                            }
                        }
                    });
                    b3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b3.getText().toString().equals(questions.getAnswer())){
                                Toast.makeText(getApplicationContext(), "CORRECT", Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(()->{
                                    correct++;
                                    getdata(table);

                                },2000);

                            }else{
                                Toast.makeText(getApplicationContext(), "WRONG, answer is: " + questions.getAnswer() , Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(()->{
                                    wrong++;
                                    getdata(table);

                                },2000);

                            }
                        }
                    });
                    b4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(b4.getText().toString().equals(questions.getAnswer())){
                                Toast.makeText(getApplicationContext(), "CORRECT",Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(()->{
                                    correct++;
                                    getdata(table);

                                },2000);

                            }else{
                                Toast.makeText(getApplicationContext(), "WRONG, answer is: " + questions.getAnswer() ,Toast.LENGTH_SHORT).show();
                                Handler handler = new Handler();
                                handler.postDelayed(()->{
                                    wrong++;
                                    getdata(table);

                                },2000);

                            }
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }


}
