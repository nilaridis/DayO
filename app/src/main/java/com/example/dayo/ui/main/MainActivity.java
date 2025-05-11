package com.example.dayo.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import com.example.dayo.ui.base.BaseActivity;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dayo.R;
import com.example.dayo.ui.login.LoginActivity;
import com.example.dayo.ui.profile.ProfileActivity;
import com.example.dayo.ui.signup.SignUpActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // Βεβαιώσου ότι φορτώνεται το σωστό layout
        setupHeaderAndNavbar(); // Ρύθμισε το header και το navbar
    }

        // Εδώ μπορείς να προσθέσεις λογική αν χρειαστεί




}