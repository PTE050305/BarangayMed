package com.stratex.barangaymed.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.stratex.barangaymed.data.model.Notification;

import java.util.List;

@Dao
public interface NotificationDao {
    @Insert
    void insert(Notification notification);

    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY createdAt DESC")
    LiveData<List<Notification>> getNotificationsForUser(int userId);
}