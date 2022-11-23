package com.example.duan1_baove.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DatLichTap;
import java.util.Calendar;
import java.util.List;

public class DatLichTapAdapter extends RecyclerView.Adapter<DatLichTapAdapter.ViewHolder> {
    private Context context;
    private List<DatLichTap> list;
    Calendar lichtap,lichnow,lichnghi;
    IClickListener iClickListener;

    public DatLichTapAdapter(Context context, IClickListener iClickListener) {
        this.context = context;
        this.iClickListener = iClickListener;
    }

    public void setData(List<DatLichTap> list){
        this.list = list;
        notifyDataSetChanged();
    }

    public interface IClickListener{
        void thongtinhocvien(DatLichTap datLichTap);
        void thongtinpt(DatLichTap datLichTap);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_datlichtap,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatLichTap datLichTap = list.get(position);
        if (datLichTap!=null){
            lichtap = Calendar.getInstance();
            lichnow = Calendar.getInstance();
            lichnghi = Calendar.getInstance();

            holder.tv_name.setText(DuAn1DataBase.getInstance(context).khachHangDAO().getName(datLichTap.getKhachhang_id()));
            holder.tv_ngaydatlich.setText(datLichTap.getThoigiandat());
            holder.tv_ngayditap.setText(datLichTap.getNgaytap());
            holder.tv_gioditap.setText(datLichTap.getGiotap());
            holder.tv_gionghi.setText(datLichTap.getGionghi());
            if (datLichTap.getAdmin_id().equals("admin")){
                holder.tv_pt.setText("Không");
                holder.tv_pt.setEnabled(false);
            }else {
                holder.tv_pt.setEnabled(true);
                holder.tv_pt.setText(DuAn1DataBase.getInstance(context).adminDAO().getName(datLichTap.getAdmin_id()));
            }
            lichtap.set(Calendar.DAY_OF_MONTH,getArrayDate(datLichTap.getNgaytap())[0]);
            lichtap.set(Calendar.MONTH,getArrayDate(datLichTap.getNgaytap())[1]);
            lichtap.set(Calendar.YEAR,getArrayDate(datLichTap.getNgaytap())[2]);
            lichtap.set(Calendar.HOUR_OF_DAY,getArrayTime(datLichTap.getGiotap())[0]);
            lichtap.set(Calendar.MINUTE,getArrayTime(datLichTap.getGiotap())[1]);

            lichnghi.set(Calendar.DAY_OF_MONTH,getArrayDate(datLichTap.getNgaytap())[0]);
            lichnghi.set(Calendar.MONTH,getArrayDate(datLichTap.getNgaytap())[1]);
            lichnghi.set(Calendar.YEAR,getArrayDate(datLichTap.getNgaytap())[2]);
            lichnghi.set(Calendar.HOUR_OF_DAY,getArrayTime(datLichTap.getGionghi())[0]);
            lichnghi.set(Calendar.MINUTE,getArrayTime(datLichTap.getGionghi())[1]);

            lichnow.add(Calendar.MONTH,1);
            Log.v("1131","Lịch tập"+lichtap.get(Calendar.DAY_OF_MONTH)+"-"+lichtap.get(Calendar.MONTH)+"-"+lichtap.get(Calendar.YEAR)+", "+lichtap.get(Calendar.HOUR)+":"+lichtap.get(Calendar.MINUTE));
            Log.v("1131","Lịch hiện tại"+lichnow.get(Calendar.DAY_OF_MONTH)+"-"+lichnow.get(Calendar.MONTH)+"-"+lichnow.get(Calendar.YEAR)+", "+lichnow.get(Calendar.HOUR)+":"+lichnow.get(Calendar.MINUTE));
            Log.v("1131","Lịch hôm sau"+lichnghi.get(Calendar.DAY_OF_MONTH)+"-"+lichnghi.get(Calendar.MONTH)+"-"+lichnghi.get(Calendar.YEAR)+", "+lichnghi.get(Calendar.HOUR)+":"+lichnghi.get(Calendar.MINUTE));

            try {
                if (lichnow.getTime().getTime()-lichtap.getTime().getTime()<0){
                    holder.tv_trangthai.setText("Đã đặt thành công");
                    holder.tv_trangthai.setTextColor(Color.GREEN);
                }else if (lichnow.getTime().getTime()-lichnghi.getTime().getTime()>0){
                    holder.tv_trangthai.setText("Buổi tập kết thúc");
                    holder.tv_trangthai.setTextColor(Color.RED);
                }else{
                    holder.tv_trangthai.setText("Đang tập");
                    holder.tv_trangthai.setTextColor(Color.YELLOW);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.tv_pt.setOnClickListener(v -> {
                iClickListener.thongtinpt(datLichTap);
            });
            holder.tv_name.setOnClickListener(v -> {
                iClickListener.thongtinhocvien(datLichTap);
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
        private TextView tv_name,tv_ngaydatlich,tv_ngayditap,tv_gioditap,tv_pt,tv_trangthai,tv_gionghi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name_itemdatlichtap);
            tv_ngaydatlich = itemView.findViewById(R.id.tv_ngaydat_itemdatlichtap);
            tv_ngayditap = itemView.findViewById(R.id.tv_ngayditap_itemdatlichtap);
            tv_gioditap = itemView.findViewById(R.id.tv_gioditap_itemdatlichtap);
            tv_pt = itemView.findViewById(R.id.tv_pt_itemdatlichtap);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai_itemdatlichtap);
            tv_gionghi = itemView.findViewById(R.id.tv_gionghi_itemdatlichtap);
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
    public int[] getArrayTime(String time){
        String[] str = time.split(":");
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
