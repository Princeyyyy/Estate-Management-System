package com.example.estate_management_system;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserHomeActivity extends AppCompatActivity {
    @BindView(R.id.recyclerView1)
    RecyclerView mrecyclerView;
    @BindView(R.id.fab)
    FloatingActionButton mFloatingActionButton;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId;
    private long pressedTime;
    private ProgressDialog loader;


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
        getSupportActionBar().setTitle("My Houses");

        mAuth = FirebaseAuth.getInstance();

        loader = new ProgressDialog(this);

        mrecyclerView = findViewById(R.id.recyclerView1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(linearLayoutManager);

        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });

    }

    private void addTask() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);


        View view = layoutInflater.inflate(R.layout.input_file, null);
        myDialog.setView(view);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText houseno = view.findViewById(R.id.houseno);
        final EditText fname = view.findViewById(R.id.fname);
        final EditText lname = view.findViewById(R.id.lname);
        Button save = view.findViewById(R.id.addBtn);
        Button cancel = view.findViewById(R.id.cancelBtn);

        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        save.setOnClickListener(v -> {
            String h = houseno.getText().toString().trim();
            String f = fname.getText().toString().trim();
            String l = lname.getText().toString().trim();
            String id = reference.push().getKey();

            if (TextUtils.isEmpty(h)) {
                houseno.setError("House No Required");
                return;
            }
            if (TextUtils.isEmpty(f)) {
                fname.setError("Fname Required");
                return;
            }
            if (TextUtils.isEmpty(l)) {
                lname.setError("Lname Required");
                return;
            } else {
                loader.setMessage("Adding Your Task");
                loader.setCanceledOnTouchOutside(false);
                loader.show();

                UserModel model = new UserModel(h, f, l, "0", "31st", "0");
                reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UserHomeActivity.this, "House Has Been Added Successfully", Toast.LENGTH_SHORT).show();
                            loader.dismiss();
                        } else {
                            String error = task.getException().toString();
                            Toast.makeText(UserHomeActivity.this, "Failed " + error, Toast.LENGTH_SHORT).show();
                            loader.dismiss();
                        }
                    }
                });
            }
            dialog.dismiss();
        });

        dialog.show();
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

                        updateHouse();

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

    private void updateHouse() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.update_data2, null);
        myDialog.setView(view);

        AlertDialog dialog = myDialog.create();

        EditText fname3 = view.findViewById(R.id.rent3);
        EditText lname3 = view.findViewById(R.id.additional_charges3);
        TextView no = view.findViewById(R.id.returnno);

        no.setText("Update House No " + houseno);
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

                UserModel userModel = new UserModel(houseno, fname, lname, rent, due_date, charges);

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logOut:
                mAuth.signOut();
                Intent intent = new Intent(UserHomeActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}