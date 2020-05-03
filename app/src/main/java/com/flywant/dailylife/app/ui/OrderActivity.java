package com.flywant.dailylife.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.flywant.dailylife.app.R;
import com.flywant.dailylife.app.ui.params.IntentParams;

public class OrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        EditText order = findViewById(R.id.order);
        Intent intent = getIntent();
        String message = intent.getStringExtra(IntentParams.PARAM_LOG_MESSAGE);
        order.setText(message);
    }
}
