package com.example.duan1_baove.activityload;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.login.Screen_MainActivity;
import com.example.duan1_baove.model.Admin;
import com.example.duan1_baove.model.ChucVu;

public class Splash_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_main);
        ChucVu chucVu =new ChucVu();
        chucVu.setTenchucvu("admin");
        Admin admin = new Admin();
        admin.setUser("admin");
        admin.setName("Dương Quang Mạnh");
        admin.setPass("admin");
        admin.setLuong(0);
        admin.setStk("2915022003");
        admin.setTennganhang("Mb bank");
        admin.setChucvu_id(1);
        if (DuAn1DataBase.getInstance(this).chucVuDAO().getAll().isEmpty()&&DuAn1DataBase.getInstance(this).adminDAO().getAll().isEmpty()){
            DuAn1DataBase.getInstance(this).chucVuDAO().insert(chucVu);
            DuAn1DataBase.getInstance(this).adminDAO().insert(admin);
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_MainActivity.this, Screen_MainActivity.class);
                startActivity(intent);
                Animatoo.INSTANCE.animateZoom(Splash_MainActivity.this);
            }
        },2000);

    }

    @Override
    public void onBackPressed() {

    }
}