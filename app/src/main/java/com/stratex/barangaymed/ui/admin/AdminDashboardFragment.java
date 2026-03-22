package com.stratex.barangaymed.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.stratex.barangaymed.databinding.FragmentAdminDashboardBinding;
import com.stratex.barangaymed.ui.auth.LoginActivity;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

public class AdminDashboardFragment extends Fragment {
    private FragmentAdminDashboardBinding binding;
    private AppointmentViewModel viewModel;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAdminDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        sessionManager = new SessionManager(requireContext());

        viewModel.getTotalCount().observe(getViewLifecycleOwner(), count -> binding.tvTotalAppts.setText(String.valueOf(count)));
        viewModel.getPendingCount().observe(getViewLifecycleOwner(), count -> binding.tvPendingAppts.setText(String.valueOf(count)));
        viewModel.getApprovedCount().observe(getViewLifecycleOwner(), count -> binding.tvApprovedAppts.setText(String.valueOf(count)));
        viewModel.getCompletedCount().observe(getViewLifecycleOwner(), count -> binding.tvCompletedAppts.setText(String.valueOf(count)));

        binding.btnAdminLogout.setOnClickListener(v -> {
            sessionManager.logoutUser();
            Intent intent = new Intent(requireContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}