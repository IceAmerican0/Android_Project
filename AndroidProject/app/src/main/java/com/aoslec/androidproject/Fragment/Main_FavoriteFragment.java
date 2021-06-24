package com.aoslec.androidproject.Fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aoslec.androidproject.Adapter.FavoriteAdapter;
import com.aoslec.androidproject.Adapter.FavoriteHistoryAdapter;
import com.aoslec.androidproject.Bean.CurrentWeatherBean;
import com.aoslec.androidproject.Bean.FavoriteHistoryBean;
import com.aoslec.androidproject.Bean.FavoriteLatLongBean;
import com.aoslec.androidproject.Bean.FavoriteLocationBean;
import com.aoslec.androidproject.NetworkTask.NetworkTask;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.sqLite.FavoriteInfo;

import java.util.ArrayList;

public class Main_FavoriteFragment extends Fragment {
    RecyclerView rvHistory;
    RecyclerView rvFavorite;
    RecyclerView.Adapter favoriteHistoryAdapter,favoriteAdapter;
    RecyclerView.LayoutManager HistoryManager,FavoriteManager;

    ArrayList<FavoriteHistoryBean> favoriteLocations;
    ArrayList<FavoriteLocationBean> favoriteLocationBeans;
    ArrayList<CurrentWeatherBean> current_weathers;
    ArrayList<FavoriteLatLongBean> favoriteLatLongBeans;

    private String location,longitude,latitude,heart;
    private int id;

    SQLiteDatabase DB;
    FavoriteInfo favoriteInfo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_favorite, container, false);

        favoriteInfo=new FavoriteInfo(getContext());
        favoriteLocations= new ArrayList<FavoriteHistoryBean>();
        favoriteLocationBeans=new ArrayList<FavoriteLocationBean>();
        favoriteLatLongBeans=new ArrayList<FavoriteLatLongBean>();

        rvHistory=view.findViewById(R.id.favorite_rvHistory);
        rvFavorite=view.findViewById(R.id.favorite_rvFavorite);

        HistoryManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        FavoriteManager=new LinearLayoutManager(getContext());

        HistoryManager.canScrollHorizontally();

        rvHistory.setLayoutManager(HistoryManager);
        rvFavorite.setLayoutManager(FavoriteManager);

        GetHistoryData();
        GetFavoriteData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        GetHistoryData();
        GetFavoriteData();
    }

    private void GetHistoryData(){
        try{
            DB=favoriteInfo.getWritableDatabase();
            String query="select * from favorite order by id desc;";
            StringBuffer stringBuffer=new StringBuffer();
            Cursor cursor=DB.rawQuery(query,null);
            while(cursor.moveToNext()){
                id=cursor.getInt(0);
                location=cursor.getString(1);
                latitude=cursor.getString(2);
                longitude=cursor.getString(3);
                heart=cursor.getString(4);


                FavoriteHistoryBean favoriteHistoryBean =new FavoriteHistoryBean(id,location,latitude,longitude,heart);
                favoriteLocations.add(favoriteHistoryBean);
            }

            cursor.close();
            favoriteInfo.close();

            favoriteHistoryAdapter=new FavoriteHistoryAdapter(getActivity(),R.layout.favorite_history_location,favoriteLocations);
            rvHistory.setAdapter(favoriteHistoryAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }

    }//GetHistoryData()

    private void GetFavoriteData(){
        try{
            favoriteLocationBeans.clear();
            favoriteLatLongBeans.clear();
            DB=favoriteInfo.getWritableDatabase();
            String query="select * from favorite where heart='Y' order by id desc;";
            Cursor cursor=DB.rawQuery(query,null);
            Log.d("Favorite",cursor.getCount()+"rows found");
            while(cursor.moveToNext()){
                id=cursor.getInt(0);
                location=cursor.getString(1);
                latitude=cursor.getString(2);
                longitude=cursor.getString(3);
                heart=cursor.getString(4);


                FavoriteLocationBean favoriteLocationBean =new FavoriteLocationBean(id,location,latitude,longitude,heart);
                favoriteLocationBeans.add(favoriteLocationBean);
            }

            cursor.close();
            favoriteInfo.close();

            Log.d("Favorite",favoriteLocationBeans.size()+"번 돌아");

            for(int i=0;i<favoriteLocationBeans.size();i++){
                String favoriteLat=favoriteLocationBeans.get(i).getLatitude();
                String favoriteLong=favoriteLocationBeans.get(i).getLongitude();
                String favoriteLocation=favoriteLocationBeans.get(i).getLocation();

                String urlAddr="https://api.openweathermap.org/data/2.5/onecall?lat="+favoriteLat+"&lon="+favoriteLong+"&exclude=minutely&appid=5a19414be68e50e321e070dbbd70b3cf&units=metric ";

                NetworkTask networkTask=new NetworkTask(getActivity(),urlAddr,"current");
                Object obj=networkTask.execute().get();
                current_weathers= (ArrayList<CurrentWeatherBean>) obj;

                int favoriteCurrentTemp=current_weathers.get(0).getCurrent_temp();
                int favoriteCurrentId=current_weathers.get(0).getCurrent_id();

                FavoriteLatLongBean favoriteLatLongBean=new FavoriteLatLongBean(favoriteLat,favoriteLong,favoriteLocation,favoriteCurrentTemp,favoriteCurrentId);
                favoriteLatLongBeans.add(favoriteLatLongBean);
            }

            favoriteAdapter=new FavoriteAdapter(getActivity(),R.layout.recycler_current_weather,favoriteLatLongBeans);
            rvFavorite.setAdapter(favoriteAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}