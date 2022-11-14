package com.example.duan1_baove.activityload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.login.Screen_MainActivity;

public class LoadHocVien_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_hoc_vien_main);
        String user = getIntent().getStringExtra("user");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LoadHocVien_MainActivity.this, HocVien_MainActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        },2000);
    }

    @Override
    public void onBackPressed() {

    }
}