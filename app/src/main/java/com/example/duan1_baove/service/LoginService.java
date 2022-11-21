package com.example.duan1_baove.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import java.util.List;

public class LoginService extends Service {
    String user;
    public static int ACTION_LOGINSUCCESSADMIN = 0;
    public static int ACTION_LOGINSUCCESSNHANVIEN = 1;
    public static int ACTION_LOGINSUCCESSPT =2;
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
        String tk = intent.getStringExtra("user");
        String mk = intent.getStringExtra("pass");
        List<Admin> list = DuAn1DataBase.getInstance(this).adminDAO().checkaccount(tk);
        if (list.size()<=0){
            Toast.makeText(this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
        }else {
            if (list.get(0).getPass().equals(mk)){
                user = list.get(0).getUser();
                String chucvu =  DuAn1DataBase.getInstance(this).chucVuDAO().chechChucVu(String.valueOf(list.get(0).getChucvu_id()));
                if (chucvu.equals("admin")){
                    sendActionToLoginAdminMain(ACTION_LOGINSUCCESSADMIN);
                }else if (chucvu.equals("PT")){
                    sendActionToLoginAdminMain(ACTION_LOGINSUCCESSPT);
                }else {
                    sendActionToLoginAdminMain(ACTION_LOGINSUCCESSNHANVIEN);
                }
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
        Intent intent = new Intent("sendActionToLoginAdminMain");
        intent.putExtra("user",user);
        intent.putExtra("action",action);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
