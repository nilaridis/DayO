package com.example.dayo.ui;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.example.dayo.R;
import com.example.dayo.data.database.DatabaseInstance;
import com.example.dayo.data.database.User;

public class SignUpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText emailEditText = findViewById(R.id.et_email);
        EditText passwordEditText = findViewById(R.id.et_password);
        EditText nameEditText = findViewById(R.id.et_username);
        Spinner categorySpinner = findViewById(R.id.spinner_preferences);
        Button signUpButton = findViewById(R.id.btn_create_account);

        TextView togglePassword = findViewById(R.id.tv_toggle_password);
        final boolean[] isPasswordVisible = {false};
        togglePassword.setOnClickListener(v -> {
            if (isPasswordVisible[0]) {
                passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                togglePassword.setText("Show");
                isPasswordVisible[0] = false;
            } else {
                passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                togglePassword.setText("Hide");
                isPasswordVisible[0] = true;
            }
            passwordEditText.setSelection(passwordEditText.length());
        });


        signUpButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String username = nameEditText.getText().toString().trim();
            String category = categorySpinner.getSelectedItem().toString();

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                try {
                    User existingUser = DatabaseInstance.getInstance(this)
                            .userDao()
                            .getUserByEmailOrUsername(email, username);

                    if (existingUser != null) {
                        runOnUiThread(() -> Toast.makeText(this, "User already exists. Please login.", Toast.LENGTH_SHORT).show());
                    } else {
                        User newUser = new User();
                        newUser.setEmail(email);
                        newUser.setPassword(password);
                        newUser.setName(username);
                        newUser.setCategory(category);

                        DatabaseInstance.getInstance(this).userDao().insertUser(newUser);

                        runOnUiThread(() -> {
                            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        });



        TextView logIn = findViewById(R.id.link_log_in);
        logIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });

    }
    private boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}