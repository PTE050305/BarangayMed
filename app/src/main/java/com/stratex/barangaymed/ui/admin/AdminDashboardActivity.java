package com.stratex.barangaymed.ui.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.stratex.barangaymed.databinding.ActivityAdminDashboardBinding;
import com.stratex.barangaymed.ui.auth.LoginActivity;
import com.stratex.barangaymed.utils.SessionManager;

public class AdminDashboardActivity extends AppCompatActivity {
    private ActivityAdminDashboardBinding binding;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        binding.tvAdminWelcome.setText("Admin: " + sessionManager.getUserName());

        binding.cardManageAppointments.setOnClickListener(v -> {
            startActivity(new Intent(this, ManageAppointmentsActivity.class));
        });

        binding.cardScanQR.setOnClickListener(v -> {
            startActivity(new Intent(this, QRScannerActivity.class));
        });

        binding.cardPatientRecords.setOnClickListener(v -> {
            startActivity(new Intent(this, PatientRecordsActivity.class));
        });

        binding.cardAdminLogout.setOnClickListener(v -> {
            sessionManager.logoutUser();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}