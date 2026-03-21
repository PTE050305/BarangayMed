package com.stratex.barangaymed.data.network;

import com.stratex.barangaymed.data.model.Appointment;
import com.stratex.barangaymed.data.model.MedicalRecord;
import com.stratex.barangaymed.data.model.Notification;
import com.stratex.barangaymed.data.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("login.php")
    Call<User> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("register.php")
    Call<User> register(@Field("name") String name, @Field("email") String email, @Field("password") String password);

    @GET("get_appointments.php")
    Call<List<Appointment>> getAppointments(@Query("user_id") int userId, @Query("role") String role);

    @FormUrlEncoded
    @POST("book_appointment.php")
    Call<Appointment> bookAppointment(@Field("user_id") int userId, @Field("patient_name") String patientName, @Field("date") String date, @Field("time") String time);

    @FormUrlEncoded
    @POST("update_appointment_status.php")
    Call<Void> updateAppointmentStatus(@Field("appointment_id") int appointmentId, @Field("status") String status);

    @GET("get_notifications.php")
    Call<List<Notification>> getNotifications(@Query("user_id") int userId);

    @GET("get_patient_records.php")
    Call<List<MedicalRecord>> getPatientRecords();
}