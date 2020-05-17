package com.example.hayfoodie.Model;

import android.text.TextUtils;
import android.widget.EditText;

public class Functions {
    public static boolean isEmpty(EditText editText, String text){
        String input = editText.getText().toString();
        if(TextUtils.isEmpty(input)){
            editText.setError("The " +  text + " fields cannot be empty");
            return false;
        }
        return true;
    }
}
