package com.example.duan1_baove.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.CustomAdapterViTri;
import com.example.duan1_baove.model.ViTri;

import java.util.ArrayList;
import java.util.List;

public class Screen_MainActivity extends AppCompatActivity {
    GridView gridView;
    List<ViTri> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_main);
        gridView = findViewById(R.id.gridview);
        list.add(new ViTri(R.raw.admin,"Admin"));
        list.add(new ViTri(R.raw.hocvien,"Học viên"));
        CustomAdapterViTri adapterViTri = new CustomAdapterViTri(this,R.layout.item_gridview,list);
        gridView.setAdapter(adapterViTri);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    Intent intent = new Intent(Screen_MainActivity.this, LoginAdmin_MainActivity.class);
                    startActivity(intent);
                    Animatoo.INSTANCE.animateSwipeLeft(Screen_MainActivity.this);
                }else  if (position == 1){
                    Intent intent = new Intent(Screen_MainActivity.this, LoginHocVien_MainActivity.class);
                    startActivity(intent);
                    Animatoo.INSTANCE.animateSwipeLeft(Screen_MainActivity.this);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}