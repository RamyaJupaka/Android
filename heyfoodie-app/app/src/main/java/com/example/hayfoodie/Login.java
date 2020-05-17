package com.example.hayfoodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hayfoodie.Model.Functions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity implements View.OnClickListener, ValueEventListener {

   EditText editTextName , editTextPsw;
   Button buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName  = findViewById(R.id.editTextName);
        editTextPsw = findViewById(R.id.editTextPsw);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(this);



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

        String name =  editTextName.getText().toString();

        DatabaseReference userDatabase = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("user")
                .child(name);

        userDatabase.addValueEventListener(this);

    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

        boolean CheckNameIsEmpty,checkPswIsEmpty;

        CheckNameIsEmpty = Functions.isEmpty(editTextName,"name");
        checkPswIsEmpty = Functions.isEmpty(editTextPsw,"password");

        if(CheckNameIsEmpty && checkPswIsEmpty) {
            if (dataSnapshot.exists()) {

                System.out.println("heel");// check this
                String name = dataSnapshot.child("name").getValue().toString().trim();
                name = name.toLowerCase();
                 System.out.println(name); //check the valuable of name

                String psw = dataSnapshot.child("password").getValue().toString().trim();
                psw = psw.toLowerCase();


                //System.out.println(psw);


                if (editTextName.getText().toString().equalsIgnoreCase(name)) {
                    if (editTextPsw.getText().toString().equalsIgnoreCase(psw)) {
                        Intent intent = new Intent(this, AdminScreen.class);
                        intent.putExtra("name",editTextName.getText().toString());
                        startActivity(intent);

                    } else {
                        Toast.makeText(getApplicationContext(), "check your password", Toast.LENGTH_LONG).show();
                        editTextPsw.setText(null);
                        editTextPsw.findFocus();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "check your name", Toast.LENGTH_LONG).show();
                    editTextName.setText(null);
                    editTextName.findFocus();
                }
            } else {
                Toast.makeText(getApplicationContext(), "User do not exist", Toast.LENGTH_LONG).show();
            }
        }else{

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
