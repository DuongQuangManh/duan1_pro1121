package com.example.duan1_baove.fragment.hocvien;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1_baove.R;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.service.SignUpServiceHocVien;
import com.google.android.material.textfield.TextInputLayout;


import java.util.Calendar;
import java.util.regex.Pattern;

public class Create_Fragment_HocVien extends Fragment {
    View view;
    private EditText edt_user,edt_name,edt_lich,edt_mk1,edt_mk2;
    private Button btn_tao,btn_huy;
    private TextInputLayout txt_lich,txt_mk1,txt_mk2;
    private Spinner spn_gioitinh;
    Calendar lich = Calendar.getInstance();
    String[] gioitinh = {"Nam","Nữ"};
    String strGioitinh;
    Intent intent;
    Calendar datenow = Calendar.getInstance();
    int year = datenow.get(Calendar.YEAR);
    int month = datenow.get(Calendar.MONTH)+1;
    int day = datenow.get(Calendar.DAY_OF_MONTH);

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("sendActionToCreateHocVien")){
                Toast.makeText(context, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                edt_user.setText("");
                edt_lich.setText("");
                edt_mk1.setText("");
                edt_mk2.setText("");
                edt_name.setText("");
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_create___hoc_vien, container, false);
        initUi();
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(broadcastReceiver,new IntentFilter("sendActionToCreateHocVien"));

        txt_mk1.setEndIconOnClickListener(v -> {
            showpass(txt_mk1,edt_mk1);
        });
        txt_mk2.setEndIconOnClickListener(v -> {
            showpass(txt_mk2,edt_mk2);
        });
        txt_lich.setStartIconOnClickListener(v -> {
            int year = lich.get(Calendar.YEAR);
            int month = lich.get(Calendar.MONTH);
            int day = lich.get(Calendar.DAY_OF_MONTH);
            new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    edt_lich.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                }
            },year,month,day).show();
        });

        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,gioitinh);
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

        btn_huy.setOnClickListener(v -> {
            edt_user.setText("");
            edt_lich.setText("");
            edt_mk1.setText("");
            edt_mk2.setText("");
            edt_name.setText("");
        });
        btn_tao.setOnClickListener(v -> {
            if (validate()){
                KhachHang khachHang = new KhachHang();
                khachHang.setHoten(edt_name.getText().toString().trim());
                khachHang.setNamSinh(edt_lich.getText().toString().trim());
                khachHang.setGioitinh(strGioitinh);
                khachHang.setSoDu(0);
                khachHang.setSoDienThoai(edt_user.getText().toString().trim());
                khachHang.setPass(edt_mk1.getText().toString().trim());
                khachHang.setNgay(day+"-"+month+"-"+year);
                intent = new Intent(getContext(), SignUpServiceHocVien.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("object",khachHang);
                intent.putExtras(bundle);
                getContext().startService(intent);
            }

        });
        return view;
    }

    private void initUi() {
        edt_user = view.findViewById(R.id.edt_tk_signinhocvien);
        edt_name = view.findViewById(R.id.edt_name_signinhocvien);
        edt_lich = view.findViewById(R.id.edt_ngaysinh_signinhocvien);
        edt_mk1 = view.findViewById(R.id.edt_mk1_signinhocvien);
        edt_mk2 = view.findViewById(R.id.edt_mk2_signinhocvien);
        btn_tao = view.findViewById(R.id.btn_signin_signinhocvien);
        btn_huy = view.findViewById(R.id.btn_huy_signinhocvien);
        txt_lich = view.findViewById(R.id.txtinput_ngay_signinhocvien);
        txt_mk1 = view.findViewById(R.id.textinput1_signinhocvien);
        txt_mk2 = view.findViewById(R.id.textinput2_signinhocvien);
        spn_gioitinh = view.findViewById(R.id.spn_gioitinh_signhocvien);

    }
    private boolean validate(){
        String email = "\\w+@\\w+.\\w+";
        String sdt = "0\\d{9}";
        Pattern patternEmail = Pattern.compile(email);
        Pattern patternSdt = Pattern.compile(sdt);
        String user = edt_user.getText().toString().trim();
        if (edt_user.getText().toString().trim().isEmpty()||edt_name.getText().toString().trim().isEmpty()||edt_lich.getText().toString().trim().isEmpty()|| edt_mk1.getText().toString().trim().isEmpty()||edt_mk2.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Vui lòng không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            if (patternEmail.matcher(user).find() || patternSdt.matcher(user).find()){
                if (edt_mk1.getText().toString().trim().length()<8){
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu dài hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    if (edt_mk1.getText().toString().trim().equals(edt_mk2.getText().toString().trim())){
                        return true;
                    }else {
                        Toast.makeText(getContext(), "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
            else {
                Toast.makeText(getContext(), "Vui lòng nhập đúng định dạng tài khoản", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    private void showpass(TextInputLayout txt,EditText edt) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(broadcastReceiver);
    }

}