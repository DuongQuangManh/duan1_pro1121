package com.example.duan1_baove.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.ViewPagerAdapter;

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
        viewPager2.setUserInputEnabled(false);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(LoginHocVien_MainActivity.this);

    }
}