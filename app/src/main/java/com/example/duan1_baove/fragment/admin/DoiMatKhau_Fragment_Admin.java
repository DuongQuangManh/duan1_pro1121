package com.example.duan1_baove.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1_baove.Admin_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;


public class DoiMatKhau_Fragment_Admin extends Fragment {
    View view;
    TextInputLayout txt,txt1,txt2;
    EditText edt_mk1,edt_mk2,edt_mkcu;
    Button btn_doimatkhau,btn_huy;

    List<Admin> list;
    Admin admin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_doi_mat_khau___admin, container, false);
        initUi();
        list = DuAn1DataBase.getInstance(getContext()).adminDAO().checkaccount(Admin_MainActivity.user);
        admin = list.get(0);
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
                admin.setPass(edt_mk1.getText().toString().trim());
                DuAn1DataBase.getInstance(getContext()).adminDAO().update(admin);
                Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                huy();
            }
        });
        btn_huy.setOnClickListener(v -> {
            huy();
        });
        return view;
    }

    private void huy(){
        edt_mkcu.setText("");
        edt_mk2.setText("");
        edt_mk1.setText("");
    }
    private void initUi() {
        txt = view.findViewById(R.id.txti_doimatkhauadmin);
        txt1 = view.findViewById(R.id.txti1_doimatkhauadmin);
        txt2 = view.findViewById(R.id.txti2_doimatkhauadmin);
        edt_mk1 = view.findViewById(R.id.edt_mk1_doimatkhauadmin);
        edt_mk2 = view.findViewById(R.id.edt_mk2_doimatkhauadmin);
        edt_mkcu = view.findViewById(R.id.edt_mk_doimatkhauadmin);
        btn_doimatkhau = view.findViewById(R.id.btn_doimatkhauadmin);
        btn_huy = view.findViewById(R.id.btn_huy_doimatkhauadmin);
        edt_mkcu.addTextChangedListener(textWatcher);
        edt_mk1.addTextChangedListener(textWatcher);
        edt_mk2.addTextChangedListener(textWatcher);
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
        if (edt_mkcu.getText().toString().trim().equals(DuAn1DataBase.getInstance(getContext()).adminDAO().getPass(Admin_MainActivity.user))){
            if (edt_mk1.getText().toString().trim().length()<8 || edt_mk2.getText().toString().trim().length()<8){
                Toast.makeText(getContext(), "Vui lòng nhập mật khẩu mới >= 8 kí tự", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                if (edt_mk1.getText().toString().trim().equals(edt_mk2.getText().toString().trim())){
                    return true;
                }else {
                    Toast.makeText(getContext(), "Mật khẩu mới không trùng khớp", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }else {
            Toast.makeText(getContext(), "Mật khẩu cũ không chính xác", Toast.LENGTH_SHORT).show();
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
                btn_doimatkhau.setBackground(getContext().getDrawable(R.drawable.bg_green));
            }else {
                btn_doimatkhau.setEnabled(true);
                btn_doimatkhau.setBackground(getContext().getDrawable(R.drawable.bg_gray));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}