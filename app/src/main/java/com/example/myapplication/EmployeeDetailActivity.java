package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.EmployeeManager;
import com.example.myapplication.databinding.ActivityEmployeeDetailBinding;
import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Intern;
import com.example.myapplication.models.Manager;
import com.example.myapplication.models.Staff;
import com.example.myapplication.util.CurrencyUtils;

public class EmployeeDetailActivity extends AppCompatActivity {

    private ActivityEmployeeDetailBinding binding;
    private EmployeeManager employeeManager;
    private Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        employeeManager = EmployeeManager.getInstance();
        
        binding.btnBack.setOnClickListener(v -> finish());

        String empId = getIntent().getStringExtra("EMP_ID");
        if (empId != null) {
            employee = employeeManager.getEmployeeById(empId);
        }

        if (employee == null) {
            Toast.makeText(this, "Employee not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        displayDetails();

        binding.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(this, EmployeeFormActivity.class);
            intent.putExtra("EMP_ID", employee.getId());
            startActivity(intent);
        });

        binding.btnDelete.setOnClickListener(v -> showDeleteConfirmation());
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh details in case it was edited
        if (employee != null) {
            employee = employeeManager.getEmployeeById(employee.getId());
            if (employee != null) {
                displayDetails();
            }
        }
    }

    private void displayDetails() {
        binding.tvDetailId.setText("ID: " + employee.getId());
        binding.tvDetailName.setText("Name: " + employee.getFullName());
        binding.tvDetailBasicSalary.setText(CurrencyUtils.formatVND(employee.getBasicSalary()));
        binding.tvDetailActualSalary.setText(CurrencyUtils.formatVND(employee.tinhluong()));

        if (employee instanceof Staff) {
            binding.tvDetailRole.setText("Role: Staff");
            binding.tvDetailDynamic.setText("Working Days: " + ((Staff) employee).getWorkingDays());
        } else if (employee instanceof Manager) {
            binding.tvDetailRole.setText("Role: Manager");
            binding.tvDetailDynamic.setText("Allowance: " + ((Manager) employee).getAllowance());
        } else if (employee instanceof Intern) {
            binding.tvDetailRole.setText("Role: Intern");
            binding.tvDetailDynamic.setText("Working Hours: " + ((Intern) employee).getWorkingHours());
        }
    }

    private void showDeleteConfirmation() {
        new AlertDialog.Builder(this)
                .setTitle("Delete Employee")
                .setMessage("Are you sure you want to delete this employee?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        employeeManager.deleteEmployee(employee.getId());
                        Toast.makeText(EmployeeDetailActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
