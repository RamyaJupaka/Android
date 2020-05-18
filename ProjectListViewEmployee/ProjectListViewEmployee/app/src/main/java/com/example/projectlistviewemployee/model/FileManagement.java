package com.example.projectlistviewemployee.model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileManagement {

    public static ArrayList<Employees> readFile(Context context, String filedName){

        ArrayList<Employees> list = new ArrayList<>();

        AssetManager assetManager = context.getResources().getAssets();

        try {
            InputStreamReader isr = new InputStreamReader(assetManager.open(filedName));

            BufferedReader br = new BufferedReader(isr);
            String oneLine = br.readLine();
            while (oneLine != null){
                StringTokenizer str = new StringTokenizer(oneLine,",");
                String empId = str.nextToken();
                String lastName = str.nextToken();
                String telePhone = str.nextToken();
                String salary = str.nextToken();
                String email = str.nextToken();
                Employees employees = new Employees(empId,lastName,telePhone,Float.parseFloat(salary),email);
                list.add(employees);
                oneLine = br.readLine();
            }
            br.close();
            isr.close();
        } catch (IOException e) {
            Log.d("File",e.getMessage());

        }
        return list;
    }
}
