package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.aoslec.androidproject.Bean.FavoriteHistoryBean;
import com.aoslec.androidproject.Bean.FavoriteLatLongBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<FavoriteLatLongBean> data=null;
    private LayoutInflater inflater=null;

    public FavoriteAdapter(Context mcontext, int layout, ArrayList<FavoriteLatLongBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView favorite_current_location,favorite_current_temp;
        public ImageView favorite_current_heart;
        public LottieAnimationView favorite_laCover;
        public ViewHolder(View convertView){
            super(convertView);
            favorite_current_location=convertView.findViewById(R.id.favorite_current_location);
            favorite_current_temp=convertView.findViewById(R.id.favorite_current_temp);
            favorite_laCover=convertView.findViewById(R.id.favorite_laCover);
            favorite_current_heart=convertView.findViewById(R.id.favorite_current_heart);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position=getAdapterPosition();

                    if(position!=RecyclerView.NO_POSITION) {
                        SaveSharedPreferences.setLat(convertView.getContext(), data.get(position).getLatitude());
                        SaveSharedPreferences.setLong(convertView.getContext(), data.get(position).getLongitude());
                        SaveSharedPreferences.setLocation(convertView.getContext(), data.get(position).getLocation());
                    }

                }
            });
        }
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_current_weather,parent,false);
        FavoriteAdapter.ViewHolder viewHolder=new FavoriteAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.ViewHolder holder, int position) {
        holder.favorite_current_location.setText(data.get(position).getLocation());
        holder.favorite_current_heart.setImageResource(R.drawable.ic_favorite_red);
        holder.favorite_current_temp.setText(Integer.toString(data.get(position).getTemp()));

        int id=data.get(position).getId();

        if(id>=200&&id<=232) holder.favorite_laCover.setAnimation(R.raw.thunder_rain);
        else if(id>=300&&id<=321) holder.favorite_laCover.setAnimation(R.raw.rainy);
        else if(id>=500&&id<=531) holder.favorite_laCover.setAnimation(R.raw.rain);
        else if(id>=600&&id<=622) holder.favorite_laCover.setAnimation(R.raw.snow);
        else if(id==800) holder.favorite_laCover.setAnimation(R.raw.sunny);
        else if(id>=800&&id<=802) holder.favorite_laCover.setAnimation(R.raw.cloudy_sun);
        else if(id>=803) holder.favorite_laCover.setAnimation(R.raw.cloudy);
        else holder.favorite_laCover.setAnimation(R.raw.cloudy);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



}

