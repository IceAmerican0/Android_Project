package com.aoslec.androidproject.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
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
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;

import java.util.ArrayList;


public class Main_WeatherFragment extends Fragment {
    RecyclerView rvHourWeather;
    RecyclerView rvDailyWeather;
    RecyclerView.Adapter hourlyAdapter;
    RecyclerView.Adapter dailyAdapter;
    RecyclerView.LayoutManager hourlylayoutManager,dailylayoutManager;
    LinearLayout main_GPS;


    ArrayList<CurrentWeatherBean> current_weathers;
    ArrayList<HourlyWeatherBean> hourly_weathers;
    ArrayList<DailyWeatherBean> daily_weathers;

    CurrentWeatherBean current_weather;

    TextView main_tvTemp,main_tvLocation;
    LottieAnimationView main_laCover;

    private String Lat="";
    private String Long="";
    private String Location="";

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_weather, container, false);

        rvHourWeather=view.findViewById(R.id.main_rvHourWeather);
        rvDailyWeather=view.findViewById(R.id.main_rvDailyWeather);

        hourlylayoutManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        hourlylayoutManager.canScrollHorizontally();
        dailylayoutManager=new LinearLayoutManager(getContext());

        rvHourWeather.setLayoutManager(hourlylayoutManager);
        rvDailyWeather.setLayoutManager(dailylayoutManager);
        main_tvTemp=view.findViewById(R.id.main_tvTemp);
        main_tvLocation=view.findViewById(R.id.main_tvLocation);
        main_laCover=view.findViewById(R.id.main_laCover);
        main_GPS=view.findViewById(R.id.main_GPS);

        Long=SaveSharedPreferences.getLong(getContext());
        Lat=SaveSharedPreferences.getLat(getContext());
        Location=SaveSharedPreferences.getLocation(getContext());

        GetCurrentData();
        GetHourlyData();
        GetDailyData();

        main_GPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), GPSActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        Long=SaveSharedPreferences.getLong(getContext());
        Lat=SaveSharedPreferences.getLat(getContext());
        Location=SaveSharedPreferences.getLocation(getContext());

        GetCurrentData();
        GetHourlyData();
        GetDailyData();
    }

    private void GetDailyData(){
        try{
            String urlAddr="https://api.openweathermap.org/data/2.5/onecall?lat="+Lat+"&lon="+Long+"&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";

            NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"daily");
            Object obj=networkTask.execute().get();
            daily_weathers= (ArrayList<DailyWeatherBean>) obj;

            dailyAdapter=new DailyWeatherAdapter(getActivity(),R.layout.recycler_day_weather,daily_weathers);
            rvDailyWeather.setAdapter(dailyAdapter);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void GetHourlyData(){
        try{
            String urlAddr="https://api.openweathermap.org/data/2.5/onecall?lat="+Lat+"&lon="+Long+"&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";

            NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"hourly");
            Object obj=networkTask.execute().get();
            hourly_weathers= (ArrayList<HourlyWeatherBean>) obj;

            hourlyAdapter=new HourlyWeatherAdapter(getActivity(),R.layout.recycler_hourly_weather,hourly_weathers);
            rvHourWeather.setAdapter(hourlyAdapter);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void GetCurrentData(){
        main_tvTemp=getActivity().findViewById(R.id.main_tvTemp);
        main_tvLocation=getActivity().findViewById(R.id.main_tvLocation);
        main_laCover=getActivity().findViewById(R.id.main_laCover);
        try{
            String urlAddr="https://api.openweathermap.org/data/2.5/onecall?lat="+Lat+"&lon="+Long+"&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";

            Log.d("main_weather",urlAddr);

            NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"current");
            Object obj=networkTask.execute().get();
            current_weathers= (ArrayList<CurrentWeatherBean>) obj;

           current_weather= current_weathers.get(0);

            main_tvTemp.setText(Integer.toString(current_weather.getCurrent_temp())+"°");
            main_tvLocation.setText(Location);

            int id=current_weather.getCurrent_id();

            if(id>=200&&id<=232) main_laCover.setAnimation(R.raw.thunder_rain);
            else if(id>=300&&id<=321) main_laCover.setAnimation(R.raw.rainy);
            else if(id>=500&&id<=531) main_laCover.setAnimation(R.raw.rain);
            else if(id>=600&&id<=622) main_laCover.setAnimation(R.raw.snow);
            else if(id==800) main_laCover.setAnimation(R.raw.sunny);
            else if(id>=800&&id<=802) main_laCover.setAnimation(R.raw.cloudy_sun);
            else if(id>=803) main_laCover.setAnimation(R.raw.cloudy);
            else main_laCover.setAnimation(R.raw.cloudy);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}