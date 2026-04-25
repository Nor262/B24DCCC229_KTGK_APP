package com.example.myapplication.models;

public class Intern extends Employee {
    private int workingHours;

    public Intern(String id, String fullName, double basicSalary, int workingHours) {
        super(id, fullName, basicSalary);
        this.setWorkingHours(workingHours);
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(int workingHours) {
        if (workingHours >= 0) {
            this.workingHours = workingHours;
        } else {
            throw new IllegalArgumentException("Working hours cannot be negative");
        }
    }

    @Override
    public double tinhluong() {
        return workingHours * 30000.0;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Role: Intern");
        System.out.println("Working Hours: " + workingHours);
    }
}
