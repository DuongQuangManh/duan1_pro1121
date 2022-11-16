package com.example.duan1_baove.fragment.hocvien;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan1_baove.Admin_MainActivity;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.activityload.Splash_MainActivity;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CaNhan_Fragment_HocVien extends Fragment {
    private View view;
    private ImageView img_naptien;
    private CircleImageView avt_hocvien;
    private TextView tv_name,tv_sodu,tv_taikhoan,tv_doimatkhau,tv_lichsugiaodich,tv_lienhe,tv_doimauchudao,tv_dangxuat;
    NumberFormat numberFormat = new DecimalFormat("###,###,###");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_ca_nhan_hoc_vien, container, false);
        initUi();
        List<KhachHang> acc = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
        tv_name.setText(acc.get(0).getHoten());
        tv_sodu.setText("Số dư: "+numberFormat.format(acc.get(0).getSoDu())+" vnđ");

        if (acc.get(0).getAvata()==null){
            avt_hocvien.setImageResource(R.drawable.ic_account);
        }else {
            String linkimg = acc.get(0).getAvata();
            Log.d("adapter",linkimg+" link");
            avt_hocvien.setImageDrawable(Drawable.createFromPath(linkimg));
        }
        tv_dangxuat.setOnClickListener(v -> {
            new AlertDialog.Builder(getContext()).setTitle("Đăng xuất").setMessage("Bạn có chắc chắn muốn đăng xuất?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), Splash_MainActivity.class);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("No",null).show();
        });
        tv_taikhoan.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),TaiKhoan_MainActivity_HocVien.class);
            getActivity().startActivity(intent);
        });
        tv_doimatkhau.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),DoiMatKhau_MainActivity_HocVien.class);
            getActivity().startActivity(intent);
        });
        tv_lichsugiaodich.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(),LichSuGiaoDich_MainActivity_HocVien.class);
            getActivity().startActivity(intent);
        });
        return view;
    }

    private void initUi() {
        img_naptien = view.findViewById(R.id.img_naptien_canhanhocvien);
        avt_hocvien = view.findViewById(R.id.avt_canhan_hocvien);
        tv_name = view.findViewById(R.id.tv_name_canhanhocvien);
        tv_sodu = view.findViewById(R.id.tv_sodu_canhanhocvien);
        tv_taikhoan = view.findViewById(R.id.tv_taikhoan_canhanhocvien);
        tv_doimatkhau = view.findViewById(R.id.tv_doimatkhau_canhanhocvien);
        tv_lichsugiaodich = view.findViewById(R.id.tv_lichsugiaodich_canhanhocvien);
        tv_lienhe = view.findViewById(R.id.tv_lienhe_canhanhocvien);
        tv_doimauchudao = view.findViewById(R.id.tv_doimauchudao_canhanhocvien);
        tv_dangxuat = view.findViewById(R.id.tv_dangxuat_canhanhocvien);

    }

    @Override
    public void onResume() {
        super.onResume();
        List<KhachHang> acc = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
        if (acc.get(0).getAvata()==null){
            avt_hocvien.setImageResource(R.drawable.ic_account);
        }else {
            String linkimg = acc.get(0).getAvata();
            Log.d("adapter",linkimg+" link");
            avt_hocvien.setImageDrawable(Drawable.createFromPath(linkimg));
        }
        tv_name.setText(acc.get(0).getHoten());
        tv_sodu.setText("Số dư: "+numberFormat.format(acc.get(0).getSoDu())+" vnđ");
    }
}