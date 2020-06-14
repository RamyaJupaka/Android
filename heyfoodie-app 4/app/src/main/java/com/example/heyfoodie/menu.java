package com.example.heyfoodie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.heyfoodie.Model.Menu;
import com.example.heyfoodie.Model.MenuItems;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class menu extends AppCompatActivity {

    EditText name, time, type, price, quantity;
    List<String> items = new ArrayList<>();
    Spinner ingredientSpn;
    Button addMenu, addIngredient;
    ArrayList<MenuItems> menuItems = new ArrayList<>();

    DatabaseReference database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        name = (EditText) findViewById(R.id.AddDishName);
        type = (EditText) findViewById(R.id.AddDishType);
        price = (EditText) findViewById(R.id.AddDishPrice);
        time = (EditText) findViewById(R.id.AddDishEstimatedTime);
        //addIngredient = (Button) findViewById(R.id.AddIngredientQuantityButton);
        addMenu = (Button) findViewById(R.id.AddMenuItemButton);
        quantity = (EditText) findViewById(R.id.AddQuantity);
        //ingredientSpn = (Spinner) findViewById(R.id.IngredientsDropDown);

        database = FirebaseDatabase.getInstance().getReference();
        database.child("inventory").addChildEventListener(new ChildEventListener() {


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
        });
        addMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean go = true;
                if (name.getText().toString().length() <= 0) {
                    name.setError("Dish Name is Required");
                    go = false;
                } else if (time.getText().toString().length() <= 0) {
                    time.setError("Est. Time is Required");
                    go = false;
                } else if (type.getText().toString().length() <= 0) {
                    type.setError("Dish Type is Required");
                    go = false;
                } else if (price.getText().toString().length() <= 0) {
                    price.setError("Price is Required");
                    go = false;
                }
                if (price.getText().toString().length() > 0 && Integer.parseInt(price.getText().toString()) < 1) {
                    price.setError("Price cannot be 0");
                    go = false;

                }
                if (go) {
                    //Add intent here
                    Menu m = new Menu(name.getText().toString(), time.getText().toString(), type.getText().toString(), price.getText().toString());
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    ref.child("Menu").child(m.getDishName()).setValue(m);
                    for (int i = 0; i < menuItems.size(); i++) {
                        ref.child("MenuItem").child(menuItems.get(i).dishName).child(menuItems.get(i).ingredientName).setValue(menuItems.get(i));
                    }
                    Toast.makeText(menu.this, "Menu Item Added Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
            });
        }
    }


