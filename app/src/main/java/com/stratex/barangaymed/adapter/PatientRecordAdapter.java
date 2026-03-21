package com.stratex.barangaymed.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stratex.barangaymed.data.model.MedicalRecord;
import com.stratex.barangaymed.databinding.ItemPatientRecordBinding;

import java.util.List;

public class PatientRecordAdapter extends RecyclerView.Adapter<PatientRecordAdapter.ViewHolder> {
    private List<MedicalRecord> records;

    public PatientRecordAdapter(List<MedicalRecord> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemPatientRecordBinding binding = ItemPatientRecordBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MedicalRecord record = records.get(position);
        holder.binding.tvPatientRecordName.setText(record.getPatientName());
        holder.binding.tvDiagnosis.setText("Diagnosis: " + record.getDiagnosis());
        holder.binding.tvTreatment.setText("Treatment: " + record.getTreatment());
        holder.binding.tvRecordDate.setText(record.getDate());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemPatientRecordBinding binding;
        public ViewHolder(ItemPatientRecordBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}