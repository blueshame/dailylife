package com.flywant.dailylife.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.flywant.api.AuthUser;
import com.flywant.dailylife.app.ui.OrderActivity;
import com.flywant.dailylife.app.ui.params.IntentParams;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LIFE";

    public class Callback implements AuthUser.ICallback {
        private Activity activity;
        public Callback(Activity activity) {
            this.activity = activity;
        }

        @Override
        public void OnLoginSuccess(AuthUser.LoginResult loginResult) {
            Intent intent = new Intent(this.activity, OrderActivity.class);
            intent.putExtra(IntentParams.PARAM_LOG_MESSAGE, loginResult.getMessage());
            this.activity.startActivity(intent);
        }

        @Override
        public void OnLoginFail(AuthUser.LoginResult loginResult) {
            Intent intent = new Intent(this.activity, OrderActivity.class);
            intent.putExtra(IntentParams.PARAM_LOG_MESSAGE, loginResult.getMessage());
            this.activity.startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button login = findViewById(R.id.login);
        final Activity activity = this;
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
                        authUser.login(username, password, new Callback(activity));
                    }
                });
                thread.start();
                }
        });
    }
}
