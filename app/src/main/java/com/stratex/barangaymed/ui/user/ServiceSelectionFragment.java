package com.stratex.barangaymed.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.card.MaterialCardView;
import com.stratex.barangaymed.R;
import com.stratex.barangaymed.databinding.FragmentServiceSelectionBinding;

public class ServiceSelectionFragment extends Fragment {
    private FragmentServiceSelectionBinding binding;
    private String selectedService = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentServiceSelectionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.cardGeneral.setOnClickListener(v -> selectCard(binding.cardGeneral, "General Check-up"));
        binding.cardPrenatal.setOnClickListener(v -> selectCard(binding.cardPrenatal, "Prenatal"));
        binding.cardImmunization.setOnClickListener(v -> selectCard(binding.cardImmunization, "Immunization"));
        binding.cardDental.setOnClickListener(v -> selectCard(binding.cardDental, "Dental"));

        binding.btnContinue.setOnClickListener(v -> {
            DateTimeSelectionFragment fragment = new DateTimeSelectionFragment();
            Bundle bundle = new Bundle();
            bundle.putString("service", selectedService);
            fragment.setArguments(bundle);

            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.book_nav_host, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
    }

    private void selectCard(MaterialCardView card, String service) {
        // Reset all cards
        binding.cardGeneral.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.lightGray));
        binding.cardPrenatal.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.lightGray));
        binding.cardImmunization.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.lightGray));
        binding.cardDental.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.lightGray));

        // Highlight selected
        card.setStrokeColor(ContextCompat.getColor(requireContext(), R.color.primaryRed));
        selectedService = service;
        binding.btnContinue.setEnabled(true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}