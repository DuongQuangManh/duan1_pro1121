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

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.DonHangChiTiet;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.TheTap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TheTapAdapter extends RecyclerView.Adapter<TheTapAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<TheTap> list;
    private List<TheTap> listOld;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdfvn = new SimpleDateFormat("dd-MM-yyyy");
    Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH)+1;
    int day = calendar.get(Calendar.DAY_OF_MONTH);
    long songay;
    int yearend,monthend,dayend;
    long day1;

    public TheTapAdapter(Context context) {
        this.context = context;
    }
    public void setData(List<TheTap> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }

    Calendar lichend = Calendar.getInstance();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thetap,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheTap theTap = list.get(position);
        if (theTap!=null){
            holder.tv_id.setText("Mã thẻ tập: "+theTap.getId());
            holder.tv_hoten.setText("Họ tên: "+ DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(theTap.getKhachhang_id()).get(0).getHoten());
            holder.tv_loathe.setText("Loại thẻ: "+ DuAn1DataBase.getInstance(context).loaiTheTapDAO().getByID(String.valueOf(theTap.getLoaithetap_id())).get(0).getName());
            holder.tv_starttime.setText("Thời gian bắt đầu: "+theTap.getNgayDangKy());
            holder.tv_endtime.setText("Thời gian kết thúc: "+theTap.getNgayHetHan());

            lichend.set(Calendar.DAY_OF_MONTH,getArrayDate(theTap.getNgayHetHan())[0]);
            lichend.set(Calendar.MONTH,getArrayDate(theTap.getNgayHetHan())[1]);
            lichend.set(Calendar.YEAR,getArrayDate(theTap.getNgayHetHan())[2]);

            yearend = lichend.get(Calendar.YEAR);
            monthend = lichend.get(Calendar.MONTH);
            dayend = lichend.get(Calendar.DAY_OF_MONTH);

            try {
                Date datenow = sdf.parse(year+"-"+month+"-"+day);
                Date dateend = sdf.parse(yearend+"-"+monthend+"-"+dayend);
                Log.d("date",datenow.toString()+"và "+dateend.toString());
                songay =  dateend.getTime() - datenow.getTime();
                if (TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS)<=0){
                    holder.tv_trangthai.setTextColor(Color.RED);
                    holder.tv_trangthai.setText("Trạng thái: hết hạn");
                }else if(TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS)<=5){
                    holder.tv_trangthai.setTextColor(Color.RED);
                    holder.tv_trangthai.setText("Trạng thái: Sắp hết hạn");
                }else {
                    holder.tv_trangthai.setTextColor(Color.GREEN);
                    holder.tv_trangthai.setText("Trạng thái: Chưa hết hạn");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            holder.tv_homnay.setText("Hôm nay: "+sdfvn.format(new Date()));
            holder.tv_songay.setText("Còn: "+ TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS));
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
                Calendar lichsearch = Calendar.getInstance();
                lichsearch.set(Calendar.DAY_OF_MONTH,getArrayDate(strSearch)[0]);
                lichsearch.set(Calendar.MONTH,getArrayDate(strSearch)[1]);
                lichsearch.set(Calendar.YEAR,getArrayDate(strSearch)[2]);

                int daysearch = lichsearch.get(Calendar.DAY_OF_MONTH);
                int monthsearch = lichsearch.get(Calendar.MONTH);
                int yearsearch = lichsearch.get(Calendar.YEAR);

                Calendar lichend1 = Calendar.getInstance();
                if (strSearch.isEmpty()){
                    list = listOld;
                }else {
                    List<TheTap> listnew = new ArrayList<>();
                    for (TheTap theTap : listOld){
                        lichend1.set(Calendar.DAY_OF_MONTH,getArrayDate(theTap.getNgayHetHan())[0]);
                        lichend1.set(Calendar.MONTH,getArrayDate(theTap.getNgayHetHan())[1]);
                        lichend1.set(Calendar.YEAR,getArrayDate(theTap.getNgayHetHan())[2]);

                        int yearend1 = lichend1.get(Calendar.YEAR);
                        int monthend1 = lichend1.get(Calendar.MONTH);
                        int dayend1 = lichend1.get(Calendar.DAY_OF_MONTH);

                        try {
                            Date datenow = sdf.parse(yearsearch+"-"+monthsearch+"-"+daysearch);
                            Date dateend = sdf.parse(yearend1+"-"+monthend1+"-"+dayend1);
                            day1 = dateend.getTime() - datenow.getTime();
                            Log.v("abcd",TimeUnit.DAYS.convert(day1, TimeUnit.MILLISECONDS)+"soos ngay");
                        }catch (Exception e){
                            e.printStackTrace();
                        }



                        KhachHang khachHang = DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(theTap.getKhachhang_id()).get(0);

                        if (khachHang.getHoten().toLowerCase().contains(strSearch.toLowerCase())|| TimeUnit.DAYS.convert(day1, TimeUnit.MILLISECONDS)<=2){
                            listnew.add(theTap);
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
                list = (List<TheTap>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_id,tv_hoten,tv_loathe,tv_starttime,tv_endtime,tv_trangthai,tv_homnay,tv_songay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_id = itemView.findViewById(R.id.tv_mathetap_itemthetap);
            tv_hoten = itemView.findViewById(R.id.tv_tenkhachhang_itemthetap);
            tv_loathe = itemView.findViewById(R.id.tv_tenloaithetap_itemthetap);
            tv_starttime = itemView.findViewById(R.id.tv_starttime_itemthetap);
            tv_endtime = itemView.findViewById(R.id.tv_endtime_itemthetap);
            tv_trangthai = itemView.findViewById(R.id.tv_trangthai_itemthetap);
            tv_homnay = itemView.findViewById(R.id.tv_ngayhomnay_itemthetap);
            tv_songay = itemView.findViewById(R.id.tv_songay_itemthetap);
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
