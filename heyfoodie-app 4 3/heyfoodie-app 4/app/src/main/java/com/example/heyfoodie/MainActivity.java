package com.example.heyfoodie;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heyfoodie.order.cartListView;

public class MainActivity extends AppCompatActivity {

    /*Button Login ,Explore;
    TextView textViewName;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*Login = findViewById(R.id.login);
        Explore = findViewById(R.id.Explore);

        textViewName = findViewById(R.id.textViewName);*/

    }

    public void onClickLogin(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);

    }

    public void onClickExplore(View view) {
        //Intent intent = new Intent(this, com.example.heyfoodie.order.MainActivity.class);
        Intent intent = new Intent(MainActivity.this, com.example.heyfoodie.order.MainActivity.class);
        startActivity(intent);
    }

}
