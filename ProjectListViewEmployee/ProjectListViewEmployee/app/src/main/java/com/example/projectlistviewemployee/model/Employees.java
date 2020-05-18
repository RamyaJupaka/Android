package com.example.projectlistviewemployee.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.PortUnreachableException;
import java.util.EnumMap;

public class Employees implements Comparable{
    private String id;
    private String lastName;
    private String telephone;
    private float salary;
    private String email;

    public Employees(String id, String lastName, String telephone, float salary,String email) {
        this.id = id;
        this.lastName = lastName;
        this.telephone = telephone;
        this.salary = salary;
        this.email = email;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    @Override
    public String toString() {
        return this.lastName;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Employees other = (Employees)obj;
        if(this.id.equalsIgnoreCase(other.getId()))
            return true;
        else
            return false;
    }

    @Override
    public int compareTo(Object o){
        Employees other = (Employees)o;
        if(this.lastName.compareTo(other.lastName)>0)
            return 1;
        else if (this.lastName.compareTo(other.lastName)==0)
            return 0;
        else
            return -1;
     }

}
