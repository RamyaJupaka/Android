package com.example.hayfoodie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminScreen extends AppCompatActivity implements View.OnClickListener {

    private CardView cardAddEmp , cardAddTable, cardUpdateEmp,cardIngredients,cardAddInventory,cardViewAddItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

       cardAddEmp = (CardView )findViewById(R.id.cardAddEmp);
        cardAddTable = (CardView )findViewById(R.id.cardAddTable);
        cardUpdateEmp = (CardView )findViewById(R.id.cardUpdateEmp);
        cardIngredients = (CardView )findViewById(R.id.cardIngredients);
        cardAddInventory = (CardView )findViewById(R.id.cardAddInventory);
        cardViewAddItem = (CardView) findViewById(R.id.cardAddItem);

        cardAddEmp.setOnClickListener(this);
        cardAddTable.setOnClickListener(this);
        cardUpdateEmp.setOnClickListener(this);
        cardIngredients.setOnClickListener(this);
        cardAddInventory.setOnClickListener(this);
        cardViewAddItem.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent i ;
        switch (v.getId())
        {
            case R.id.cardAddEmp: i = new Intent(this,AddEmp.class);startActivity(i);break;
            case R.id.cardAddTable: i = new Intent(this,AddTable.class);startActivity(i);break;
            case R.id.cardAddItem: i = new Intent(this,AddItem.class);startActivity(i);break;
            case R.id.cardAddInventory: i = new Intent(this,UpdateEmployee.class);startActivity(i);break;
            case R.id.cardIngredients: i = new Intent(this,UpdateEmployee.class);startActivity(i);break;

        }
    }


}
