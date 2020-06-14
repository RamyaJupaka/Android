package com.example.heyfoodie.order;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.heyfoodie.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Feedback extends AppCompatActivity {
    OrderBill o;
    String orderid;

    TextView ordertext;
    RatingBar rbar;
    EditText email;
    EditText para;

    String emailid;

    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference ref = db.getReference();

    Context c = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_feedback);
        Bundle extras = getIntent().getExtras();

        orderid = extras.getString("orderid");
        o = (OrderBill) extras.getSerializable("order");

        TextView ordertext = findViewById(R.id.orderid_feedback);
        ordertext.setText("OrderID: " + orderid);

        rbar = (RatingBar) findViewById(R.id.ratingBar_feedback);
        email = (EditText) findViewById(R.id.email_feedback);
        para = (EditText) findViewById(R.id.editText2);
        Button submit = (Button) findViewById(R.id.submit_feedback);
        submit.setOnClickListener(
                new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) { emailid = email.getText().toString();
             /* if (emailid.matches("")) {
                Toast.makeText(v.getContext(), "Email is compulsory!", Toast.LENGTH_SHORT).show();
              } else*/
             {
              Toast.makeText(v.getContext(), "Feedback Submitted!", Toast.LENGTH_SHORT).show();

              ref.child("Feedback").child(orderid).child("orderid").setValue(orderid);
              ref.child("Feedback").child(orderid).child("rating").setValue(rbar.getRating());
              ref.child("Feedback").child(orderid).child("email").setValue(emailid);
              ref.child("Feedback").child(orderid).child("comments").setValue(para.getText().toString());

             Intent i = new Intent(android.content.Intent.ACTION_SEND);
              i.setType("plain/text");
              i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{emailid});

              i.putExtra(Intent.EXTRA_SUBJECT, " Receipt for your Order");
              String message = "Greetings Customer,\n" + "Hope you enjoyed your meal at our restaurant. \n" +
                                                      "Your receipt for your order is given below: \n\n";
               for (int j = 0; j < o.D.size(); j++) {
                   message = message + "Dish: " + o.D.get(j).dishName + " \tPrice: " + o.D.get(j).getTotal() + "\n";
               }
               message = message + "Total Bill: " + o.getTotal() + "\n\nWe hope to see you again!\n\nRegards";
               i.putExtra(Intent.EXTRA_TEXT, message);
               try {
                   startActivity(Intent.createChooser(i, "Select your email client: "));
               } catch (android.content.ActivityNotFoundException ex) {
                   Toast.makeText(c, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
               }
              }
                }
                }
        );
    }
}
