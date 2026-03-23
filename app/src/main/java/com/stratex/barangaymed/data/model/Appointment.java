package com.stratex.barangaymed.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "appointments")
public class Appointment {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;
    
    @SerializedName("user_id")
    private int userId;
    
    @SerializedName("patient_name")
    private String patientName;
    
    @SerializedName("service_type")
    private String serviceType;
    
    @SerializedName("date")
    private String date;
    
    @SerializedName("time")
    private String time;
    
    @SerializedName("status")
    private String status; // "pending", "approved", "cancelled", "completed"

    public Appointment(int userId, String patientName, String serviceType, String date, String time, String status) {
        this.userId = userId;
        this.patientName = patientName;
        this.serviceType = serviceType;
        this.date = date;
        this.time = time;
        this.status = status;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public String getPatientName() { return patientName; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}