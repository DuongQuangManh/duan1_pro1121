package com.example.duan1_baove.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duan1_baove.R;
import com.example.duan1_baove.database.DuAn1DataBase;
import com.example.duan1_baove.model.KhachHang;
import com.example.duan1_baove.model.LichSuGiaoDich;
import com.example.duan1_baove.model.LoaiTheTap;
import com.example.duan1_baove.model.TheTap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TheTapAdapter extends RecyclerView.Adapter<TheTapAdapter.ViewHolder> implements Filterable {
    private IClickListener iClickListener;
    private Context context;
    private List<TheTap> list;
    private List<TheTap> listOld;
    private SimpleDateFormat sdfvn = new SimpleDateFormat("dd-MM-yyyy");
    Calendar calendar;
    Calendar lichend;
    long songay;

    private EditText edt_id,edt_starttime,edt_endtime;
    private Spinner spn_khachhang,spn_loaithetap;
    private Button btn_add,btn_huy;
    private List<KhachHang> listKhachHang;
    private List<LoaiTheTap> listLoaiTheTap;
    private Calendar calendarEndTime = Calendar.getInstance();
    private String strKhachHangID;
    private int intLoaiTheTap;
    int yearEnd,monthEnd, dayEnd;
    int giagoitapcu;
    String idkhachcu;
    int TongTienGiaTheTap,idloaithetapcu;
    int idkhachhang;
    Calendar calendarDangKy;


    public TheTapAdapter(Context context,IClickListener iClickListener) {
        this.context = context;
        this.iClickListener = iClickListener;
    }

    public void setData(List<TheTap> list){
        this.list = list;
        this.listOld = list;
        notifyDataSetChanged();
    }

    public interface IClickListener{
        void giahan(TheTap theTap);
    }

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
            idkhachcu = theTap.getKhachhang_id();
            giagoitapcu = DuAn1DataBase.getInstance(context).loaiTheTapDAO().getByID(String.valueOf(theTap.getLoaithetap_id())).get(0).getGia();
            Log.v("giaca",giagoitapcu+"Gía cũ");
            Log.v("giaca",DuAn1DataBase.getInstance(context).theTapDAO().getTongSoTien(theTap.getKhachhang_id())+"Tổng tiền cũ");
            holder.tv_id.setText("Mã thẻ tập: "+theTap.getId());
            holder.tv_hoten.setText("Họ tên: "+ DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(theTap.getKhachhang_id()).get(0).getHoten());
            holder.tv_loathe.setText("Loại thẻ: "+ DuAn1DataBase.getInstance(context).loaiTheTapDAO().getByID(String.valueOf(theTap.getLoaithetap_id())).get(0).getName());
            holder.tv_starttime.setText("Thời gian bắt đầu: "+theTap.getNgayDangKy());
            holder.tv_endtime.setText("Thời gian kết thúc: "+theTap.getNgayHetHan());
            calendar = Calendar.getInstance();
            lichend = Calendar.getInstance();
            lichend.set(Calendar.DAY_OF_MONTH,getArrayDate(theTap.getNgayHetHan())[0]);
            lichend.set(Calendar.MONTH,getArrayDate(theTap.getNgayHetHan())[1]);
            lichend.set(Calendar.YEAR,getArrayDate(theTap.getNgayHetHan())[2]);

            calendar.add(Calendar.MONTH,1);
            songay =  lichend.getTime().getTime() - calendar.getTime().getTime();
            if (TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS)<=0){
                holder.tv_trangthai.setTextColor(Color.RED);
                holder.tv_trangthai.setText("Trạng thái: hết hạn");

                holder.tv_homnay.setEnabled(false);
                holder.tv_hoten.setEnabled(false);
                holder.tv_songay.setEnabled(false);
                holder.tv_trangthai.setEnabled(false);
                holder.tv_endtime.setEnabled(false);
                holder.tv_starttime.setEnabled(false);
                holder.tv_loathe.setEnabled(false);
                holder.tv_id.setEnabled(false);
                holder.layout_update.setEnabled(false);

                holder.layout_giahan.setVisibility(View.VISIBLE);
                holder.btn_giahan.setEnabled(true);
            }
            else if(TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS)<=5){

                holder.tv_homnay.setEnabled(true);
                holder.tv_hoten.setEnabled(true);
                holder.tv_songay.setEnabled(true);
                holder.tv_trangthai.setEnabled(true);
                holder.tv_endtime.setEnabled(true);
                holder.tv_starttime.setEnabled(true);
                holder.tv_loathe.setEnabled(true);
                holder.tv_id.setEnabled(true);
                holder.layout_update.setEnabled(true);

                holder.layout_giahan.setVisibility(View.INVISIBLE);
                holder.btn_giahan.setEnabled(false);

                holder.tv_trangthai.setTextColor(Color.RED);
                holder.tv_trangthai.setText("Trạng thái: Sắp hết hạn");
            }else {
                holder.tv_homnay.setEnabled(true);
                holder.tv_hoten.setEnabled(true);
                holder.tv_songay.setEnabled(true);
                holder.tv_trangthai.setEnabled(true);
                holder.tv_endtime.setEnabled(true);
                holder.tv_starttime.setEnabled(true);
                holder.tv_loathe.setEnabled(true);
                holder.tv_id.setEnabled(true);
                holder.layout_update.setEnabled(true);

                holder.layout_giahan.setVisibility(View.INVISIBLE);
                holder.btn_giahan.setEnabled(false);
                holder.tv_trangthai.setTextColor(Color.GREEN);
                holder.tv_trangthai.setText("Trạng thái: Chưa hết hạn");
            }
            holder.tv_homnay.setText("Hôm nay: "+sdfvn.format(new Date()));
            holder.tv_songay.setText("Còn: "+ TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS));
            holder.btn_giahan.setOnClickListener(v -> {
                iClickListener.giahan(theTap);
            });


            holder.layout_update.setOnClickListener(v -> {
                calendarDangKy = Calendar.getInstance();
                calendarDangKy.set(Calendar.DAY_OF_MONTH,getArrayDate(theTap.getNgayDangKy())[0]);
                calendarDangKy.set(Calendar.MONTH,getArrayDate(theTap.getNgayDangKy())[1]);
                calendarDangKy.set(Calendar.YEAR,getArrayDate(theTap.getNgayDangKy())[2]);
                songay =  calendar.getTime().getTime() - calendarDangKy.getTime().getTime();
                if (TimeUnit.DAYS.convert(songay, TimeUnit.MILLISECONDS)<=2){
                    Dialog dialog = new Dialog(context);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_addthetap);
                    dialog.show();
                    Window window = dialog.getWindow();
                    if (window == null){
                        return;
                    }
                    window.setBackgroundDrawable(null);
                    edt_id = dialog.findViewById(R.id.edt_mathetap_dialogthetap);
                    edt_starttime = dialog.findViewById(R.id.edt_starttime_dialogthetap);
                    edt_endtime = dialog.findViewById(R.id.edt_endtime_dialogthetap);
                    btn_add = dialog.findViewById(R.id.btn_luu_dialogthetap);
                    btn_huy = dialog.findViewById(R.id.btn_huy_dialogthetap);
                    spn_khachhang = dialog.findViewById(R.id.spn_tenkhachhang_dialogthetap);
                    spn_loaithetap = dialog.findViewById(R.id.spn_loaithetap_dialogthetap);

                    listKhachHang = DuAn1DataBase.getInstance(context).khachHangDAO().getAll();
                    SpinnerAdapterNapTien spinnerKhachHang = new SpinnerAdapterNapTien(context,R.layout.item_spiner_naptien,listKhachHang);
                    spn_khachhang.setAdapter(spinnerKhachHang);
                    spn_khachhang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            strKhachHangID = listKhachHang.get(position).getSoDienThoai();
                            if (DuAn1DataBase.getInstance(context).theTapDAO().checkTheTap(strKhachHangID).size()>0){
                                spn_khachhang.setSelection(position);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    listLoaiTheTap = DuAn1DataBase.getInstance(context).loaiTheTapDAO().getAll();
                    SpinnerAdapterLoaiTheTap spinnerLoaiTheTap = new SpinnerAdapterLoaiTheTap(context,R.layout.item_spiner_naptien,listLoaiTheTap);
                    spn_loaithetap.setAdapter(spinnerLoaiTheTap);
                    spn_loaithetap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            intLoaiTheTap = listLoaiTheTap.get(position).getId();
                            calendarEndTime = Calendar.getInstance();
                            calendarEndTime.add(Calendar.MONTH,Integer.parseInt(listLoaiTheTap.get(position).getHanSuDung()));
                            yearEnd = calendarEndTime.get(Calendar.YEAR);
                            monthEnd = calendarEndTime.get(Calendar.MONTH)+1;
                            dayEnd = calendarEndTime.get(Calendar.DAY_OF_MONTH);
                            edt_endtime.setText(dayEnd+"-"+monthEnd+"-"+yearEnd);
                            TongTienGiaTheTap = DuAn1DataBase.getInstance(context).loaiTheTapDAO().getByID(String.valueOf(intLoaiTheTap)).get(0).getGia();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    for (int i1=0;i1<listKhachHang.size();i1++){
                        if (listKhachHang.get(i1).getSoDienThoai().equals(theTap.getKhachhang_id())){
                            idkhachhang = i1;
                        }
                    }
                    spn_khachhang.setSelection(idkhachhang);

                    for (int i =0;i<listLoaiTheTap.size();i++){
                        if (listLoaiTheTap.get(i).getId()==theTap.getLoaithetap_id()){
                            idloaithetapcu = i;
                        }
                    }
                    spn_loaithetap.setSelection(idloaithetapcu);
                    edt_starttime.setText(sdfvn.format(new Date()));


                    btn_huy.setOnClickListener(v1 -> {
                        dialog.cancel();
                    });
                    btn_add.setOnClickListener(v1 -> {
                        theTap.setKhachhang_id(strKhachHangID);
                        theTap.setLoaithetap_id(intLoaiTheTap);
                        theTap.setNgayDangKy(edt_starttime.getText().toString().trim());
                        theTap.setNgayHetHan(edt_endtime.getText().toString().trim());
                        if (idkhachcu.equals(strKhachHangID)){
                            theTap.setTongsotiendamuathetap(DuAn1DataBase.getInstance(context).theTapDAO().getTongSoTien(strKhachHangID)-giagoitapcu+TongTienGiaTheTap);
                        }else {
                            theTap.setTongsotiendamuathetap(DuAn1DataBase.getInstance(context).theTapDAO().getTongSoTien(strKhachHangID)+TongTienGiaTheTap);
                        }
                        DuAn1DataBase.getInstance(context).theTapDAO().update(theTap);
                        Toast.makeText(context, "Update thẻ tập thành công", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                        dialog.dismiss();
                    });
                }
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
        private TextView tv_id,tv_hoten,tv_loathe,tv_starttime,tv_endtime,tv_trangthai,tv_homnay,tv_songay;
        private LinearLayout layout_giahan;
        private RelativeLayout layout_update;
        private Button btn_giahan;
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
            layout_giahan = itemView.findViewById(R.id.layout_giahan_thetap);
            btn_giahan = itemView.findViewById(R.id.btn_giahan_thetap);
            layout_update = itemView.findViewById(R.id.layout_update_itemthetap);
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
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    list = listOld;
                }else {
                    List<TheTap> listnew = new ArrayList<>();
                    for (TheTap theTap : listOld){
                        KhachHang khachHang = DuAn1DataBase.getInstance(context).khachHangDAO().checkAcc(theTap.getKhachhang_id()).get(0);
                        if (khachHang.getHoten().toLowerCase().contains(strSearch.toLowerCase())){
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
}
