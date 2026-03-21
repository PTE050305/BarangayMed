package com.stratex.barangaymed.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.stratex.barangaymed.data.local.AppDatabase;
import com.stratex.barangaymed.data.local.UserDao;
import com.stratex.barangaymed.data.model.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AuthRepository {
    private UserDao userDao;
    private ExecutorService executorService;

    public AuthRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        userDao = db.userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<User> login(String email, String password) {
        MutableLiveData<User> result = new MutableLiveData<>();
        executorService.execute(() -> {
            User user = userDao.login(email, password);
            result.postValue(user);
        });
        return result;
    }

    public LiveData<User> register(String name, String email, String password) {
        MutableLiveData<User> result = new MutableLiveData<>();
        executorService.execute(() -> {
            // Updated role logic for testing:
            // If the name includes "admin" (case insensitive), set role to admin.
            // Otherwise, default to user.
            String role = name.toLowerCase().contains("admin") ? "admin" : "user";
            
            User newUser = new User(name, email, password, role);
            userDao.register(newUser);
            result.postValue(newUser);
        });
        return result;
    }
}