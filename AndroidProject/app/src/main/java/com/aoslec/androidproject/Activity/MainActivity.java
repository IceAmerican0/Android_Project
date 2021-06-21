package com.aoslec.androidproject.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.aoslec.androidproject.Adapter.MainTabAdapter;
import com.aoslec.androidproject.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 pager2;
    MainTabAdapter adapter;
    final static int RValue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("날씨정보");

        //상단 네비
        tabLayout = findViewById(R.id.Main_tabLayout);

        //페이지
        pager2 = findViewById(R.id.Main_viewPager2);

        //페이지연결
        FragmentManager fm = getSupportFragmentManager();
        adapter = new MainTabAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });

    }//c

    //액션바 타이틀 변경 메소드
    public void setActionBarTitle(String title) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflate = getMenuInflater();
        inflate.inflate(R.menu.main_menu, menu);
        return true;
    }

    //메뉴 선택시

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.location:
                Intent intent=new Intent(MainActivity.this,GPSActivity.class);
                startActivityForResult(intent,RValue);
                break;
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case MainActivity.RValue:
                TextView favorite_lat=findViewById(R.id.favorite_lat);
                TextView favorite_long=findViewById(R.id.favorite_long);
                TextView favorite_location=findViewById(R.id.favorite_location);
//                textView.setText("Return Value : "+data.getStringExtra("result"));
                break;
            default:
                break;
        }
    }

}