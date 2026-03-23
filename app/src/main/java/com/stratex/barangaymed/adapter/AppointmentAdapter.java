package com.stratex.barangaymed.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stratex.barangaymed.data.model.Appointment;
import com.stratex.barangaymed.databinding.ItemAppointmentBinding;

import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private List<Appointment> appointments;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    public AppointmentAdapter(List<Appointment> appointments, OnItemClickListener listener) {
        this.appointments = appointments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAppointmentBinding binding = ItemAppointmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.binding.tvServiceType.setText(appointment.getServiceType());
        holder.binding.tvPatientName.setText("Patient: " + appointment.getPatientName());
        holder.binding.tvDateTime.setText(appointment.getDate() + " at " + appointment.getTime());
        holder.binding.tvStatus.setText(appointment.getStatus().toUpperCase());
        holder.itemView.setOnClickListener(v -> listener.onItemClick(appointment));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ItemAppointmentBinding binding;
        public ViewHolder(ItemAppointmentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}