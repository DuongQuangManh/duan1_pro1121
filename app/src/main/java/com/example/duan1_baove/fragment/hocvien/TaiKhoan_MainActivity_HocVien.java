package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TaiKhoan_MainActivity_HocVien extends AppCompatActivity {
    private static final int IMAGE_PICKER_SELECT_TAIKHOANHOCVIEN = 0;
    private CircleImageView avt;
    private EditText edt_tk,edt_name,edt_namsinh;
    private Spinner spn_gioitinh;
    private Button btn_thaydoithongtin,btn_thaydoi,btn_huy;
    private LinearLayout layout_thaydoithongtin,layout_thaydoi;
    private TextInputLayout txt;
    private boolean isThayDoi;
    private ImageView img_back;

    private List<KhachHang> list;
    KhachHang khachHang;
    String img;
    Calendar lich = Calendar.getInstance();
    String[] gioitinh = {"Nam","Nữ"};
    String strGioitinh;
    int idgioitinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tai_khoan_main_hoc_vien);
        initUi();

        list = DuAn1DataBase.getInstance(this).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
        khachHang = list.get(0);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,gioitinh);
        spn_gioitinh.setAdapter(adapter);
        spn_gioitinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strGioitinh = gioitinh[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        for (int i=0;i<gioitinh.length;i++){
            if (khachHang.getGioitinh().equals(gioitinh[i])){
                idgioitinh = i;
                Log.d("abcas",i+"");
            }
        }

        if (khachHang.getAvata()==null){
            avt.setImageResource(R.drawable.ic_account);
        }else {
            String linkimg = khachHang.getAvata();
            avt.setImageDrawable(Drawable.createFromPath(linkimg));
        }
        edt_tk.setText(khachHang.getSoDienThoai());
        edt_name.setText(khachHang.getHoten());
        edt_namsinh.setText(khachHang.getNamSinh());
        spn_gioitinh.setSelection(idgioitinh);

        btn_thaydoithongtin.setOnClickListener(v -> {
            btnclick();
        });
        btn_thaydoi.setOnClickListener(v -> {
            btnclick();
            if (validate()){
                khachHang.setHoten(edt_name.getText().toString().trim());
                khachHang.setNamSinh(edt_namsinh.getText().toString().trim());
                khachHang.setGioitinh(strGioitinh);
                khachHang.setAvata(img);
                DuAn1DataBase.getInstance(this).khachHangDAO().update(khachHang);
                Toast.makeText(this, "Thay đổi thông tin thành công", Toast.LENGTH_SHORT).show();
            }
        });
        btn_huy.setOnClickListener(v -> {
            btnclick();
        });
        avt.setOnClickListener(v -> {
            selectImg();
        });
        txt.setStartIconOnClickListener(v -> {
            int year = lich.get(Calendar.YEAR);
            int month = lich.get(Calendar.MONTH);
            int day = lich.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edt_namsinh.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            },year,month,day).show();
        });


        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }

    private boolean validate(){
        if (edt_name.getText().toString().trim().isEmpty() || edt_namsinh.getText().toString().trim().isEmpty()|| edt_tk.getText().toString().trim().isEmpty()){
            Toast.makeText(this, "Vui lòng không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    private void initUi() {
        avt = findViewById(R.id.avt_thongtinhocvien);
        avt.setEnabled(false);
        edt_tk = findViewById(R.id.edt_tk_thongtinhocvien);
        edt_name = findViewById(R.id.edt_name_thongtinhocvien);
        edt_namsinh = findViewById(R.id.edt_ngaysinh_thongtinhocvien);
        spn_gioitinh = findViewById(R.id.spn_gioitinh_thongtinhocvien);
        spn_gioitinh.setEnabled(false);
        btn_thaydoithongtin = findViewById(R.id.btn_thaydoithongtinhocvien);
        btn_thaydoi = findViewById(R.id.btn_thaydoi_thongtinhocvien);
        btn_huy = findViewById(R.id.btn_huy_thongtinhocvien);
        layout_thaydoithongtin = findViewById(R.id.layout_thaydoithongtinhocvien);
        layout_thaydoi = findViewById(R.id.layout_thaydoihocvien);
        txt = findViewById(R.id.txtinput_ngay_thongtinhocvien);
        txt.setEnabled(false);
        img_back = findViewById(R.id.img_back_thonghocvien);
    }
    private void btnclick() {
        if (isThayDoi) {
            layout_thaydoithongtin.setVisibility(View.VISIBLE);
            btn_thaydoithongtin.setEnabled(true);
            layout_thaydoi.setVisibility(View.INVISIBLE);
            avt.setEnabled(false);
            btn_huy.setEnabled(false);
            btn_thaydoi.setEnabled(false);
            edt_tk.setEnabled(false);
            edt_name.setEnabled(false);
            txt.setEnabled(false);
            edt_namsinh.setEnabled(false);
            spn_gioitinh.setEnabled(false);
            isThayDoi = false;
        } else {
            layout_thaydoithongtin.setVisibility(View.INVISIBLE);
            btn_thaydoithongtin.setEnabled(false);
            layout_thaydoi.setVisibility(View.VISIBLE);
            btn_huy.setEnabled(true);
            btn_thaydoi.setEnabled(true);
            avt.setEnabled(true);
            edt_tk.setEnabled(false);
            edt_name.setEnabled(true);
            txt.setEnabled(true);
            edt_namsinh.setEnabled(true);
            spn_gioitinh.setEnabled(true);
            isThayDoi = true;
        }
    }
    private void selectImg(){
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*, video/*");
        if (i.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(i, IMAGE_PICKER_SELECT_TAIKHOANHOCVIEN);
        }

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICKER_SELECT_TAIKHOANHOCVIEN
                && resultCode == Activity.RESULT_OK) {
            img = getPathFromURI(Uri.parse(data.getDataString()));
            avt.setImageDrawable(Drawable.createFromPath(img));
//            avt_adminmain.setImageDrawable(Drawable.createFromPath(img));
        }
    }


    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(TaiKhoan_MainActivity_HocVien.this);
    }
}