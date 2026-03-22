package com.stratex.barangaymed.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.stratex.barangaymed.adapter.UserAdapter;
import com.stratex.barangaymed.databinding.FragmentUsersBinding;
import com.stratex.barangaymed.viewmodel.UserViewModel;

public class UsersFragment extends Fragment {
    private FragmentUsersBinding binding;
    private UserViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUsersBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        binding.rvUsers.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getAllUsers().observe(getViewLifecycleOwner(), users -> {
            if (users != null) {
                binding.rvUsers.setAdapter(new UserAdapter(users, user -> {
                    Toast.makeText(requireContext(), "Selected: " + user.getName(), Toast.LENGTH_SHORT).show();
                    // Could navigate to a detailed User History fragment/activity here
                }));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}