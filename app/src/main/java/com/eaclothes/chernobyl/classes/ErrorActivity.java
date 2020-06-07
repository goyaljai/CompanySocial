package com.eaclothes.chernobyl.classes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.eaclothes.chernobyl.MainActivity;
import com.eaclothes.chernobyl.R;

import androidx.appcompat.app.AppCompatActivity;

public class ErrorActivity extends AppCompatActivity {
    private Button errorbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_error);
        errorbtn = findViewById(R.id.error_button);
        errorbtn.setOnClickListener(v -> {
            Intent intent = new Intent(ErrorActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                    Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finishAffinity();
            finish();
        });
    }
}
