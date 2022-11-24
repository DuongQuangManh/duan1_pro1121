package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.R;

public class ChucNangNapTien_MainActivity_HocVien extends AppCompatActivity {
    ImageView img_back;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuc_nang_nap_tien_main_hoc_vien);
        initUi();
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private void initUi() {
        img_back = findViewById(R.id.img_back_naptienhocvien);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(ChucNangNapTien_MainActivity_HocVien.this);
    }
}