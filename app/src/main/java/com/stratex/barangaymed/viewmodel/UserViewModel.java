package com.stratex.barangaymed.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.stratex.barangaymed.data.model.MedicalRecord;
import com.stratex.barangaymed.data.repository.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public LiveData<List<MedicalRecord>> getPatientRecords() {
        return userRepository.getPatientRecords();
    }
}