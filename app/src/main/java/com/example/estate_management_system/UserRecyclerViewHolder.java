package com.example.estate_management_system;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserRecyclerViewHolder extends RecyclerView.ViewHolder {
    View mview;
    public UserRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        mview = itemView;
    }

    public void setFName(String fName){
        TextView fNameTextView = mview.findViewById(R.id.ufirst_name);
        fNameTextView.setText(fName);
    }

    public void setLName(String LName){
        TextView lNameTextView = mview.findViewById(R.id.ulast_name);
        lNameTextView.setText(LName);
    }

    public void setHouseNo(String houseNo){
        TextView houseNoTextView = mview.findViewById(R.id.uhouse_no);
        houseNoTextView.setText(houseNo);
    }

    public void setRent(String rent){
        TextView rentTextView = mview.findViewById(R.id.urent);
        rentTextView.setText(rent);
    }

    public void setDueDate(String dueDate){
        TextView dueDateTextView = mview.findViewById(R.id.udue_date);
        dueDateTextView.setText(dueDate);
    }

    public void setAdditionalCharges(String additionalCharges){
        TextView additionalChargesTextView = mview.findViewById(R.id.uadditional_charges2);
        additionalChargesTextView.setText(additionalCharges);
    }
}
