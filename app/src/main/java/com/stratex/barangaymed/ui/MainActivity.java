package com.stratex.barangaymed.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stratex.barangaymed.R;
import com.stratex.barangaymed.databinding.ActivityMainBinding;
import com.stratex.barangaymed.ui.user.AppointmentFragment;
import com.stratex.barangaymed.ui.user.HomeFragment;
import com.stratex.barangaymed.ui.user.ProfileFragment;
import com.stratex.barangaymed.ui.user.QRFragment;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Default fragment
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment());
        }

        binding.bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.nav_appointments) {
                replaceFragment(new AppointmentFragment());
            } else if (itemId == R.id.nav_qr) {
                replaceFragment(new QRFragment());
            } else if (itemId == R.id.nav_profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }
}