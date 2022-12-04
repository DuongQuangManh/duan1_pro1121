package com.example.duan1_baove.fragment.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.SpinnerAdapterNapTien;
import com.example.duan1_baove.adapter.ViewPagerAdapterNapTienAdmin;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.LichSuGiaoDich;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NapTien_Fragment_Admin extends Fragment {
    View view;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ViewPagerAdapterNapTienAdmin adapter;
    String[] tab = {"Nạp tiền","DS nạp tiền"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=  inflater.inflate(R.layout.fragment_nap_tien___admin, container, false);
        initUi();
        adapter = new ViewPagerAdapterNapTienAdmin(getActivity());
        viewPager2.setAdapter(adapter);
        tabLayout.setSelectedTabIndicator(R.drawable.bg_green);
        new TabLayoutMediator(tabLayout,viewPager2,(tab1, position) -> tab1.setText(tab[position])).attach();
        viewPager2.setUserInputEnabled(false);
        return view;
    }

    private void initUi() {
        tabLayout = view.findViewById(R.id.tablayout_naptienadmin);
        viewPager2 = view.findViewById(R.id.viewpager_naptienadmin);
    }


}