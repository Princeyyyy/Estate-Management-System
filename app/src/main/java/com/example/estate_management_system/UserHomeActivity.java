package com.example.estate_management_system;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserHomeActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView1)
    RecyclerView mrecyclerView;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId;
    private long pressedTime;


    private Toolbar toolbar;

    private String key = "";
    private String houseno;
    private String fname;
    private String lname;
    private String rent;
    private String due_date;
    private String charges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.homeToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("User Houses");

        mAuth = FirebaseAuth.getInstance();

        mrecyclerView = findViewById(R.id.recyclerView1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(linearLayoutManager);

        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(reference, UserModel.class)
                .build();

        FirebaseRecyclerAdapter<UserModel, UserRecyclerViewHolder> adapter = new FirebaseRecyclerAdapter<UserModel, UserRecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull UserRecyclerViewHolder holder, int i, @NonNull UserModel model) {
                holder.setFName("First Name: " + model.getFname());
                holder.setLName("Last Name: " + model.getLname());
                holder.setHouseNo("House No " + model.getHouseno());
                holder.setRent("Rent: " + model.getRent() + ".ksh");
                holder.setDueDate("Rent Due Date: " + model.getDue_date());
                holder.setAdditionalCharges("Additional Charges: " + model.getAdditional_charges() + ".ksh");

                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key = getRef(i).getKey();
                        rent = model.getRent();
                        charges = model.getAdditional_charges();
                        houseno = model.getHouseno();
                        fname = model.getFname();
                        lname = model.getLname();
                        due_date = model.getDue_date();

                        updateTask();

                    }
                });
            }

            @NonNull
            @Override
            public UserRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().from(parent.getContext()).inflate(R.layout.return_layout2, parent, false);
                return new UserRecyclerViewHolder(view);
            }
        };

        mrecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    private void updateTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_data2, null);
        myDialog.setView(view);

        AlertDialog dialog = myDialog.create();

        EditText fname3 = view.findViewById(R.id.rent3);
        EditText lname3 = view.findViewById(R.id.additional_charges3);

        fname3.setText(fname);
        fname3.setSelection(fname.length());
        lname3.setText(lname);
        lname3.setSelection(lname.length());


        Button updateButton = view.findViewById(R.id.updateUserBtn);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = fname3.getText().toString().trim();
                lname = lname3.getText().toString().trim();

                UserModel userModel = new UserModel(houseno,fname,lname,rent,due_date,charges);

                reference.child(key).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserHomeActivity.this, "House Has Been Updated Successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            String error = task.getException().toString();
                            Toast.makeText(UserHomeActivity.this, "Update Failed" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.dismiss();

            }
        });

        dialog.show();
    }

    private void exit() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }
}