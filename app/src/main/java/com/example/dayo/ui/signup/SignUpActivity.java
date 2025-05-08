package com.example.dayo.ui.signup;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.R;
import com.example.dayo.ui.login.LoginActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TextView logIn = findViewById(R.id.link_log_in);
        logIn.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
            startActivity(intent);
        });


        // Εδώ μπορείς να προσθέσεις τη λογική για την εγγραφή
    }
}