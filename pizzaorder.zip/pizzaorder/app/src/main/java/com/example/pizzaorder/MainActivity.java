package com.example.pizzaorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pizzaorder.Module.Order;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    private EditText editTextClientNumber,editTextNbSlices;
    private RadioGroup rgPizza;
    private TextView textViewAmount;
    private Button btnOrder,btnShowOrder,btnShowAllOrders;
    private Order order;
    private ArrayList<Order> listofOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        editTextClientNumber=findViewById(R.id.editTextClientnumber);
        editTextNbSlices=findViewById(R.id.editTextNbSlices);
        rgPizza=findViewById(R.id.rgPizza);
        textViewAmount=findViewById(R.id.textViewAmount);
        btnOrder=findViewById(R.id.btnOrder);
        btnShowOrder=findViewById(R.id.btnShowOrder);
        btnShowAllOrders=findViewById(R.id.btnShowAllOrders);
        editTextNbSlices.addTextChangedListener(this);
        btnOrder.setOnClickListener(this);
        btnShowOrder.setOnClickListener(this);
        btnShowAllOrders.setOnClickListener(this);
//        listOfOrders
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    try {
        int clientNumber;
        clientNumber = Integer.valueOf(editTextClientNumber.getText().toString());
        int nbSlices = Integer.valueOf(editTextNbSlices.getText().toString());
        int rbId = rgPizza.getCheckedRadioButtonId();
        String pizza = null;
        switch (rbId) {
            case R.id.radioButton:
                pizza = "vegetarian";
                break;
            case R.id.radioButton2:
                pizza = "cheese";
                break;
            case R.id.radioButton3:
                pizza = "mexican";
                break;
        }
        order = new Order(clientNumber, pizza, nbSlices);
        float amount = order.getAmount();
        textViewAmount.setText(String.valueOf(amount));
    }
    catch(Exception e){


    }
    }

    @Override
    public void onClick(View v) {
    int btnId=v.getId();
        Snackbar.make(v,"The order of the client"+" is saved !",Snackbar.LENGTH_LONG).show();
        Toast.makeText(this, Integer.toString(btnId), Toast.LENGTH_SHORT).show();
    switch (btnId){
        case R.id.btnOrder:
            saveOrder(v);
            break;
        case R.id.btnShowOrder:
        showOrder();

            break;
        case R.id.btnShowAllOrders:

            break;
    }
    }

    private void showOrder() {
        Intent intent= new Intent(this,ShowOrder.class);
        intent.putExtra("order",listofOrders);
        startActivity(intent);


    }

    private void saveOrder(View v) {
        listofOrders.add(order);
        String client=editTextClientNumber.getText().toString();
    }
}
