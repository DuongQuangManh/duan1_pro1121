package com.example.duan1_baove.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.duan1_baove.R;
import com.example.duan1_baove.activityload.LoadAdmin_MainActivity;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import com.example.duan1_baove.model.ChucVu;
import com.example.duan1_baove.service.LoginService;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;
import java.util.Locale;

public class LoginAdmin_MainActivity extends AppCompatActivity {
    private EditText edt_sdt,edt_mk;
    private TextInputLayout txt_layout;
    private Button btn_dangnhap;
    private CheckBox checkBox;
    private int action;
    private String user;
    private Intent intent;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("sendActionToLoginAdminMain")){
                 action = intent.getIntExtra("action",-1);
                 user = intent.getStringExtra("user");
                 if (action== LoginService.ACTION_LOGINSUCCESSADMIN || action == LoginService.ACTION_LOGINSUCCESSNHANVIEN){
                     Intent intent1 = new Intent(LoginAdmin_MainActivity.this, LoadAdmin_MainActivity.class);
                     intent1.putExtra("action",action);
                     intent1.putExtra("user",user);
                     startActivity(intent1);
                     List<Admin> adminList = DuAn1DataBase.getInstance(getApplicationContext()).adminDAO().checkaccount(user);
                     rememberUser(adminList.get(0).getUser(),adminList.get(0).getPass(),checkBox.isChecked());
                 }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin_main);
        initUi();
        edt_sdt.addTextChangedListener(textWatcher);
        edt_mk.addTextChangedListener(textWatcher);

        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,new IntentFilter("sendActionToLoginAdminMain"));

        SharedPreferences preferences = getSharedPreferences("USER_ADMIN", Context.MODE_PRIVATE);
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
            intent = new Intent(LoginAdmin_MainActivity.this, LoginService.class);
            intent.putExtra("user",edt_sdt.getText().toString().trim());
            intent.putExtra("pass",edt_mk.getText().toString().trim());
            startService(intent);
        });
    }



    private void initUi(){
        edt_sdt = findViewById(R.id.edt_sdt_loginadmin);
        edt_mk = findViewById(R.id.edt_mk_loginadmin);
        txt_layout = findViewById(R.id.textinput_loginadmin);
        btn_dangnhap = findViewById(R.id.btn_dangnhap_loginadmin);
        checkBox = findViewById(R.id.cbo_loginadmin);
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String sdt = edt_sdt.getText().toString().trim();
            String mk = edt_mk.getText().toString().trim();
            if (!sdt.isEmpty() && ! mk.isEmpty() && sdt.length() >= 1 && mk.length() >= 8 || mk.equals("admin")){
                btn_dangnhap.setEnabled(true);
                btn_dangnhap.setBackground(getDrawable(R.drawable.bg_green));
            }else {
                btn_dangnhap.setEnabled(true);
                btn_dangnhap.setBackground(getDrawable(R.drawable.bg_gray));
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    public void rememberUser(String u,String p,boolean status){
        SharedPreferences pref = getSharedPreferences("USER_ADMIN", Context.MODE_PRIVATE);
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
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

}