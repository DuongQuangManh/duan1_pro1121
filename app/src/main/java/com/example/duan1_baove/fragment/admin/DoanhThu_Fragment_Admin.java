package com.example.duan1_baove.fragment.admin;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.duan1_baove.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class DoanhThu_Fragment_Admin extends Fragment {
    View view;
    PieChart chart;
    List<PieEntry>  list = new ArrayList<>();
    String[] type = {"Thẻ tập","Cửa hàng","Dịch vụ"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_doanh_thu___admin, container, false);
        initUi();

        setUpView();
        return view;
    }

    private void setUpView() {
        for (int i=1;i<=3;i++){
            PieEntry pieEntry = new PieEntry(i,i+100);
            list.add(pieEntry);
        }
        PieDataSet pieDataSet = new PieDataSet(list,"Doanh Thu");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(new PieData(pieDataSet));
        chart.animateXY(3000,3000);
        chart.getDescription().setEnabled(true);
    }

    private void initUi() {
        chart = view.findViewById(R.id.piechart_doanhthu);
    }
}