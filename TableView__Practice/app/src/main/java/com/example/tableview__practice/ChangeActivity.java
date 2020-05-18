package com.example.tableview__practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChangeActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextTaskDesc;
    Button btnReturn;
    String taskDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        initialize();
    }

    private void initialize() {
        editTextTaskDesc = findViewById(R.id.editTextTaskDesc);
        btnReturn = findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(this);

        taskDesc = getIntent().getStringExtra("task");
        editTextTaskDesc.setText(taskDesc);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();

        if(taskDesc.equalsIgnoreCase(editTextTaskDesc.getText().toString())){
            setResult(RESULT_CANCELED,intent);
        }else{
            intent.putExtra("task",editTextTaskDesc.getText().toString());
            setResult(RESULT_OK,intent);


        }
        finish();
    }
}
