package com.aoslec.androidproject.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aoslec.androidproject.Bean.User;
import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.ShareVar.SaveSharedPreferences;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MyPageActivity extends AppCompatActivity {

    ImageView iv_profile;
    WebView wv_profile;
    TextView tv_name;

    ListView lv_item;

    String urlAddr = null;
    ArrayList<User> user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        iv_profile = findViewById(R.id.iv_icon_maypage);
        wv_profile = findViewById(R.id.wv_icon_maypage);
        tv_name = findViewById(R.id.tv_name_mypage);

        lv_item = findViewById(R.id.lv_item_mypage);

        tv_name.setText(SaveSharedPreferences.getPrefName(MyPageActivity.this));
        //profile 이미지를 동그랗게
        iv_profile.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            iv_profile.setClipToOutline(true);
        }
        if (SaveSharedPreferences.getPrefImage(MyPageActivity.this) != null) {
            Glide.with(this).load(SaveSharedPreferences.getPrefImage(MyPageActivity.this)).into(iv_profile);
        }else {
            Glide.with(this).load(R.drawable.kakao_default_profile_image).into(iv_profile);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        //데이터를 갱신할 수 있는 LIFECYCLE
        connectGetData();
    }
    private  void connectGetData(){
        try{
            User_NT user_nt = new User_NT(MyPageActivity.this, urlAddr, "select");
            Object obj = user_nt.execute().get();
            user = (ArrayList<User>) obj;

//            adapter = new StudentsAdapter(SelectAllActivity.this, R.layout.student_layout, members);
//            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(onItemClickListener);
//            listView.setOnItemLongClickListener(onItemLongClickListener);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}