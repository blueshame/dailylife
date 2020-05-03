package com.flywant.dailylife.app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flywant.api.AuthUser;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LIFE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            Log.e(TAG, "clicks");
            EditText usernameView = findViewById(R.id.username);
            EditText passwordView = findViewById(R.id.password);
            final String username = usernameView.getText().toString();
            final String password = passwordView.getText().toString();
            String message = username + " loging";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    AuthUser authUser = new AuthUser();
                    authUser.login(username, password, null);
                }
            });
            thread.start();
            }
        });
    }
}
