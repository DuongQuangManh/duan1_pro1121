package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.LichSuGiaoDichAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.LichSuGiaoDich;

import java.util.List;

public class LichSuGiaoDich_MainActivity_HocVien extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LichSuGiaoDichAdapter adapter;
    private ImageView img_back;
    private List<LichSuGiaoDich> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_giao_dich_main_hoc_vien);
        initUi();
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
        capnhat();
    }

    private void capnhat() {
        list = DuAn1DataBase.getInstance(this).lichSuGiaoDichDAO().getByID(HocVien_MainActivity.userHocVien);
        adapter = new LichSuGiaoDichAdapter(this);
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initUi() {
        recyclerView = findViewById(R.id.rcy_lichsugiaodich);
        img_back = findViewById(R.id.img_back_lichsugiaodichhocvien);
    }
}