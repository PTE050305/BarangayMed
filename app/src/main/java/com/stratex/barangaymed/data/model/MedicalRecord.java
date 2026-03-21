package com.stratex.barangaymed.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "medical_records")
public class MedicalRecord {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private int id;
    
    @SerializedName("user_id")
    private int userId;
    
    @SerializedName("patient_name")
    private String patientName;
    
    @SerializedName("diagnosis")
    private String diagnosis;
    
    @SerializedName("treatment")
    private String treatment;
    
    @SerializedName("date")
    private String date;

    public MedicalRecord(int userId, String patientName, String diagnosis, String treatment, String date) {
        this.userId = userId;
        this.patientName = patientName;
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.date = date;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getUserId() { return userId; }
    public String getPatientName() { return patientName; }
    public String getDiagnosis() { return diagnosis; }
    public String getTreatment() { return treatment; }
    public String getDate() { return date; }
}