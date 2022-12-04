package com.example.duan1_baove.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_baove.fragment.hocvien.DanhSachYeuCauNapTien_Fragment_HocVien;
import com.example.duan1_baove.fragment.hocvien.NapTien_Fragment_HocVien;

public class ViewPagerAdapterNapTien extends FragmentStateAdapter {

    public ViewPagerAdapterNapTien(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1: return new DanhSachYeuCauNapTien_Fragment_HocVien();
            default: return new NapTien_Fragment_HocVien();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
