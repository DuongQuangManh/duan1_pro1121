package com.example.duan1_baove;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.duan1_baove.activityload.Splash_MainActivity;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.fragment.admin.ChucVu_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.CuaHang_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.DoanhThu_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.DoiMatKhau_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.KhachHang_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.LoaiTheTap_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.NhanVien_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.TaiKhoan_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.TheTap_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.ThietBi_Fragment_Admin;
import com.example.duan1_baove.fragment.admin.ThongBao_Fragment_Admin;
import com.example.duan1_baove.service.LoginService;
import com.google.android.material.navigation.NavigationView;

import de.hdodenhof.circleimageview.CircleImageView;

public class Admin_MainActivity extends AppCompatActivity {

    private static final int FRAGMENT_THONGBAO = 0;
    private static final int FRAGMENT_KHACHHANG= 1;
    private static final int FRAGMENT_THETAP = 2;
    private static final int FRAGMENT_CUAHANG = 3;
    private static final int FRAGMENT_THIETBI = 4;
    private static final int FRAGMENT_LOAITHETAP = 6;
    private static final int FRAGMENT_CHUCVU = 7;
    private static final int FRAGMENT_NHANVIEN= 8;
    private static final int FRAGMENT_DOANHTHU = 9;
    private static final int FRAGMENT_TAIKHOAN= 10;
    private static final int FRAGMENT_DOIMATKHAU = 11;
    private int CurrentFragment = FRAGMENT_THONGBAO;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    FrameLayout frameLayout;
    Toolbar toolbar;
    View layout_header;
    CircleImageView avt;
    TextView tv_user_header;



    public static String user;
    int action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();

        action = getIntent().getIntExtra("action",-1);
        user = getIntent().getStringExtra("user");

        tv_user_header.setText("Hi, "+ DuAn1DataBase.getInstance(this).adminDAO().getName(user));
        if (DuAn1DataBase.getInstance(this).adminDAO().checkaccount(user).get(0).getHinhanh()==null){
            avt.setImageResource(R.drawable.ic_account);
        }else {
            avt.setImageURI(Uri.parse(DuAn1DataBase.getInstance(this).adminDAO().checkaccount(user).get(0).getHinhanh()));
        }

        if (action == LoginService.ACTION_LOGINSUCCESSNHANVIEN){
            navigationView.getMenu().findItem(R.id.nav_doanhthu).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_quanlychucvu).setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_quanlyadmin).setVisible(false);
        }

        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.open();
            }
        });
        replaceFragment(new ThongBao_Fragment_Admin());
        navigationView.getMenu().findItem(R.id.nav_quanlythongbao).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_quanlythongbao){
                    if (CurrentFragment != FRAGMENT_THONGBAO){
                        replaceFragment(new ThongBao_Fragment_Admin());
                        CurrentFragment = FRAGMENT_THONGBAO;
                    }
                }else if (id == R.id.nav_quanlykhachhang){
                    if (CurrentFragment != FRAGMENT_KHACHHANG){
                        replaceFragment(new KhachHang_Fragment_Admin());
                        CurrentFragment = FRAGMENT_KHACHHANG;
                    }
                }else if (id == R.id.nav_quanlythetap){
                    if (CurrentFragment != FRAGMENT_THETAP){
                        replaceFragment(new TheTap_Fragment_Admin());
                        CurrentFragment = FRAGMENT_THETAP;
                    }
                }else if (id == R.id.nav_quanlycuahang){
                    if (CurrentFragment != FRAGMENT_CUAHANG){
                        replaceFragment(new CuaHang_Fragment_Admin());
                        CurrentFragment = FRAGMENT_CUAHANG;
                    }
                }else if (id == R.id.nav_quanlythietbi){
                    if (CurrentFragment != FRAGMENT_THIETBI){
                        replaceFragment(new ThietBi_Fragment_Admin());
                        CurrentFragment = FRAGMENT_THIETBI;
                    }
                }else if (id == R.id.nav_quanlyloaithetap){
                    if (CurrentFragment != FRAGMENT_LOAITHETAP){
                        replaceFragment(new LoaiTheTap_Fragment_Admin());
                        CurrentFragment = FRAGMENT_LOAITHETAP;
                    }
                }else if (id == R.id.nav_quanlychucvu){
                    if (CurrentFragment != FRAGMENT_CHUCVU){
                        replaceFragment(new ChucVu_Fragment_Admin());
                        CurrentFragment = FRAGMENT_CHUCVU;
                    }
                }else if (id == R.id.nav_quanlyadmin){
                    if (CurrentFragment != FRAGMENT_NHANVIEN){
                        replaceFragment(new NhanVien_Fragment_Admin());
                        CurrentFragment = FRAGMENT_NHANVIEN;
                    }
                }else if (id == R.id.nav_doanhthu){
                    if (CurrentFragment != FRAGMENT_DOANHTHU){
                        replaceFragment(new DoanhThu_Fragment_Admin());
                        CurrentFragment = FRAGMENT_DOANHTHU;
                    }
                }else if (id == R.id.nav_quanlytaikhoan){
                    if (CurrentFragment != FRAGMENT_TAIKHOAN){
                        replaceFragment(new TaiKhoan_Fragment_Admin());
                        CurrentFragment = FRAGMENT_TAIKHOAN;
                    }
                }else if (id == R.id.nav_doimatkhau){
                    if (CurrentFragment != FRAGMENT_DOIMATKHAU){
                        replaceFragment(new DoiMatKhau_Fragment_Admin());
                        CurrentFragment = FRAGMENT_DOIMATKHAU;
                    }
                }else if (id == R.id.nav_dangxuat){
                    new AlertDialog.Builder(Admin_MainActivity.this).setTitle("Đăng xuất").setMessage("Bạn có chắc chắn muốn đăng xuất?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(getApplicationContext(), Splash_MainActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No",null).show();
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void initUi() {
        navigationView = findViewById(R.id.navigation_view_admin);
        drawerLayout = findViewById(R.id.drawerlayout_admin);
        frameLayout = findViewById(R.id.content_layout);
        toolbar = findViewById(R.id.toolbar_admin);
        layout_header = navigationView.getHeaderView(0);
        avt = layout_header.findViewById(R.id.circle_imageview_headeradmin);
        tv_user_header = layout_header.findViewById(R.id.tv_name_header);

    }
    private void replaceFragment(Fragment fragment){
        Fragment fragment1 = getSupportFragmentManager().findFragmentByTag(fragment.getClass().getSimpleName());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment1 != null) {
            transaction.replace(R.id.content_layout,fragment1);
        }else{
            transaction.replace(R.id.content_layout, fragment, fragment.getClass().getSimpleName()).addToBackStack(fragment.getClass().getSimpleName());
        }

        transaction.commit();
    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }

}