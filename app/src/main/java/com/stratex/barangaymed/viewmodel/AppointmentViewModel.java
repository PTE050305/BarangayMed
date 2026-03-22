package com.stratex.barangaymed.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.stratex.barangaymed.data.model.Appointment;
import com.stratex.barangaymed.data.repository.AppointmentRepository;

import java.util.List;

public class AppointmentViewModel extends AndroidViewModel {
    private AppointmentRepository appointmentRepository;

    public AppointmentViewModel(@NonNull Application application) {
        super(application);
        appointmentRepository = new AppointmentRepository(application);
    }

    public LiveData<List<Appointment>> getAppointments(int userId, String role) {
        return appointmentRepository.getAppointments(userId, role);
    }

    public void bookAppointment(int userId, String patientName, String date, String time) {
        appointmentRepository.bookAppointment(userId, patientName, date, time);
    }

    public void updateStatus(int appointmentId, String status) {
        appointmentRepository.updateAppointmentStatus(appointmentId, status);
    }

    public LiveData<Appointment> getNearestUpcomingAppointment(int userId) {
        return appointmentRepository.getNearestUpcomingAppointment(userId);
    }

    public LiveData<Integer> getTotalCount() { return appointmentRepository.getTotalCount(); }
    public LiveData<Integer> getPendingCount() { return appointmentRepository.getPendingCount(); }
    public LiveData<Integer> getApprovedCount() { return appointmentRepository.getApprovedCount(); }
    public LiveData<Integer> getCompletedCount() { return appointmentRepository.getCompletedCount(); }
}