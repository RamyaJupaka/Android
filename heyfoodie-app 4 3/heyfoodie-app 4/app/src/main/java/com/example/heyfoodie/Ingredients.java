package com.example.heyfoodie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.heyfoodie.Model.MenuItems;
import com.example.heyfoodie.order.Menu;
import com.example.heyfoodie.order.MenuItem;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Ingredients extends AppCompatActivity  {


ListView listView;

DatabaseReference ref;
FirebaseDatabase database;

ArrayList<String> list;
ArrayAdapter<String> adapter;
MenuItems menuitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);

        menuitems = new MenuItems();
        listView = (ListView) findViewById(R.id.ingredientList);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("MenuItem");
        list = new ArrayList<>();
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);


        

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                   // menuitems = ds.getValue(MenuItems.class);
                    String items = ds.getValue(MenuItem.class).toString();
                    list.add(items);

                }
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

}
