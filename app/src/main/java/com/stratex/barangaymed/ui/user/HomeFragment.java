package com.stratex.barangaymed.ui.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.stratex.barangaymed.databinding.FragmentHomeBinding;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

public class HomeFragment extends Fragment {
    private FragmentHomeBinding binding;
    private SessionManager sessionManager;
    private AppointmentViewModel appointmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(requireContext());
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);

        binding.tvWelcome.setText("Good morning, " + sessionManager.getUserName());

        appointmentViewModel.getNearestUpcomingAppointment(sessionManager.getUserId()).observe(getViewLifecycleOwner(), appointment -> {
            if (appointment != null) {
                binding.cardUpcoming.setVisibility(View.VISIBLE);
                binding.cardNoAppointment.setVisibility(View.GONE);
                
                binding.tvApptType.setText(appointment.getServiceType());
                binding.tvApptDate.setText(appointment.getDate());
                binding.tvApptTime.setText(appointment.getTime());
                binding.tvBadgeDate.setText(appointment.getDate());
            } else {
                binding.cardUpcoming.setVisibility(View.GONE);
                binding.cardNoAppointment.setVisibility(View.VISIBLE);
            }
        });

        binding.btnBook.setOnClickListener(v -> {
            // Regular booking starts from service selection
            startActivity(new Intent(requireContext(), BookAppointmentActivity.class));
        });

        // Click listeners for Quick Services - redirects directly to Date/Time
        binding.cardGeneral.setOnClickListener(v -> startBookingWithService("General Check-up"));
        binding.cardPrenatal.setOnClickListener(v -> startBookingWithService("Prenatal"));
        binding.cardImmunization.setOnClickListener(v -> startBookingWithService("Immunization"));
        binding.cardDental.setOnClickListener(v -> startBookingWithService("Dental"));
        
        binding.btnViewDetails.setOnClickListener(v -> {
            // Logic to switch tab can be implemented via MainActivity
        });
    }

    private void startBookingWithService(String service) {
        Intent intent = new Intent(requireContext(), BookAppointmentActivity.class);
        intent.putExtra("selected_service", service);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}