package com.example.duan1_baove.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_baove.fragment.admin.TabDanhSachNapTien_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.TabNapTien_Fragment_Admin;

public class ViewPagerAdapterNapTienAdmin extends FragmentStateAdapter {

    public ViewPagerAdapterNapTienAdmin(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1: return  new TabDanhSachNapTien_Fragment_Admin();
            default:return new TabNapTien_Fragment_Admin();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
