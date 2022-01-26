package com.example.estate_management_system;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.loginUsername)
    EditText mloginEmail;
    @BindView(R.id.loginPassword)
    EditText mloginPassword;
    @BindView(R.id.loginButton)
    Button mloginButton;

    private FirebaseAuth mAuth;
    private String mAuth2;
    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        mAuth2 = FirebaseAuth.getInstance().getUid();

        loader = new ProgressDialog(this);

        if (mAuth.getCurrentUser() != null && mAuth2.equals("uJSOFVb3ueOcS6uFy3D8O5irjxf2")) {
            Toast.makeText(this, "Welcome Admin", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
            startActivity(intent);
        } else if (mAuth.getCurrentUser() != null) {
            Toast.makeText(this, "Welcome User", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
            startActivity(intent);
        }

        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mloginEmail.getText().toString().trim();
                String password = mloginPassword.getText().toString().trim();
                String mail = mloginEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    mloginEmail.setError("Username Required");
                }
                if (TextUtils.isEmpty(password)) {
                    mloginPassword.setError("Password Required");
                } else {
                    loader.setMessage("LogIn in Progress");
                    loader.setCanceledOnTouchOutside(false);
                    loader.show();

                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful() && mail.equals("admin@gmail.com")) {
                                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, AdminHomeActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            } else if (task.isSuccessful() && !mail.equals("admin@gmail.com")) {
                                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, UserHomeActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();
                            } else {
                                String error = task.getException().toString();
                                Toast.makeText(LoginActivity.this, "Login Failed " + error, Toast.LENGTH_SHORT).show();
                                loader.dismiss();
                            }
                        }
                    });
                }
            }
        });
    }

    public void onRegistrationClick(View View) {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}