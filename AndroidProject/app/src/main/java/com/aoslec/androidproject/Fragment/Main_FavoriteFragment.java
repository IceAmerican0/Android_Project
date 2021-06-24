package com.aoslec.androidproject.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.androidproject.Activity.GPSActivity;
import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.Adapter.CurrentWeatherAdapter;
import com.aoslec.androidproject.Adapter.DailyWeatherAdapter;
import com.aoslec.androidproject.Adapter.HourlyWeatherAdapter;
import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.Bean.DailyWeatherBean;
import com.aoslec.androidproject.Bean.FavoriteLocationBean;
import com.aoslec.androidproject.Bean.HourlyWeatherBean;
import com.aoslec.androidproject.NetworkTask.NetworkTask;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.SaveSharedPreferences.SaveSharedPreferences;
import com.aoslec.androidproject.sqLite.FavoriteInfo;

import java.util.ArrayList;

public class Main_FavoriteFragment extends Fragment {

    FavoriteInfo favoriteInfo;
    SQLiteDatabase DB;
    ArrayList<FavoriteLocationBean> favoriteLocations;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_favorite, container, false);

        connectGetData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        connectGetData();
    }

    private void connectGetData(){
        try{
            DB=favoriteInfo.getWritableDatabase();
            String query="select * from favorite order by id desc;";
            StringBuffer stringBuffer=new StringBuffer();
            Cursor cursor=DB.rawQuery(query,null);
            while(cursor.moveToNext()){
                int id=cursor.getInt(0);
                String location=cursor.getString(1);
                String latitude=cursor.getString(2);
                String longitude=cursor.getString(3);
                String heart=cursor.getString(4);

                FavoriteLocationBean favoriteLocationBean=new FavoriteLocationBean(id,location,latitude,longitude,heart);
                favoriteLocations.add(favoriteLocationBean);
            }

            cursor.close();
            favoriteInfo.close();

            Toast.makeText(getContext(),"Select OK!",Toast.LENGTH_SHORT).show();
        }catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getContext(),"Select Error!",Toast.LENGTH_SHORT).show();
        }
    }

}