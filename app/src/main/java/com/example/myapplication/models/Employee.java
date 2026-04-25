package com.example.myapplication.models;

import java.io.Serializable;

public abstract class Employee implements Serializable {
    private String id;
    private String fullName;
    private double basicSalary;

    public Employee(String id, String fullName, double basicSalary) {
        this.id = id;
        this.fullName = fullName;
        this.setBasicSalary(basicSalary); // Use setter to enforce validation
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(double basicSalary) {
        if (basicSalary > 0) {
            this.basicSalary = basicSalary;
        } else {
            throw new IllegalArgumentException("Basic salary must be greater than 0");
        }
    }

    // Abstract method for polymorphism
    public abstract double tinhluong();

    public void hienThiThongTin() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + fullName);
        System.out.println("Basic Salary: " + basicSalary);
        System.out.println("Actual Salary: " + tinhluong());
    }
}
