package com.stratex.barangaymed.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.stratex.barangaymed.databinding.ActivityLoginBinding;
import com.stratex.barangaymed.ui.MainActivity;
import com.stratex.barangaymed.ui.admin.AdminMainActivity;
import com.stratex.barangaymed.utils.Constants;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AuthViewModel;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AuthViewModel authViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            navigateBasedOnRole(sessionManager.getUserRole());
        }

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.etEmail.getText().toString().trim();
            String password = binding.etPassword.getText().toString().trim();

            if (!email.isEmpty() && !password.isEmpty()) {
                authViewModel.login(email, password).observe(this, user -> {
                    if (user != null) {
                        sessionManager.createLoginSession(user.getId(), user.getName(), user.getEmail(), user.getRole());
                        navigateBasedOnRole(user.getRole());
                    } else {
                        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            }
        });

        binding.tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        });
    }

    private void navigateBasedOnRole(String role) {
        if (Constants.ROLE_ADMIN.equals(role)) {
            // Redirect to the new Fragment-based Admin Main Activity
            startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
        } else {
            // Redirect to the new Fragment-based User Main Activity
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        finish();
    }
}