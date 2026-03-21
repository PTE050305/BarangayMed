package com.stratex.barangaymed.ui.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.stratex.barangaymed.utils.Constants;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

import android.content.Intent;

public class QRScannerActivity extends AppCompatActivity {
    private AppointmentViewModel appointmentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("Scan Appointment QR Code");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(true);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                finish();
            } else {
                try {
                    int appointmentId = Integer.parseInt(result.getContents());
                    appointmentViewModel.updateStatus(appointmentId, Constants.STATUS_COMPLETED);
                    Toast.makeText(this, "Patient Checked-in: ID " + appointmentId, Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}