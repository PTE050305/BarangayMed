package com.stratex.barangaymed.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.stratex.barangaymed.data.model.Appointment;
import com.stratex.barangaymed.data.model.MedicalRecord;
import com.stratex.barangaymed.data.model.Notification;
import com.stratex.barangaymed.data.model.User;

@Database(entities = {User.class, Appointment.class, Notification.class, MedicalRecord.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract UserDao userDao();
    public abstract AppointmentDao appointmentDao();
    public abstract NotificationDao notificationDao();
    public abstract MedicalRecordDao medicalRecordDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "barangay_med_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}