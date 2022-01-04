package com.example.estate_management_system;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminRecyclerViewHolder extends RecyclerView.ViewHolder {
    View mview;
    public AdminRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mview = itemView;
    }

    public void setFName(String fName){
        TextView fNameTextView = mview.findViewById(R.id.afirst_name);
        fNameTextView.setText(fName);
    }

    public void setLName(String LName){
        TextView lNameTextView = mview.findViewById(R.id.alast_name);
        lNameTextView.setText(LName);
    }

    public void setHouseNo(String houseNo){
        TextView houseNoTextView = mview.findViewById(R.id.ahouse_no);
        houseNoTextView.setText(houseNo);
    }

    public void setRent(String rent){
        TextView rentTextView = mview.findViewById(R.id.arent);
        rentTextView.setText(rent);
    }

    public void setDueDate(String dueDate){
        TextView dueDateTextView = mview.findViewById(R.id.adue_date);
        dueDateTextView.setText(dueDate);
    }

    public void setAdditionalCharges(String additionalCharges){
        TextView additionalChargesTextView = mview.findViewById(R.id.aadditional_charges);
        additionalChargesTextView.setText(additionalCharges);
    }
}
