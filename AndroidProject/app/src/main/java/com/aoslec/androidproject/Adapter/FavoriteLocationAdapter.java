package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.aoslec.androidproject.Bean.FavoriteLocationBean;
import com.aoslec.androidproject.Bean.HourlyWeatherBean;
import com.aoslec.androidproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteLocationAdapter extends RecyclerView.Adapter<FavoriteLocationAdapter.ViewHolder> {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<FavoriteLocationBean> data=null;
    private LayoutInflater inflater=null;

    public FavoriteLocationAdapter(Context mcontext, int layout, ArrayList<FavoriteLocationBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView hourly_time,hourly_temp,hourly_pop;
        public ViewHolder(View convertView){
            super(convertView);
            hourly_time = convertView.findViewById(R.id.Hourly_time);
        }
    }

    @NonNull
    @NotNull
    @Override
    public FavoriteLocationAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hourly_weather,parent,false);
        FavoriteLocationAdapter.ViewHolder viewHolder=new FavoriteLocationAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteLocationAdapter.ViewHolder holder, int position) {
//        holder.hourly_temp.setText("온도 : "+data.get(position).getHourly_temp()+"°");
//        holder.hourly_pop.setText("강수 확률 : "+data.get(position).getHourly_pop()+"%");
//        holder.hourly_time.setText(data.get(position).getHourly_time());
//
//        int id=data.get(position).getHourly_id();


    }

    @Override
    public int getItemCount() {
        return 3;
    }



}

