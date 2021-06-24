package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aoslec.androidproject.Activity.GPSActivity;
import com.aoslec.androidproject.Bean.FavoriteLocationBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavoriteHistoryAdapter extends RecyclerView.Adapter<FavoriteHistoryAdapter.ViewHolder> {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<FavoriteLocationBean> data=null;
    private LayoutInflater inflater=null;

    public FavoriteHistoryAdapter(Context mcontext, int layout, ArrayList<FavoriteLocationBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView history_location;
        public ImageView history_heart;
        public ViewHolder(View convertView){
            super(convertView);
            history_location=convertView.findViewById(R.id.history_location);
            history_heart=convertView.findViewById(R.id.history_heart);

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
    public FavoriteHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.favorite_history_location,parent,false);
        FavoriteHistoryAdapter.ViewHolder viewHolder=new FavoriteHistoryAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteHistoryAdapter.ViewHolder holder, int position) {
        holder.history_location.setText(data.get(position).getLocation());
        holder.history_heart.setImageResource(R.drawable.ic_favorite);
//        if(data.get(position).getHeart().equals("Y")) holder.history_heart.;
//        else holder.history_heart;
    }

    @Override
    public int getItemCount() {
        if(data.size()>5) return 5;
        else return data.size();
    }



}

