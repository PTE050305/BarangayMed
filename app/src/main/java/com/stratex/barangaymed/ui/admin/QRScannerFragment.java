package com.stratex.barangaymed.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.zxing.ResultPoint;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.stratex.barangaymed.databinding.FragmentQrScannerBinding;
import com.stratex.barangaymed.viewmodel.AppointmentViewModel;

import java.util.List;

public class QRScannerFragment extends Fragment {
    private FragmentQrScannerBinding binding;
    private AppointmentViewModel viewModel;
    private boolean isScanning = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentQrScannerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AppointmentViewModel.class);

        binding.btnRescan.setOnClickListener(v -> {
            if (!isScanning) {
                startScanning();
            } else {
                stopScanning();
            }
        });
    }

    private void startScanning() {
        isScanning = true;
        binding.btnRescan.setText("Stop Scanning");
        binding.tvScanResult.setText("Scanning...");
        binding.barcodeScanner.decodeContinuous(callback);
        binding.barcodeScanner.resume();
    }

    private void stopScanning() {
        isScanning = false;
        binding.btnRescan.setText("Start Scanning");
        binding.barcodeScanner.pause();
    }

    private final BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() != null) {
                binding.barcodeScanner.pause();
                processResult(result.getText());
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {}
    };

    private void processResult(String data) {
        try {
            int appointmentId = Integer.parseInt(data);
            viewModel.updateStatus(appointmentId, "completed");
            binding.tvScanResult.setText("Check-in Successful for ID: " + appointmentId);
            Toast.makeText(requireContext(), "Check-in Successful", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            binding.tvScanResult.setText("Invalid QR Code");
            Toast.makeText(requireContext(), "Invalid QR Code", Toast.LENGTH_SHORT).show();
        }
        stopScanning();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isScanning) binding.barcodeScanner.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.barcodeScanner.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}