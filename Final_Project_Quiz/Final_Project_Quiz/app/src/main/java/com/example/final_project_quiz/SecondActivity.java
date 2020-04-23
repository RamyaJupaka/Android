package com.example.final_project_quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.os.PowerManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonGo;
    RadioGroup radioGroup;
    //TextView textViewDisplayName;
    String name;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        name = getIntent().getStringExtra("username");
        name = name.toUpperCase();

        Toast toast = Toast.makeText(SecondActivity.this,"Welcome " + name + " to IQuiz",Toast.LENGTH_LONG);
        View view = toast.getView();
        view.setBackgroundColor(Color.TRANSPARENT);
        TextView toastMessage = (TextView) toast.getView().findViewById(android.R.id.message);
        toast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP,0,150);
        toastMessage.setTextColor(Color.rgb(180,100,100));
        toastMessage.setTextSize(30);

        toast.show();
        //textViewDisplayName = findViewById(R.id.textViewDisplayName);
        buttonGo = findViewById(R.id.buttonGo);
        buttonGo.setOnClickListener(this);
        radioGroup = findViewById(R.id.rgCategory);

        mediaPlayer = MediaPlayer.create(SecondActivity.this,R.raw.s);
        mediaPlayer.start();

    }

    @Override
    public void onClick(View v) {
        if (v == buttonGo) {
            Intent intent = new Intent(this,ThirdActivity.class);
            intent.putExtra("type",radioGroup.getCheckedRadioButtonId());
            intent.putExtra("usename",name);
            startActivity(intent);

        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.release();
    }




}
