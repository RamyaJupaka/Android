package com.example.heyfoodie.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.String.valueOf;

public class ConfirmPayment extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private BillAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private RecyclerView mRecyclerView2;
    private BillAdapter mAdapter2;
    private RecyclerView.LayoutManager mLayoutManager2;
    private OrderBill o;

    String oid;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference();

    HashMap<String, DishPrice> prices = new HashMap<>();

    DatabaseReference refOrder;
    DatabaseReference refMenu;
    DatabaseReference refOrderDetails;

    ArrayList<String> keys;
    ArrayList<DishOrder> splitlist;

    Context c;
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_confirm_payment);
        c = this;
        splitlist = new ArrayList<>();
        keys = new ArrayList<>();

        refOrder = FirebaseDatabase.getInstance().getReference().child("Order");;
        refMenu = FirebaseDatabase.getInstance().getReference().child("Menu");
        refOrderDetails = FirebaseDatabase.getInstance().getReference().child("OrderDetails");

        TextView orderid = (TextView) findViewById(R.id.orderid_confirm);
        TextView table = (TextView) findViewById(R.id.tableid_confirm);
        TextView time = (TextView) findViewById(R.id.time_confirm);
        TextView total = (TextView) findViewById(R.id.total_bill1);
        Button confirm = (Button) findViewById(R.id.confirm_payment);

        confirm.setOnClickListener(onConfirmClick);
        Intent intent = getIntent();
        name = intent.getExtras().getString("dishname");

        o = new OrderBill();
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            o = (OrderBill) extras.getSerializable("order");
            if (o == null) {
                oid = Integer.toString(confirmOrder.order_id);
                populateForCustomer(oid);
            }
            else if (o.id != null) {

                Toast.makeText(getApplication(), "hello", Toast.LENGTH_SHORT).show();
                oid = o.id;
                orderid.setText("Order ID: " + o.id);
                table.setText("Table: " + o.table);
                time.setText("Time: " + o.time);
                total.setText("Total Price: $500 ");
                //total.setText("Total Price: " + o.getTotal());
                populateForManager();
            }
        }
    }


    public void onFeedbackClick(View v) {

        Intent i = new Intent(c, Feedback.class);
        i.putExtra("orderid", o.id);
        i.putExtra("order", o);
        startActivity(i);
    }
    public void populateForCustomer(String id) {
        findViewById(R.id.confirm_payment).setVisibility(View.INVISIBLE);
        findViewById(R.id.payment_feedback).setVisibility(View.VISIBLE);

//        refMenu = ref.child("Menu");
//        refOrder = ref.child("Order");
//        refOrderDetails = ref.child("OrderDetails");

        final TextView orderid = (TextView) findViewById(R.id.orderid_confirm);
        final TextView table = (TextView) findViewById(R.id.tableid_confirm);
        final TextView time = (TextView) findViewById(R.id.time_confirm);
        final TextView total = (TextView) findViewById(R.id.total_bill1);

        refMenu.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Price", valueOf(dataSnapshot.getChildrenCount()));
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    DishPrice post = postSnapshot.getValue(DishPrice.class);
                    Log.e("Get Data", post.toString());
                    prices.put(post.dishName, post);
                }

                refOrder.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            String id = postSnapshot.child("id").getValue(String.class);
                            int status = postSnapshot.child("status").getValue(Integer.class);
                            String time1 = postSnapshot.child("timestamp").getValue(String.class);
                            String table1 = postSnapshot.child("tableID").getValue(String.class);
                            if (status != 4) {
                                o = new OrderBill();

                                o.id = id;
                                o.time = time1;
                                o.D = new ArrayList<>();

                                orderid.setText("Order ID: " + id);
                                table.setText("Table: " + table1);
                                time.setText("Time: " + time1);

                            }
                        }

                        refOrderDetails.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.e("OrderDetails", valueOf(dataSnapshot.getChildrenCount()));

                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                    String id = postSnapshot.child("orderid").getValue(String.class);
                                    String dishname = postSnapshot.child("dishname").getValue(String.class);
                                    int quantity = postSnapshot.child("servings").getValue(int.class);
                                    int status = postSnapshot.child("status").getValue(int.class);
                                    Log.e("Dish", id);
                                    Log.e("Dish", dishname);
                                    if (status != 4 && id.equals(oid)) {
                                        //System.out.println("hello world");
                                        DishOrder d = new DishOrder();

                                        d.quantity = quantity;
                                        DishPrice p = new DishPrice();
                                        if (prices.containsKey(dishname)) {
                                            p = prices.get(dishname);
                                            d.price = p.price;
                                          //  Toast.makeText(ConfirmPayment.this, d.price, Toast.LENGTH_SHORT).show();
                                            d.dishName = p.dishName;
                                            o.D.add(d);

                                        } else {
                                            d.quantity = quantity;
                                            d.dishName = dishname;
                                            o.D.add(d);
                                        }
                                    }

                                }

                                refMenu.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        if(dataSnapshot.exists()){
                                            String dishnameMenu = dataSnapshot.child(name).child("dishName").getValue().toString();
                                            Toast.makeText(ConfirmPayment.this, dishnameMenu, Toast.LENGTH_SHORT).show();
                                            String priceMenu = dataSnapshot.child(dishnameMenu).child("price").getValue().toString();
//                                            orderid.setText("Order ID: " + o.id);
                                             table.setText("Table: " + o.table);
//                                            time.setText("Time: " + o.time);
                                            total.setText("Total Price: " + priceMenu);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

//
//                                if (mAdapter != null) {
//                                    mAdapter.mDataset = o.getD();
//                                    mAdapter.notifyDataSetChanged();
//                                } else {
//                                    mRecyclerView = (RecyclerView) findViewById(R.id.billRecView);
//
//                                    mLayoutManager = new LinearLayoutManager(c);
//                                    mRecyclerView.setLayoutManager(mLayoutManager);
//                                    mAdapter = new BillAdapter(o.getD(), R.layout.order_bill_view, c);
//                                    mRecyclerView.setAdapter(mAdapter);
//
//                                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//                                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(c);
//                                    DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
//                                            mRecyclerView.getContext(),
//                                            mLayoutManager.getOrientation()
//                                    );
//
//                                    mRecyclerView.addItemDecoration(mDividerItemDecoration);
//
//                                    mRecyclerView2 = (RecyclerView) findViewById(R.id.billRecView2);
//
//                                    mLayoutManager2 = new LinearLayoutManager(c);
//                                    mRecyclerView2.setLayoutManager(mLayoutManager2);
//                                    mAdapter2 = new BillAdapter(splitlist, R.layout.order_bill_view, c);
//                                    mRecyclerView2.setAdapter(mAdapter2);
//
//                                    mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
//                                    LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(c);
//                                    DividerItemDecoration mDividerItemDecoration2 = new DividerItemDecoration(
//                                            mRecyclerView2.getContext(),
//                                            mLayoutManager2.getOrientation()
//                                    );
//                                    mRecyclerView2.addItemDecoration(mDividerItemDecoration2);
//
//                                    mAdapter.setAdapter2(mAdapter2);
//                                    mAdapter2.setAdapter2(mAdapter);
//                                    mAdapter.setBill1((TextView) findViewById(R.id.bill1));
//                                    mAdapter.setBill2((TextView) findViewById(R.id.bill2));
//                                    mAdapter2.setBill1((TextView) findViewById(R.id.bill2));
//                                    mAdapter2.setBill2((TextView) findViewById(R.id.bill1));
//                                }

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
            public void onCancelled(DatabaseError error) {
                Log.w("Test", "Failed to read value.", error.toException());
            }
        });

    }


    public void populateForManager() {
        mRecyclerView = (RecyclerView) findViewById(R.id.billRecView);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new BillAdapter(o.getD(), R.layout.order_bill_view, this);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration mDividerItemDecoration = new DividerItemDecoration(
                mRecyclerView.getContext(),
                mLayoutManager.getOrientation()
        );

        mRecyclerView.addItemDecoration(mDividerItemDecoration);

        mRecyclerView2 = (RecyclerView) findViewById(R.id.billRecView2);

        mLayoutManager2 = new LinearLayoutManager(this);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        mAdapter2 = new BillAdapter(splitlist, R.layout.order_bill_view, this);
        mRecyclerView2.setAdapter(mAdapter2);

        mRecyclerView2.setItemAnimator(new DefaultItemAnimator());
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(this);
        DividerItemDecoration mDividerItemDecoration2 = new DividerItemDecoration(
                mRecyclerView2.getContext(),
                mLayoutManager2.getOrientation()
        );
        mRecyclerView2.addItemDecoration(mDividerItemDecoration2);

        mAdapter.setAdapter2(mAdapter2);
        mAdapter2.setAdapter2(mAdapter);
        mAdapter.setBill1((TextView) findViewById(R.id.bill1));
        mAdapter.setBill2((TextView) findViewById(R.id.bill2));
        mAdapter2.setBill1((TextView) findViewById(R.id.bill2));
        mAdapter2.setBill2((TextView) findViewById(R.id.bill1));
    }


    /*public void onClickSplit(View v) {
        mAdapter2.tap = true;
        mAdapter.tap = true;
        findViewById(R.id.billRecView2).setVisibility(View.VISIBLE);
        findViewById(R.id.split_button).setVisibility(View.INVISIBLE);
        findViewById(R.id.payment_merge).setVisibility(View.VISIBLE);
        findViewById(R.id.bill2).setVisibility(View.VISIBLE);

    }*/
    public void onClickMerge(View v) {
        mAdapter2.tap = false;
        mAdapter.tap = false;
        findViewById(R.id.billRecView2).setVisibility(View.INVISIBLE);
        //findViewById(R.id.split_button).setVisibility(View.VISIBLE);
        findViewById(R.id.payment_merge).setVisibility(View.INVISIBLE);
        findViewById(R.id.bill2).setVisibility(View.INVISIBLE);
        mAdapter.mDataset = o.D;
        mAdapter.notifyDataSetChanged();
        mAdapter2.mDataset.clear();
        mAdapter2.notifyDataSetChanged();
        int total = o.getTotal();
        TextView bill = (TextView) findViewById(R.id.bill1);
        bill.setText(String.valueOf(total));
    }
    public View.OnClickListener onConfirmClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            refOrderDetails = ref.child("OrderDetails");
            v.setEnabled(false);
            refOrderDetails.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.e("OrderDetails", valueOf(dataSnapshot.getChildrenCount()));

                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        String id = postSnapshot.child("orderid").getValue(String.class);
                        int status = postSnapshot.child("status").getValue(int.class);
                        if (status != 4 && id.equals(oid)) {
                            keys.add(postSnapshot.getKey());

                        }
                    }

                    if (!keys.isEmpty()) {
                        for (int i = 0; i < keys.size(); i++) {
                            ref.child("OrderDetails").child(keys.get(i)).child("status").setValue(4);
                        }
                    }
                    ref.child("Order").child(oid).child("status").setValue(4);
                    Toast.makeText(c, "Payment successfuly saved in database", Toast.LENGTH_SHORT);

                    ref.child("Receipt").child(oid).child("paid").setValue(1);
                    ref.child("Receipt").child(oid).child("orderid").setValue(String.valueOf(o.id));
                    ref.child("Receipt").child(oid).child("totalamount").setValue(o.getTotal());
                    finish();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    Log.w("Test", "Failed to read value.", error.toException());
                }
            });


        }

    };

}
