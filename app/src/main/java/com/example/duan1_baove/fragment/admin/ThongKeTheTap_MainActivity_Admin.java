package com.example.duan1_baove.fragment.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class ThongKeTheTap_MainActivity_Admin extends AppCompatActivity {
    ImageView img_back;
    BarChart chart;
    EditText edt_year;
    ImageView img_tang,img_giam;
    int year = 2022;
    String[] months = {"","T1","T2","T3","T4","T5","T6","T7","T8","T9","T10","T11","T12"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_the_tap_main_admin);
        initUi();
        setUpView();
        img_back.setOnClickListener(v -> {
            onBackPressed();
        });
        img_tang.setOnClickListener(v -> {
            year++;
            edt_year.setText(year+"");
            setUpView();
        });
        img_giam.setOnClickListener(v -> {
            year--;
            edt_year.setText(year+"");
            setUpView();
        });
    }
    private void initUi() {
        img_back = findViewById(R.id.img_back_thongkethetap);
        chart = findViewById(R.id.barchar_thongkethetap);
        edt_year = findViewById(R.id.edt_year_thongkethetap);
        img_giam = findViewById(R.id.img_giam_thongkethetap);
        img_tang = findViewById(R.id.img_tang_thongkethetap);
    }
    public void setUpView(){
        List<BarEntry> list = new ArrayList<>();
        for (int i=1;i<=12;i++){
            int money = DuAn1DataBase.getInstance(this).theTapDAO().getDoanhThu("%-"+i+"-"+year+"%");
            Log.d("money",money+"");
            BarEntry barEntry = new BarEntry(i,money);
            list.add(barEntry);
        }
        BarDataSet set = new BarDataSet(list,"abc");
        set.setColors(ColorTemplate.COLORFUL_COLORS);
        set.setValueFormatter(new LargeValueFormatter());
        set.setValueTextSize(15);
        chart.setData(new BarData(set));
        chart.animateY(3000);
        chart.setScaleEnabled(false);
        chart.getDescription().setEnabled(false);

        chart.setDrawBarShadow(false);

        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        chart.getAxisRight().setEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(months));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(12);
        xAxis.setDrawLimitLinesBehindData(false);

        YAxis yAxis1 = chart.getAxisLeft();
        yAxis1.setValueFormatter(new LargeValueFormatter());
        yAxis1.setLabelCount(5, false);
        yAxis1.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis1.setAxisMinimum(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.INSTANCE.animateSlideRight(ThongKeTheTap_MainActivity_Admin.this);
    }
}