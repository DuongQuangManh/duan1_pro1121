package com.example.duan1_baove.fragment.admin;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class DoanhThu_Fragment_Admin extends Fragment {
    View view;
    PieChart chart;
    List<PieEntry>  list;
    String[] type = {"Thẻ tập","Cửa hàng","Dịch vụ"};
    NumberFormat format = new DecimalFormat("###,###,###");
    TextView tv_tongtienthetap,tv_tongtiencuahang,tv_tongtiendichvu;
    float tongsotiennhap,doanhthucuahang,doanhthudichvu,doanhthuthetap,tongdoanhthu;
    float phantramdoanhthucuahang,phantramdoanhthudichvu,phantramdoanhthuthetap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_doanh_thu___admin, container, false);
        initUi();
        setUpView();
        return view;
    }

    private void setUpView() {
        tongsotiennhap = (DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getTongSoTienNhapMonHang());
        doanhthucuahang = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getTongSoTienKiemDuocTuMonHang()-tongsotiennhap;
        doanhthudichvu = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getTongSoTienKiemDuocTuDichVu();
        doanhthuthetap = DuAn1DataBase.getInstance(getContext()).theTapDAO().getDoanhThuTuTheTap();
        tv_tongtienthetap.setText(format.format(doanhthuthetap)+" vnđ");
        tv_tongtiencuahang.setText(format.format(doanhthucuahang)+" vnđ");
        tv_tongtiendichvu.setText(format.format(doanhthudichvu)+" vnđ");

        tongdoanhthu = doanhthudichvu+doanhthuthetap+doanhthucuahang;
        if (tongdoanhthu==0){
           return;
        }
        phantramdoanhthucuahang = (doanhthucuahang/tongdoanhthu)*100;
        phantramdoanhthudichvu = (doanhthudichvu/tongdoanhthu)*100;
        phantramdoanhthuthetap = (doanhthuthetap/tongdoanhthu)*100;
        list = new ArrayList<>();
        PieEntry pieEntry = new PieEntry(phantramdoanhthuthetap,type[0]);
        list.add(pieEntry);
        PieEntry pieEntry1 = new PieEntry(phantramdoanhthucuahang,type[1]);
        list.add(pieEntry1);
        PieEntry pieEntry2 = new PieEntry(phantramdoanhthudichvu,type[2]);
        list.add(pieEntry2);

        PieDataSet pieDataSet = new PieDataSet(list,"");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(12);

        chart.setData(new PieData(pieDataSet));
        chart.animateXY(3000,3000);
        chart.getDescription().setEnabled(true);
        chart.setDrawEntryLabels(false);
        chart.getDescription().setText("%");
        chart.getDescription().setTextSize(19);

        Legend legend = chart.getLegend();
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);


        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String label = ((PieEntry)e).getLabel();
                if (label.equals("Thẻ tập")){
                    Intent intent = new Intent(getContext(),ThongKeTheTap_MainActivity_Admin.class);
                    getActivity().startActivity(intent);
                    Animatoo.INSTANCE.animateSwipeLeft(getContext());
                }else if (label.equals("Cửa hàng")){
                    Intent intent = new Intent(getContext(),ThongKeCuaHang_MainActivity_Admin.class);
                    getActivity().startActivity(intent);
                    Animatoo.INSTANCE.animateSwipeLeft(getContext());
                }else if (label.equals("Dịch vụ")){
                    Intent intent = new Intent(getContext(),ThongKeDichVu_MainActivity_Admin.class);
                    getActivity().startActivity(intent);
                    Animatoo.INSTANCE.animateSwipeLeft(getContext());
                }
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }

    private void initUi() {
        chart = view.findViewById(R.id.piechart_doanhthu);
        tv_tongtiencuahang = view.findViewById(R.id.tv_tongtiencuahang);
        tv_tongtiendichvu = view.findViewById(R.id.tv_tongtiendichvu);
        tv_tongtienthetap = view.findViewById(R.id.tv_tongtienthetap);
    }
}