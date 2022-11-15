package com.example.duan1_baove.fragment.hocvien;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.duan1_baove.Admin_MainActivity;
import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import com.example.duan1_baove.model.KhachHang;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class DoiMatKhau_MainActivity_HocVien extends AppCompatActivity {
    TextInputLayout txt,txt1,txt2;
    EditText edt_mk1,edt_mk2,edt_mkcu;
    Button btn_doimatkhau,btn_huy;
    ImageView img_back;

    List<KhachHang> list;
    KhachHang khachHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau_main_hoc_vien);
        initUi();
        list = DuAn1DataBase.getInstance(this).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);
        khachHang = list.get(0);
        txt.setEndIconOnClickListener(v -> {
            showpass(edt_mkcu,txt);
        });
        txt1.setEndIconOnClickListener(v -> {
            showpass(edt_mk1,txt1);
        });
        txt2.setEndIconOnClickListener(v -> {
            showpass(edt_mk2,txt2);
        });
        btn_doimatkhau.setOnClickListener(v -> {
            if (checkvalidate()){
                khachHang.setPass(edt_mk1.getText().toString().trim());
                DuAn1DataBase.getInstance(this).khachHangDAO().update(khachHang);
                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                huy();
            }
        });
        btn_huy.setOnClickListener(v -> {
            huy();
        });
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    private void huy(){
        edt_mkcu.setText("");
        edt_mk2.setText("");
        edt_mk1.setText("");
    }
    private void initUi() {
        txt = findViewById(R.id.txti_doimatkhauhocvien);
        txt1 = findViewById(R.id.txti1_doimatkhauhocvien);
        txt2 = findViewById(R.id.txti2_doimatkhauhocvien);
        edt_mk1 = findViewById(R.id.edt_mk1_doimatkhauhocvien);
        edt_mk2 = findViewById(R.id.edt_mk2_doimatkhauhocvien);
        edt_mkcu = findViewById(R.id.edt_mk_doimatkhauhocvien);
        btn_doimatkhau = findViewById(R.id.btn_doimatkhauhocvien);
        btn_huy = findViewById(R.id.btn_huy_doimatkhauhocvien);
        edt_mkcu.addTextChangedListener(textWatcher);
        edt_mk1.addTextChangedListener(textWatcher);
        edt_mk2.addTextChangedListener(textWatcher);
        img_back = findViewById(R.id.img_back_doimatkhauhocvien);
    }

    private void showpass(EditText edt,TextInputLayout txt) {
        if(edt.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            edt.setInputType( InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txt.setEndIconDrawable(R.drawable.ic_eye_off);
        }else {
            edt.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
            txt.setEndIconDrawable(R.drawable.ic_eye);
        }
        edt.setSelection(edt.getText().length());
    }

    public boolean checkvalidate(){
        if (edt_mkcu.getText().toString().trim().equals(DuAn1DataBase.getInstance(this).khachHangDAO().getPass(HocVien_MainActivity.userHocVien))){
            if (edt_mk1.getText().toString().trim().length()<8 || edt_mk2.getText().toString().trim().length()<8){
                Toast.makeText(this, "Vui lòng nhập mật khẩu mới >= 8 kí tự", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                if (edt_mk1.getText().toString().trim().equals(edt_mk2.getText().toString().trim())){
                    return true;
                }else {
                    Toast.makeText(this, "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }else {
            Toast.makeText(this, "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String mkcu = edt_mkcu.getText().toString().trim();
            String mk1 = edt_mk1.getText().toString().trim();
            String mk2= edt_mk2.getText().toString().trim();
            if (!mkcu.isEmpty() && ! mk1.isEmpty() && ! mk2.isEmpty()){
                btn_doimatkhau.setEnabled(true);
                btn_doimatkhau.setBackground(getDrawable(R.drawable.bg_green));
            }else {
                btn_doimatkhau.setEnabled(true);
                btn_doimatkhau.setBackground(getDrawable(R.drawable.bg_gray));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}