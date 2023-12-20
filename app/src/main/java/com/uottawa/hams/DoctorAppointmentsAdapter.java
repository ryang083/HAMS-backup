package com.uottawa.hams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DoctorAppointmentsAdapter extends RecyclerView.Adapter<DoctorAppointmentsAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Appointment> appointments;

    private OnAppointmentClickListener listener;

    public DoctorAppointmentsAdapter(Context context, ArrayList<Appointment> appointments, OnAppointmentClickListener listener) {
        this.context = context;
        this.appointments = appointments;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.entrydocappointment, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Appointment appointment = appointments.get(position);
        holder.firstName.setText(appointment.getFirstName());
        holder.lastName.setText(appointment.getLastName());
        holder.date.setText(appointment.getDate());
        holder.startTime.setText(appointment.getStartTime());
        holder.endTime.setText(appointment.getEndTime());
        holder.status.setText(appointment.getStatus());

        // Set the click listener for the item view
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                Appointment clickedAppointment = appointments.get(holder.getAdapterPosition());
                listener.onAppointmentClick(clickedAppointment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView firstName, lastName, date, startTime, endTime, status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            firstName = itemView.findViewById(R.id.textfirstname);
            lastName = itemView.findViewById(R.id.textlastname);
            date = itemView.findViewById(R.id.textDate);
            startTime = itemView.findViewById(R.id.textStartTime);
            endTime = itemView.findViewById(R.id.textEndTime);
            status = itemView.findViewById(R.id.textStatus);
        }
    }

    public interface OnAppointmentClickListener {
        void onAppointmentClick(Appointment appointment);
    }
}