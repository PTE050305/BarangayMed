package com.stratex.barangaymed.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.stratex.barangaymed.databinding.FragmentDatetimeSelectionBinding;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

import java.util.Locale;

public class DateTimeSelectionFragment extends Fragment {
    private FragmentDatetimeSelectionBinding binding;
    private String selectedService = "";
    private String selectedDate = "";
    private String selectedTime = "";
    private AppointmentViewModel viewModel;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDatetimeSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);
        sessionManager = new SessionManager(requireContext());

        if (getArguments() != null) {
            selectedService = getArguments().getString("service");
        }

        binding.calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            // Format with leading zeros YYYY-MM-DD
            selectedDate = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, dayOfMonth);
            checkConfirmEnabled();
        });

        binding.chipGroupTime.setOnCheckedStateChangeListener((group, checkedIds) -> {
            if (!checkedIds.isEmpty()) {
                int checkedId = checkedIds.get(0);
                if (checkedId == binding.chip9am.getId()) selectedTime = "09:00 AM";
                else if (checkedId == binding.chip10am.getId()) selectedTime = "10:00 AM";
                else if (checkedId == binding.chip11am.getId()) selectedTime = "11:00 AM";
                else if (checkedId == binding.chip1pm.getId()) selectedTime = "01:00 PM";
                else if (checkedId == binding.chip2pm.getId()) selectedTime = "02:00 PM";
                checkConfirmEnabled();
            }
        });

        binding.btnConfirm.setOnClickListener(v -> {
            // Passing userId, patientName (from session), serviceType, date, and time
            viewModel.bookAppointment(
                    sessionManager.getUserId(), 
                    sessionManager.getUserName(), 
                    selectedService, 
                    selectedDate, 
                    selectedTime
            );
            Toast.makeText(requireContext(), "Appointment Booked Successfully!", Toast.LENGTH_SHORT).show();
            requireActivity().finish(); // Go back to Home
        });
    }

    private void checkConfirmEnabled() {
        binding.btnConfirm.setEnabled(!selectedDate.isEmpty() && !selectedTime.isEmpty());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}