package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.ViewPagerAdapterNapTien;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.NapTien;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ChucNangNapTien_MainActivity_HocVien extends AppCompatActivity {
    ImageView img_back;
    ViewPager2 viewPager2;
    TabLayout tabLayout;
    ViewPagerAdapterNapTien adapterNapTien;
    String[] tab = {"Nạp tiền","Lịch sử nạp tiền"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang_nap_tien_main_hoc_vien);
        initUi();
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
        adapterNapTien = new ViewPagerAdapterNapTien(this);
        viewPager2.setAdapter(adapterNapTien);
        viewPager2.setUserInputEnabled(false);
        tabLayout.setSelectedTabIndicator(R.drawable.bg_green);
        new TabLayoutMediator(tabLayout,viewPager2,(tab1, position) -> tab1.setText(tab[position])).attach();
    }

    private void initUi() {
        img_back = findViewById(R.id.img_back_naptienhocvien);
        viewPager2 = findViewById(R.id.viewpager_naptienhocvien);
        tabLayout = findViewById(R.id.tablayout_naptienhocvien);
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(ChucNangNapTien_MainActivity_HocVien.this);
    }
}