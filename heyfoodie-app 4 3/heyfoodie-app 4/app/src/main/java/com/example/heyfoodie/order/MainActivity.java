package com.example.heyfoodie.order;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
{
    int[] images={R.drawable.burger,R.drawable.chicken,R.drawable.chocolate,R.drawable.coconut,R.drawable.fish,R.drawable.fries,R.drawable.hotdog,R.drawable.pineapple,R.drawable.pizza,R.drawable.soup,R.drawable.stawberry};

    ArrayList<Menu> menuItems=new ArrayList<Menu>();

    Order order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_menulist);


        ListView listview=(ListView)findViewById(R.id.simpleListView);
        final CustomAdaptor cad=new CustomAdaptor(this,menuItems);
        listview.setAdapter(cad);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                startActivity(new Intent(MainActivity.this, setQuantity.class));
            }
        });

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Menu");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               menuItems.clear();
                for(DataSnapshot child:dataSnapshot.getChildren())
                {
                    Menu obj=child.getValue(Menu.class);
                    menuItems.add(obj);
                    System.out.print(obj.getDishName());
                    System.out.print(obj.getType());
                    System.out.print(obj.getPrice());
                    cad.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void redirectToQuantity(View v)
    {
        startActivity(new Intent(this,setQuantity.class));
    }
    public void redirectToCart(View v)
    {
        startActivity(new Intent(MainActivity.this,cartListView.class));
    }
    class CustomAdaptor extends BaseAdapter
    {
        private Activity mContext;
        private ArrayList<Menu> mItems;
        public CustomAdaptor(Activity context, ArrayList<Menu> list) {
            mContext = context;
            mItems = list;
        }
        @Override
        public int getCount() {
            return  menuItems.size();
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

            convertView = getLayoutInflater().inflate(R.layout.order_item, null);

            TextView dishName = (TextView) convertView.findViewById(R.id.textView_dishName);
            TextView dishDesc = (TextView) convertView.findViewById(R.id.textView2_description);
            TextView price = (TextView) convertView.findViewById(R.id.dishPrice);
            ImageButton addcart = (ImageButton) convertView.findViewById(R.id.imageView);
            ImageView pic = (ImageView)convertView.findViewById(R.id.pic);

            final String dishNamme=menuItems.get(position).getDishName();
            addcart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    SharedPreferences sharedPreferences=getSharedPreferences("shared preferences",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("dishName",dishNamme);
                    editor.commit();
                    order = new Order();
                    order.setDishName(dishNamme);
                    startActivity(new Intent(MainActivity.this,setQuantity.class));
                }
            });
            dishName.setText(menuItems.get(position).getDishName());
            String type="Its a "+menuItems.get(position).getType();
            dishDesc.setText(type);
            String pric="Price= "+menuItems.get(position).getPrice();
            price.setText(pric);
            pic.setImageResource(images[position]);



            return convertView;
        }
    }
    public class ViewHolder {

        ImageButton addcart;
        TextView dishName;
        TextView dishDesc;
        TextView price;
        ImageView pic;
    }
}
