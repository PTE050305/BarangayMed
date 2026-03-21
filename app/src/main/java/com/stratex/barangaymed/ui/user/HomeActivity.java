package com.stratex.barangaymed.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.stratex.barangaymed.R;
import com.stratex.barangaymed.data.model.Appointment;
import com.stratex.barangaymed.databinding.ActivityHomeBinding;
import com.stratex.barangaymed.ui.auth.LoginActivity;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding binding;
    private SessionManager sessionManager;
    private AppointmentViewModel appointmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);

        binding.tvWelcome.setText("Good morning, " + sessionManager.getUserName());

        // Observe Dynamic Upcoming Appointment
        appointmentViewModel.getNearestUpcomingAppointment(sessionManager.getUserId()).observe(this, appointment -> {
            if (appointment != null) {
                binding.cardUpcoming.setVisibility(View.VISIBLE);
                binding.tvNoAppointment.setVisibility(View.GONE);
                
                binding.tvApptType.setText(appointment.getPatientName() + " - " + appointment.getStatus());
                binding.tvApptDate.setText(appointment.getDate());
                binding.tvApptTime.setText(appointment.getTime());
                binding.tvBadgeDate.setText(appointment.getDate()); // Simple date for badge
            } else {
                binding.cardUpcoming.setVisibility(View.GONE);
                binding.tvNoAppointment.setVisibility(View.VISIBLE);
            }
        });

        // Book Appointment Button
        binding.cardBook.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, BookAppointmentActivity.class));
        });

        // View Details in Upcoming Card
        binding.btnViewDetails.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, AppointmentListActivity.class));
        });

        // Bottom Navigation
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                return true;
            } else if (itemId == R.id.nav_appointments) {
                startActivity(new Intent(this, AppointmentListActivity.class));
                return true;
            } else if (itemId == R.id.nav_qr) {
                startActivity(new Intent(this, AppointmentListActivity.class));
                return true;
            } else if (itemId == R.id.nav_profile) {
                sessionManager.logoutUser();
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return true;
            }
            return false;
        });
        
        binding.bottomNav.setSelectedItemId(R.id.nav_home);
    }
}