package com.example.heyfoodie.order;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.heyfoodie.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class KitchenAdapter extends RecyclerView.Adapter<KitchenAdapter.ViewHoder> {

    private List<Order> menuList;

    public KitchenAdapter(List<Order> menuList) {
        this.menuList = menuList;
    }

    public class ViewHoder extends RecyclerView.ViewHolder {

        TextView dishName, dishStatus,dishTableId;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.kitchen_dishName);
            dishStatus = itemView.findViewById(R.id.kitchen_status);
            dishTableId = itemView.findViewById(R.id.kitchen_tableId);
        }

    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kithen_layout,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHoder holder, int position) {

        Order data = menuList.get(position);
        holder.dishName.setText("Dish Name: " +data.getDishName());
        holder.dishStatus.setText("Status: " + data.getStatus() + "");
        holder.dishTableId.setText("Table Id: " + data.getTabletID());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.dishStatus.setText("Status: server");
                holder.itemView.setBackgroundColor(Color.parseColor("#567845"));
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }


}



