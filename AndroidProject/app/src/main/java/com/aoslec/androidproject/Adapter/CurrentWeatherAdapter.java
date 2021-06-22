package com.aoslec.androidproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.R;

import java.util.ArrayList;


public class CurrentWeatherAdapter extends BaseAdapter {
    private Context mcontext=null;
    private int layout=0;
    private ArrayList<CurrentWeatherBean> data=null;
    private LayoutInflater inflater=null;

    public CurrentWeatherAdapter(Context mcontext, int layout, ArrayList<CurrentWeatherBean> data) {
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
        return data.get(position).getTimezone();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(this.layout, parent, false);
        TextView list_timezone = convertView.findViewById(R.id.list_timezone);
        TextView list_current_temp = convertView.findViewById(R.id.list_current_temp);
        TextView list_current_time = convertView.findViewById(R.id.list_current_time);
        TextView list_current_description = convertView.findViewById(R.id.list_current_description);
        TextView list_current_main = convertView.findViewById(R.id.list_current_main);
        TextView list_current_id = convertView.findViewById(R.id.list_current_id);
        TextView list_current_feels_like = convertView.findViewById(R.id.list_current_feels_like);
        TextView list_current_uvi = convertView.findViewById(R.id.list_current_uvi);
        TextView list_current_humidity = convertView.findViewById(R.id.list_current_humidity);

        list_timezone.setText("지역 : " + data.get(position).getTimezone());
        list_current_description.setText("상태 : " + data.get(position).getCurrent_description());
        list_current_main.setText("날씨 : " + data.get(position).getCurrent_main());
        list_current_id.setText("id : " + data.get(position).getCurrent_id());
        list_current_feels_like.setText("체감온도 : " + data.get(position).getCurrent_feels_like()+"도");
        list_current_humidity.setText("습도 : " + data.get(position).getCurrent_humidity()+"%");
        list_current_temp.setText("현재온도 : " + data.get(position).getCurrent_temp()+"도");
        list_current_time.setText("현재시간 : " + data.get(position).getCurrent_time()+"도");
        list_current_uvi.setText("자외선 : " + data.get(position).getCurrent_uvi());

        return convertView;
    }
}

