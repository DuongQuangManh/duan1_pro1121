package com.example.duan1_baove.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_baove.fragment.admin.TabDanhSachNapTien_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.TabNapTien_Fragment_Admin;

public class ViewPagerAdapterNapTienAdmin extends FragmentStateAdapter {
    Fragment fragment1,fragment2;

    public ViewPagerAdapterNapTienAdmin(@NonNull FragmentActivity fragmentActivity,Fragment fragment,Fragment fragment1) {
        super(fragmentActivity);
        this.fragment1 = fragment;
        this.fragment2 = fragment1;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1: return fragment2;
            default:return fragment1;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
