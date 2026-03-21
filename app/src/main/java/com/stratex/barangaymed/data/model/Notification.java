package com.stratex.barangaymed.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "notifications")
public class Notification {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;
    
    @SerializedName("user_id")
    private int userId;
    
    @SerializedName("title")
    private String title;
    
    @SerializedName("message")
    private String message;
    
    @SerializedName("created_at")
    private String createdAt;

    public Notification(int userId, String title, String message, String createdAt) {
        this.userId = userId;
        this.title = title;
        this.message = message;
        this.createdAt = createdAt;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getCreatedAt() { return createdAt; }
}