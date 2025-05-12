package com.example.dayo.ui;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.R;
import com.example.dayo.data.database.DatabaseInstance;
import com.example.dayo.data.database.User;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText emailEditText = findViewById(R.id.et_email);
        EditText passwordEditText = findViewById(R.id.et_password);
        EditText nameEditText = findViewById(R.id.et_username);
        Button signUpButton = findViewById(R.id.btn_create_account);

        signUpButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String username = nameEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            new Thread(() -> {
                try {
                    // Εκτέλεση Query
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

                        DatabaseInstance.getInstance(this).userDao().insertUser(newUser);

                        runOnUiThread(() -> {
                            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace(); // Εκτύπωση λάθους για debugging
                    runOnUiThread(() -> Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        });



        TextView logIn = findViewById(R.id.link_log_in);
        logIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });


        // Εδώ μπορείς να προσθέσεις τη λογική για την εγγραφή
    }
}