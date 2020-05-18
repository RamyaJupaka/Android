package com.example.sport_linearlayout_jan24;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

public class MainActivity<packageContext> extends AppCompatActivity implements View.OnClickListener {
    // Handle Click
    // 1. Implements the interface OnClickListener
   // 3. definition
    Button btnClear,btnNext;
    EditText editTextName;
    RadioGroup radioGroupSport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnClear = findViewById(R.id.btnClear);

        btnClear.setOnClickListener(this);
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(this);

        editTextName = findViewById(R.id.editTextName);

        radioGroupSport = findViewById(R.id.radioGroupSport);
    }

    // 2. Implements the method onclick
    @Override
    public void onClick(View view)
    {
        //4. process the clear button
        if (view == btnClear) {

            editTextName.setText(null);
            radioGroupSport.clearCheck();
        }
        else {
            Intent intent = new Intent (this,SecondActivity.class);
            intent.putExtra("name",editTextName.getText().toString());
            intent.putExtra("sport",radioGroupSport.getCheckedRadioButtonId());
            startActivity(intent);





        }
    }
}
