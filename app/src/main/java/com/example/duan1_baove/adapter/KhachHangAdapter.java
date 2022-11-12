package com.example.duan1_baove.adapter;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.Admin;
import com.example.duan1_baove.model.KhachHang;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<KhachHang> list;
    private List<KhachHang> listOld;

    EditText edt_user,edt_name,edt_ngaysinh,edt_pass;
    TextInputLayout txt_pass,txt_ngaysinh;
    Button btn_add,btn_huy;
    Spinner spn_gioitinh;
    CircleImageView avt_khachhang;
    Calendar lich = Calendar.getInstance();
    String[] Listgioitinh = {"Nam","Nữ"};
    ArrayAdapter gioitinhAdapter;
    String gioitinh;
    int positionGioiTinh;

    public KhachHangAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<KhachHang> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_khachhang,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        KhachHang khachHang = list.get(position);
        if (khachHang!=null){
            holder.tv_user.setText("Tài khoản: "+khachHang.getSoDienThoai());
            holder.tv_name.setText("Họ tên: "+khachHang.getHoten());
            holder.tv_ngaysinh.setText("Ngày sinh: "+khachHang.getNamSinh());
            holder.tv_gioitinh.setText("Giới tính: "+khachHang.getGioitinh());
            holder.tv_pass.setText("Pass: "+khachHang.getPass());
            holder.tv_lienlac.setText("Liên lạc: "+khachHang.getSoDienThoai());

            if (khachHang.getAvata()==null){
                holder.img.setImageResource(R.drawable.ic_account);
            }else {
                String linkimg = khachHang.getAvata();
                Log.d("adapter",linkimg+" link");
                holder.img.setImageDrawable(Drawable.createFromPath(linkimg));
            }
            holder.layout_update.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    new AlertDialog.Builder(context).setTitle("Xoá khách hàng ?")
                            .setMessage("Bạn có chắc chắn muốn xoá khách hàng ?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    DuAn1DataBase.getInstance(context).khachHangDAO().delete(khachHang);
                                    Toast.makeText(context, "Delete khách hàng thành công", Toast.LENGTH_SHORT).show();
                                    list.remove(khachHang);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No",null)
                            .show();
                    return true;
                }
            });
            holder.layout_update.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_addkhachhang);
                dialog.show();
                Window window = dialog.getWindow();
                if (window == null){
                    return;
                }
                window.setBackgroundDrawable(null);
                avt_khachhang = dialog.findViewById(R.id.avt_dialogkhachhang);
                edt_user = dialog.findViewById(R.id.edt_tk_dialogkhachhang);
                edt_name = dialog.findViewById(R.id.edt_name_dialogkhachhang);
                edt_ngaysinh = dialog.findViewById(R.id.edt_ngaysinh_dialogkhachhang);
                edt_pass = dialog.findViewById(R.id.edt_mk1_dialogkhachhang);
                txt_pass = dialog.findViewById(R.id.textinput1_dialogkhachhang);
                txt_ngaysinh = dialog.findViewById(R.id.txtinput_ngay_dialogkhachhang);
                btn_add = dialog.findViewById(R.id.btn_luu_dialogkhachhang);
                btn_huy = dialog.findViewById(R.id.btn_huy_dialogkhachhang);
                spn_gioitinh = dialog.findViewById(R.id.spn_gioitinh_dialogkhachhang);

                gioitinhAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item,Listgioitinh);
                spn_gioitinh.setAdapter(gioitinhAdapter);

                spn_gioitinh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        gioitinh = Listgioitinh[position];
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                edt_user.setEnabled(false);

                txt_ngaysinh.setStartIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int year = lich.get(Calendar.YEAR);
                        int month = lich.get(Calendar.MONTH);
                        int day = lich.get(Calendar.DAY_OF_MONTH);
                        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                edt_ngaysinh.setText(dayOfMonth+"-"+(month+1)+"-"+year);
                            }
                        },year,month,day).show();
                    }
                });
                txt_pass.setEndIconOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showpass(txt_pass,edt_pass);
                    }
                });

                edt_user.setText(khachHang.getSoDienThoai());
                edt_name.setText(khachHang.getHoten());
                edt_ngaysinh.setText(khachHang.getNamSinh());
                edt_pass.setText(khachHang.getPass());
                for (int i =0;i<Listgioitinh.length;i++){
                    if (Listgioitinh[i].equals(khachHang.getGioitinh())){
                        positionGioiTinh = i;
                    }
                }
                spn_gioitinh.setSelection(positionGioiTinh);

                if (khachHang.getAvata()==null){
                    holder.img.setImageResource(R.drawable.ic_account);
                }else {
                    String linkimg = khachHang.getAvata();
                    Log.d("adapter",linkimg+" link");
                    holder.img.setImageDrawable(Drawable.createFromPath(linkimg));
                }

                btn_huy.setOnClickListener(v1 -> {
                    dialog.cancel();
                });
                btn_add.setOnClickListener(v1 -> {
                    if (validate()){
                        khachHang.setGioitinh(gioitinh);
                        khachHang.setHoten(edt_name.getText().toString().trim());
                        khachHang.setSoDienThoai(edt_user.getText().toString().trim());
                        khachHang.setNamSinh(edt_ngaysinh.getText().toString().trim());
                        khachHang.setPass(edt_pass.getText().toString().trim());
                        DuAn1DataBase.getInstance(context).khachHangDAO().update(khachHang);
                        Toast.makeText(context, "Insert khách hàng thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list!=null){
            return list.size();
        }
        return 0;
    }
    private void showpass(TextInputLayout txt,EditText edt) {
        if(edt.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
            edt.setInputType( InputType.TYPE_CLASS_TEXT |
                    InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txt.setEndIconDrawable(R.drawable.ic_eye_off);
        }else {
            edt.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
            txt.setEndIconDrawable(R.drawable.ic_eye);
        }
        edt.setSelection(edt.getText().length());
    }
    private boolean validate(){
        String email = "^\\w@\\w.\\w";
        String sdt = "^0\\d{9}";
        Pattern patternEmail = Pattern.compile(email);
        Pattern patternSdt = Pattern.compile(sdt);
        String user = edt_user.getText().toString().trim();
        if (edt_user.getText().toString().trim().isEmpty()||edt_name.getText().toString().trim().isEmpty()||edt_ngaysinh.getText().toString().trim().isEmpty()|| edt_pass.getText().toString().trim().isEmpty()){
            Toast.makeText(context, "Vui lòng không bỏ trống thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            if (patternEmail.matcher(user).find() || patternSdt.matcher(user).find()){
                if (edt_pass.getText().toString().trim().length()<8){
                    Toast.makeText(context, "Vui lòng nhập mật khẩu dài hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    return true;
                }
            }
            else {
                Toast.makeText(context, "Vui lòng nhập đúng định dạng tài khoản", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    list = listOld;
                }else {
                    List<KhachHang> listnew = new ArrayList<>();
                    for (KhachHang khachHang:listOld){
                        if (khachHang.getHoten().toLowerCase().contains(strSearch.toLowerCase())){
                            listnew.add(khachHang);
                        }
                    }
                    list = listnew;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = list;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (List<KhachHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_user,tv_name,tv_ngaysinh,tv_gioitinh,tv_pass,tv_lienlac;
        private ImageView img;
        private RelativeLayout layout_update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user = itemView.findViewById(R.id.tv_user_itemkhachhang);
            tv_name = itemView.findViewById(R.id.tv_name_itemkhachhang);
            tv_ngaysinh = itemView.findViewById(R.id.tv_ngaysinh_itemkhachhang);
            tv_gioitinh = itemView.findViewById(R.id.tv_gioitinh_itemkhachhang);
            tv_pass = itemView.findViewById(R.id.tv_pass_itemkhachhang);
            tv_lienlac = itemView.findViewById(R.id.tv_lienlac_itemkhachhang);
            img = itemView.findViewById(R.id.avt_itemkhachhang);
            layout_update = itemView.findViewById(R.id.layout_update_itemkhachhang);
        }
    }
}
