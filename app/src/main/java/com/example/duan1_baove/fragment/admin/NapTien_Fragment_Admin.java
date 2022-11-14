package com.example.duan1_baove.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.SpinnerAdapterNapTien;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;

import java.util.List;

public class NapTien_Fragment_Admin extends Fragment {
    View view;
    private Spinner spn_thanhvien;
    private EditText edt_sotien;
    private Button btn_naptien;
    private List<KhachHang> list;
    String userThanhVien;
    int soDuHientai;
    int soDuMoi;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=  inflater.inflate(R.layout.fragment_nap_tien___admin, container, false);
        initUi();
        list = DuAn1DataBase.getInstance(getContext()).khachHangDAO().getAll();
        SpinnerAdapterNapTien adapter = new SpinnerAdapterNapTien(getContext(), R.layout.item_spiner_naptien,list);
        spn_thanhvien.setAdapter(adapter);
        spn_thanhvien.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userThanhVien = list.get(position).getSoDienThoai();
                soDuHientai = list.get(position).getSoDu();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        edt_sotien.addTextChangedListener(textWatcher);
        btn_naptien.setOnClickListener(v -> {
            List<KhachHang> list = DuAn1DataBase.getInstance(getContext()).khachHangDAO().checkAcc(userThanhVien);
            if (validate()){
                soDuMoi = soDuHientai + Integer.parseInt(edt_sotien.getText().toString().trim());
                KhachHang khachHang = list.get(0);
                khachHang.setSoDu(soDuMoi);
                DuAn1DataBase.getInstance(getContext()).khachHangDAO().update(khachHang);
                Toast.makeText(getContext(), "Nạp tiền thành công", Toast.LENGTH_SHORT).show();
                edt_sotien.setText("");
            }
        });
        return view;
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (validate()){
                btn_naptien.setEnabled(true);
                btn_naptien.setBackground(getContext().getDrawable(R.drawable.bg_green));
            }else {
                btn_naptien.setEnabled(false);
                btn_naptien.setBackground(getContext().getDrawable(R.drawable.bg_gray));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void initUi() {
        spn_thanhvien = view.findViewById(R.id.spn_thanhvien_naptienadmin);
        edt_sotien = view.findViewById(R.id.edt_sotien_naptienadmin);
        btn_naptien = view.findViewById(R.id.btn_naptien_naptienadmin);
    }
    private boolean validate(){
        if (edt_sotien.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            try {
                Integer.parseInt(edt_sotien.getText().toString().trim());
                return true;
            }catch (Exception e){
                Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng số tiền", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

}