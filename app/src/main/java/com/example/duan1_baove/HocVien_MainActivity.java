package com.example.duan1_baove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;

import com.example.duan1_baove.adapter.AdapterBotTonNav_HocVien;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HocVien_MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager;
    public static String userHocVien;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_vien_main);
        initUi();
        setUpViewPager();
        userHocVien = getIntent().getStringExtra("user");
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottonnav_trangchu:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.bottonnav_cuahang:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.bottonnav_canhan:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });
    }

    private void initUi(){
        bottomNavigationView = findViewById(R.id.botton_nav_hocvien);
        viewPager = findViewById(R.id.viewpager_hocvien);
    }
    private void setUpViewPager(){
        AdapterBotTonNav_HocVien adapter = new AdapterBotTonNav_HocVien(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
    }

    @Override
    public void onBackPressed() {

    }
}