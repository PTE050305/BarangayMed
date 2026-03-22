package com.stratex.barangaymed.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.stratex.barangaymed.data.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void register(User user);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    User login(String email, String password);

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    User getUserByEmail(String email);

    @Query("SELECT * FROM users WHERE role = 'user'")
    LiveData<List<User>> getAllUsers();
}