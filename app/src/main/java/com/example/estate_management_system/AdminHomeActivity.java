package com.example.estate_management_system;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminHomeActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView mrecyclerView;

    private DatabaseReference reference;
    private DatabaseReference reference2;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId;

    private Toolbar toolbar;

    public String key = "";
    private String houseno;
    private String fname;
    private String lname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        ButterKnife.bind(this);

        toolbar = findViewById(R.id.homeToolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tenants");

        mAuth = FirebaseAuth.getInstance();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(linearLayoutManager);

        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        FirebaseRecyclerOptions<UserModel> options = new FirebaseRecyclerOptions.Builder<UserModel>()
                .setQuery(reference, UserModel.class)
                .build();

        FirebaseRecyclerAdapter<UserModel, RecyclerViewHolder> adapter = new FirebaseRecyclerAdapter<UserModel, RecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i, @NonNull UserModel model) {
                holder.setFName("First Name: " + model.getFname());
                holder.setLName("Last Name: " + model.getLname());
                holder.setHouseNo("House No " + model.getHouseno());

                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        key = getRef(i).getKey();
                        houseno = model.getHouseno();
                        fname = model.getFname();
                        lname = model.getLname();
                        Log.d("key", "id" + key);

                        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(key);

                        Intent intent = new Intent(AdminHomeActivity.this, ViewHouses.class);
                        intent.putExtra("key", key);
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().from(parent.getContext()).inflate(R.layout.admin_return, parent, false);
                return new RecyclerViewHolder(view);
            }
        };

        mrecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}