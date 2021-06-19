package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.Bean.DailyWeatherBean;
import com.aoslec.androidproject.R;

import java.util.ArrayList;



public class DailyWeatherAdapter extends BaseAdapter {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<DailyWeatherBean> data=null;
    private LayoutInflater inflater=null;

    public DailyWeatherAdapter(Context mcontext, int layout, ArrayList<DailyWeatherBean> data) {
        this.mcontext = mcontext;
        this.layout = layout;
        this.data = data;
        this.inflater= (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getDaily_id();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(this.layout, parent, false);

        TextView list_daily_id = convertView.findViewById(R.id.list_daily_id);
        TextView list_daily_description = convertView.findViewById(R.id.list_daily_description);
        TextView list_daily_feels_like = convertView.findViewById(R.id.list_daily_feels_like);
        TextView list_daily_humidity = convertView.findViewById(R.id.list_daily_humidity);
        TextView list_daily_main = convertView.findViewById(R.id.list_daily_main);
        TextView list_daily_pop = convertView.findViewById(R.id.list_daily_pop);
        TextView list_daily_temp_min = convertView.findViewById(R.id.list_daily_temp_max);
        TextView list_daily_temp_max = convertView.findViewById(R.id.list_daily_temp_min);
        TextView list_daily_time = convertView.findViewById(R.id.list_daily_time);
        TextView list_daily_uvi = convertView.findViewById(R.id.list_daily_uvi);



        list_daily_pop.setText("강수확률 : " + data.get(position).getDaily_pop()+"%");
        list_daily_description.setText("상태 : " + data.get(position).getDaily_description());
        list_daily_main.setText("날씨 : " + data.get(position).getDaily_main());
        list_daily_id.setText("id : " + data.get(position).getDaily_id());
        list_daily_feels_like.setText("체감온도 : " + data.get(position).getDaily_feels_like()+"도");
        list_daily_humidity.setText("습도 : " + data.get(position).getDaily_humidity()+"%");
        list_daily_temp_max.setText("최고온도 : " + data.get(position).getDaily_temp_max()+"도");
        list_daily_temp_min.setText("최저온도 : " + data.get(position).getDaily_temp_min()+"도");
        list_daily_time.setText("날짜 : " + data.get(position).getDaily_time());
        list_daily_uvi.setText("자외선 : " + data.get(position).getDaily_uvi());

        return convertView;
    }
}

