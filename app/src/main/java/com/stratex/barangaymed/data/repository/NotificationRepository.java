package com.stratex.barangaymed.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.stratex.barangaymed.data.local.AppDatabase;
import com.stratex.barangaymed.data.local.NotificationDao;
import com.stratex.barangaymed.data.model.Notification;

import java.util.List;

public class NotificationRepository {
    private NotificationDao notificationDao;

    public NotificationRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        notificationDao = db.notificationDao();
    }

    public LiveData<List<Notification>> getNotifications(int userId) {
        return notificationDao.getNotificationsForUser(userId);
    }
}