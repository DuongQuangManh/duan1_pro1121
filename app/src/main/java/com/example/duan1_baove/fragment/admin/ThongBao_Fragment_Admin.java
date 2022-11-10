package com.example.duan1_baove.fragment.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.duan1_baove.Admin_MainActivity;
import com.example.duan1_baove.R;
import com.example.duan1_baove.adapter.ChucVuAdapter;
import com.example.duan1_baove.adapter.ThongBaoAdapter;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import com.example.duan1_baove.model.ThongBao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ThongBao_Fragment_Admin extends Fragment {
    private List<ThongBao> list;
    private View view;
    private CircleImageView avt_thongbao;
    private EditText edt_themthongbao;
    private RecyclerView recyclerView;

    private EditText edt_id,edt_title,edt_content;
    private Button btn_add,btn_huy;
    private ThongBao thongBao;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy,HH:mm:ss", Locale.getDefault());
    String currentDateandTime = sdf.format(new Date());

    private ThongBaoAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thong_bao___admin, container, false);
        initUi();
        capNhat();
        edt_themthongbao.setOnClickListener(v -> {
            add();
        });
        return view;
    }

    private void add() {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_addthongbao);
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null){
            return;
        }
        window.setBackgroundDrawable(null);
        edt_id = dialog.findViewById(R.id.edt_mathongbao_dialogthongbao);
        edt_title = dialog.findViewById(R.id.edt_tieude_dialogthongbao);
        edt_content = dialog.findViewById(R.id.edt_noidung_dialogthongbao);
        btn_add = dialog.findViewById(R.id.btn_luu_dialogthongbao);
        btn_huy = dialog.findViewById(R.id.btn_huy_dialogthongbao);

        btn_huy.setOnClickListener(v -> {
            dialog.cancel();
        });
        btn_add.setOnClickListener(v -> {
            thongBao = new ThongBao();
            if (validate()){
                thongBao.setTieude(edt_title.getText().toString().trim());
                thongBao.setNoidung(edt_content.getText().toString().trim());
                thongBao.setUser_id(Admin_MainActivity.user);
                thongBao.setThoigian(currentDateandTime);
                DuAn1DataBase.getInstance(getContext()).thongBaoDAO().insert(thongBao);
                Toast.makeText(getContext(), "Insert thông báo thành công ", Toast.LENGTH_SHORT).show();
                capNhat();
                dialog.dismiss();
                hideSoftKeyBroad();
            }
        });

    }

    private void initUi() {
        avt_thongbao = view.findViewById(R.id.avt_thongbao);
        edt_themthongbao = view.findViewById(R.id.edt_themthongbao);
        recyclerView = view.findViewById(R.id.rcy_thongbao);
    }
    private boolean validate(){
        if (edt_title.getText().toString().trim().isEmpty() || edt_content.getText().toString().trim().isEmpty()){
            Toast.makeText(getContext(), "Vui lòng không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }
    public void capNhat(){
        list = DuAn1DataBase.getInstance(getContext()).thongBaoDAO().getAll();
        adapter = new ThongBaoAdapter(getContext(), new ThongBaoAdapter.MyOnclick() {
            @Override
            public void update(ThongBao thongBao) {
                Dialog dialog = new Dialog(getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_addthongbao);
                dialog.show();
                Window window = dialog.getWindow();
                if (window == null){
                    return;
                }
                window.setBackgroundDrawable(null);
                edt_id = dialog.findViewById(R.id.edt_mathongbao_dialogthongbao);
                edt_title = dialog.findViewById(R.id.edt_tieude_dialogthongbao);
                edt_content = dialog.findViewById(R.id.edt_noidung_dialogthongbao);
                btn_add = dialog.findViewById(R.id.btn_luu_dialogthongbao);
                btn_huy = dialog.findViewById(R.id.btn_huy_dialogthongbao);

                edt_id.setText(thongBao.getId()+"");
                edt_title.setText(thongBao.getTieude());
                edt_content.setText(thongBao.getNoidung());

                btn_huy.setOnClickListener(v1 -> {
                    dialog.cancel();
                });
                btn_add.setOnClickListener(v1 -> {
                    if (validate()){
                        thongBao.setTieude(edt_title.getText().toString().trim());
                        thongBao.setNoidung(edt_content.getText().toString().trim());
                        thongBao.setUser_id(Admin_MainActivity.user);
                        thongBao.setThoigian(currentDateandTime);
                        DuAn1DataBase.getInstance(getContext()).thongBaoDAO().update(thongBao);
                        Toast.makeText(getContext(), "Update thông báo thành công ", Toast.LENGTH_SHORT).show();
                        capNhat();
                        dialog.dismiss();
                        hideSoftKeyBroad();
                    }
                });
            }

            @Override
            public void delete(ThongBao thongBao) {
                new AlertDialog.Builder(getContext()).setTitle("Xoá thông báo ?")
                        .setMessage("Bạn có chắc chắn muốn xoá thông báo ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DuAn1DataBase.getInstance(getContext()).thongBaoDAO().delete(thongBao);
                                list.remove(thongBao);
                                capNhat();
                            }
                        })
                        .setNegativeButton("No",null)
                        .show();

            }
        });
        adapter.setData(list);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }
    public void hideSoftKeyBroad(){
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),0);
    }
}