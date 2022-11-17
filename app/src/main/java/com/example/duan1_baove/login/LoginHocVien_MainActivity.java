package com.example.duan1_baove.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.activityload.LoadHocVien_MainActivity;
import com.example.duan1_baove.adapter.ViewPagerAdapter;
import com.example.duan1_baove.fragment.hocvien.Create_Fragment_HocVien;
import com.example.duan1_baove.fragment.hocvien.Login_Fragment_HocVien;
import com.google.android.material.textfield.TextInputLayout;

public class LoginHocVien_MainActivity extends AppCompatActivity {
    TextView tv_dangnhap,tv_dangky;
    ViewPager2 viewPager2;
    ViewPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_hoc_vien_main);
        initUi();
        tv_dangnhap.setBackground(this.getResources().getDrawable(R.drawable.bg_white));
        tv_dangky.setBackgroundColor(Color.TRANSPARENT);
        adapter = new ViewPagerAdapter(this);
        viewPager2.setAdapter(adapter);
        tv_dangnhap.setOnClickListener(v -> {
            viewPager2.setCurrentItem(0);
            tv_dangnhap.setBackground(this.getResources().getDrawable(R.drawable.bg_white));
            tv_dangky.setBackgroundColor(Color.TRANSPARENT);
        });
        tv_dangky.setOnClickListener(v -> {
            viewPager2.setCurrentItem(1);
            tv_dangky.setBackground(this.getResources().getDrawable(R.drawable.bg_white));
            tv_dangnhap.setBackgroundColor(Color.TRANSPARENT);
        });

    }

    private void initUi(){
        tv_dangnhap = findViewById(R.id.tv_dangnhap_hocvien);
        tv_dangky = findViewById(R.id.tv_dangky_hocvien);
        viewPager2 = findViewById(R.id.viewpager_hocvien);
    }
}