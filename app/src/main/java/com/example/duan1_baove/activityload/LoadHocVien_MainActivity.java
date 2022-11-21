package com.example.duan1_baove.activityload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;

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
                Animatoo.INSTANCE.animateZoom(LoadHocVien_MainActivity.this);
            }
        },2000);
    }

    @Override
    public void onBackPressed() {

    }
}