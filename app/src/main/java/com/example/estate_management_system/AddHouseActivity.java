package com.example.estate_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddHouseActivity extends AppCompatActivity {

    @BindView(R.id.addhouseno)
    EditText addhouseno;
    @BindView(R.id.addfirstname)
    EditText addfirstname;
    @BindView(R.id.addlastname)
    EditText addlastname;
    @BindView(R.id.addhouse)
    Button addhouse;
    @BindView(R.id.back)
    TextView back;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private String onlineUserId;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);
        ButterKnife.bind(this);

        loader = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddHouseActivity.this,ChoiceActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
            }
        });


        addhouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTenant();
            }
        });

    }

    private void addTenant() {
        String house = addhouseno.getText().toString().trim();
        String first = addfirstname.getText().toString().trim();
        String last = addlastname.getText().toString().trim();
        String id = reference.push().getKey();

        if (TextUtils.isEmpty(house)) {
            addhouseno.setError("House No Required");
            return;
        }
        if (TextUtils.isEmpty(first)) {
            addfirstname.setError("First Name Required");
            return;
        }
        if (TextUtils.isEmpty(last)) {
            addlastname.setError("Last Name Required");
            return;
        } else {
            loader.setMessage("Adding House");
            loader.setCanceledOnTouchOutside(false);
            loader.show();

            UserModel model = new UserModel(house,first,last,"0","31st","0");
            reference.child(id).setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddHouseActivity.this, "House Has Been Added Successfully", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(AddHouseActivity.this, UserHomeActivity.class);
                        startActivity(intent);
                        loader.dismiss();
                    } else {
                        String error = task.getException().toString();
                        Toast.makeText(AddHouseActivity.this, "Failed " + error, Toast.LENGTH_SHORT).show();
                        loader.dismiss();
                    }
                }
            });
        }
    }
}