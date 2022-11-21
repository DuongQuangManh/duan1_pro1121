package com.example.duan1_baove.fragment.hocvien;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.R;
import com.example.duan1_baove.activityload.LoadHocVien_MainActivity;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.service.LoginServiceHocVien;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class Login_Fragment_HocVien extends Fragment {
    private View view;
    private EditText edt_sdt,edt_mk;
    private TextInputLayout txt_layout;
    private Button btn_dangnhap;
    private CheckBox checkBox;
    private Intent intent;

    private int action;
    private String user;
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("sendActionToLoginFragmentHocVien")){
                action = intent.getIntExtra("action",-1);
                user = intent.getStringExtra("user");
                if (action == LoginServiceHocVien.ACTION_LOGINSUCCESS){
                    Intent intent1 = new Intent(getActivity(), LoadHocVien_MainActivity.class);
                    intent1.putExtra("user",user);
                    startActivity(intent1);
                    Animatoo.INSTANCE.animateFade(getContext());
                    List<KhachHang> hangList = DuAn1DataBase.getInstance(getActivity()).khachHangDAO().checkAcc(user);
                    rememberUser(hangList.get(0).getSoDienThoai(),hangList.get(0).getPass(),checkBox.isChecked());
                }
            }
        }
    };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login___hoc_vien, container, false);
        initUi();

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(broadcastReceiver,new IntentFilter("sendActionToLoginFragmentHocVien"));

        edt_sdt.addTextChangedListener(textWatcher);
        edt_mk.addTextChangedListener(textWatcher);
        SharedPreferences preferences = getActivity().getSharedPreferences("USER_HOCVIEN", Context.MODE_PRIVATE);
        String user = preferences.getString("USERNAME","");
        String pass =preferences.getString("PASSWORD","");
        Boolean rem = preferences.getBoolean("REMEMBER",false);

        edt_sdt.setText(user);
        edt_mk.setText(pass);
        checkBox.setChecked(rem);

        txt_layout.setEndIconOnClickListener(v -> {
            showpass();
        });
        btn_dangnhap.setOnClickListener(v -> {
            intent = new Intent(getActivity(), LoginServiceHocVien.class);
            intent.putExtra("user",edt_sdt.getText().toString().trim());
            intent.putExtra("pass",edt_mk.getText().toString().trim());
            getActivity().startService(intent);

        });
        return view;
    }

    private void initUi() {
        edt_sdt = view.findViewById(R.id.edt_sdt_loginhocvien);
        edt_mk = view.findViewById(R.id.edt_mk_loginhocvien);
        txt_layout = view.findViewById(R.id.textinput_loginhocvien);
        btn_dangnhap = view.findViewById(R.id.btn_dangnhap_loginhocvien);
        checkBox = view.findViewById(R.id.cbo_loginhocvien);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String sdt = edt_sdt.getText().toString().trim();
            String mk = edt_mk.getText().toString().trim();
            if (!sdt.isEmpty() && ! mk.isEmpty() && sdt.length() == 10 && mk.length() >= 8){
                btn_dangnhap.setEnabled(true);
                btn_dangnhap.setBackground(getActivity().getDrawable(R.drawable.bg_green));
            }else {
                btn_dangnhap.setEnabled(true);
                btn_dangnhap.setBackground(getActivity().getDrawable(R.drawable.bg_gray));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void rememberUser(String u,String p,boolean status){
        SharedPreferences pref = getActivity().getSharedPreferences("USER_HOCVIEN", Context.MODE_PRIVATE);
        SharedPreferences.Editor edt = pref.edit();
        if (!status){
            edt.clear();
        }else {
            edt.putString("USERNAME",u);
            edt.putString("PASSWORD",p);
            edt.putBoolean("REMEMBER",status);
        }
        edt.commit();
    }

    private void showpass() {
        if(edt_mk.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            edt_mk.setInputType( InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txt_layout.setEndIconDrawable(R.drawable.ic_eye_off);
        }else {
            edt_mk.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
            txt_layout.setEndIconDrawable(R.drawable.ic_eye);
        }
        edt_mk.setSelection(edt_mk.getText().length());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(broadcastReceiver);
    }
}