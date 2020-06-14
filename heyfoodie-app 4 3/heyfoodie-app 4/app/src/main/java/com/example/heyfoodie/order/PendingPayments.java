package com.example.heyfoodie.order;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heyfoodie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import static java.lang.String.valueOf;

public class PendingPayments extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<OrderBill> data;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference();

    HashMap<String, DishPrice> prices = new HashMap<>();
    HashMap<String, OrderBill> orderlist = new HashMap<>();

    DatabaseReference refOrders;
    DatabaseReference refMenu;
    DatabaseReference refOrderDetails;

    Context c = this;

    //Refresh is called to populate the recyclerview
    public void Refresh() {
        prices = new HashMap<>();
        orderlist = new HashMap<>();
        data = new ArrayList<>();


        refMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Price", valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot dishSnapshot : dataSnapshot.getChildren()) {
                    DishPrice dish = dishSnapshot.getValue(DishPrice.class);
                    Log.e("Get Data", dish.toString());
                    prices.put(dish.dishName, dish);
                }
                refOrders.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String id = postSnapshot.child("id").getValue(String.class);
                            int status = postSnapshot.child("status").getValue(int.class);
                            String time = postSnapshot.child("timestamp").getValue(String.class);
                            String table = postSnapshot.child("tableID").getValue(String.class);

                            if (status != 4) {
                                OrderBill o = new OrderBill();
                                o.table = table;
                                o.id = id;
                                o.time = time;
                                o.D = new ArrayList<>();
                                Log.e("ID", id);
                                orderlist.put(o.id, o);
                            }
                        }

                        refOrderDetails.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String id = postSnapshot.child("orderid").getValue(String.class);
                                    String dishname = postSnapshot.child("dishname").getValue(String.class);
                                    int quantity = postSnapshot.child("servings").getValue(int.class);
                                    int status = postSnapshot.child("status").getValue(int.class);

                                    if (status != 4) {
                                        DishOrder d = new DishOrder();
                                        d.quantity = quantity;
                                        DishPrice p = new DishPrice();
                                        if (prices.containsKey(dishname)) {
                                            p = prices.get(dishname);
                                            d.price = p.price;
                                            d.dishName = p.dishName;

                                            try {
                                                orderlist.get(id).D.add(d);

                                            } catch (Exception e) {

                                            }
                                        } else {

                                            d.quantity = quantity;
                                            if (orderlist.containsKey(id)) {
                                                orderlist.get(id).D.add(d);
                                            }
                                        }
                                    }
                                }
                                data = new ArrayList<>(orderlist.values());//recycle view
                                Collections.sort(data);
                                if (mAdapter != null) {
                                    mAdapter.notifyDataSetChanged();
                                } else {
                                    createRecyclerView(data);
                                }
                                createRecyclerView(data);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                Log.w("Billing Module, Test", "Failed to read value.", error.toException());
                            }
                        });
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        Log.w("Test", "Failed to read value.", error.toException());
                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Test", "Failed to read value.", error.toException());
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        Refresh();
    }

    //constructor
    public PendingPayments() {
        data = new ArrayList<>();
        refMenu = ref.child("Menu");
        refOrders = ref.child("Order");
        refOrderDetails = ref.child("OrderDetails");
        Refresh();
    }

    protected void createRecyclerView(ArrayList<OrderBill> d) {
        mRecyclerView = (RecyclerView) findViewById(R.id.ordersRecView);
        ProgressBar spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.INVISIBLE);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new OrdersAdapter(data, R.layout.order_pending_view, this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                mLayoutManager.getOrientation()
        );
        mRecyclerView.addItemDecoration(mDividerItemDecoration);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_pending_payments);
        ref.keepSynced(true);
    }
}
