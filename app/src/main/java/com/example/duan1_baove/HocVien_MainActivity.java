package com.example.duan1_baove;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.duan1_baove.adapter.AdapterBotTonNav_HocVien;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DonHangChiTiet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.List;

public class HocVien_MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    ViewPager2 viewPager;
    public static String userHocVien;
    Calendar now = Calendar.getInstance();
    Calendar enddichvu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoc_vien_main);
        initUi();
        setUpViewPager();
        userHocVien = getIntent().getStringExtra("user");
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bottonnav_trangchu:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.bottonnav_cuahang:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.bottonnav_canhan:
                    viewPager.setCurrentItem(2);
                    break;
            }
            return true;
        });
        List<DonHangChiTiet> list = DuAn1DataBase.getInstance(this).donHangChiTietDAO().getDichVu(HocVien_MainActivity.userHocVien);
        for (int i=0;i<list.size();i++){
            DonHangChiTiet donHangChiTiet = list.get(i);
            enddichvu = Calendar.getInstance();
            enddichvu.set(Calendar.DAY_OF_MONTH,getArrayDate(donHangChiTiet.getEndtime())[0]);
            enddichvu.set(Calendar.MONTH,getArrayDate(donHangChiTiet.getEndtime())[1]);
            enddichvu.set(Calendar.YEAR,getArrayDate(donHangChiTiet.getEndtime())[2]);

            if (now.after(enddichvu)){
                donHangChiTiet.setTinhTrang("Hết hạn");
                DuAn1DataBase.getInstance(this).donHangChiTietDAO().update(donHangChiTiet);
            }
        }
    }

    private void initUi(){
        bottomNavigationView = findViewById(R.id.botton_nav_hocvien);
        viewPager = findViewById(R.id.viewpager_hocvien);
    }
    private void setUpViewPager(){
        AdapterBotTonNav_HocVien adapter = new AdapterBotTonNav_HocVien(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
    }

    @Override
    public void onBackPressed() {

    }
    public int[] getArrayDate(String date){
        String[] str = date.split("-");
        int arr[] = new int[str.length];
        try{
            for(int i = 0;i<str.length;i++){
                arr[i] = Integer.parseInt(str[i]);
            }
        }catch (NumberFormatException e){
            return null;
        }
        return arr;
    }
}