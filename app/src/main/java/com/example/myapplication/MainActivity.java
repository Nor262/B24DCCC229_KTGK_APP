package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.data.EmployeeManager;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Intern;
import com.example.myapplication.models.Manager;
import com.example.myapplication.models.Staff;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EmployeeManager employeeManager;
    private EmployeeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        employeeManager = EmployeeManager.getInstance();

        setupRecyclerView();
        setupSearch();
        setupFilter();

        binding.fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EmployeeFormActivity.class);
            startActivity(intent);
        });

        binding.btnSort.setOnClickListener(v -> {
            employeeManager.sortSalaryDescending();
            adapter.updateData(employeeManager.getAllEmployees());
            binding.spFilter.setSelection(0); // Reset filter
        });

        binding.btnMaxSalary.setOnClickListener(v -> {
            Employee maxEmp = employeeManager.getHighestSalaryEmployee();
            if (maxEmp != null) {
                ArrayList<Employee> maxList = new ArrayList<>();
                maxList.add(maxEmp);
                adapter.updateData(maxList);
            } else {
                Toast.makeText(this, "No employees found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.updateData(employeeManager.getAllEmployees());
    }

    private void setupRecyclerView() {
        binding.rvEmployees.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeeAdapter(employeeManager.getAllEmployees(), employee -> {
            Intent intent = new Intent(MainActivity.this, EmployeeDetailActivity.class);
            intent.putExtra("EMP_ID", employee.getId());
            startActivity(intent);
        });
        binding.rvEmployees.setAdapter(adapter);
    }

    private void setupSearch() {
        binding.etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<Employee> results = employeeManager.searchByName(s.toString());
                adapter.updateData(results);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void setupFilter() {
        String[] filters = {"All", "Staff", "Manager", "Intern"};
        ArrayAdapter<String> filterAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, filters);
        filterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spFilter.setAdapter(filterAdapter);

        binding.spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Employee> results;
                switch (position) {
                    case 1:
                        results = employeeManager.filterByType(Staff.class);
                        break;
                    case 2:
                        results = employeeManager.filterByType(Manager.class);
                        break;
                    case 3:
                        results = employeeManager.filterByType(Intern.class);
                        break;
                    default:
                        results = employeeManager.getAllEmployees();
                        break;
                }
                adapter.updateData(results);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
