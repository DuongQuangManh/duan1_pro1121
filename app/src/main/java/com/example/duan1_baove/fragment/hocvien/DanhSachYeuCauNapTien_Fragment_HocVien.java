package com.example.duan1_baove.fragment.hocvien;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.DanhSachNapTienAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.NapTien;

import java.util.List;

public class DanhSachYeuCauNapTien_Fragment_HocVien extends Fragment {
    View view;
    RecyclerView recyclerView;
    DanhSachNapTienAdapter adapter;
    List<NapTien> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_danh_sach_yeu_cau_nap_tien___hoc_vien, container, false);
        initUi();
        capnhat();
        return view;
    }

    private void capnhat() {
        list = DuAn1DataBase.getInstance(getContext()).napTienDAO().getByID(HocVien_MainActivity.userHocVien);
        adapter = new DanhSachNapTienAdapter(getContext(), new DanhSachNapTienAdapter.IClickListener() {
            @Override
            public void duyet(NapTien napTien) {

            }
        });
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initUi() {
        recyclerView = view.findViewById(R.id.rcy_danhsachyeucaunaptien);
    }

    @Override
    public void onResume() {
        super.onResume();
        capnhat();
    }
}