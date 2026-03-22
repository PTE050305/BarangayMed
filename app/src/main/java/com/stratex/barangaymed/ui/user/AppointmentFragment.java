package com.stratex.barangaymed.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.stratex.barangaymed.adapter.AppointmentAdapter;
import com.stratex.barangaymed.data.model.Appointment;
import com.stratex.barangaymed.databinding.FragmentAppointmentBinding;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class AppointmentFragment extends Fragment {
    private FragmentAppointmentBinding binding;
    private AppointmentViewModel viewModel;
    private SessionManager sessionManager;
    private List<Appointment> allAppointments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        sessionManager = new SessionManager(requireContext());

        binding.rvAppointments.setLayoutManager(new LinearLayoutManager(requireContext()));

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Pending"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Approved"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("History"));

        viewModel.getAppointments(sessionManager.getUserId(), sessionManager.getUserRole()).observe(getViewLifecycleOwner(), appointments -> {
            if (appointments != null) {
                allAppointments = appointments;
                filterAppointments(0);
            }
        });

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                filterAppointments(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void filterAppointments(int position) {
        List<Appointment> filteredList = new ArrayList<>();
        for (Appointment appt : allAppointments) {
            if (position == 0 && appt.getStatus().equalsIgnoreCase("pending")) {
                filteredList.add(appt);
            } else if (position == 1 && appt.getStatus().equalsIgnoreCase("approved")) {
                filteredList.add(appt);
            } else if (position == 2 && (appt.getStatus().equalsIgnoreCase("completed") || appt.getStatus().equalsIgnoreCase("cancelled"))) {
                filteredList.add(appt);
            }
        }
        binding.rvAppointments.setAdapter(new AppointmentAdapter(filteredList, appt -> {
            // Handle item click
        }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}