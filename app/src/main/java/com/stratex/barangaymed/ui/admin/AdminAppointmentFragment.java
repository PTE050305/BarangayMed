package com.stratex.barangaymed.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.stratex.barangaymed.adapter.AppointmentAdapter;
import com.stratex.barangaymed.data.model.Appointment;
import com.stratex.barangaymed.databinding.FragmentAdminAppointmentBinding;
import com.stratex.barangaymed.utils.Constants;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class AdminAppointmentFragment extends Fragment {
    private FragmentAdminAppointmentBinding binding;
    private AppointmentViewModel viewModel;
    private SessionManager sessionManager;
    private List<Appointment> allAppointments = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminAppointmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        sessionManager = new SessionManager(requireContext());

        binding.rvAdminAppointments.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getAppointments(sessionManager.getUserId(), sessionManager.getUserRole()).observe(getViewLifecycleOwner(), appointments -> {
            if (appointments != null) {
                allAppointments = appointments;
                filterAppointments(binding.adminTabLayout.getSelectedTabPosition());
            }
        });

        binding.adminTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { filterAppointments(tab.getPosition()); }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void filterAppointments(int position) {
        List<Appointment> filteredList = new ArrayList<>();
        for (Appointment appt : allAppointments) {
            if (position == 0 && appt.getStatus().equalsIgnoreCase("pending")) filteredList.add(appt);
            else if (position == 1 && appt.getStatus().equalsIgnoreCase("approved")) filteredList.add(appt);
            else if (position == 2 && appt.getStatus().equalsIgnoreCase("completed")) filteredList.add(appt);
        }

        binding.rvAdminAppointments.setAdapter(new AppointmentAdapter(filteredList, appt -> {
            showActionDialog(appt);
        }));
    }

    private void showActionDialog(Appointment appt) {
        String[] actions = {"Approve", "Reject", "Complete", "Cancel"};
        new AlertDialog.Builder(requireContext())
                .setTitle("Manage Appointment")
                .setItems(actions, (dialog, which) -> {
                    String status = "";
                    switch (which) {
                        case 0: status = "approved"; break;
                        case 1: status = "denied"; break;
                        case 2: status = "completed"; break;
                        case 3: status = "cancelled"; break;
                    }
                    viewModel.updateStatus(appt.getId(), status);
                    Toast.makeText(requireContext(), "Status updated to " + status, Toast.LENGTH_SHORT).show();
                })
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}