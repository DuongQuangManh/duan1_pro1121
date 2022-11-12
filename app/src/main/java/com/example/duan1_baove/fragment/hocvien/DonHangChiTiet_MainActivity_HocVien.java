package com.example.duan1_baove.fragment.hocvien;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_baove.R;

public class DonHangChiTiet_MainActivity_HocVien extends AppCompatActivity {
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_chi_tiet_main_hoc_vien);
        initUi();
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void initUi() {
        img_back = findViewById(R.id.img_back_donhanghocvien);
    }
}