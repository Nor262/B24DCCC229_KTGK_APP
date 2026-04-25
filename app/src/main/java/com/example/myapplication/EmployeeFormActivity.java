package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.data.EmployeeManager;
import com.example.myapplication.databinding.ActivityEmployeeFormBinding;
import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Intern;
import com.example.myapplication.models.Manager;
import com.example.myapplication.models.Staff;

public class EmployeeFormActivity extends AppCompatActivity {

    private ActivityEmployeeFormBinding binding;
    private EmployeeManager employeeManager;
    private Employee currentEmployee = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEmployeeFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        employeeManager = EmployeeManager.getInstance();

        binding.btnBack.setOnClickListener(v -> finish());

        setupSpinner();

        String empId = getIntent().getStringExtra("EMP_ID");
        if (empId != null) {
            currentEmployee = employeeManager.getEmployeeById(empId);
            if (currentEmployee != null) {
                populateForm();
            }
        }

        binding.btnSave.setOnClickListener(v -> saveEmployee());
    }

    private void setupSpinner() {
        String[] types = {"Staff", "Manager", "Intern"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spEmployeeType.setAdapter(adapter);

        binding.spEmployeeType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateDynamicField(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateDynamicField(int typePosition) {
        switch (typePosition) {
            case 0: // Staff
                binding.tilDynamic.setHint("Working Days (0-26)");
                break;
            case 1: // Manager
                binding.tilDynamic.setHint("Allowance");
                break;
            case 2: // Intern
                binding.tilDynamic.setHint("Working Hours");
                break;
        }
    }

    private void populateForm() {
        binding.etId.setText(currentEmployee.getId());
        binding.etId.setEnabled(false); // Can't change ID
        binding.etName.setText(currentEmployee.getFullName());
        binding.etBasicSalary.setText(String.valueOf(currentEmployee.getBasicSalary()));

        if (currentEmployee instanceof Staff) {
            binding.spEmployeeType.setSelection(0);
            binding.etDynamic.setText(String.valueOf(((Staff) currentEmployee).getWorkingDays()));
        } else if (currentEmployee instanceof Manager) {
            binding.spEmployeeType.setSelection(1);
            binding.etDynamic.setText(String.valueOf(((Manager) currentEmployee).getAllowance()));
        } else if (currentEmployee instanceof Intern) {
            binding.spEmployeeType.setSelection(2);
            binding.etDynamic.setText(String.valueOf(((Intern) currentEmployee).getWorkingHours()));
        }
        binding.spEmployeeType.setEnabled(false); // Can't change type
    }

    private void saveEmployee() {
        String id = binding.etId.getText().toString().trim();
        String name = binding.etName.getText().toString().trim();
        String basicSalaryStr = binding.etBasicSalary.getText().toString().trim();
        String dynamicStr = binding.etDynamic.getText().toString().trim();

        if (id.isEmpty() || name.isEmpty() || basicSalaryStr.isEmpty() || dynamicStr.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double basicSalary = Double.parseDouble(basicSalaryStr);
            int type = binding.spEmployeeType.getSelectedItemPosition();
            Employee employee = null;

            if (type == 0) { // Staff
                int days = Integer.parseInt(dynamicStr);
                employee = new Staff(id, name, basicSalary, days);
            } else if (type == 1) { // Manager
                double allowance = Double.parseDouble(dynamicStr);
                employee = new Manager(id, name, basicSalary, allowance);
            } else if (type == 2) { // Intern
                int hours = Integer.parseInt(dynamicStr);
                employee = new Intern(id, name, basicSalary, hours);
            }

            if (currentEmployee == null) {
                if (employeeManager.getEmployeeById(id) != null) {
                    Toast.makeText(this, "ID already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                employeeManager.addEmployee(employee);
                Toast.makeText(this, "Added successfully", Toast.LENGTH_SHORT).show();
            } else {
                employeeManager.updateEmployee(employee);
                Toast.makeText(this, "Updated successfully", Toast.LENGTH_SHORT).show();
            }

            finish();
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid number format", Toast.LENGTH_SHORT).show();
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
