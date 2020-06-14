package com.example.heyfoodie.order;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heyfoodie.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;



public class Time extends AppCompatActivity {
    private static long START_TIME_IN_MILLIS;
    private TextView mTextViewCountDown;
    private TextView mTextViewNotif;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = 60000;
    ArrayList<OrderDetails> orderDetail=new ArrayList<OrderDetails>();
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_activity_time);
        final String orderid="1";
        mTextViewCountDown = findViewById(R.id.countdown);
        mTextViewNotif = findViewById(R.id.eTime);
        startTimer();
        Intent intent = getIntent();
        name = intent.getStringExtra("dishname");

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("OrderDetails");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                orderDetail.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    OrderDetails obj = child.getValue(OrderDetails.class);
                    if(obj.orderid.equals(orderid)) {
                        orderDetail.add(obj);
                    }

                }
                int sizeo=orderDetail.size();
                int timeCount=0;
                for(int i=0;i<sizeo;i++)
                {
                    timeCount=timeCount+orderDetail.get(i).getEstimatedtime();
                }
                Toast.makeText(getApplicationContext(), "total estimated time= "+timeCount+"minutes", Toast.LENGTH_LONG).show();
                int minutes = 1;
                long milliseconds = timeCount * 60000;
                START_TIME_IN_MILLIS=milliseconds;
                mTimeLeftInMillis = START_TIME_IN_MILLIS;
//                mTextViewCountDown = findViewById(R.id.countdown);
//                mTextViewNotif = findViewById(R.id.eTime);
//                startTimer();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {
                mTimerRunning = false;
                mTextViewNotif.setText("It's Time!");
            }
        }.start();

        mTimerRunning = true;
    }

    private void resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }
    private void updateCountDownText() {
        Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        Toast.makeText(this, "time " + timeLeftFormatted, Toast.LENGTH_SHORT).show();

        mTextViewCountDown.setText(timeLeftFormatted);
    }
    public void updateorder(View v)
    {
        Toast.makeText(getApplicationContext(), "Redirect to Update Order", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Time.this,Time.class));
    }
    public void cancelOrder(View v)
    {
        Toast.makeText(getApplicationContext(), "Redirect to Cancel Order", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Time.this,MainActivity.class));

    }
    public void viewBill(View v)
    {

        Intent i = new Intent(Time.this,ConfirmPayment.class);
        i.putExtra("customerview", "true")
        .putExtra("dishname",name);
        startActivity(i);
    }
}


