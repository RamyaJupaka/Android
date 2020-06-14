package com.example.heyfoodie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heyfoodie.Model.AddEmp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddItem extends AppCompatActivity implements View.OnClickListener {

    EditText AddItemName,AddItemPrice,AddItemQuantity,AddItemThresold;
    Button AddNewItemButton;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

      AddItemName = findViewById(R.id.AddItemName);
      AddItemPrice= findViewById(R.id.AddItemPrice);
      AddItemQuantity= findViewById(R.id.AddItemQuantity);
      AddItemThresold= findViewById(R.id.AddItemThreshold);
      AddNewItemButton= findViewById(R.id.AddNewItemButton);

      AddNewItemButton.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {

        EditText name = (EditText)findViewById(R.id.AddItemName);
        EditText price = (EditText)findViewById(R.id.AddItemPrice);
        EditText quantity = (EditText)findViewById(R.id.AddItemQuantity);
        EditText threshold = (EditText)findViewById(R.id.AddItemThreshold);

        boolean add = true;

        if (name.getText().toString().length() <= 0) {
            name.setError("Name is empty");
            add = false;
        }
        if (price.getText().toString().length() <= 0) {
            price.setError("Price is empty");
            add = false;
        }
        if (quantity.getText().toString().length() <= 0) {
            quantity.setError("Quantity is empty");
            add = false;
        }
        if (threshold.getText().toString().length() <= 0) {
            threshold.setError("Threshold is empty");
            add = false;
        }
        if (price.getText().toString().length() > 0 && Integer.parseInt(price.getText().toString()) < 1) {
            price.setError("Price must be greater than 0");
            add = false;
        }
        if (quantity.getText().toString().length() > 0 && Integer.parseInt(quantity.getText().toString()) < 1) {
            quantity.setError("Quantity must be greater than 0");
            add = false;
        }
        if (threshold.getText().toString().length() > 0 && Integer.parseInt(threshold.getText().toString()) < 1) {
            quantity.setError("Threshold must be greater than 0");
            add = false;
        }
        if (add) {

            com.example.heyfoodie.Model.AddItem item = new com.example.heyfoodie.Model.AddItem(name.getText().toString(),price.getText().toString(),quantity.getText().toString(),threshold.getText().toString());

            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            ref.child("Inventory").child(name.getText().toString()).setValue(item);
            Toast.makeText(this, "Item has been added to the inventory", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, AdminScreen.class);
            startActivity(intent);
        }


    }
}
