package com.example.duan1_baove.fragment.hocvien;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.NapTien;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NapTien_Fragment_HocVien extends Fragment {
    View view;
    EditText edt_sotien;
    Button btn_naptien;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy, hh:mm:ss");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_nap_tien___hoc_vien, container, false);
        initUi();
        edt_sotien.addTextChangedListener(textWatcher);
        btn_naptien.setOnClickListener(v -> {
            if (validate()){
                NapTien napTien = new NapTien();
                napTien.setKhachhang_id(HocVien_MainActivity.userHocVien);
                napTien.setSotien(Integer.parseInt(edt_sotien.getText().toString().trim()));
                napTien.setTrangthai("Chưa kiểm duyệt");
                napTien.setDate(sdf.format(new Date()));
                DuAn1DataBase.getInstance(getContext()).napTienDAO().insert(napTien);
                Toast.makeText(getContext(), "Gửi yêu cầu nạp tiền thành công", Toast.LENGTH_SHORT).show();
                edt_sotien.setText("");
            }
        });
        return view;
    }

    private void initUi() {
        edt_sotien =view.findViewById(R.id.edt_sotien_naptienhocvien);
        btn_naptien=  view.findViewById(R.id.btn_naptien_naptienhocvien);
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            String sotien = edt_sotien.getText().toString().trim();
            if (sotien.isEmpty()){
                btn_naptien.setEnabled(false);
                btn_naptien.setBackgroundDrawable(getContext().getDrawable(R.drawable.bg_gray));
            }else {
                btn_naptien.setEnabled(true);
                btn_naptien.setBackgroundDrawable(getContext().getDrawable(R.drawable.custom_btn));
            }
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    public boolean validate(){
        try {
            Integer.parseInt(edt_sotien.getText().toString().trim());
            return true;
        }catch (Exception e){
             e.printStackTrace();
            Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng số tiền", Toast.LENGTH_SHORT).show();
             return false;
        }
    }
}