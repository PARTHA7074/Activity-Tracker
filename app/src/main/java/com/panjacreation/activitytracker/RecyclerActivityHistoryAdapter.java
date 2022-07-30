package com.panjacreation.activitytracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerActivityHistoryAdapter extends RecyclerView.Adapter<RecyclerActivityHistoryAdapter.ViewHolder> {
    Context context;
    ArrayList<ActivityDetails> arrayActivityDetails;

    public RecyclerActivityHistoryAdapter(Context context, ArrayList<ActivityDetails> arrayActivityDetails) {
        this.context = context;
        this.arrayActivityDetails = arrayActivityDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_detatils_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.activityName.setText(arrayActivityDetails.get(arrayActivityDetails.size()-1-position).getActivityName());
        holder.location.setText(arrayActivityDetails.get(arrayActivityDetails.size()-1-position).getLocation());
    }

    @Override
    public int getItemCount() {
        return arrayActivityDetails.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView activityName,location;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            activityName = itemView.findViewById(R.id.activity);
            location = itemView.findViewById(R.id.location);

        }
    }
}
