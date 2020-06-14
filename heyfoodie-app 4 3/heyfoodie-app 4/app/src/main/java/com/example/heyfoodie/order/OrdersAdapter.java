package com.example.heyfoodie.order;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.heyfoodie.R;
import java.util.ArrayList;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    private ArrayList<OrderBill> mDataset;
    private Context c;
    private int itemLayout;
    OrdersViewHolder holder;

    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        public TextView orderid;
        public TextView tableid;
        public TextView totalbill;
        Context c;

        public OrdersViewHolder(View v) {
            super(v);
            orderid = (TextView) v.findViewById(R.id.orderid_text);
            tableid = (TextView) v.findViewById(R.id.tableid_text);
            totalbill = (TextView) v.findViewById(R.id.totalbill_text);
        }

        public void setValues(OrderBill o) {
            orderid.setText("OrderBill ID: " + o.id);
            tableid.setText("Table: " + o.table);
            totalbill.setText("Total Bill: " + o.getTotal());
        }
    }
    public OrdersAdapter(ArrayList<OrderBill> data, int layout, Context c) {
        mDataset = new ArrayList<OrderBill>(data);
        itemLayout = layout;
        this.c = c;
    }

    @Override
    public OrdersAdapter.OrdersViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(itemLayout, parent, false);
        holder = new OrdersViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(OrdersViewHolder holder, int position) {

        if (mDataset != null && holder != null) {
            holder.setValues(mDataset.get(position));
            final int x = position;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Opening OrderBill Details", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(c, ConfirmPayment.class);
                    i.putExtra("order", mDataset.get(x));
                    v.getContext().startActivity(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}