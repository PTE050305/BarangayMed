package com.stratex.barangaymed.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.stratex.barangaymed.data.local.AppDatabase;
import com.stratex.barangaymed.data.local.MedicalRecordDao;
import com.stratex.barangaymed.data.model.MedicalRecord;

import java.util.List;

public class UserRepository {
    private MedicalRecordDao medicalRecordDao;

    public UserRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        medicalRecordDao = db.medicalRecordDao();
    }

    public LiveData<List<MedicalRecord>> getPatientRecords() {
        return medicalRecordDao.getAllRecords();
    }
}