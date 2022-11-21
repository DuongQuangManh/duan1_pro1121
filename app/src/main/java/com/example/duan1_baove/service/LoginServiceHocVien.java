package com.example.duan1_baove.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;

import java.util.List;

public class LoginServiceHocVien extends Service {
    String tk;
    public static int ACTION_LOGINSUCCESS= 0;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String user = intent.getStringExtra("user");
        String pass =intent.getStringExtra("pass");
        List<KhachHang> list = DuAn1DataBase.getInstance(this).khachHangDAO().checkAcc(user);
        if (list.size()<=0){
            Toast.makeText(this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
        }else {
            if (list.get(0).getPass().equals(pass)){
                tk = list.get(0).getSoDienThoai();
                sendActionToLoginAdminMain(ACTION_LOGINSUCCESS);
                stopSelf();
            }else {
                Toast.makeText(this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    private void sendActionToLoginAdminMain(int action){
        Intent intent = new Intent("sendActionToLoginFragmentHocVien");
        intent.putExtra("user",tk);
        intent.putExtra("action",action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
