package com.example.dayo.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.R;
import com.example.dayo.ui.signup.SignUpActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Εδώ μπορείς να προσθέσεις τη λογική για το Login
        TextView signUp = findViewById(R.id.link_sign_up);
        signUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}