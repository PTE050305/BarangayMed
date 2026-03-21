package com.stratex.barangaymed.ui.user;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.stratex.barangaymed.databinding.ActivityQrcodeBinding;
import com.stratex.barangaymed.utils.QRCodeGenerator;

public class QRCodeActivity extends AppCompatActivity {
    private ActivityQrcodeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityQrcodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int appointmentId = getIntent().getIntExtra("appointment_id", -1);
        if (appointmentId != -1) {
            binding.tvAppointmentId.setText("Appointment ID: " + appointmentId);
            Bitmap bitmap = QRCodeGenerator.generateQRCode(String.valueOf(appointmentId));
            if (bitmap != null) {
                binding.ivQRCode.setImageBitmap(bitmap);
            }
        }
    }
}