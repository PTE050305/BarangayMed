package com.stratex.barangaymed.ui.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stratex.barangaymed.R;
import com.stratex.barangaymed.databinding.ActivityAdminMainBinding;

public class AdminMainActivity extends AppCompatActivity {
    private ActivityAdminMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (savedInstanceState == null) {
            replaceFragment(new AdminDashboardFragment());
        }

        binding.adminBottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.admin_nav_dashboard) {
                replaceFragment(new AdminDashboardFragment());
            } else if (itemId == R.id.admin_nav_appointments) {
                replaceFragment(new AdminAppointmentFragment());
            } else if (itemId == R.id.admin_nav_qr) {
                replaceFragment(new QRScannerFragment());
            } else if (itemId == R.id.admin_nav_users) {
                replaceFragment(new UsersFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.admin_nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }
}