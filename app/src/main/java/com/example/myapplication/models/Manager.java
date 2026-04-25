package com.example.myapplication.models;

public class Manager extends Employee {
    private double allowance;

    public Manager(String id, String fullName, double basicSalary, double allowance) {
        super(id, fullName, basicSalary);
        this.setAllowance(allowance);
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        if (allowance >= 0) {
            this.allowance = allowance;
        } else {
            throw new IllegalArgumentException("Allowance cannot be negative");
        }
    }

    @Override
    public double tinhluong() {
        return getBasicSalary() + allowance;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Role: Manager");
        System.out.println("Allowance: " + allowance);
    }
}
