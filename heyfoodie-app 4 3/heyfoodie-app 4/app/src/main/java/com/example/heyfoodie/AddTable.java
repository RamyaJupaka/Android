package com.example.heyfoodie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.heyfoodie.Model.Table;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddTable extends AppCompatActivity  {

    private Spinner spinner1;
    ArrayList<Table> t = new ArrayList<Table>();
    Spinner spnStatus;
    static ArrayList<Long> empid = new ArrayList<Long>();
    EditText txtCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_table);
        spnStatus = (Spinner) findViewById(R.id.TableStatusDropDown);
        txtCapacity = (EditText) findViewById(R.id.AddTableCapacity);
        addItemsOnSpinner();
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner() {

        spinner1 = (Spinner) findViewById(R.id.TableStatusDropDown);
        List<String> list = new ArrayList<String>();
        list.add("Free");
        list.add("Booked");
        list.add("Occupied");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(dataAdapter);
    }

    public void btnBlick(View v) {

        boolean go = true;

        if (txtCapacity.getText().toString().length() <= 0) {
            txtCapacity.setError("Capacity is required");
            go = false;

        }

        if (txtCapacity.getText().toString().length() > 0
                && Integer.parseInt(txtCapacity.getText().toString()) <= 0) {
            txtCapacity.setError("Capacity should be greater than 0");
            go = false;
        }
        if (go) {

            DatabaseReference mDatabase1 = FirebaseDatabase.getInstance().getReference();
            mDatabase1.child("Ids").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                    if (dataSnapshot != null) {

                        if (dataSnapshot != null) {
                            Toast.makeText(AddTable.this,
                                    "IDZ=" + String.valueOf(dataSnapshot.getValue(Long.class)),
                                    Toast.LENGTH_SHORT).show();

                            empid.add(dataSnapshot.getValue(Long.class));

                        }
                    }

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });
            DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
            if (t.size() == 0) {

                ref1.child("Ids").child("Tableid").setValue(1);
            }


            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
            Query ref = null;
            mDatabase.child("Table").addListenerForSingleValueEvent(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Table table = new Table(String.valueOf(empid.get(1)), spnStatus.getSelectedItem().toString(), txtCapacity.getText().toString());
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    reference.child("Table").child(String.valueOf(empid.get(1))).setValue(table);
                    reference.child("Ids").child("Tableid").setValue(empid.get(1) + 1);

                    Toast.makeText(AddTable.this, "Table has been added Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddTable.this, AdminScreen.class);
                    empid.clear();
                    startActivity(intent);


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }


            });

            mDatabase.child("Table").addChildEventListener(new ChildEventListener() {
                public void onChildAdded(DataSnapshot dataSnapshot, String previousKey) {
                    Table item = dataSnapshot.getValue(Table.class);
                    t.add(item);

                }

                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

}

