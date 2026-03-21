package com.stratex.barangaymed.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.stratex.barangaymed.data.local.AppDatabase;
import com.stratex.barangaymed.data.local.AppointmentDao;
import com.stratex.barangaymed.data.model.Appointment;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppointmentRepository {
    private AppointmentDao appointmentDao;
    private ExecutorService executorService;

    public AppointmentRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        appointmentDao = db.appointmentDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Appointment>> getAppointments(int userId, String role) {
        if ("admin".equals(role)) {
            return appointmentDao.getAllAppointments();
        } else {
            return appointmentDao.getAppointmentsForUser(userId);
        }
    }

    public void bookAppointment(int userId, String patientName, String date, String time) {
        executorService.execute(() -> {
            Appointment appointment = new Appointment(userId, patientName, date, time, "pending");
            appointmentDao.insert(appointment);
        });
    }

    public void updateAppointmentStatus(int appointmentId, String status) {
        executorService.execute(() -> {
            appointmentDao.updateStatus(appointmentId, status);
        });
    }

    public LiveData<Appointment> getNearestUpcomingAppointment(int userId) {
        return appointmentDao.getNearestUpcomingAppointment(userId);
    }
}