package com.example.navigationdrawer.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Country implements Serializable {

    private String cName;
    private String cCapital;



    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public String getcCapital() {
        return cCapital;
    }

    public void setcCapital(String cCapital) {
        this.cCapital = cCapital;
    }

    public Country(String cName, String cCapital) {
        this.cName = cName;
        this.cCapital = cCapital;
    }

    @NonNull
    @Override
    public String toString() {
        return cName;
    }
}
