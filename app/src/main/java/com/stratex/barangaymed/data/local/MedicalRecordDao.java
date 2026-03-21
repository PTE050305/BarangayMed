package com.stratex.barangaymed.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.stratex.barangaymed.data.model.MedicalRecord;

import java.util.List;

@Dao
public interface MedicalRecordDao {
    @Insert
    void insert(MedicalRecord record);

    @Query("SELECT * FROM medical_records")
    LiveData<List<MedicalRecord>> getAllRecords();
}