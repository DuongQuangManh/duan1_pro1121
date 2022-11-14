package com.example.duan1_baove.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.model.CuaHang;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class CuaHangHocVienAdapter extends RecyclerView.Adapter<CuaHangHocVienAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<CuaHang> list;
    private List<CuaHang> listOld;
    NumberFormat numberFormat = new DecimalFormat("###,###,###");
    IclickListener iclickListener;

    public interface IclickListener{
        void muahang(CuaHang cuaHang);
    }

    public CuaHangHocVienAdapter(Context context, IclickListener iclickListener) {
        this.context = context;
        this.iclickListener = iclickListener;
    }

    public void setData(List<CuaHang> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cuahang_hocvien,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CuaHang cuaHang = list.get(position);
        if (cuaHang!=null){
            if (cuaHang.getSoLuong()<= 0){
                holder.img_hethang.setVisibility(View.VISIBLE);
            }else {
                holder.img_hethang.setVisibility(View.GONE);
            }
            if (position <= 1){
                holder.img_new.setVisibility(View.VISIBLE);
            }else {
                holder.img_new.setVisibility(View.GONE);
            }
            if (cuaHang.getImg()==null){
                holder.img_avt.setImageResource(R.drawable.ic_account);
            }else {
                String linkimg = cuaHang.getImg();
                Log.d("adapter",linkimg+" link");
                holder.img_avt.setImageDrawable(Drawable.createFromPath(linkimg));
            }
            holder.tv_name.setText(cuaHang.getName());
            holder.tv_gia.setText(numberFormat.format(cuaHang.getGia())+" vnđ");
            holder.tv_soluong.setText("SL: "+cuaHang.getSoLuong());
            holder.layout_update.setOnClickListener(v -> {
                iclickListener.muahang(cuaHang);
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    list = listOld;
                }else {
                    List<CuaHang> listnew = new ArrayList<>();
                    for (CuaHang cuaHang:listOld){
                        if (cuaHang.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            listnew.add(cuaHang);
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
                list = (List<CuaHang>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_avt,img_new,img_hethang;
        private TextView tv_name,tv_gia,tv_soluong;
        private RelativeLayout layout_update;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avt = itemView.findViewById(R.id.img_monhang_itemcuahanghocvien);
            tv_name = itemView.findViewById(R.id.tv_name_itemcuahanghocvien);
            tv_gia = itemView.findViewById(R.id.tv_gia_itemcuahanghocvien);
            tv_soluong = itemView.findViewById(R.id.tv_soluong_itemcuahanghocvien);
            layout_update = itemView.findViewById(R.id.layout_update_itemcuahanghocvien);
            img_new = itemView.findViewById(R.id.img_trangthainew_itemcuahanghocvien);
            img_hethang = itemView.findViewById(R.id.img_trangthai_itemcuahanghocvien);
        }
    }
}
