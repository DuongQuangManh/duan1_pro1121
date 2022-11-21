package com.example.duan1_baove.fragment.admin;

import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.DebugUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_baove.Admin_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.DatLichTapAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DatLichTap;
import com.example.duan1_baove.model.KhachHang;

import java.util.List;

public class DanhSachDayHocVien_Fragment_Admin extends Fragment {
    View view;
    RecyclerView recyclerView;
    DatLichTapAdapter adapter;
    private List<DatLichTap> list;
    TextView tv_name,tv_ngaysinh,tv_gioitinh,tv_lienhe;
    ImageView avt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_danh_sach_day_hoc_vien___admin, container, false);
        initUi();
        capnhat();
        return view;
    }

    private void capnhat() {
        list = DuAn1DataBase.getInstance(getContext()).datLichTapDAO().getCVbyIdPT(Admin_MainActivity.user);
        adapter = new DatLichTapAdapter(getContext(), new DatLichTapAdapter.IClickListener() {
            @Override
            public void thongtinhocvien(DatLichTap datLichTap) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_thongtinkhachhang);
                dialog.show();
                Window window = dialog.getWindow();
                if (window == null){
                    return;
                }
                window.setBackgroundDrawable(null);
                tv_name = dialog.findViewById(R.id.tv_name_itemthongtinkhachhang);
                tv_ngaysinh = dialog.findViewById(R.id.tv_ngaysinh_itemthongtinkhachhang);
                tv_gioitinh = dialog.findViewById(R.id.tv_gioitinh_itemthongtinkhachhang);
                tv_lienhe = dialog.findViewById(R.id.tv_lienhe_itemthongtinkhachhang);
                avt = dialog.findViewById(R.id.avt_itemthongtinkhachhang);
                KhachHang khachHang = DuAn1DataBase.getInstance(getContext()).khachHangDAO().getObject(datLichTap.getKhachhang_id());

                if (khachHang.getAvata()==null){
                    avt.setImageResource(R.drawable.ic_account);
                }else {
                    String linkimg = khachHang.getAvata();
                    Log.d("adapter",linkimg+" link");
                    avt.setImageDrawable(Drawable.createFromPath(linkimg));
                }
                tv_name.setText("Tên: "+khachHang.getHoten());
                tv_ngaysinh.setText("Ngày sinh: "+khachHang.getNamSinh());
                tv_gioitinh.setText("Giới tính: "+ khachHang.getGioitinh());
                tv_lienhe.setText("Liên hệ: "+khachHang.getSoDienThoai());
            }

            @Override
            public void thongtinpt(DatLichTap datLichTap) {

            }
        });
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initUi() {
        recyclerView = view.findViewById(R.id.rcy_danhsachdayhocvien);
    }
}