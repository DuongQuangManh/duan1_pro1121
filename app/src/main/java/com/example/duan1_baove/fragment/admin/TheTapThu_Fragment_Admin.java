package com.example.duan1_baove.fragment.admin;

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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.DangKiTheTapThuAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DangKiTapThu;

import java.util.List;

public class TheTapThu_Fragment_Admin extends Fragment {
    private View view;
    private LinearLayout layout_search;
    private EditText edt_search;
    private RecyclerView recyclerView;
    private List<DangKiTapThu> list;
    private DangKiTheTapThuAdapter adapter;

    int y;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_the_tap_thu___admin, container, false);
        initUi();
        capnhat();
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
                return true;
            }
        });
        return view;
    }

    private void search(String search) {
        adapter.getFilter().filter(search);
    }

    private void capnhat() {
        list = DuAn1DataBase.getInstance(getContext()).dangKiTapThuDAO().getAll();
        adapter = new DangKiTheTapThuAdapter(getContext());
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void initUi() {
        layout_search = view.findViewById(R.id.layout_search_thetapthu);
        edt_search = view.findViewById(R.id.edt_search_thetapthu);
        recyclerView = view.findViewById(R.id.rcy_thetapthu);

    }
}