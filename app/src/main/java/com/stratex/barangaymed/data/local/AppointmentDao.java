package com.stratex.barangaymed.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.stratex.barangaymed.data.model.Appointment;

import java.util.List;

@Dao
public interface AppointmentDao {
    @Insert
    void insert(Appointment appointment);

    @Query("SELECT * FROM appointments WHERE userId = :userId")
    LiveData<List<Appointment>> getAppointmentsForUser(int userId);

    @Query("SELECT * FROM appointments")
    LiveData<List<Appointment>> getAllAppointments();

    @Update
    void update(Appointment appointment);

    @Query("UPDATE appointments SET status = :status WHERE id = :appointmentId")
    void updateStatus(int appointmentId, String status);
    
    @Query("SELECT * FROM appointments WHERE id = :id LIMIT 1")
    Appointment getAppointmentById(int id);

    @Query("SELECT * FROM appointments WHERE userId = :userId AND (status = 'approved' OR status = 'pending') ORDER BY date ASC, time ASC LIMIT 1")
    LiveData<Appointment> getNearestUpcomingAppointment(int userId);

    @Query("SELECT COUNT(*) FROM appointments")
    LiveData<Integer> getTotalCount();

    @Query("SELECT COUNT(*) FROM appointments WHERE status = 'pending'")
    LiveData<Integer> getPendingCount();

    @Query("SELECT COUNT(*) FROM appointments WHERE status = 'approved'")
    LiveData<Integer> getApprovedCount();

    @Query("SELECT COUNT(*) FROM appointments WHERE status = 'completed'")
    LiveData<Integer> getCompletedCount();
}