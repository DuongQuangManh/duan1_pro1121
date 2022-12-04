package com.example.duan1_baove.fragment.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.DanhSachNapTienAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.LichSuGiaoDich;
import com.example.duan1_baove.model.NapTien;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TabDanhSachNapTien_Fragment_Admin extends Fragment {
    View view;
    RecyclerView recyclerView;
    DanhSachNapTienAdapter adapter;
    List<NapTien> list;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_tab_danh_sach_nap_tien___admin, container, false);
        initUi();
        capnhat();
        return view;
    }

    private void capnhat() {
        list = DuAn1DataBase.getInstance(getContext()).napTienDAO().getAll();
        adapter = new DanhSachNapTienAdapter(getContext(), new DanhSachNapTienAdapter.IClickListener() {
            @Override
            public void duyet(NapTien napTien) {
                new AlertDialog.Builder(getContext()).setTitle("Duyệt yêu cầu nạp tiền")
                        .setMessage("Bạn có chắc chắn muốn duyệt đơn nạp tiền ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                KhachHang khachHang = DuAn1DataBase.getInstance(getContext()).khachHangDAO().getObject(napTien.getKhachhang_id());
                                khachHang.setSoDu(DuAn1DataBase.getInstance(getContext()).khachHangDAO().getSoDU(napTien.getKhachhang_id())+napTien.getSotien());
                                DuAn1DataBase.getInstance(getContext()).khachHangDAO().update(khachHang);
                                napTien.setTrangthai("Đã kiểm duyệt");
                                DuAn1DataBase.getInstance(getContext()).napTienDAO().update(napTien);
                                LichSuGiaoDich lichSuGiaoDich = new LichSuGiaoDich();
                                lichSuGiaoDich.setThoigian(sdf.format(new Date()));
                                lichSuGiaoDich.setSoTien(napTien.getSotien());
                                lichSuGiaoDich.setType("Cộng");
                                lichSuGiaoDich.setKhachang_id(napTien.getKhachhang_id());
                                DuAn1DataBase.getInstance(getContext()).lichSuGiaoDichDAO().insert(lichSuGiaoDich);
                                capnhat();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

            }
        });
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initUi() {
        recyclerView = view.findViewById(R.id.rcy_danhsachyeucaunaptien_admin);
    }
}