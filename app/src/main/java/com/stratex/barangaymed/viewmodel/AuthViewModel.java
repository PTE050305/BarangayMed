package com.stratex.barangaymed.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.stratex.barangaymed.data.model.User;
import com.stratex.barangaymed.data.repository.AuthRepository;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;

    public AuthViewModel(@NonNull Application application) {
        super(application);
        authRepository = new AuthRepository(application);
    }

    public LiveData<User> login(String email, String password) {
        return authRepository.login(email, password);
    }

    public LiveData<User> register(String name, String email, String password) {
        return authRepository.register(name, email, password);
    }
}