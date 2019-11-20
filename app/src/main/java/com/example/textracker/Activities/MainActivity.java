package com.example.textracker.Activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.textracker.models.LoginResponse;
import com.example.textracker.retrofitClasses.APIClient;
import com.example.textracker.retrofitClasses.APIInterface;
import com.example.textracker.models.Login;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.textracker.R;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button submit;
    String uname, pass, ans, token;
    APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        submit = findViewById(R.id.submit);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uname = username.getText().toString();
                pass = password.getText().toString();

                Call<LoginResponse> call = apiInterface.getLoggedIn(new Login(uname, pass));
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(MainActivity.this, response.body().getToken(), Toast.LENGTH_SHORT).show();
                            token = response.body().getToken();
                            Log.d("token", token);
                        } else {
                            Toast.makeText(MainActivity.this, "error :( no response", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                startActivity(new Intent(MainActivity.this, DocumentUploadView.class));
            }
        });
    }
}
