package com.stratex.barangaymed.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.stratex.barangaymed.data.model.Notification;
import com.stratex.barangaymed.data.repository.NotificationRepository;

import java.util.List;

public class NotificationViewModel extends AndroidViewModel {
    private NotificationRepository notificationRepository;

    public NotificationViewModel(@NonNull Application application) {
        super(application);
        notificationRepository = new NotificationRepository(application);
    }

    public LiveData<List<Notification>> getNotifications(int userId) {
        return notificationRepository.getNotifications(userId);
    }
}