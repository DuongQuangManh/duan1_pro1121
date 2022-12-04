package com.example.duan1_baove.fragment.admin;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DoanhThu_Fragment_Admin extends Fragment {
    View view;
    PieChart chart;
    List<PieEntry>  list;
    String[] type = {"Thẻ tập","Cửa hàng","Dịch vụ"};
    NumberFormat format = new DecimalFormat("###,###,###");
    TextView tv_tongtienthetap,tv_tongtiencuahang,tv_tongtiendichvu,
            tv_tongtienthetap_thang,tv_tongtiencuahang_thang,tv_tongtiendichvu_thang,tv_luongnhanvien_thang,tv_tienbaotri_thang,tv_tongdoanhthu_thang;
    float tongsotiennhap,doanhthucuahang,doanhthudichvu,doanhthuthetap,tongdoanhthu;
    float phantramdoanhthucuahang,phantramdoanhthudichvu,phantramdoanhthuthetap;
    TextView tv_khachhang,tv_nhanvien,tv_thetap,tv_donhang,tv_thietbi,tv_pt;

    BarChart barchart;
    EditText edt_year;
    ImageView img_tang,img_giam;
    int year = 2022;
    String[] months = {"","T1","T2","T3","T4","T5","T6","T7","T8","T9","T10","T11","T12"};
    int month = Calendar.getInstance().get(Calendar.MONTH)+1;
    TextView tv_thang;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_doanh_thu___admin, container, false);
        initUi();
        setUpView();
        setUpCount();
        setUpViewBarChart();
        Log.v("1003",month+"");
        setUpDoanhThuByMonth(month,year);
        tv_thang.setText("Tháng "+month);
        img_tang.setOnClickListener(v -> {
            year++;
            edt_year.setText(year+"");
            setUpViewBarChart();
            setUpDoanhThuByMonth(month,year);
        });
        img_giam.setOnClickListener(v -> {
            year--;
            edt_year.setText(year+"");
            setUpViewBarChart();
            setUpDoanhThuByMonth(month,year);
        });
        return view;
    }

    private void setUpCount() {
        tv_khachhang.setText(format.format(DuAn1DataBase.getInstance(getContext()).khachHangDAO().getCount())+"");
        tv_thietbi.setText(format.format(DuAn1DataBase.getInstance(getContext()).thietBiDAO().getCount())+"");
        tv_donhang.setText(format.format(DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getCount())+"");
        tv_nhanvien.setText(format.format(DuAn1DataBase.getInstance(getContext()).adminDAO().getCount())+"");
        tv_thetap.setText(format.format(DuAn1DataBase.getInstance(getContext()).theTapDAO().getCount())+"");
        tv_pt.setText(format.format(DuAn1DataBase.getInstance(getContext()).adminDAO().getSoluongPT("PT"))+"");
    }

    private void setUpDoanhThuByMonth(int thang,int nam){
        int thetap = DuAn1DataBase.getInstance(getContext()).theTapDAO().getDoanhThu("%-"+thang+"-"+nam+"%");
        int cuahang = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getDoanhThuByMonth("%-"+thang+"-"+nam+"%");
        int dichvu = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getDoanhThuDichVuByMonth("%-"+thang+"-"+nam+"%");
        int luong =DuAn1DataBase.getInstance(getContext()).adminDAO().getTongLuong();
        int baotri= DuAn1DataBase.getInstance(getContext()).soTienBaoTriThietBiDAO().getMoneyByDate("%-"+thang+"-"+nam+"%");
        int tong = thetap +cuahang+dichvu-luong-baotri;
        tv_tongtienthetap_thang.setText("+"+format.format(thetap)+ " vnđ");
        tv_tongtiencuahang_thang.setText("+"+format.format(cuahang)+ " vnđ");
        tv_tongtiendichvu_thang.setText("+"+format.format(dichvu)+ " vnđ");
        tv_luongnhanvien_thang.setText("-"+format.format(luong)+ " vnđ");
        tv_tienbaotri_thang.setText("-"+format.format( baotri)+ " vnđ");
        if (tong <= 0){
            tv_tongdoanhthu_thang.setTextColor(Color.RED);
        }else {
            tv_tongdoanhthu_thang.setTextColor(Color.GREEN);
        }
        tv_tongdoanhthu_thang.setText(format.format(tong)+ " vnđ");
    }

    private void initUi() {
        chart = view.findViewById(R.id.piechart_doanhthu);
        tv_tongtiencuahang = view.findViewById(R.id.tv_tongtiencuahang);
        tv_tongtiendichvu = view.findViewById(R.id.tv_tongtiendichvu);
        tv_tongtienthetap = view.findViewById(R.id.tv_tongtienthetap);
        tv_khachhang = view.findViewById(R.id.tv_soluongkhachhang);
        tv_nhanvien = view.findViewById(R.id.tv_soluongnhanvien);
        tv_thetap = view.findViewById(R.id.tv_soluongthetap);
        tv_donhang = view.findViewById(R.id.tv_soluongdonhang);
        tv_thietbi = view.findViewById(R.id.tv_soluongthietbi);
        tv_pt = view.findViewById(R.id.tv_soluongpt);
        barchart = view.findViewById(R.id.barchar_doanhthu);
        edt_year = view.findViewById(R.id.edt_year_doanhthu);
        img_giam = view.findViewById(R.id.img_giam_doanhthu);
        img_tang = view.findViewById(R.id.img_tang_doanhthu);

        tv_tongtienthetap_thang =view.findViewById(R.id.tv_tongtienthetap_thang);
        tv_tongtiencuahang_thang = view.findViewById(R.id.tv_tongtiencuahang_thang);
        tv_tongtiendichvu_thang = view.findViewById(R.id.tv_tongtiendichvu_thang);
        tv_luongnhanvien_thang = view.findViewById(R.id.tv_luongnhanvien_thang);
        tv_tienbaotri_thang =view.findViewById(R.id.tv_tienbaotri_thang);
        tv_tongdoanhthu_thang = view.findViewById(R.id.tv_tongdoanhthu_thang);
        tv_thang = view.findViewById(R.id.tv_thang_doanhthu);
    }


    public void setUpViewBarChart(){
        List<BarEntry> list = new ArrayList<>();
        for (int i=1;i<=12;i++){
            int donhang = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getDoanhThuByMonth("%-"+i+"-"+year+"%");
            int dichvu = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getDoanhThuDichVuByMonth("%-"+i+"-"+year+"%");
            int thetap = DuAn1DataBase.getInstance(getContext()).theTapDAO().getDoanhThu("%-"+i+"-"+year+"%");
            int luongnhanvien = DuAn1DataBase.getInstance(getContext()).adminDAO().getTongLuong();
            int sotienbaotrithietbi = DuAn1DataBase.getInstance(getContext()).soTienBaoTriThietBiDAO().getMoneyByDate("%-"+i+"-"+year+"%");
            int tongdoanhthu = donhang+dichvu+thetap-luongnhanvien-sotienbaotrithietbi;
            Log.d("doanhthu933",donhang+"Thang "+i);
            Log.d("doanhthu933",dichvu+"Thang "+i);
            Log.d("doanhthu933",thetap+"Thang "+i);
            Log.d("doanhthu933",luongnhanvien+"Thang "+i);
            Log.d("doanhthu933",sotienbaotrithietbi+"Thang "+i);
            Log.d("doanhthu933",tongdoanhthu+"");
            BarEntry barEntry = new BarEntry(i,tongdoanhthu);
            list.add(barEntry);
        }
        BarDataSet set = new BarDataSet(list,"abc");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueFormatter(new LargeValueFormatter());
        set.setValueTextSize(15);
        barchart.setData(new BarData(set));
        barchart.animateY(3000);
        barchart.setScaleEnabled(false);
        barchart.getDescription().setEnabled(false);

        barchart.setDrawBarShadow(false);

        Legend legend = barchart.getLegend();
        legend.setEnabled(false);

        barchart.getAxisRight().setEnabled(false);

        XAxis xAxis = barchart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(12);
        xAxis.setDrawLimitLinesBehindData(false);

        YAxis yAxis1 = barchart.getAxisLeft();
        yAxis1.setValueFormatter(new LargeValueFormatter());
        yAxis1.setLabelCount(5, false);
        yAxis1.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis1.setAxisMinimum(0);

        barchart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                setUpDoanhThuByMonth((int)e.getX(),year);
                tv_thang.setText("Tháng "+(int)e.getX());
                Toast.makeText(getContext(), (int)e.getX()+"", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected() {

            }
        });
    }
    private void setUpView() {
        tongsotiennhap = (DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getTongSoTienNhapMonHang());
        doanhthucuahang = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getTongSoTienKiemDuocTuMonHang()-tongsotiennhap;
        doanhthudichvu = DuAn1DataBase.getInstance(getContext()).donHangChiTietDAO().getTongSoTienKiemDuocTuDichVu();
        doanhthuthetap = DuAn1DataBase.getInstance(getContext()).theTapDAO().getDoanhThuTuTheTap();
        tv_tongtienthetap.setText(format.format(doanhthuthetap)+" vnđ");
        tv_tongtiencuahang.setText(format.format(doanhthucuahang)+" vnđ");
        tv_tongtiendichvu.setText(format.format(doanhthudichvu)+" vnđ");
        Log.d("805","tongsotiennhap "+format.format(tongsotiennhap)+" vnd");
        Log.d("805","doanhthucuahang "+format.format(doanhthucuahang)+" vnd");
        Log.d("805","doanhthudichvu "+format.format(doanhthudichvu)+" vnd");
        Log.d("805","doanhthuthetap "+format.format(doanhthuthetap)+" vnd");


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


}