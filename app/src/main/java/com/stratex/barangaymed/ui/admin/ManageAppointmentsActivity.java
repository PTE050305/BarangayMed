package com.stratex.barangaymed.ui.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.stratex.barangaymed.adapter.AppointmentAdapter;
import com.stratex.barangaymed.databinding.ActivityManageAppointmentsBinding;
import com.stratex.barangaymed.utils.Constants;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

public class ManageAppointmentsActivity extends AppCompatActivity {
    private ActivityManageAppointmentsBinding binding;
    private AppointmentViewModel appointmentViewModel;
    private SessionManager sessionManager;
    private AppointmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManageAppointmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        sessionManager = new SessionManager(this);

        binding.rvManageAppointments.setLayoutManager(new LinearLayoutManager(this));
        
        loadAppointments();
    }

    private void loadAppointments() {
        appointmentViewModel.getAppointments(sessionManager.getUserId(), sessionManager.getUserRole()).observe(this, appointments -> {
            if (appointments != null) {
                adapter = new AppointmentAdapter(appointments, appointment -> {
                    showActionDialog(appointment.getId());
                });
                binding.rvManageAppointments.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Failed to load appointments", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showActionDialog(int appointmentId) {
        String[] actions = {"Approve", "Cancel", "Complete"};
        new AlertDialog.Builder(this)
                .setTitle("Update Status")
                .setItems(actions, (dialog, which) -> {
                    String status = "";
                    switch (which) {
                        case 0: status = Constants.STATUS_APPROVED; break;
                        case 1: status = Constants.STATUS_CANCELLED; break;
                        case 2: status = Constants.STATUS_COMPLETED; break;
                    }
                    appointmentViewModel.updateStatus(appointmentId, status);
                    Toast.makeText(this, "Status updated to " + status, Toast.LENGTH_SHORT).show();
                    loadAppointments(); // Refresh list
                })
                .show();
    }
}