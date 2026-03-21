package com.stratex.barangaymed.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.stratex.barangaymed.adapter.AppointmentAdapter;
import com.stratex.barangaymed.data.model.Appointment;
import com.stratex.barangaymed.databinding.ActivityAppointmentListBinding;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

import java.util.ArrayList;

public class AppointmentListActivity extends AppCompatActivity {
    private ActivityAppointmentListBinding binding;
    private AppointmentViewModel appointmentViewModel;
    private SessionManager sessionManager;
    private AppointmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAppointmentListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        sessionManager = new SessionManager(this);

        binding.rvAppointments.setLayoutManager(new LinearLayoutManager(this));
        
        loadAppointments();
    }

    private void loadAppointments() {
        appointmentViewModel.getAppointments(sessionManager.getUserId(), sessionManager.getUserRole()).observe(this, appointments -> {
            if (appointments != null) {
                adapter = new AppointmentAdapter(appointments, appointment -> {
                    Intent intent = new Intent(AppointmentListActivity.this, QRCodeActivity.class);
                    intent.putExtra("appointment_id", appointment.getId());
                    startActivity(intent);
                });
                binding.rvAppointments.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Failed to load appointments", Toast.LENGTH_SHORT).show();
            }
        });
    }
}