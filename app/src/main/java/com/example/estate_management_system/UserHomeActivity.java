package com.example.estate_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.ButterKnife;

public class UserHomeActivity extends AppCompatActivity {
//    @BindView(R.id.recyclerView)
//    RecyclerView mrecyclerView;

    private RecyclerView mrecyclerView;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId;
    private long pressedTime;

    private String key = "";
    private String fname;
    private String houseno;
    private String rent;
    private String dueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        mrecyclerView = findViewById(R.id.recyclerView);
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
                .setQuery(reference,UserModel.class)
                .build();

        FirebaseRecyclerAdapter<UserModel, RecyclerViewHolder> adapter = new FirebaseRecyclerAdapter<UserModel, RecyclerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerViewHolder holder, int i, @NonNull UserModel model) {
                holder.setFName(model.getFname());
                holder.setHouseNo(model.getHouseno());
                holder.setRent(model.getRent());
                holder.setDueDate(model.getDue_date());

                holder.mview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        key = getRef(i).getKey();
                        fname = model.getFname();
                        houseno = model.getHouseno();
                        rent = model.getRent();
                        dueDate = model.getDue_date();
                    }
                });
            }

            @NonNull
            @Override
            public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = getLayoutInflater().from(parent.getContext()).inflate(R.layout.activity_user_home, parent, false);
                return new RecyclerViewHolder(view);
            }
        };

        mrecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

//    public void Menu(View view) {
//        if (pressedTime + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed();
//            finish();
//        } else {
//            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
//        }
//        pressedTime = System.currentTimeMillis();
//    }
}