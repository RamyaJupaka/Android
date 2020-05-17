package com.example.hayfoodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddEmp extends AppCompatActivity   {
     EditText AddEmployeeName,AddEmpType,AddSpeciality,AddEmployeeSalary,AddEmployeeEmail,AddEmployeePassword;
     Button AddNewEmployeeButton;
     DatabaseReference databaseReference;
     FirebaseDatabase firebaseDatabase;
     FirebaseAuth firebaseAuth;

    public AddEmp(String name, String password, String email, String emptype, String speciality, String salary) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);

        AddEmployeeName = findViewById(R.id.AddEmployeeName);
        AddEmpType = findViewById(R.id.AddEmpType);
        AddSpeciality = findViewById(R.id.AddSpeciality);
        AddEmployeeSalary = findViewById(R.id.AddEmployeeSalary);
        AddEmployeePassword = findViewById(R.id.AddEmployeePassword);
        AddEmployeeEmail= findViewById(R.id.AddEmployeeEmail);

        AddNewEmployeeButton= findViewById(R.id.AddNewEmployeeButton);

         databaseReference = firebaseDatabase.getInstance().getReference("employee");

         firebaseAuth = FirebaseAuth.getInstance();

         AddNewEmployeeButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 final String   name = AddEmployeeName.getText().toString();
                 final String  password=AddEmployeePassword.getText().toString();
                 final String   email = AddEmployeeEmail.getText().toString();
                 final String  emptype =AddEmpType.getText().toString();
                 final String   speciality = AddSpeciality.getText().toString();
                 final String  salary=AddEmployeeSalary.getText().toString();


                 if(TextUtils.isEmpty(name))
                 {
                     Toast.makeText(AddEmp.this,"please enter the name ",Toast.LENGTH_SHORT).show();

                 }
                 if(TextUtils.isEmpty(password))
                 {
                     Toast.makeText(AddEmp.this,"please enter the password ",Toast.LENGTH_SHORT).show();

                 }
                 if(TextUtils.isEmpty(email))
                 {
                     Toast.makeText(AddEmp.this,"please enter the email ",Toast.LENGTH_SHORT).show();

                 }
                 if(TextUtils.isEmpty(salary))
                 {
                     Toast.makeText(AddEmp.this,"please enter the salary ",Toast.LENGTH_SHORT).show();

                 }
                 if(TextUtils.isEmpty(speciality))
                 {
                     Toast.makeText(AddEmp.this,"please enter the speciality ",Toast.LENGTH_SHORT).show();

                 }
                 if(TextUtils.isEmpty(emptype))
                 {
                     Toast.makeText(AddEmp.this,"please enter the emptype ",Toast.LENGTH_SHORT).show();

                 }
                 firebaseAuth.createUserWithEmailAndPassword(email, password)
                         .addOnCompleteListener(AddEmp.this, new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 if (task.isSuccessful()) {

                                     AddEmp information = new AddEmp(
                                             name,
                                             password,
                                             email,
                                             emptype,
                                             speciality,
                                             salary
                                     );
                                     FirebaseDatabase.getInstance().getReference("employee")
                                             .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                             .setValue(information).addOnCompleteListener(new OnCompleteListener<Void>() {
                                         @Override
                                         public void onComplete(@NonNull Task<Void> task) {
                                             Toast.makeText(AddEmp.this, "employee added", Toast.LENGTH_SHORT).show();
                                             startActivity(new Intent(getApplicationContext(),AdminScreen.class));
                                         }
                                     });

                                 } else {

                                 }

                                 // ...
                             }
                         });



             }
         });




    }
}
