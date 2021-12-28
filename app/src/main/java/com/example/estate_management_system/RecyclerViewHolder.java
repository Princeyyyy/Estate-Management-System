package com.example.estate_management_system;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    View mview;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mview = itemView;
    }

    public void setFName(String fName){
        TextView taskTextView = mview.findViewById(R.id.name);
        taskTextView.setText(fName);
    }

    public void setHouseNo(String houseNo){
        TextView taskTextView = mview.findViewById(R.id.house_no);
        taskTextView.setText(houseNo);
    }

    public void setRent(String rent){
        TextView taskTextView = mview.findViewById(R.id.rent);
        taskTextView.setText(rent);
    }

    public void setDueDate(String dueDate){
        TextView taskTextView = mview.findViewById(R.id.due_date);
        taskTextView.setText(dueDate);
    }
}
