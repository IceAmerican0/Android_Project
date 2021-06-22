package com.aoslec.androidproject.Util;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.aoslec.androidproject.Activity.IdPwActivity;
import com.aoslec.androidproject.Activity.SignupActivity;

public class RedirectActivity extends Activity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        intent.setClass(RedirectActivity.this, IdPwActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

}