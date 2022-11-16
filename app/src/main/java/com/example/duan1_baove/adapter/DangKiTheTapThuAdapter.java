package com.example.duan1_baove.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DangKiTapThu;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.LoaiTheTap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class DangKiTheTapThuAdapter extends RecyclerView.Adapter<DangKiTheTapThuAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<DangKiTapThu> list;
    private List<DangKiTapThu> listOld;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Calendar lich = Calendar.getInstance();
    int year,month,day;
    long songay;

    public DangKiTheTapThuAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<DangKiTapThu> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dangkitapthu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DangKiTapThu dangKiTapThu = list.get(position);
        if (dangKiTapThu!=null){
            KhachHang khachHang = DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(dangKiTapThu.getKhachhang_id()).get(0);
            if (khachHang!=null){
                holder.tv_name.setText("Họ tên: "+khachHang.getHoten());
                holder.tv_ngaysinh.setText("Ngày sinh: "+khachHang.getNamSinh());
                holder.tv_lienhe.setText("Liên hệ: "+khachHang.getSoDienThoai());
                holder.tv_gioitinh.setText("Giới tính: "+khachHang.getGioitinh());
            }
            holder.tv_ngaytap.setText("Ngày tập: "+dangKiTapThu.getNgayTap());
            lich.set(Calendar.DAY_OF_MONTH,getArrayDate(dangKiTapThu.getNgayTap())[0]);
            lich.set(Calendar.MONTH,getArrayDate(dangKiTapThu.getNgayTap())[1]);
            lich.set(Calendar.YEAR,getArrayDate(dangKiTapThu.getNgayTap())[2]);

            year = lich.get(Calendar.YEAR);
            month = lich.get(Calendar.MONTH);
            day = lich.get(Calendar.DAY_OF_MONTH);

            try {
                Date date = simpleDateFormat.parse(year+"-"+month+"-"+day);
                Date datenow = new Date();
                songay =  date.getTime() - datenow.getTime();
                if (TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS)<=0){
                    holder.tv_trangthai.setTextColor(Color.RED);
                    holder.tv_trangthai.setText("Trạng thái: Hết hạn");
                }else {
                    holder.tv_trangthai.setTextColor(Color.GREEN);
                    holder.tv_trangthai.setText("Trạng thái: Chưa hết hạn");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        if (list!=null){
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
                    List<DangKiTapThu> listnew = new ArrayList<>();
                    for (DangKiTapThu dangKiTapThu:listOld){
                        KhachHang khachHang = DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(dangKiTapThu.getKhachhang_id()).get(0);
                        if (khachHang.getHoten().toLowerCase().contains(strSearch.toLowerCase())){
                            listnew.add(dangKiTapThu);
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
                list = (List<DangKiTapThu>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_name,tv_ngaysinh,tv_lienhe,tv_gioitinh,tv_ngaytap,tv_trangthai;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_itemthetapthu);
            tv_ngaysinh = itemView.findViewById(R.id.tv_ngaysinh_itemthetapthu);
            tv_lienhe = itemView.findViewById(R.id.tv_sodienthoai_itemthetapthu);
            tv_gioitinh = itemView.findViewById(R.id.tv_gioitinh_itemthetapthu);
            tv_ngaytap = itemView.findViewById(R.id.tv_ngaytap_itemthetapthu);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai_itemthetapthu);
        }
    }
    public int[] getArrayDate(String date){
        String[] str = date.split("-");
        int arr[] = new int[str.length];
        try{
            for(int i = 0;i<str.length;i++){
                arr[i] = Integer.parseInt(str[i]);
            }
        }catch (NumberFormatException e){
            return null;
        }
        return arr;
    }
}
