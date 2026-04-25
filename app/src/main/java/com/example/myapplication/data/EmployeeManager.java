package com.example.myapplication.data;

import com.example.myapplication.models.Employee;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class EmployeeManager {
    private static EmployeeManager instance;
    private ArrayList<Employee> employeeList;

    private EmployeeManager() {
        employeeList = new ArrayList<>();
    }

    public static EmployeeManager getInstance() {
        if (instance == null) {
            instance = new EmployeeManager();
        }
        return instance;
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    public void updateEmployee(Employee employee) {
        for (int i = 0; i < employeeList.size(); i++) {
            if (employeeList.get(i).getId().equals(employee.getId())) {
                employeeList.set(i, employee);
                return;
            }
        }
    }

    public void deleteEmployee(String id) {
        employeeList.removeIf(emp -> emp.getId().equals(id));
    }

    public ArrayList<Employee> getAllEmployees() {
        return employeeList;
    }

    public Employee getEmployeeById(String id) {
        for (Employee emp : employeeList) {
            if (emp.getId().equals(id)) {
                return emp;
            }
        }
        return null;
    }

    public ArrayList<Employee> searchByName(String name) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee emp : employeeList) {
            if (emp.getFullName().toLowerCase().contains(name.toLowerCase())) {
                result.add(emp);
            }
        }
        return result;
    }

    public ArrayList<Employee> filterByType(Class<?> type) {
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee emp : employeeList) {
            if (type.isInstance(emp)) {
                result.add(emp);
            }
        }
        return result;
    }

    public void sortSalaryDescending() {
        Collections.sort(employeeList, new Comparator<Employee>() {
            @Override
            public int compare(Employee e1, Employee e2) {
                return Double.compare(e2.tinhluong(), e1.tinhluong());
            }
        });
    }

    public Employee getHighestSalaryEmployee() {
        if (employeeList.isEmpty()) return null;
        Employee maxEmp = employeeList.get(0);
        for (Employee emp : employeeList) {
            if (emp.tinhluong() > maxEmp.tinhluong()) {
                maxEmp = emp;
            }
        }
        return maxEmp;
    }
}
