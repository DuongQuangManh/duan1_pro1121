package com.example.duan1_baove.fragment.hocvien;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.ThongBaoAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.ThongBao;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrangChu_Fragment_HocVien extends Fragment {
    private View view;
    private RecyclerView recyclerView;
    private ThongBaoAdapter adapter;
    private TextView tv_name;
    private CircleImageView avt;
    private List<ThongBao> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trang_chu_hoc_vien, container, false);
        initUi();
        capNhat();
        List<KhachHang> listKhachHang = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
        tv_name.setText(listKhachHang.get(0).getHoten());
        if (listKhachHang.get(0).getAvata()==null){
            avt.setImageResource(R.drawable.ic_account);
        }else {
            String linkimg = listKhachHang.get(0).getAvata();
            Log.d("adapter",linkimg+" link");
            avt.setImageDrawable(Drawable.createFromPath(linkimg));
        }
        return view;
    }

    private void initUi() {
        recyclerView = view.findViewById(R.id.rcy_trangchu_hocvien);
        tv_name = view.findViewById(R.id.tv_name_trangchu_hocvien);
        avt = view.findViewById(R.id.avt_trangchu_hocvien);
    }

    public void capNhat(){
        list = DuAn1DataBase.getInstance(getContext()).thongBaoDAO().getAll();
        adapter = new ThongBaoAdapter(getContext(), new ThongBaoAdapter.MyOnclick() {
            @Override
            public void update(ThongBao thongBao) {

            }

            @Override
            public void delete(ThongBao thongBao) {

            }
        });
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
}