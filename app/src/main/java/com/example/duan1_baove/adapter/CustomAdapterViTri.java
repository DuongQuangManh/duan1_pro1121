package com.example.duan1_baove.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.airbnb.lottie.LottieAnimationView;
import com.example.duan1_baove.R;
import com.example.duan1_baove.model.ViTri;

import java.util.List;

public class CustomAdapterViTri extends ArrayAdapter {
    private Context context;
    private int resource;
    private List<ViTri> objects;
    private LayoutInflater inflater;
    public CustomAdapterViTri(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(resource,null);
            viewHolder.lottieAnimationView = convertView.findViewById(R.id.lottie_itemgridview);
            viewHolder.tv = convertView.findViewById(R.id.tv_name_itemgridview);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ViTri viTri = objects.get(position);
        viewHolder.lottieAnimationView.setAnimation(viTri.getImg());
        viewHolder.tv.setText(viTri.getName());
        return convertView;
    }

    public class ViewHolder {
        LottieAnimationView lottieAnimationView;
        TextView tv;
    }
}
