package com.example.tableview__practice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tableview__practice.model.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView[] listTextView;
    int widgets[] = {R.id.textViewData,R.id.textViewDataMorning,R.id.textViewDataAfternoon};
    TextView clickedTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {
        Task[] listOfTasks = new Task[widgets.length];

//        listOfTasks[0] = new Task(0,"Monday class", Color.MAGENTA);
//        listOfTasks[1] = new Task(1,"Sport", Color.RED);
//        listOfTasks[2] = new Task(2,"Eating");

        listOfTasks[0] = new Task("Monday class", Color.MAGENTA);
        listOfTasks[1] = new Task("Sport", Color.RED);
        listOfTasks[2] = new Task("Eating",Color.RED);

        listTextView = new TextView[widgets.length];


        for(int i =0; i < widgets.length; i++){


           listTextView[i] = findViewById(widgets[i]);
           listTextView[i].setOnClickListener(this);

            listTextView[i].setText(listOfTasks[i].getTaskDescription());
            listTextView[i].setTextColor(listOfTasks[i].getTaskTextColor());

        }

    }

    @Override
    public void onClick(View v) {
        TextView textView = (TextView)v;
        clickedTV = textView;

        Intent intent = new Intent(this,ChangeActivity.class);
        intent.putExtra("task",textView.getText().toString());
        startActivityForResult(intent,1 );


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1 && resultCode == RESULT_OK){
            String taskDesc = (String)data.getStringExtra("task");
            clickedTV.setText(taskDesc);
        }
    }
}
