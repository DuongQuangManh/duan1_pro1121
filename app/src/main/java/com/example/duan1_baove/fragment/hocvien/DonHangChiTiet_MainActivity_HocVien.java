package com.example.duan1_baove.fragment.hocvien;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.DonHangAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DonHangChiTiet;

import java.util.List;

public class DonHangChiTiet_MainActivity_HocVien extends AppCompatActivity {
    private ImageView img_back;
    private RecyclerView recyclerView;
    private List<DonHangChiTiet> list;
    private DonHangAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang_chi_tiet_main_hoc_vien);
        initUi();
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
        capnhat();
    }

    private void capnhat() {
        list = DuAn1DataBase.getInstance(this).donHangChiTietDAO().getAll();
        adapter = new DonHangAdapter(this, new DonHangAdapter.IClickListener() {
            @Override
            public void duyet(DonHangChiTiet donHangChiTiet) {

            }
        });
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initUi() {
        img_back = findViewById(R.id.img_back_donhanghocvien);
        recyclerView = findViewById(R.id.rcy_donhang_donhangchitiet);
    }

}