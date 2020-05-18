package com.example.projectlistviewemployee;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectlistviewemployee.model.Employees;
import com.example.projectlistviewemployee.model.FileManagement;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener {

    EditText editTextEmployeeId, editTextNewSalary;
    ListView listViewEmployees;
    Button btnLoad, btnUpdate, btnSort;

    ArrayList<Employees> listOfEmployees;
    ArrayAdapter<Employees> employeeAdapter;

    AlertDialog.Builder alerDialog;

    int currentPosition;

    View view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

//        get id
        editTextEmployeeId = findViewById(R.id.editTextEmpId);
        editTextNewSalary = findViewById(R.id.editTextSalary);
        btnLoad = findViewById(R.id.btnLoad);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnSort = findViewById(R.id.btnSort);
        listViewEmployees = findViewById(R.id.listViewEmployees);

//        create click listener
        btnLoad.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnSort.setOnClickListener(this);


        listViewEmployees.setOnItemClickListener(this);
        listViewEmployees.setOnItemLongClickListener(this);


//      alerDialog
        alerDialog = new AlertDialog.Builder(this);
        alerDialog.setTitle("Delete a Employee");
        alerDialog.setMessage("Do you want to delete (Y/N)");
        alerDialog.setPositiveButton("Yes",this);
        alerDialog.setNegativeButton("No",this);

    }

    @Override
    public void onClick(View v) {
        view = v;
        int btnId = v.getId();
        switch (btnId){
            case R.id.btnLoad:
                load();
                break;
            case R.id.btnUpdate:
                update();
                break;
            case R.id.btnSort:
                sort();
                break;
        }

    }

    private void sort() {

        Collections.sort(listOfEmployees);
        employeeAdapter.notifyDataSetChanged();
    }

    private void update() {
        String empId = editTextEmployeeId.getText().toString();
        String newSalary = editTextNewSalary.getText().toString();
        Employees tmp = listOfEmployees.get(Integer.parseInt(empId) - 1 );
        tmp.setSalary(Float.parseFloat(newSalary));
        Employees emp = new Employees(empId, tmp.getLastName(),tmp.getTelephone(),tmp.getSalary(),tmp.getEmail());

        if(listOfEmployees.contains(emp)){
            int position = listOfEmployees.indexOf(emp);
            listOfEmployees.set(position,emp);
            employeeAdapter.notifyDataSetChanged();

            Toast.makeText(this,"Update successfully" + " New Salary is " + emp.getLastName() + " " + newSalary ,Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this,"Not exists",Toast.LENGTH_LONG).show();

    }

    private void load() {
        listOfEmployees = FileManagement.readFile(this,"Employees.txt");

        employeeAdapter = new ArrayAdapter<>(this,R.layout.one_item,listOfEmployees);
        listViewEmployees.setAdapter(employeeAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Employees employee = listOfEmployees.get(position);
        String show = employee.getTelephone() + " " + employee.getEmail() + " " + employee.getSalary();
        editTextEmployeeId.setText(employee.getId());

        Snackbar.make(view,show,Snackbar.LENGTH_LONG).show();

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
       currentPosition = position;
       alerDialog.create().show();
       return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which){
            case Dialog.BUTTON_POSITIVE:
                listOfEmployees.remove(currentPosition);
                employeeAdapter.notifyDataSetChanged();
                break;
            case Dialog.BUTTON_NEGATIVE:
                break;
        }
    }
}
