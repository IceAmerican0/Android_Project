package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.Bean.HourlyWeatherBean;
import com.aoslec.androidproject.R;

import java.util.ArrayList;



public class HourlyWeatherAdapter extends BaseAdapter {
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

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getHourly_id();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(this.layout, parent, false);

        TextView list_hourly_id = convertView.findViewById(R.id.list_hourly_id);
        TextView list_hourly_description = convertView.findViewById(R.id.list_hourly_description);
        TextView list_hourly_feels_like = convertView.findViewById(R.id.list_hourly_feels_like);
        TextView list_hourly_humidity = convertView.findViewById(R.id.list_hourly_humidity);
        TextView list_hourly_main = convertView.findViewById(R.id.list_hourly_main);
        TextView list_hourly_pop = convertView.findViewById(R.id.list_hourly_pop);
        TextView list_hourly_temp = convertView.findViewById(R.id.list_hourly_temp);
        TextView list_hourly_time = convertView.findViewById(R.id.list_hourly_time);
        TextView list_hourly_uvi = convertView.findViewById(R.id.list_hourly_uvi);

        list_hourly_pop.setText("강수확률 : " + data.get(position).getHourly_pop());
        list_hourly_description.setText("상태 : " + data.get(position).getHourly_description());
        list_hourly_main.setText("날씨 : " + data.get(position).getHourly_main());
        list_hourly_id.setText("id : " + data.get(position).getHourly_id());
        list_hourly_feels_like.setText("체감온도 : " + data.get(position).getHourly_feels_like()+"도");
        list_hourly_humidity.setText("습도 : " + data.get(position).getHourly_humidity()+"%");
        list_hourly_temp.setText("현재온도 : " + data.get(position).getHourly_temp()+"도");
        list_hourly_time.setText("현재시간 : " + data.get(position).getHourly_time());
        list_hourly_uvi.setText("자외선 : " + data.get(position).getHourly_uvi());

        return convertView;
    }
}

