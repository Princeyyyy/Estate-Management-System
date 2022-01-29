package com.example.estate_management_system;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;



public class ViewHouses extends AppCompatActivity {

    Intent intent = getIntent();
    String key = intent.getParcelableExtra("key");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_houses);

    }
}