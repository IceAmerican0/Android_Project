package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.androidproject.Bean.HourlyWeatherBean;
import com.aoslec.androidproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<HourlyWeatherBean> data=null;
    private LayoutInflater inflater=null;

    public HourlyWeatherAdapter(Context mcontext, int layout, ArrayList<HourlyWeatherBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView hourly_time,hourly_temp,hourly_pop;
        public int hourly_id;
        public ViewHolder(View convertView){
            super(convertView);
            hourly_time = convertView.findViewById(R.id.Hourly_time);
            hourly_temp=convertView.findViewById(R.id.Hourly_temp);
            hourly_pop=convertView.findViewById(R.id.Hourly_pop);
        }
    }

    @NonNull
    @NotNull
    @Override
    public HourlyWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hourly_weather,parent,false);
        ViewHolder viewHolder=new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull HourlyWeatherAdapter.ViewHolder holder, int position) {
       holder.hourly_temp.setText(data.get(position).getHourly_temp()+"°");
       holder.hourly_pop.setText(data.get(position).getHourly_pop()+"°");
       holder.hourly_time.setText(data.get(position).getHourly_time());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}

