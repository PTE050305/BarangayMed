package com.stratex.barangaymed.ui.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.stratex.barangaymed.adapter.PatientRecordAdapter;
import com.stratex.barangaymed.databinding.ActivityPatientRecordsBinding;
import com.stratex.barangaymed.viewmodel.UserViewModel;

public class PatientRecordsActivity extends AppCompatActivity {
    private ActivityPatientRecordsBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientRecordsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        binding.rvPatientRecords.setLayoutManager(new LinearLayoutManager(this));

        userViewModel.getPatientRecords().observe(this, records -> {
            if (records != null) {
                binding.rvPatientRecords.setAdapter(new PatientRecordAdapter(records));
            } else {
                Toast.makeText(this, "Failed to load patient records", Toast.LENGTH_SHORT).show();
            }
        });
    }
}