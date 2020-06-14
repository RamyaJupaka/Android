package com.example.heyfoodie.order;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heyfoodie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class setQuantity extends AppCompatActivity {
    int quantity = 1;
    String dishname="";

    ArrayList<MenuItem> inc =new ArrayList<MenuItem>();
    ArrayList<Integer> menuQunatity = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_set_quantity);
        ListView listview=(ListView)findViewById(R.id.ingridientListView);
        final CustomAdaptor cad=new CustomAdaptor();
        listview.setAdapter(cad);
        ImageButton closeQuantity = (ImageButton) findViewById(R.id.closeQuantity);

        Button add=(Button)findViewById(R.id.add);


        SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
        dishname = sharedPreferences.getString(("dishName"), "");
        //dishname="Soup";
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("MenuItem").child(dishname);
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                        for(DataSnapshot ds: dataSnapshot.getChildren())
                        {
                            MenuItem MI = ds.getValue(MenuItem.class);
                            String displayname = MI.getDishName();
                            inc.add(MI);
                            cad.notifyDataSetChanged();
                        }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        closeQuantity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(setQuantity.this, MainActivity.class));
            }
        });

        final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Inventory");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                     ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                boolean invalid=false;
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                        Item i = ds.getValue(Item.class);
                                        for (final MenuItem ms : inc) {
                                            if (ms.getIngredientName().equals(i.getName())) {
                                                int requiredamount = (Integer.parseInt(ms.getQuantity())) * (quantity);
                                                if (requiredamount >= Integer.parseInt(i.getThreshold())) {
                                                    quantityInvalidPrompt qp = new quantityInvalidPrompt();
                                                    qp.show(getSupportFragmentManager(), "Quantity is Invalid");
                                                    invalid = true;
                                                    final long CURRENT_TIME_MILLIS = System.currentTimeMillis();
                                                    Date instant = new Date(CURRENT_TIME_MILLIS);
                                                    SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
                                                    String time = sdf.format( instant );
                                                    NotificationClass notify = new NotificationClass(i.getName(), time,false);
                                                    FirebaseDatabase.getInstance().getReference().child("notification").push().setValue(notify);
                                                }
                                                else
                                                {
                                                    int newQ= Integer.parseInt(i.quantity)- requiredamount;
                                                    Item item = new Item(i.getName(),i.getPrice(), String.valueOf(newQ),i.getThreshold());
                                                    ref2.child(i.getName()).setValue(item);
                                                }
                                            }
                                        }
                                    }
                                    if(invalid==false)
                                    {
                                           writeNewItemEntry(dishname, quantity);
                                            startActivity(new Intent(setQuantity.this, cartListView.class)
                                            .putExtra("dishname",dishname));
                                        }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });



            }
        });




    }
    public void increaseInteger(View view) {
        quantity = quantity + 1;
        display(quantity);

    }public void decreaseInteger(View view) {
        if(quantity !=0)
            quantity = quantity - 1;
        display(quantity);
    }

    private void display(int number) {
        TextView displayInteger = (TextView) findViewById(
                R.id.integer_number);
        displayInteger.setText("" + number);
    }

    private void writeNewItemEntry(String itemname, int q){
        cart entry = new cart(itemname, q);
        FirebaseDatabase.getInstance().getReference().child("cart").push().setValue(entry);
    }


    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return inc.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView =getLayoutInflater().inflate(R.layout.order_ingridient,null);
            TextView name=(TextView)findViewById(R.id.textView2_description);
            TextView dName=(TextView)convertView.findViewById(R.id.dishIngridient);
            dName.setText(inc.get(position).getIngredientName());
            name.setText(dishname);
            return convertView;
        }
    }
}
