package com.example.dayo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;

import com.example.dayo.R;
import com.example.dayo.data.database.DatabaseInstance;
import com.example.dayo.data.database.User;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText usernameEditText = findViewById(R.id.et_username);
        EditText passwordEditText = findViewById(R.id.et_password);
        Button loginButton = findViewById(R.id.btn_login);
        TextView signUp = findViewById(R.id.link_sign_up);

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

        loginButton.setOnClickListener(v -> {
            String email = usernameEditText.getText().toString().trim();
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                try {
                    User user = DatabaseInstance.getInstance(this)
                            .userDao()
                            .getUserByEmailOrUsername(email,username);

                    runOnUiThread(() -> {
                        if (user == null) {
                            Toast.makeText(this, "User does not exist. Please create an account.", Toast.LENGTH_SHORT).show();
                        } else if (!user.getPassword().equals(password)) {
                            Toast.makeText(this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        } else {
                            getSharedPreferences("user_prefs", MODE_PRIVATE)
                                    .edit()
                                    .putInt("user_id", user.getId())
                                    .apply();

                            Toast.makeText(this, "Login successful!" + user.getName(), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        });


        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}