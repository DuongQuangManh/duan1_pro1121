package com.example.duan1_baove.fragment.admin;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.duan1_baove.Admin_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TaiKhoan_Fragment_Admin extends Fragment {
    private static final int IMAGE_PICKER_SELECT_TAIKHOAN = 0;
    View view;
    private CircleImageView avt;
    private EditText edt_tk,edt_name,edt_tennganhang,edt_stk;
    private Button btn_thaydoithongtin,btn_thaydoi,btn_huy;
    private LinearLayout layout_thaydoithongtin,layout_thaydoi;
    private boolean isThayDoi;
    private  List<Admin> list;
    Admin admin;
    String img;
    Admin_MainActivity mainActivity;


    NavigationView navigationView;
    CircleImageView avt_adminmain;
    TextView tv_user_header;
    View layout_header;

    public TaiKhoan_Fragment_Admin(Admin_MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tai_khoan___admin, container, false);
        initUi();
        list = DuAn1DataBase.getInstance(getContext()).adminDAO().checkaccount(Admin_MainActivity.user);
        admin = list.get(0);
        img = DuAn1DataBase.getInstance(getContext()).adminDAO().getObject(Admin_MainActivity.user).getHinhanh();
        if (admin.getHinhanh()==null){
            avt.setImageResource(R.drawable.ic_account);
        }else {
            String linkimg = admin.getHinhanh();
            avt.setImageDrawable(Drawable.createFromPath(linkimg));
        }
        edt_tk.setText(admin.getUser());
        edt_name.setText(admin.getName());
        edt_tennganhang.setText(admin.getTennganhang());
        edt_stk.setText(admin.getStk());

        btn_thaydoithongtin.setOnClickListener(v -> {
            btnclick();
        });
        btn_thaydoi.setOnClickListener(v -> {
            btnclick();
            if (validate()){
                admin.setName(edt_name.getText().toString().trim());
                admin.setTennganhang(edt_tennganhang.getText().toString().trim());
                admin.setStk(edt_stk.getText().toString().trim());
                admin.setHinhanh(img);
                DuAn1DataBase.getInstance(getContext()).adminDAO().update(admin);
                tv_user_header.setText("Hi, "+edt_name.getText().toString().trim());
            }
        });
        btn_huy.setOnClickListener(v -> {
            btnclick();
        });
        avt.setOnClickListener(v -> {
            selectImg();
        });
        return view;
    }

    private void btnclick(){
        if (isThayDoi){
            layout_thaydoithongtin.setVisibility(View.VISIBLE);
            btn_thaydoithongtin.setEnabled(true);
            layout_thaydoi.setVisibility(View.INVISIBLE);
            avt.setEnabled(false);
            btn_huy.setEnabled(false);
            btn_thaydoi.setEnabled(false);
            edt_tk.setEnabled(false);
            edt_name.setEnabled(false);
            edt_stk.setEnabled(false);
            edt_tennganhang.setEnabled(false);
            isThayDoi = false;
        }else {
            layout_thaydoithongtin.setVisibility(View.INVISIBLE);
            btn_thaydoithongtin.setEnabled(false);
            layout_thaydoi.setVisibility(View.VISIBLE);
            btn_huy.setEnabled(true);
            btn_thaydoi.setEnabled(true);
            avt.setEnabled(true);
            edt_tk.setEnabled(false);
            edt_name.setEnabled(true);
            edt_stk.setEnabled(true);
            edt_tennganhang.setEnabled(true);
            isThayDoi = true;
        }
    }
    private void initUi() {
        avt = view.findViewById(R.id.avt_thongtin);
        avt.setEnabled(false);
        edt_tk = view.findViewById(R.id.edt_taikhoan_thongtin);
        edt_name = view.findViewById(R.id.edt_hoten_thongtin);
        edt_tennganhang = view.findViewById(R.id.edt_tennganhang_thongtin);
        edt_stk = view.findViewById(R.id.edt_sotaikhoan_thongtin);
        btn_thaydoithongtin = view.findViewById(R.id.btn_thaydoithongtin);
        btn_thaydoi = view.findViewById(R.id.btn_thaydoi_thongtin);
        btn_huy = view.findViewById(R.id.btn_huy_thongtin);
        layout_thaydoithongtin = view.findViewById(R.id.layout_thaydoithongtin);
        layout_thaydoi = view.findViewById(R.id.layout_thaydoi);

        navigationView = mainActivity.findViewById(R.id.navigation_view_admin);
        layout_header = navigationView.getHeaderView(0);
        avt_adminmain = layout_header.findViewById(R.id.circle_imageview_headeradmin);
        tv_user_header = layout_header.findViewById(R.id.tv_name_header);
    }
    private void selectImg(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*, video/*");
        if (i.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(i, IMAGE_PICKER_SELECT_TAIKHOAN);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_PICKER_SELECT_TAIKHOAN
                && resultCode == Activity.RESULT_OK) {
            img = getPathFromURI(Uri.parse(data.getDataString()));
            avt.setImageDrawable(Drawable.createFromPath(img));
            avt_adminmain.setImageDrawable(Drawable.createFromPath(img));
        }
    }

    private boolean validate(){
        if (edt_name.getText().toString().trim().isEmpty() || edt_stk.getText().toString().trim().isEmpty() || edt_tk.getText().toString().trim().isEmpty() || edt_tennganhang.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Không được bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}