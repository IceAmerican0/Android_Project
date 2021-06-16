package com.aoslec.androidproject.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.Adapter.WeatherAdapter;
import com.aoslec.androidproject.Bean.WeatherBean;
import com.aoslec.androidproject.NetworkTask.NetworkTask;
import com.aoslec.androidproject.R;

import java.util.ArrayList;

public class Main_FavoriteFragment extends Fragment {

    ArrayList<WeatherBean> weathers;
    WeatherAdapter adapter;
    ListView listView;

    String urlAddr="https://api.aerisapi.com/forecasts/seoul,korea?format=json&filter=3hr&limit=10&client_id=xUgGFiLRB82Ifc82XCGmi&client_secret=hKjZZ8ZKuC7ro0QFGCoWKyL9yhORoHKi9D4JdLCz";


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
            NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"select");
            Object obj=networkTask.execute().get();
            weathers= (ArrayList<WeatherBean>) obj;

            adapter=new WeatherAdapter(getContext(),R.layout.weather_list,weathers);
            listView.setAdapter(adapter);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

}