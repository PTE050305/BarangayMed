package com.stratex.barangaymed.ui.user;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.stratex.barangaymed.databinding.ActivityBookAppointmentBinding;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {
    private ActivityBookAppointmentBinding binding;
    private AppointmentViewModel appointmentViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        sessionManager = new SessionManager(this);

        binding.etDate.setOnClickListener(v -> showDatePicker());
        binding.etTime.setOnClickListener(v -> showTimePicker());

        binding.btnBook.setOnClickListener(v -> {
            String patientName = binding.etPatientName.getText().toString().trim();
            String date = binding.etDate.getText().toString().trim();
            String time = binding.etTime.getText().toString().trim();

            if (!patientName.isEmpty() && !date.isEmpty() && !time.isEmpty()) {
                appointmentViewModel.bookAppointment(sessionManager.getUserId(), patientName, date, time);
                Toast.makeText(this, "Appointment Booking Requested", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            binding.etDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        new TimePickerDialog(this, (view, hourOfDay, minute) -> {
            binding.etTime.setText(hourOfDay + ":" + minute);
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true).show();
    }
}