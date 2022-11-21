package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DangKiTapThu;
import com.example.duan1_baove.model.KhachHang;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

public class DangKiTapThu_MainActivity_HocVien extends AppCompatActivity {
    private EditText edt_name,edt_gioitinh,edt_ngaysinh,edt_sodienthoai,edt_chonngaytap;
    private TextInputLayout txt_chonngay;
    private Button btn_huy,btn_dangki;
    private ImageView img_back;
    Calendar lich = Calendar.getInstance();
    Calendar lichnow = Calendar.getInstance();
    int yearnow = lichnow.get(Calendar.YEAR);
    int monthnow = lichnow.get(Calendar.MONTH);
    int daynow = lichnow.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki_tap_thu_main_hoc_vien);
        initUi();
        KhachHang khachHang = DuAn1DataBase.getInstance(this).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien).get(0);
        edt_name.setText(khachHang.getHoten());
        edt_gioitinh.setText(khachHang.getGioitinh());
        edt_ngaysinh.setText(khachHang.getNamSinh());
        edt_sodienthoai.setText(khachHang.getSoDienThoai());
        txt_chonngay.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = lich.get(Calendar.YEAR);
                int month = lich.get(Calendar.MONTH);
                int day = lich.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(DangKiTapThu_MainActivity_HocVien.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        edt_chonngaytap.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                    }
                },year,month,day).show();
            }
        });
        btn_huy.setOnClickListener(v -> {
            onBackPressed();
        });
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
        List<DangKiTapThu> list = DuAn1DataBase.getInstance(this).dangKiTapThuDAO().check(HocVien_MainActivity.userHocVien);
        if (list.size()>0){
            btn_dangki.setEnabled(false);
            btn_dangki.setBackground(getDrawable(R.drawable.bg_gray));
        }else {
            btn_dangki.setEnabled(true);
            btn_dangki.setBackground(getDrawable(R.drawable.bg_green));
        }
        btn_dangki.setOnClickListener(v -> {
            DangKiTapThu dangKiTapThu = new DangKiTapThu();
            dangKiTapThu.setNgayTap(edt_chonngaytap.getText().toString().trim());
            dangKiTapThu.setKhachhang_id(HocVien_MainActivity.userHocVien);
            dangKiTapThu.setNgaydangki(daynow+"-"+monthnow+"-"+yearnow+", "+lichnow.get(Calendar.HOUR)+":"+lichnow.get(Calendar.MINUTE)+":"+lichnow.get(Calendar.SECOND));
            DuAn1DataBase.getInstance(this).dangKiTapThuDAO().insert(dangKiTapThu);
            Toast.makeText(this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
            onBackPressed();
        });
    }

    private void initUi() {
        edt_name = findViewById(R.id.edt_name_dangkitapthu);
        edt_gioitinh = findViewById(R.id.edt_gioitinh_dangkitapthu);
        edt_ngaysinh = findViewById(R.id.edt_ngaysinh_dangkitapthu);
        edt_sodienthoai = findViewById(R.id.edt_lienhe_dangkitapthu);
        edt_chonngaytap = findViewById(R.id.edt_chonngaytap_dangkitapthu);
        txt_chonngay = findViewById(R.id.txtinput_ngay_dangkitapthu);
        btn_huy = findViewById(R.id.btn_huy_dangkitapthu);
        btn_dangki = findViewById(R.id.btn_dangki_dangkitapthu);
        img_back = findViewById(R.id.img_back_dangkitapthu);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(DangKiTapThu_MainActivity_HocVien.this);
    }
}