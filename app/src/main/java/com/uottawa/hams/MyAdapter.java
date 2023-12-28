package com.uottawa.hams;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    // adapter for registration request recycler list
    private Context context;
    private ArrayList userType_id, firstName_id, lastName_id, userId_id;

    public MyAdapter(Context context, ArrayList userType_id, ArrayList firstName_id, ArrayList lastName_id, ArrayList<String> userId_id) {
        this.context = context;
        this.userType_id = userType_id;
        this.firstName_id = firstName_id;
        this.lastName_id = lastName_id;
        this.userId_id = userId_id;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.userentry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.userType_id.setText(String.valueOf(userType_id.get(position)));
        holder.firstName_id.setText(String.valueOf(firstName_id.get(position)));
        holder.lastName_id.setText(String.valueOf(lastName_id.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    if (listener != null) {
                        listener.onSelect(currentPosition);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return firstName_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userType_id, firstName_id, lastName_id;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userType_id = itemView.findViewById(R.id.textusertype);
            firstName_id = itemView.findViewById(R.id.textfirstname);
            lastName_id = itemView.findViewById(R.id.textlastname);
        }
    }

    public interface SelectListener {
        void onSelect(int position);
    }

    private SelectListener listener;

    public void setSelectListener(SelectListener listener) {
        this.listener = listener;
    }
}
