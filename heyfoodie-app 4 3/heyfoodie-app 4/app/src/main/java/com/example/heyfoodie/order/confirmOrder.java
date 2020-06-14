package com.example.heyfoodie.order;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.heyfoodie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;

/*This class is responsible for showing pop up when Confirm Order is clicked by user in cart page*/

public class confirmOrder extends AppCompatDialogFragment {
    public static int order_id = 1;
    String dishName;

    int tableid = 1, servingSize;

    FirebaseDatabase appeteaserDb = FirebaseDatabase.getInstance();
    final DatabaseReference appDb = FirebaseDatabase.getInstance().getReference("");


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.order_orderconfirmpopup,null);
        builder.setView(view);
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(confirmOrder.this.getActivity(), cartListView.class));
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                appDb.child("cart").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange( DataSnapshot dataSnapshot) {
                        for(DataSnapshot dsCart: dataSnapshot.getChildren())
                        {
                            cart c = dsCart.getValue(cart.class);
                            servingSize = c.getQuantity();
                            dishName = c.getAddedname();

                        }

                        if(dishName != null)
                        {
                            String ts = new Timestamp(System.currentTimeMillis()).toString();
                            Order ord = new Order(Integer.toString(order_id), Integer.toString(tableid), ts, 0,dishName);
                            OrderDetails od = new OrderDetails(dishName, 0, Integer.toString(order_id), 0, servingSize, 0);

                            DatabaseReference addRow;

                            addRow = appDb.child("Order").push();
                            addRow.setValue(ord);
                            addRow = appDb.child("OrderDetails").push();
                            addRow.setValue(od);


                            order_id++;
                            appDb.child("cart").removeValue();
                            Toast.makeText(getContext(), ord.getDishName(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(confirmOrder.this.getActivity(),Time.class).
                                    putExtra("dishname",dishName));
                        }
                    }

                    @Override
                    public void onCancelled( DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });


            }
        });

        return builder.create();
    }
}
