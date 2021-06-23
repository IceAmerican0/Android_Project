package com.aoslec.androidproject.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.Adapter.CurrentWeatherAdapter;
import com.aoslec.androidproject.Adapter.DailyWeatherAdapter;
import com.aoslec.androidproject.Adapter.HourlyWeatherAdapter;
import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.Bean.DailyWeatherBean;
import com.aoslec.androidproject.Bean.HourlyWeatherBean;
import com.aoslec.androidproject.NetworkTask.NetworkTask;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;

import java.util.ArrayList;


public class Main_WeatherFragment extends Fragment {
    RecyclerView rvHourWeather,rvDayWeather;
    RecyclerView.Adapter adapter;

    ArrayList<CurrentWeatherBean> current_weathers;
    ArrayList<HourlyWeatherBean> hourly_weathers;
    ArrayList<DailyWeatherBean> daily_weathers;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_weather, container, false);

        rvHourWeather=view.findViewById(R.id.main_rvHourWeather);
//        rvDayWeather=view.findViewById(R.id.main_rvDayWeather);

        GetHourlyData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //액션바 타이틀 변경
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle(getResources().getString(R.string.weather_title));
        }

        GetHourlyData();
    }


    private void GetHourlyData(){
        try{
            String Lat=SaveSharedPreferences.getLocation(getContext());
            String Long=SaveSharedPreferences.getLat(getContext());

            String urlAddr="https://api.openweathermap.org/data/2.5/onecall?lat="+Lat+"&lon="+Long+"&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";

            NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"hourly");
            Object obj=networkTask.execute().get();
            hourly_weathers= (ArrayList<HourlyWeatherBean>) obj;

            adapter=new HourlyWeatherAdapter(getActivity(),R.layout.recycler_hourly_weather,hourly_weathers);
            rvHourWeather.setAdapter(adapter);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}