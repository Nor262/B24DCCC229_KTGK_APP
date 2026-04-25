package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemEmployeeBinding;
import com.example.myapplication.models.Employee;
import com.example.myapplication.models.Intern;
import com.example.myapplication.models.Manager;
import com.example.myapplication.models.Staff;

import com.example.myapplication.util.CurrencyUtils;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.EmployeeViewHolder> {

    private ArrayList<Employee> employeeList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Employee employee);
    }

    public EmployeeAdapter(ArrayList<Employee> employeeList, OnItemClickListener listener) {
        this.employeeList = employeeList;
        this.listener = listener;
    }

    public void updateData(ArrayList<Employee> newEmployeeList) {
        this.employeeList = newEmployeeList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemEmployeeBinding binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new EmployeeViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.binding.tvIdName.setText(employee.getId() + " - " + employee.getFullName());
        
        String role = "";
        if (employee instanceof Staff) role = "Staff";
        else if (employee instanceof Manager) role = "Manager";
        else if (employee instanceof Intern) role = "Intern";
        holder.binding.tvRole.setText("Role: " + role);

        holder.binding.tvSalary.setText(CurrencyUtils.formatVND(employee.tinhluong()));

        holder.itemView.setOnClickListener(v -> listener.onItemClick(employee));
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }

    static class EmployeeViewHolder extends RecyclerView.ViewHolder {
        ItemEmployeeBinding binding;

        public EmployeeViewHolder(@NonNull ItemEmployeeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
