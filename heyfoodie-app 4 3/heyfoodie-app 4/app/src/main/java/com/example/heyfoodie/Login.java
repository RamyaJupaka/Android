package com.example.heyfoodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heyfoodie.Model.Functions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener {

   EditText editTextEmail , editTextPsw;
   Button buttonLogin;
   FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail  = findViewById(R.id.editTextEmail);
        editTextPsw = findViewById(R.id.editTextPsw);
        buttonLogin = findViewById(R.id.buttonLogin);


        buttonLogin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();



    }

    @Override
    public void onClick(View v) {

        int btnId = v.getId();

        switch (btnId)
        {
            case R.id.buttonLogin:
                login();
        }

    }

    private void login() {

        final String email = editTextEmail.getText().toString();
        String psw = editTextPsw.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(psw)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
        }else{
            mAuth.signInWithEmailAndPassword(email,psw)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                if(email .equals("ramya@gmail.com")){
                                    Intent i = new Intent(Login.this, AdminScreen.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivity(i);
                                }
                                if(email.equals("jupaka@gmail.com")){
                                    Intent i = new Intent(Login.this, KitchenMainActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                                    startActivity(i);
                                }
                            }
                        }
                    });
        }


    }

}
