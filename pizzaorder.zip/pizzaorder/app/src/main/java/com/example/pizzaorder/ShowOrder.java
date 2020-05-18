package com.example.pizzaorder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.pizzaorder.Module.Order;

import java.util.ArrayList;

public class ShowOrder extends AppCompatActivity implements OnClickListener  {

    EditText editTextClientNumber;
    TextView textViewPizaa,textViewNbSlices,textViewAmount;
    Button btnFind;
    ArrayList<Order> listOfOrders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        initialize();
    }


    private void initialize() {
        editTextClientNumber=findViewById(R.id.editTextClientnumber);
        textViewPizaa=findViewById(R.id.textViewPizza);
        textViewNbSlices=findViewById(R.id.textViewnbSlices);
        textViewAmount=findViewById(R.id.textViewAmount);
        btnFind=findViewById(R.id.btnFind);
        btnFind.setOnClickListener(this);
        listOfOrders=(ArrayList<Order>)getIntent().getExtras().getSerializable("order");
    }


    @Override
    public void onClick(View v) {
        int clientNumber=Integer.valueOf(editTextClientNumber.getText().toString());

        for(Order order:listOfOrders)
            if(clientNumber == order.getClientNumber()) {
                String pizza = order.getPizza();
                int nbSlices = order.getNbSlices();
                float amount= order.getAmount();
                textViewPizaa.setText("Pizza: " + pizza);
                textViewNbSlices.setText("NbSlices: " + String.valueOf(nbSlices));
                textViewAmount.setText("Amount: " + String.valueOf(amount));

            }

    }
}
