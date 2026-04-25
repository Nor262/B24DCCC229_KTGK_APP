package com.example.myapplication.models;

public class Staff extends Employee {
    private int workingDays;

    public Staff(String id, String fullName, double basicSalary, int workingDays) {
        super(id, fullName, basicSalary);
        this.setWorkingDays(workingDays);
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(int workingDays) {
        if (workingDays >= 0 && workingDays <= 26) {
            this.workingDays = workingDays;
        } else {
            throw new IllegalArgumentException("Working days must be between 0 and 26");
        }
    }

    @Override
    public double tinhluong() {
        return getBasicSalary() * (workingDays / 26.0);
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Role: Staff");
        System.out.println("Working Days: " + workingDays);
    }
}
