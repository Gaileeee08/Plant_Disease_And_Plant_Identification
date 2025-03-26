package com.saurabh.lettucelens;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.saurabh.homepage.R;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmail, mPass, username, phone;
    private Button signUpBtn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI elements
        mEmail = findViewById(R.id.reditTextEmail);
        mPass = findViewById(R.id.reditTextPassword);
        username = findViewById(R.id.reditTextName);
        phone = findViewById(R.id.reditTextMobile);
        signUpBtn = findViewById(R.id.cirRegisterButton);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Set up the sign-up button listener
        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newUser();
            }
        });

        changeStatusBarColor();
    }

    public void changeStatusBarColor() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
    }

    public void onLoginClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    public void newUser() {
        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString();
        String name = username.getText().toString();
        String mobile = phone.getText().toString();

        // Validate inputs
        if (validateInputs(name, mobile, email, pass)) {
            // Create user with email and password
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Handle successful registration
                        saveUserData(name, mobile, email);
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Registration failed.";
                        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private boolean validateInputs(String name, String mobile, String email, String pass) {
        if (name.isEmpty()) {
            username.setError("Enter the full name");
            username.requestFocus();
            return false;
        }
        if (mobile.isEmpty()) {
            phone.setError("Enter the mobile number");
            phone.requestFocus();
            return false;
        }
        if (email.isEmpty()) {
            mEmail.setError("Empty Fields Are not Allowed");
            mEmail.requestFocus();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("Please enter the Email Correctly!!");
            mEmail.requestFocus();
            return false;
        }
        if (pass.isEmpty()) {
            mPass.setError("Empty Fields Are not Allowed");
            mPass.requestFocus();
            return false;
        }
        if (pass.length() < 8) {
            mPass.setError("Enter password of at least 8 characters");
            return false;
        }
        return true;
    }

    private void saveUserData(String name, String mobile, String email) {
        User user = new User(name, mobile, email);
        DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User")
                .child(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

        userReference.setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registered Successfully !!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Error saving user data: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}