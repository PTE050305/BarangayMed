package com.stratex.barangaymed.ui.auth;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.stratex.barangaymed.databinding.ActivityRegisterBinding;
import com.stratex.barangaymed.viewmodel.AuthViewModel;

import java.util.Calendar;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private AuthViewModel authViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);

        binding.etBirthdate.setOnClickListener(v -> showDatePicker());

        binding.btnRegister.setOnClickListener(v -> {
            String name = binding.etFullName.getText().toString().trim();
            String birthdate = binding.etBirthdate.getText().toString().trim();
            String address = binding.etAddress.getText().toString().trim();
            String phone = binding.etPhoneNumber.getText().toString().trim();
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();
            String confirmPassword = binding.etConfirmPassword.getText().toString().trim();

            if (name.isEmpty() || birthdate.isEmpty() || address.isEmpty() || phone.isEmpty() || 
                email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else if (!password.equals(confirmPassword)) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            } else {
                authViewModel.register(name, email, password, birthdate, address, phone).observe(this, user -> {
                    if (user != null) {
                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        binding.tvLogin.setOnClickListener(v -> finish());
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            // Format with leading zeros YYYY-MM-DD
            String formattedDate = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            binding.etBirthdate.setText(formattedDate);
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
    }
}