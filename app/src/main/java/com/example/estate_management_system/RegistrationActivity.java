package com.example.estate_management_system;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrationActivity extends AppCompatActivity {

    @BindView(R.id.registrationEmail)
    EditText mregistrationEmail;
    @BindView(R.id.registrationPassword)
    EditText mregistrationPassword;
    @BindView(R.id.houseno)
    EditText mhouseno;
    @BindView(R.id.firstname)
    EditText mfirstname;
    @BindView(R.id.lastname)
    EditText mlastname;
    @BindView(R.id.signupButton)
    Button msignupButton;

    private FirebaseAuth mAuth;
    private ProgressDialog loader;

    private DatabaseReference reference;
    private FirebaseUser mUser;
    private String onlineUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        loader = new ProgressDialog(this);

        mUser = mAuth.getCurrentUser();
        onlineUserId = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(onlineUserId);

        msignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mregistrationEmail.getText().toString().trim();
                String password = mregistrationPassword.getText().toString().trim();
                String houseno = mhouseno.getText().toString().trim();
                String firstname = mfirstname.getText().toString().trim();
                String lastname = mlastname.getText().toString().trim();
                String id = reference.push().getKey();

                if (TextUtils.isEmpty(houseno)) {
                    mhouseno.setError("House no Required");
                    return;
                }
                if (TextUtils.isEmpty(firstname)) {
                    mfirstname.setError("First Name Required");
                    return;
                }
                if (TextUtils.isEmpty(lastname)) {
                    mlastname.setError("Last Name Required");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mregistrationEmail.setError("Email Required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mregistrationPassword.setError("Password Required");
                } else {
                    loader.setMessage("Registration in Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    UserModel userModel = new UserModel(houseno, firstname, lastname, "0", "31st");

                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                reference.child(id).setValue(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> mtask) {
                                        if (mtask.isSuccessful()) {
                                            Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegistrationActivity.this, UserHomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                            loader.dismiss();
                                        } else {
                                            String error = mtask.getException().toString();
                                            Toast.makeText(RegistrationActivity.this, "Failed " + error, Toast.LENGTH_SHORT).show();
                                            loader.dismiss();
                                        }
                                    }
                                });
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(RegistrationActivity.this, "Registration Failed " + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }
            }

        });
    }

    public void onLoginClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay);

    }
}