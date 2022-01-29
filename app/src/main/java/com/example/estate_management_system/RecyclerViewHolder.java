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

    public void setID(String id){
        TextView fNameTextView = mview.findViewById(R.id.vfirst_name);
        fNameTextView.setText(id);
    }

    public void setLName(String LName){
        TextView lNameTextView = mview.findViewById(R.id.vlast_name);
        lNameTextView.setText(LName);
    }

    public void setHouseNo(String houseNo){
        TextView houseNoTextView = mview.findViewById(R.id.vhouse_no);
        houseNoTextView.setText(houseNo);
    }
}
