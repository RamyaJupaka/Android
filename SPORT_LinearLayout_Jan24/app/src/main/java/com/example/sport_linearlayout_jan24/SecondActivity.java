package com.example.sport_linearlayout_jan24;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView textViewName , textViewSport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        textViewName = findViewById(R.id.textViewName);
        textViewSport = findViewById(R.id.textViewSport);

        String name = getIntent().getStringExtra("name");
        textViewName.setText(name);

        int sportId = getIntent().getIntExtra("sport",0);

        String sport = null;
        switch (sportId){
            case R.id.rbtnSoccer:
                 sport = "Soccer";break;
            case R.id.rbtnFootball:
                sport = "Football";break;
            case R.id.rbtnHockey:
                sport = "Hockey";break;
            default:
                sport = "no sport";break;

        }
        textViewSport.setText(sport);


    }
}
