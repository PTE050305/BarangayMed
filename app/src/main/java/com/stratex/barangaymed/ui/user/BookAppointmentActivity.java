package com.stratex.barangaymed.ui.user;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.stratex.barangaymed.R;
import com.stratex.barangaymed.databinding.ActivityBookAppointmentBinding;

public class BookAppointmentActivity extends AppCompatActivity {
    private ActivityBookAppointmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        binding.toolbar.setNavigationOnClickListener(v -> onBackPressed());

        if (savedInstanceState == null) {
            String selectedService = getIntent().getStringExtra("selected_service");
            
            if (selectedService != null && !selectedService.isEmpty()) {
                // If service is already selected from Quick Services, skip to Date/Time selection
                DateTimeSelectionFragment fragment = new DateTimeSelectionFragment();
                Bundle bundle = new Bundle();
                bundle.putString("service", selectedService);
                fragment.setArguments(bundle);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.book_nav_host, fragment);
                transaction.commit();
            } else {
                // Otherwise, start from the beginning (Service Selection)
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.book_nav_host, new ServiceSelectionFragment());
                transaction.commit();
            }
        }
    }
}