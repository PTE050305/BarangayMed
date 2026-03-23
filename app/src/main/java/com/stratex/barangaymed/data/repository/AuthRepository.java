package com.stratex.barangaymed.data.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.stratex.barangaymed.data.local.AppDatabase;
import com.stratex.barangaymed.data.local.UserDao;
import com.stratex.barangaymed.data.model.User;
import com.stratex.barangaymed.utils.PasswordUtils;

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
            User user = userDao.login(email);
            if (user != null && PasswordUtils.verifyPassword(password, user.getPassword())) {
                result.postValue(user);
            } else {
                result.postValue(null);
            }
        });
        return result;
    }

    public LiveData<User> register(String name, String email, String password, String birthdate, String address, String phone) {
        MutableLiveData<User> result = new MutableLiveData<>();
        executorService.execute(() -> {
            String hashedPassword = PasswordUtils.hashPassword(password);
            // Default role is always 'user' to prevent privilege escalation
            User newUser = new User(name, email, hashedPassword, birthdate, address, phone, "user");
            userDao.register(newUser);
            result.postValue(newUser);
        });
        return result;
    }
}