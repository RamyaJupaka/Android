package com.example.hayfoodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button Login ,Explore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Login = findViewById(R.id.login);
        Explore = findViewById(R.id.Explore);

        Login.setOnClickListener(this);
        Explore.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        int btnId = v.getId();
        switch (btnId){
            case R.id.login:
                login();
                break;
            case R.id.Explore:
                Explore();
                break;
        }


    }

    private void Explore() {


    }

    private void login() {

        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}
