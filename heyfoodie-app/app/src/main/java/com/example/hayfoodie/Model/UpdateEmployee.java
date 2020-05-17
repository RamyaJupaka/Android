package com.example.hayfoodie.Model;

public class UpdateEmployee {

    private String name;
    private String password;
    private int salary;
    private String type;


    public UpdateEmployee() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public UpdateEmployee(String name, String password, int salary, String type) {
        this.name = name;
        this.password = password;
        this.salary = salary;
        this.type = type;
    }

}
