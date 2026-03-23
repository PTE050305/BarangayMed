package com.stratex.barangaymed.ui.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import com.stratex.barangaymed.utils.Constants;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

public class QRScannerActivity extends AppCompatActivity {
    private AppointmentViewModel appointmentViewModel;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
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
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        appointmentViewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);

        ScanOptions options = new ScanOptions();
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
        options.setPrompt("Scan Appointment QR Code");
        options.setCameraId(0);
        options.setBeepEnabled(true);
        options.setBarcodeImageEnabled(true);
        options.setOrientationLocked(false);
        
        barcodeLauncher.launch(options);
    }
}