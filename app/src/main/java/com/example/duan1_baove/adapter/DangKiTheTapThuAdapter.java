package com.example.duan1_baove.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
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
    Calendar lich;
    Calendar lichnow;
    long songay;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
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
            lich = Calendar.getInstance();
            KhachHang khachHang = DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(dangKiTapThu.getKhachhang_id()).get(0);
            if (khachHang!=null){
                holder.tv_name.setText("H??? t??n: "+khachHang.getHoten());
                holder.tv_ngaysinh.setText("Ng??y sinh: "+khachHang.getNamSinh());
                holder.tv_lienhe.setText("Li??n h???: "+khachHang.getSoDienThoai());
                holder.tv_gioitinh.setText("Gi???i t??nh: "+khachHang.getGioitinh());
            }
            holder.tv_ngaytap.setText("Ng??y t???p: "+dangKiTapThu.getNgayTap());
            lich.set(Calendar.DAY_OF_MONTH,getArrayDate(dangKiTapThu.getNgayTap())[0]);
            lich.set(Calendar.MONTH,getArrayDate(dangKiTapThu.getNgayTap())[1]);
            lich.set(Calendar.YEAR,getArrayDate(dangKiTapThu.getNgayTap())[2]);

            lichnow = Calendar.getInstance();
            lichnow.add(Calendar.MONTH,1);
            Log.d("314",lichnow.get(Calendar.DAY_OF_MONTH)+"-"+lichnow.get(Calendar.MONTH)+"-"+lichnow.get(Calendar.YEAR));
            Log.d("314",lich.get(Calendar.DAY_OF_MONTH)+"-"+lich.get(Calendar.MONTH)+"-"+lich.get(Calendar.YEAR));

            songay =  lichnow.getTime().getTime() - lich.getTime().getTime();
            if (songay>0){
                holder.tv_trangthai.setTextColor(Color.RED);
                holder.tv_trangthai.setText("Tr???ng th??i: H???t h???n");
            }else {
                holder.tv_trangthai.setTextColor(Color.GREEN);
                holder.tv_trangthai.setText("Tr???ng th??i: Ch??a h???t h???n");
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
