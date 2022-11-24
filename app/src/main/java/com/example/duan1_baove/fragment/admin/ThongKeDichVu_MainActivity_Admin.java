package com.example.duan1_baove.fragment.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.R;

public class ThongKeDichVu_MainActivity_Admin extends AppCompatActivity {
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_dich_vu_main_admin);
        initUi();
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    private void initUi() {
        img_back = findViewById(R.id.img_back_thongkedichvu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(ThongKeDichVu_MainActivity_Admin.this);
    }
}