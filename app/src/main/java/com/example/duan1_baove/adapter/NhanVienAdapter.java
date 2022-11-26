package com.example.duan1_baove.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.example.duan1_baove.model.ChucVu;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NhanVienAdapter extends RecyclerView.Adapter<NhanVienAdapter.ViewHolder> implements Filterable {
    private Context context;

    private List<Admin> list;
    private List<Admin> listOld;
    private CircleImageView avt;
    private EditText edt_user,edt_name,edt_pass,edt_luong,edt_tennganhang,edt_stk;
    private Spinner spn_chucvu;
    private Button btn_add,btn_huy;

    SpinnerAdapter spinerAdapter;
    List<ChucVu> chucVuList;
    int chucvu_id;

    NumberFormat numberFormat = new DecimalFormat("###,###,###");


    public NhanVienAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Admin> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_nhanvien,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Admin admin = list.get(position);
        if (admin!=null){
            holder.tv_user.setText("Tài khoản: "+admin.getUser());
            holder.tv_name.setText("Họ tên: "+admin.getName());
            holder.tv_pass.setText("Pass: "+admin.getPass());
            holder.tv_luong.setText("Lương: "+numberFormat.format(admin.getLuong())+" vnđ");
            holder.tv_nganhang.setText("Ngân hàng: "+admin.getTennganhang());
            holder.tv_stk.setText("Số tài khoản: "+admin.getStk());
            String chucvu = DuAn1DataBase.getInstance(context).chucVuDAO().chechChucVu(String.valueOf(admin.getChucvu_id()));
            holder.tv_chucvu.setText("Chức vụ: "+chucvu);

            if (admin.getHinhanh()==null){
                holder.avt.setImageResource(R.drawable.ic_account);
            }else {
                String linkimg = admin.getHinhanh();
                Log.d("adapter",linkimg+" link");
                holder.avt.setImageDrawable(Drawable.createFromPath(linkimg));
            }
            holder.update.setOnClickListener(v -> {
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_addnhanvien);
                dialog.show();
                Window window = dialog.getWindow();
                if (window==null){
                    return;
                }
                window.setBackgroundDrawable(null);

                avt = dialog.findViewById(R.id.avt_dialognhanvien);
                edt_user = dialog.findViewById(R.id.edt_taikhoan_dialognhanvien);
                edt_name = dialog.findViewById(R.id.edt_hoten_dialognhanvien);
                edt_pass = dialog.findViewById(R.id.edt_mk_dialognhanvien);
                edt_luong = dialog.findViewById(R.id.edt_luong_dialognhanvien);
                edt_tennganhang = dialog.findViewById(R.id.edt_tennganhang_dialognhanvien);
                edt_stk = dialog.findViewById(R.id.edt_stk_dialognhanvien);
                btn_add = dialog.findViewById(R.id.btn_luu_dialognhanvien);
                btn_huy = dialog.findViewById(R.id.btn_huy_dialognhanvien);
                avt = dialog.findViewById(R.id.avt_dialognhanvien);
                spn_chucvu = dialog.findViewById(R.id.spinner_chucvu_dialognhanvien);

                chucVuList = DuAn1DataBase.getInstance(context).chucVuDAO().getAll();
                spinerAdapter = new SpinnerAdapter(context,R.layout.item_spiner,chucVuList);
                spn_chucvu.setAdapter(spinerAdapter);

                spn_chucvu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        chucvu_id = chucVuList.get(position).getId();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
                edt_user.setEnabled(false);
                edt_user.setText(admin.getUser());
                edt_name.setText(admin.getName());
                edt_pass.setText(admin.getPass());
                edt_luong.setText(admin.getLuong()+"");
                edt_tennganhang.setText(admin.getTennganhang());
                edt_stk.setText(admin.getStk());
                if (admin.getHinhanh()==null){
                    avt.setImageResource(R.drawable.ic_account);
                }else {
                    String linkimg = admin.getHinhanh();
                    Log.d("adapter",linkimg+" link");
                    avt.setImageDrawable(Drawable.createFromPath(linkimg));
                }
                spn_chucvu.setSelection(admin.getChucvu_id()-1);

                btn_huy.setOnClickListener(v1 -> {
                    dialog.cancel();
                });
                btn_add.setOnClickListener(v1 -> {
                    if (validate()){
                        admin.setName(edt_name.getText().toString().trim());
                        admin.setPass(edt_pass.getText().toString().trim());
                        admin.setChucvu_id(chucvu_id);
                        admin.setLuong(Integer.parseInt(edt_luong.getText().toString().trim()));
                        admin.setTennganhang(edt_tennganhang.getText().toString().trim());
                        admin.setStk(edt_stk.getText().toString().trim());
                        DuAn1DataBase.getInstance(context).adminDAO().update(admin);
                        Toast.makeText(context, "Update nhân viên thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            });
        }

    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        return 0;
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
                    List<Admin> listnew = new ArrayList<>();
                    for (Admin admin:listOld){
                        ChucVu chucVu = DuAn1DataBase.getInstance(context).chucVuDAO().checkForId(String.valueOf(admin.getChucvu_id())).get(0);
                        if (admin.getName().toLowerCase().contains(strSearch.toLowerCase())|| strSearch.equals(chucVu.getTenchucvu())){
                            listnew.add(admin);
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
                list = (List<Admin>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_user,tv_name,tv_pass,tv_luong,tv_nganhang,tv_stk,tv_chucvu;
        private RelativeLayout update;
        private ImageView avt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_user = itemView.findViewById(R.id.tv_user_itemadmin);
            tv_name = itemView.findViewById(R.id.tv_name_itemadmin);
            tv_pass = itemView.findViewById(R.id.tv_pass_itemadmin);
            tv_luong = itemView.findViewById(R.id.tv_luong_itemadmin);
            tv_nganhang = itemView.findViewById(R.id.tv_tennganhang_itemadmin);
            tv_stk = itemView.findViewById(R.id.tv_stk_itemadmin);
            tv_chucvu = itemView.findViewById(R.id.tv_chucvu_itemadmin);
            avt = itemView.findViewById(R.id.avt_itemadmin);
            update = itemView.findViewById(R.id.layout_update_itemadmin);
        }
    }
    private boolean validate(){
        if (edt_user.getText().toString().trim().isEmpty() || edt_name.getText().toString().trim().isEmpty() ||
                edt_pass.getText().toString().trim().isEmpty() || edt_luong.getText().toString().trim().isEmpty() ||
                edt_tennganhang.getText().toString().trim().isEmpty() || edt_stk.getText().toString().trim().isEmpty()){
            return false;
        }else {
            if (edt_pass.getText().toString().trim().length()<8){
                Toast.makeText(context, "Vui lòng nhập mật khẩu dài hơn 8 kí tự", Toast.LENGTH_SHORT).show();
                return false;
            }else {
                try {
                    Integer.parseInt(edt_luong.getText().toString().trim());
                    return true;
                }catch (Exception e){
                    Toast.makeText(context, "Lương không hợp lệ", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }
    }
}
