package com.example.duan1_baove.adapter;

import android.companion.WifiDeviceFilter;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.CuaHang;
import com.example.duan1_baove.model.DonHangChiTiet;
import com.example.duan1_baove.model.KhachHang;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<DonHangChiTiet> list;
    private List<DonHangChiTiet> listOld;
    NumberFormat numberFormat = new DecimalFormat("###,###,###");
    private IClickListener iClickListener;
    Calendar start,end;

    public DonHangAdapter(Context context, IClickListener iClickListener) {
        this.context = context;
        this.iClickListener = iClickListener;
    }

    public void setData(List<DonHangChiTiet> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
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
                    List<DonHangChiTiet> listnew = new ArrayList<>();
                    for (DonHangChiTiet donHangChiTiet : listOld){
                        KhachHang khachHang = DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(donHangChiTiet.getKhachang_id()).get(0);
                        if (khachHang.getHoten().toLowerCase().contains(strSearch.toLowerCase())){
                            listnew.add(donHangChiTiet);
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
                list = (List<DonHangChiTiet>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface IClickListener{
        void duyet(DonHangChiTiet donHangChiTiet);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_donhangchitiet,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DonHangChiTiet donHangChiTiet = list.get(position);
        if (donHangChiTiet!=null){
            if (donHangChiTiet.getTinhTrang().equals("Đã kiểm duyệt")){
                holder.tv_tinhtrang.setTextColor(Color.GREEN);
            }else {
                holder.tv_tinhtrang.setTextColor(Color.RED);
            }
            holder.tv_nameuser.setText("Tên người đặt: "+DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(donHangChiTiet.getKhachang_id()).get(0).getHoten());
            holder.tv_thoigiandathang.setText(donHangChiTiet.getStarttime());
            holder.tv_id.setText("Mã đơn đơn hàng: "+donHangChiTiet.getId());
            List<CuaHang> list = DuAn1DataBase.getInstance(context).cuaHangDAO().getByID(String.valueOf(donHangChiTiet.getCuahang_id()));
            holder.tv_name.setText("Tên đơn hàng: "+list.get(0).getName());

            if (list.get(0).getTheloai().equals("Món hàng")){
                holder.tv_gia.setText("Giá: "+numberFormat.format(DuAn1DataBase.getInstance(context).cuaHangDAO().getGiaBan(String.valueOf(donHangChiTiet.getCuahang_id())))+" vnđ/sp");
                holder.tv_strarttime.setVisibility(View.GONE);
                holder.tv_endtime.setVisibility(View.GONE);
                holder.tv_soluong.setVisibility(View.VISIBLE);
                holder.tv_soluong.setText("Số lượng: "+donHangChiTiet.getSoLuong());
                Log.d("zzzzzz","món hàng");
            }else  if (list.get(0).getTheloai().equals("Dịch vụ")){
                holder.tv_gia.setText("Giá: "+numberFormat.format(donHangChiTiet.getGianiemyet())+" vnđ/tháng");
                holder.tv_soluong.setVisibility(View.GONE);
                holder.tv_strarttime.setVisibility(View.VISIBLE);
                holder.tv_endtime.setVisibility(View.VISIBLE);
                holder.tv_strarttime.setText("Thời gian bắt đầu: "+donHangChiTiet.getStarttime());
                holder.tv_endtime.setText("Thời gian kết thúc: "+donHangChiTiet.getEndtime());
                Log.d("zzzzzz","dịch vụ");
                start = Calendar.getInstance();
                end = Calendar.getInstance();
                start.add(Calendar.MONTH,1);

                end.set(Calendar.DAY_OF_MONTH,getArrayDate(donHangChiTiet.getEndtime())[0]);
                end.set(Calendar.DAY_OF_MONTH,getArrayDate(donHangChiTiet.getEndtime())[1]);
                end.set(Calendar.DAY_OF_MONTH,getArrayDate(donHangChiTiet.getEndtime())[2]);

                if (end.getTime().getTime()-start.getTime().getTime()<=0){
                    donHangChiTiet.setTinhTrang("Hết hạn");
                    holder.tv_tinhtrang.setText("Hết hạn");
                    holder.tv_tinhtrang.setTextColor(Color.RED);
                }
            }
            holder.tv_tongtien.setText("Tổng tiền: "+numberFormat.format(donHangChiTiet.getTongtien())+ " vnđ");
            holder.tv_tinhtrang.setText("Tình trạng: "+donHangChiTiet.getTinhTrang());
            holder.tv_hinhthucthanhtoan.setText("HTTT: "+donHangChiTiet.getHinhthucthanhtoan());
            holder.layout_update.setOnClickListener(v -> {
                iClickListener.duyet(donHangChiTiet);
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_id,tv_nameuser,tv_name,tv_gia,tv_soluong,tv_strarttime,tv_endtime,tv_tongtien,tv_tinhtrang,tv_thoigiandathang,tv_hinhthucthanhtoan;
        private RelativeLayout layout_update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_madonhangchitiet_donhangchitiet);
            tv_nameuser = itemView.findViewById(R.id.tv_tennguoidat_donhangchitiet);
            tv_name = itemView.findViewById(R.id.tv_tenmonhang_donhangchitiet);
            tv_gia = itemView.findViewById(R.id.tv_gianiemyet_donhangchitiet);
            tv_soluong = itemView.findViewById(R.id.tv_soluong_donhangchitiet);
            tv_strarttime = itemView.findViewById(R.id.tv_starttime_donhangchitiet);
            tv_endtime = itemView.findViewById(R.id.tv_endtime_donhangchitiet);
            tv_tongtien = itemView.findViewById(R.id.tv_tongtien_donhangchitiet);
            tv_tinhtrang = itemView.findViewById(R.id.tv_tinhtrang_donhangchitiet);
            tv_thoigiandathang = itemView.findViewById(R.id.tv_thoigianmua_donhangchitiet);
            layout_update = itemView.findViewById(R.id.layout_update_donhangchitiet);
            tv_hinhthucthanhtoan = itemView.findViewById(R.id.tv_hinhthucthanhtoan_donhangchitiet);
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
