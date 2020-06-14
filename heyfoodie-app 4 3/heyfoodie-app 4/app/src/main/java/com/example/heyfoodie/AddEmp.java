package com.example.heyfoodie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddEmp extends AppCompatActivity implements View.OnClickListener, ValueEventListener, ChildEventListener {

     EditText AddEmpId,AddEmployeeName,AddEmpType,AddEmployeeSalary,AddEmployeeEmail,AddEmployeePassword;
     Button AddNewEmployeeButton;

     DatabaseReference employeeData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emp);

        initialize();
    }
    private void initialize() {
        AddEmployeeName = findViewById(R.id.AddEmployeeName);
        AddEmpType = findViewById(R.id.AddEmpType);
        AddEmpId = findViewById(R.id.AddEmpId);
        AddEmployeeSalary = findViewById(R.id.AddEmployeeSalary);
        AddEmployeePassword = findViewById(R.id.AddEmployeePassword);
        AddEmployeeEmail = findViewById(R.id.AddEmployeeEmail);
        AddNewEmployeeButton = findViewById(R.id.AddNewEmployeeButton);

        AddNewEmployeeButton.setOnClickListener(this);
        employeeData = FirebaseDatabase.getInstance().getReference().child("employee");
    }

    @Override
    public void onClick(View v) {


        EditText name = (EditText)findViewById(R.id.AddEmployeeName);
        EditText password = (EditText)findViewById(R.id.AddEmployeePassword);
        EditText email = (EditText)findViewById(R.id.AddEmployeeEmail);
        EditText salary = (EditText)findViewById(R.id.AddEmployeeSalary);
        EditText type = (EditText)findViewById(R.id.AddEmpType);
        EditText id = (EditText)findViewById(R.id.AddEmpId);
        boolean add = true;

        if (name.getText().toString().length() <= 0) {
            name.setError("Name is empty");
            add = false;
        }
        if (id.getText().toString().length() <= 0) {
            id.setError("Id is empty");
            add = false;
        }
        if (password.getText().toString().length() <= 0) {
            password.setError("password is empty");
            add = false;
        }
        if (email.getText().toString().length() <= 0) {
            email.setError("email is empty");
            add = false;
        }
        if (salary.getText().toString().length() <= 0) {
            salary.setError("salary is empty");
            add = false;
        }
        if (type.getText().toString().length() <= 0) {
            type.setError("type is empty");
            add = false;
        }

        if (add) {

            com.example.heyfoodie.Model.AddItem item = new com.example.heyfoodie.Model.AddItem(name.getText().toString(),id.getText().toString(),password.getText().toString(),email.getText().toString(),type.getText().toString(),salary.getText().toString());

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("employee").child(name.getText().toString()).setValue(item);
            Toast.makeText(this, "employee has been added to the inventory", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AdminScreen.class);
            startActivity(intent);
        }

        addemployee();
    }

    private void addemployee() {
        int id = Integer.valueOf(AddEmpId.getText().toString());
        String name = AddEmployeeName.getText().toString();
        String email = AddEmployeeEmail.getText().toString();
        String password = AddEmployeePassword.getText().toString();
        String salary = AddEmployeeSalary.getText().toString();
        String type = AddEmpType.getText().toString();


        com.example.heyfoodie.Model.AddEmp addEmp = new com.example.heyfoodie.Model.AddEmp(id,name,email,password,type,salary);


        employeeData.child(String.valueOf(id)).setValue(addEmp);
        Log.d("FIREBASE",employeeData.toString() + "is added to employee collection");
        AddEmpId.setText(null);
        AddEmployeeName.setText(null);
        AddEmployeePassword.setText(null);
        AddEmployeeSalary.setText(null);
        AddEmpType.setText(null);
        AddEmployeeEmail.setText(null);
        AddEmpId.requestFocus();


    }


    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }


    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }


}
