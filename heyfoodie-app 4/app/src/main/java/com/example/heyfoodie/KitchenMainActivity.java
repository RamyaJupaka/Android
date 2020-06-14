package com.example.heyfoodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.heyfoodie.order.KitchenAdapter;
import com.example.heyfoodie.order.Menu;
import com.example.heyfoodie.order.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KitchenMainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Order> listMenu;
    private KitchenAdapter kitchenAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kitchen_activity_kitchen_main);

        recyclerView = findViewById(R.id.kitchen_recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listMenu = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Order");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot db : dataSnapshot.getChildren()){
                        Order l = db.getValue(Order.class);
                        listMenu.add(l);
                    }
                    kitchenAdapter = new KitchenAdapter(listMenu);
                    recyclerView.setAdapter(kitchenAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
