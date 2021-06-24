package com.aoslec.androidproject.Fragment;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.aoslec.androidproject.Adapter.FavoriteHistoryAdapter;
import com.aoslec.androidproject.Bean.FavoriteLocationBean;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.sqLite.FavoriteInfo;

import java.util.ArrayList;

public class Main_FavoriteFragment extends Fragment {
    RecyclerView rvHistory;
    RecyclerView.Adapter favoriteHistoryAdapter;
    RecyclerView.LayoutManager HistoryManager;

    ArrayList<FavoriteLocationBean> favoriteLocations;

    private String location,longitude,latitude,heart;
    private int id;

    SQLiteDatabase DB;
    FavoriteInfo favoriteInfo;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_favorite, container, false);

        favoriteInfo=new FavoriteInfo(getContext());
        favoriteLocations= new ArrayList<FavoriteLocationBean>();

        rvHistory=view.findViewById(R.id.favorite_rvHistory);

        HistoryManager=new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        HistoryManager.canScrollHorizontally();

        rvHistory.setLayoutManager(HistoryManager);

        GetHistoryData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        GetHistoryData();
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


                FavoriteLocationBean favoriteLocationBean=new FavoriteLocationBean(id,location,latitude,longitude,heart);
                favoriteLocations.add(favoriteLocationBean);
            }

            cursor.close();
            favoriteInfo.close();

            favoriteHistoryAdapter=new FavoriteHistoryAdapter(getActivity(),R.layout.favorite_history_location,favoriteLocations);
            rvHistory.setAdapter(favoriteHistoryAdapter);
        }catch(Exception e){
            e.printStackTrace();
        }

    }//GetHistoryData()

}