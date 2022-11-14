package com.example.duan1_baove.fragment.admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.DonHangAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DonHangChiTiet;

import java.util.ArrayList;
import java.util.List;

public class DuyetDonHang_Fragment_Admin extends Fragment {
    View view;
    private RecyclerView recyclerView;
    private List<DonHangChiTiet> list;
    private Spinner spinner;
    private DonHangAdapter adapter;
    private LinearLayout layout_search;
    private EditText edt_search;
    String[] trangthai = {"Tất cả","Chưa kiểm duyệt","Đã kiểm duyệt"};
    String strTrangthai = "Tất cả";
    int y;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_duyet_don_hang___admin, container, false);
        initUi();
        ArrayAdapter adapterspn = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item,trangthai);
        spinner.setAdapter(adapterspn);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strTrangthai = trangthai[position];
                if (strTrangthai.equals("Tất cả")){
                    capnhat();
                }else{
                    getByTrangThai(strTrangthai);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(y<=0){
                    layout_search.setVisibility(View.VISIBLE);
                }
                else{
                    y=0;
                    layout_search.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                y=dy;
            }
        });
        edt_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    search(edt_search.getText().toString().trim());
                }
                return false;
            }
        });
        return view;
    }

    private void search(String search) {
        adapter.getFilter().filter(search);
    }

    public void capnhat(){
        list = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getAllAdmin();
        adapter = new DonHangAdapter(getContext(), new DonHangAdapter.IClickListener() {
            @Override
            public void duyet(DonHangChiTiet donHangChiTiet) {
                duyetDon(donHangChiTiet);
            }
        });
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public void getByTrangThai(String trangthai){
        list = new ArrayList<>();
        list = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getByTrangThai(trangthai);
        adapter = new DonHangAdapter(getContext(), new DonHangAdapter.IClickListener() {
            @Override
            public void duyet(DonHangChiTiet donHangChiTiet) {
                duyetDonBy(donHangChiTiet);
            }
        });
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void duyetDon(DonHangChiTiet donHangChiTiet){
        new AlertDialog.Builder(getContext()).setTitle("Kiểm duyệt")
                .setMessage(" Bạn muốn kiểm duyệt bấm yes !\n Bạn muốn huỷ kiểm duyệt bấm no !")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        donHangChiTiet.setTinhTrang("Đã kiểm duyệt");
                        DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().update(donHangChiTiet);
                        capnhat();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        donHangChiTiet.setTinhTrang("Chưa kiểm duyệt");
                        DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().update(donHangChiTiet);
                        capnhat();
                    }
                })
                .show();
    }
    private void duyetDonBy(DonHangChiTiet donHangChiTiet){
        new AlertDialog.Builder(getContext()).setTitle("Kiểm duyệt")
                .setMessage(" Bạn muốn kiểm duyệt bấm yes !\n Bạn muốn huỷ kiểm duyệt bấm no !")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        donHangChiTiet.setTinhTrang("Đã kiểm duyệt");
                        DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().update(donHangChiTiet);
                        getByTrangThai(strTrangthai);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        donHangChiTiet.setTinhTrang("Chưa kiểm duyệt");
                        DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().update(donHangChiTiet);
                        getByTrangThai(strTrangthai);
                    }
                })
                .show();
    }

    private void initUi() {
        recyclerView = view.findViewById(R.id.rcy_duyetdonhang);
        spinner = view.findViewById(R.id.spn_trangthai_duyetdonhang);
        layout_search = view.findViewById(R.id.layout_search_duyetdonhang);
        edt_search = view.findViewById(R.id.edt_search_duyetdonhang);
    }
}