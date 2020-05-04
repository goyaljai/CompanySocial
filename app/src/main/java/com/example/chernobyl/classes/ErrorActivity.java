package com.example.chernobyl.classes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chernobyl.MainActivity;
import com.example.chernobyl.R;

public class ErrorActivity extends AppCompatActivity {
private Button errorbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_error);
        errorbtn = findViewById(R.id.error_button);
        errorbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ErrorActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
