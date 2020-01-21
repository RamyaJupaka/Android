package com.example.myapplicationcalsimply;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button btnsum = findViewById(R.id.buttonSum);

        btnsum.setOnClickListener((v)->{
            final TextView num1 = findViewById(R.id.editTextNumOne);
            final TextView num2 = findViewById(R.id.editTextNumTwo);
            final TextView res = findViewById(R.id.editText4);

            int n1 = Integer.parseInt(num1.getText().toString());

            int n2 = Integer.parseInt(num2.getText().toString());
            int re ;
            re = n1 + n2;
            res.setText(String.valueOf(re));
        });


    }

//    public void addition (View view){
//        final TextView num1 = findViewById(R.id.editTextNumOne);
//        final TextView num2 = findViewById(R.id.editTextNumTwo);
//        final TextView res = findViewById(R.id.editText4);
//
//        int n1 = Integer.parseInt(num1.getText().toString());
//        int n2 = Integer.parseInt(num2.getText().toString());
//        int re ;
//
//        re = n1 + n2;
//
//        res.setText(String.valueOf(re));
//
//    }


   public void multiply(View view)
   {
       final TextView num1 = findViewById(R.id.editTextNumOne);
       final TextView num2 = findViewById(R.id.editTextNumTwo);
       final TextView res = findViewById(R.id.editText4);

       int n1 = Integer.parseInt(num1.getText().toString());

       int n2 = Integer.parseInt(num2.getText().toString());
       int re ;

       re = n1 * n2;

       res.setText(String.valueOf(re));
   }

   public void quit (View view){
        finish();
   }





}
