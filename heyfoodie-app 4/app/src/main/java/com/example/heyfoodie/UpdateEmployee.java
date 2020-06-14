package com.example.heyfoodie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateEmployee extends AppCompatActivity implements View.OnClickListener , ValueEventListener , ChildEventListener {

      EditText UpdatePassword,UpdateEmail,UpdateSalary,UpdateType,UpdateEmpId,UpdateName;
    Button UpdateEmployeeButton,FindEmployeeButton;

    DatabaseReference employeeData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_employee);

        initialize();
    }

    private void initialize() {
        UpdateName = findViewById(R.id.UpdateName);
        UpdatePassword = findViewById(R.id.UpdatePassword);
        UpdateSalary = findViewById(R.id.UpdateSalary);
        UpdateType = findViewById(R.id.UpdateType);
        UpdateEmpId = findViewById(R.id.UpdateEmpId);
        UpdateEmail = findViewById(R.id.UpdateEmail);
        UpdateEmployeeButton = findViewById(R.id.UpdateEmployeeButton);
        FindEmployeeButton = findViewById(R.id.FindEmployeeButton);

        UpdateEmployeeButton.setOnClickListener(this);
        FindEmployeeButton.setOnClickListener(this);

        employeeData = FirebaseDatabase.getInstance().getReference().child("employee");
    }

    @Override
    public void onClick(View v) {

        int btnid = v.getId();
        switch (btnid) {
            case R.id.UpdateEmployeeButton:
                updateemp();
                break;
            case R.id.FindEmployeeButton:
                findemp();
                break;
            default:
                Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                break;
        }
    }

    private void findemp() {

       String id = UpdateEmpId.getText().toString();

       DatabaseReference employeeChild = FirebaseDatabase
               .getInstance()
               .getReference()
               .child("employee")
               .child(id);
       employeeChild.addValueEventListener(this);
    }

    private void updateemp() {
        int id = Integer.valueOf(UpdateEmpId.getText().toString());
        String name = UpdateName.getText().toString();
        String email = UpdateEmail.getText().toString();
        String password = UpdatePassword.getText().toString();
        String salary = UpdateSalary.getText().toString();
        String type = UpdateType.getText().toString();

        com.example.heyfoodie.Model.UpdateEmployee updateEmp = new com.example.heyfoodie.Model.UpdateEmployee(id,name,email,password,type,salary);

        DatabaseReference employeeChild = FirebaseDatabase
                .getInstance()
                .getReference()
                .child("employee")
                .child(String.valueOf(id));
        employeeChild.setValue(updateEmp);
        Log.d("FIREBASE",updateEmp.toString() + "is updated to employee");
        UpdateEmpId.setText(null);
        UpdateName.setText(null);
        UpdatePassword.setText(null);
        UpdateSalary.setText(null);
        UpdateType.setText(null);
        UpdateEmail.setText(null);
        UpdateEmpId.requestFocus();


    }

    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



       if (dataSnapshot.exists())
       {
           String name = dataSnapshot.child("name").getValue().toString();
           String password = dataSnapshot.child("password").getValue().toString();
           String salary = dataSnapshot.child("salary").getValue().toString();
           String email = dataSnapshot.child("email").getValue().toString();
           String type = dataSnapshot.child("type").getValue().toString();

           UpdateName.setText(name);
           UpdatePassword.setText(password);
           UpdateSalary.setText(salary);
           UpdateType.setText(type);
           UpdateEmail.setText(email);
           Log.d("FIREBASE","emp info is found successfully");

       }
       else
       {
           Log.d("FIREBASE","emp info does not exist");
       }


    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


        com.example.heyfoodie.Model.UpdateEmployee updateEmployee = dataSnapshot.getValue(com.example.heyfoodie.Model.UpdateEmployee.class);
        Log.d("FIREBASE","UpdateEmployee: "+updateEmployee.toString());


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

