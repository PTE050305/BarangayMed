package com.stratex.barangaymed.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users")
public class User {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;
    
    @SerializedName("name")
    private String name;
    
    @SerializedName("email")
    private String email;
    
    @SerializedName("password")
    private String password; // Store hashed password
    
    @SerializedName("birthdate")
    private String birthdate;
    
    @SerializedName("address")
    private String address;
    
    @SerializedName("phone")
    private String phone;
    
    @SerializedName("role")
    private String role; // "admin" or "user"

    public User(String name, String email, String password, String birthdate, String address, String phone, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.address = address;
        this.phone = phone;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getBirthdate() { return birthdate; }
    public void setBirthdate(String birthdate) { this.birthdate = birthdate; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}