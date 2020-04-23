package com.example.final_project_quiz.model;

import android.text.TextUtils;
import android.widget.EditText;

public class Funtions {

    public static boolean isEmpty(EditText editText, String text){
        String input = editText.getText().toString();
        if(TextUtils.isEmpty(input)){
            editText.setError("The " +  text + " name cannot be empty");
            return false;
        }
        return true;
    }


}
