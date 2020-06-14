package com.example.heyfoodie.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heyfoodie.R;


public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(), "Continue for Menu!", Toast.LENGTH_LONG).show();

    }

public void redirectToMenu(View v)
{
    startActivity(new Intent(WelcomePage.this,MainActivity.class));
}

}
