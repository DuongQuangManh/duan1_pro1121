package com.example.duan1_baove.activitykhachhang;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.duan1_baove.HocVien_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;

import java.util.List;

public class ChangePassKhachHangActivity extends AppCompatActivity {
    EditText edt_passOldKH,edt_passNewKH,edt_rePassNewKH;
    List<KhachHang> listKH;
    Button btnSaveUserChange,btnCancelUserChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass_khach_hang);
        initUi();
        listKH = DuAn1DataBase.getInstance(getApplicationContext()).khachHangDAO().checkAcc(HocVien_MainActivity.userHocVien);

        btnSaveUserChange.setOnClickListener(v -> {
            changPass();
        });
        btnCancelUserChange.setOnClickListener(v -> {
            onBackPressed();
        });
    }
    private void initUi(){
        edt_passOldKH = findViewById(R.id.edt_PassOldKH);
        edt_passNewKH = findViewById(R.id.edt_PassNewKH);
        edt_rePassNewKH = findViewById(R.id.edt_RePassNewKH);
        btnSaveUserChange = findViewById(R.id.btnSaveUserChange);
        btnCancelUserChange = findViewById(R.id.btnCancelUserChange);
    }
    private void changPass(){
        if(validate()==true){
            KhachHang khachHang = listKH.get(0);
            khachHang.setPass(edt_passNewKH.getText().toString());
            DuAn1DataBase.getInstance(getApplicationContext()).khachHangDAO().update(khachHang);
            Toast.makeText(this, "Đổi mật khẩu thành công !", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
    }
    private boolean validate(){
        KhachHang khachHang = listKH.get(0);
        String passNew = edt_passNewKH.getText().toString().trim();
        String repassNew = edt_rePassNewKH.getText().toString().trim();
        String passOld = edt_passOldKH.getText().toString().trim();
        if(passOld.length()==0||passNew.length()==0||repassNew.length()==0){
            Toast.makeText(this, "Bạn phải nhập đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            if(!passOld.equals(khachHang.getPass())){
                Toast.makeText(this, "Mật khẩu cũ không đúng", Toast.LENGTH_SHORT).show();
                return false;
            }else{
                if (passNew.length()<8){
                    Toast.makeText(this, "Mật khẩu mới phải lớn hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                    return false;
                }else{
                    if(!repassNew.equals(passNew)){
                        Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                        return false;
                    }else{
                        Toast.makeText(this, "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        return true;
                    }
                }
            }
        }
    }
}