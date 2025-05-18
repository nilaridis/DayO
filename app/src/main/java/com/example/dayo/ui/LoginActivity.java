package com.example.dayo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.R;
import com.example.dayo.data.database.DatabaseInstance;
import com.example.dayo.data.database.User;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        EditText usernameEditText = findViewById(R.id.et_username);
        EditText passwordEditText = findViewById(R.id.et_password);
        Button loginButton = findViewById(R.id.btn_login);
        TextView signUp = findViewById(R.id.link_sign_up);

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
                            // Αν δεν υπάρχει χρήστης
                            Toast.makeText(this, "User does not exist. Please create an account.", Toast.LENGTH_SHORT).show();
                        } else if (!user.getPassword().equals(password)) {
                            // Αν ο κωδικός είναι λάθος
                            Toast.makeText(this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        } else {
                            // Επιτυχής σύνδεση
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





        //Αλλαγη οθονης σε Sign up
        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}