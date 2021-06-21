package com.aoslec.androidproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.aoslec.androidproject.Activity.GPSActivity;
import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.Adapter.CurrentWeatherAdapter;
import com.aoslec.androidproject.Adapter.DailyWeatherAdapter;
import com.aoslec.androidproject.Adapter.HourlyWeatherAdapter;
import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.Bean.DailyWeatherBean;
import com.aoslec.androidproject.Bean.HourlyWeatherBean;
import com.aoslec.androidproject.NetworkTask.NetworkTask;
import com.aoslec.androidproject.R;

import java.util.ArrayList;

public class Main_FavoriteFragment extends Fragment {

    ArrayList<CurrentWeatherBean> current_weathers;
    ArrayList<HourlyWeatherBean> hourly_weathers;
    ArrayList<DailyWeatherBean> daily_weathers;
    DailyWeatherAdapter adapter;
    ListView listView;
    Context context=null;

    String urlAddr=" https://api.openweathermap.org/data/2.5/onecall?lat=37.5642135&lon=127.0016985&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_favorite, container, false);



        listView=view.findViewById(R.id.lv_weatherList);
        connectGetData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //액션바 타이틀 변경
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("관심지역");
        }
        connectGetData();
    }

    private void connectGetData(){
        try{
            NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"daily");
            Object obj=networkTask.execute().get();
            daily_weathers= (ArrayList<DailyWeatherBean>) obj;

            adapter=new DailyWeatherAdapter(getContext(),R.layout.daily_weather_list,daily_weathers);
            listView.setAdapter(adapter);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}