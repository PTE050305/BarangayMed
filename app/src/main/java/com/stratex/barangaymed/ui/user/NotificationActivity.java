package com.stratex.barangaymed.ui.user;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.stratex.barangaymed.adapter.NotificationAdapter;
import com.stratex.barangaymed.databinding.ActivityNotificationBinding;
import com.stratex.barangaymed.utils.SessionManager;
import com.stratex.barangaymed.viewmodel.NotificationViewModel;

public class NotificationActivity extends AppCompatActivity {
    private ActivityNotificationBinding binding;
    private NotificationViewModel notificationViewModel;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        sessionManager = new SessionManager(this);

        binding.rvNotifications.setLayoutManager(new LinearLayoutManager(this));

        notificationViewModel.getNotifications(sessionManager.getUserId()).observe(this, notifications -> {
            if (notifications != null) {
                binding.rvNotifications.setAdapter(new NotificationAdapter(notifications));
            } else {
                Toast.makeText(this, "Failed to load notifications", Toast.LENGTH_SHORT).show();
            }
        });
    }
}