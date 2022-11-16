package com.example.duan1_baove.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;

import java.util.List;

public class SignUpServiceHocVien extends Service {
    public static int ACTION_SUCCESS = 0;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            KhachHang khachHang = (KhachHang) bundle.get("object");
            List<KhachHang> check = DuAn1DataBase.getInstance(this).khachHangDAO().checkAcc(khachHang.getSoDienThoai());
            if (check.size()>0){
                Toast.makeText(this, "Tài khoản đã tồn tại", Toast.LENGTH_SHORT).show();
            }else {
                DuAn1DataBase.getInstance(this).khachHangDAO().insert(khachHang);
                sendActionToCreateHocVien(ACTION_SUCCESS);
                stopSelf();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void sendActionToCreateHocVien(int action){
        Intent intent = new Intent("sendActionToCreateHocVien");
        intent.putExtra("action",action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
