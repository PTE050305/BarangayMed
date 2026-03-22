package com.stratex.barangaymed.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.stratex.barangaymed.data.local.AppDatabase;
import com.stratex.barangaymed.data.local.MedicalRecordDao;
import com.stratex.barangaymed.data.local.UserDao;
import com.stratex.barangaymed.data.model.MedicalRecord;
import com.stratex.barangaymed.data.model.User;

import java.util.List;

public class UserRepository {
    private UserDao userDao;
    private MedicalRecordDao medicalRecordDao;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
        medicalRecordDao = db.medicalRecordDao();
    }

    public LiveData<List<MedicalRecord>> getPatientRecords() {
        return medicalRecordDao.getAllRecords();
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }
}