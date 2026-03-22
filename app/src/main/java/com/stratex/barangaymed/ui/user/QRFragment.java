package com.stratex.barangaymed.ui.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.stratex.barangaymed.databinding.FragmentQrBinding;
import com.stratex.barangaymed.utils.QRCodeGenerator;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

public class QRFragment extends Fragment {
    private FragmentQrBinding binding;
    private AppointmentViewModel viewModel;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQrBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        sessionManager = new SessionManager(requireContext());

        viewModel.getNearestUpcomingAppointment(sessionManager.getUserId()).observe(getViewLifecycleOwner(), appointment -> {
            if (appointment != null && appointment.getStatus().equalsIgnoreCase("approved")) {
                binding.tvQRInfo.setText("Appointment: " + appointment.getPatientName() + "\nDate: " + appointment.getDate());
                Bitmap bitmap = QRCodeGenerator.generateQRCode(String.valueOf(appointment.getId()));
                if (bitmap != null) {
                    binding.ivQRCode.setImageBitmap(bitmap);
                }
            } else {
                binding.tvQRInfo.setText("No approved appointment found for check-in.");
                binding.ivQRCode.setImageBitmap(null);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}