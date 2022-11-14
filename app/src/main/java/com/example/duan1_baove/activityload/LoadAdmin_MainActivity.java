package com.example.duan1_baove.activityload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.duan1_baove.Admin_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.login.Screen_MainActivity;

public class LoadAdmin_MainActivity extends AppCompatActivity {

    int action;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_main);
        action = getIntent().getIntExtra("action",-1);
        user = getIntent().getStringExtra("user");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadAdmin_MainActivity.this, Admin_MainActivity.class);
                intent.putExtra("action",action);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        },2000);
    }

    @Override
    public void onBackPressed() {

    }
}