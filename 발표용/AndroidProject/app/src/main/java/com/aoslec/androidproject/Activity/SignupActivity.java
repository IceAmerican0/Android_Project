package com.aoslec.androidproject.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.ShareVar;
import com.aoslec.androidproject.Util.Counter;
import com.aoslec.androidproject.Util.GMailSender;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends Activity {
    String urlAddr, pinCode;

    Counter mobileCounter = new Counter();

    EditText et_email_signup, et_pw_signup, et_pwcheck_signup,
            et_name_signup, et_phone_signup, et_code_signup;
    Button btn_ok_signup, btn_code_signup, btn_pin_ok_signup;
    TextView codeTimer, tv_pin_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        codeTimer = findViewById(R.id.codecount_signup);
        et_code_signup = findViewById(R.id.et_code_signup);
        et_phone_signup = findViewById(R.id.et_phone_signup);
        et_email_signup = findViewById(R.id.et_Email_signup);
        et_pw_signup = findViewById(R.id.et_Pw_signup);
        et_pwcheck_signup = findViewById(R.id.et_PwCheck_signup);
        et_name_signup = findViewById(R.id.et_name_signup);
        btn_code_signup = findViewById(R.id.btn_pin_signup);
        btn_ok_signup = findViewById(R.id.btn_ok_signup);
        tv_pin_signup = findViewById(R.id.tv_pin_signup);
        btn_pin_ok_signup = findViewById(R.id.btn_pin_ok_signup);

        btn_ok_signup.setOnClickListener(signupAction);
        btn_pin_ok_signup.setOnClickListener(codeAction);
        btn_code_signup.setOnClickListener(codeAction);

        et_phone_signup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                et_code_signup.setText("");
            }
        });
    }
    View.OnClickListener codeAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_pin_signup :

                    String number = et_phone_signup.getText().toString().trim();
                    Log.v("Message", number);
                    String phoneNum = "+82 " + number.substring(1);
                    getPwWithMobile(phoneNum);
                    break;
                case R.id.btn_pin_ok_signup :
                    if (codeTimer.getText() == "00:00"){
                        Toast.makeText(SignupActivity.this, "?????? ????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                    } else {
                        tv_pin_signup.setVisibility(View.INVISIBLE);
                        btn_ok_signup.setVisibility(View.INVISIBLE);
                        btn_ok_signup.setEnabled(false);
                        String userCode = et_code_signup.getText().toString();
                        if (!userCode.equals(pinCode)){
                            tv_pin_signup.setVisibility(View.VISIBLE);
                        }else {
                            Toast.makeText(SignupActivity.this, "????????? ???????????????.",Toast.LENGTH_SHORT).show();
                            btn_ok_signup.setVisibility(View.VISIBLE);
                            btn_ok_signup.setEnabled(true);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    View.OnClickListener signupAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (existCheck("?????????") == true) {
                if (existCheck("????????????") == true) {
                    if (existCheck("??????") == true) {
                        if (existCheck("??????") == true) {
                            if (ruleCheck("?????????") == true) {
                                if (ruleCheck("????????????") == true) {
//                                final ProgressDialog dialog = new ProgressDialog(this);
//                                dialog.setMessage("??????????????? ??????????????????");
//                                dialog.show();
//                                Util.hideKeyboard(Registration.this);
                                    btn_ok_signup.setText("?????????");
                                    btn_ok_signup.setEnabled(false);
                                    String strEmail = et_email_signup.getText().toString().trim();
                                    String strPW = et_pw_signup.getText().toString().trim();
                                    String strName = et_name_signup.getText().toString().trim();
                                    String strPhone = et_phone_signup.getText().toString().trim();
                                    String strImage = "null";

                                    urlAddr = ShareVar.sUrl + "insert_user.jsp?email=" + strEmail + "&pw=" + strPW + "&name=" + strName + "&phone=" + strPhone;
                                    Log.v("Message", urlAddr);
                                    String result = connectInsertData();
                                    if (result.equals("1")) {
                                        Toast.makeText(SignupActivity.this, "??????????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                                        finish(); //?????????????????? ??????
                                    } else {
                                        Toast.makeText(SignupActivity.this, "?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                                    }

                                    /** firebase DB
                                     DbManager dbManager = new DbManager(SignupActivity.this);
                                     int returnint = dbManager.writeNewUser(strName,strID, strPW,strImage);
                                     if (returnint == 0) {
                                     Intent intent = new Intent(SignupActivity.this, SignInActivity.class);
                                     startActivity(intent);
                                     */
//                                }
                                }
                            }
                        }
                    }
                }
            }

        }
    };

    private boolean existCheck(String check) {
        switch (check) {
            case "?????????" :
                if (et_email_signup.length() == 0) {
                et_email_signup.requestFocus();
                    Toast.makeText(SignupActivity.this, "???????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    return true;
                }
            case "????????????" :
                if (7 >= et_pw_signup.length()) {
                    et_pw_signup.requestFocus();
                    Toast.makeText(SignupActivity.this, "??????????????? 8??? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    if (7 >= et_pwcheck_signup.length()) {
                        et_pwcheck_signup.requestFocus();
                        Toast.makeText(SignupActivity.this, "??????????????? 8??? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                        return false;
                    }else {
                        if (!et_pw_signup.getText().toString().equals(et_pwcheck_signup.getText().toString())) {
                            et_pwcheck_signup.requestFocus();
                            Toast.makeText(SignupActivity.this, "??????????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                            return false;
                        } else {
                            return true;
                        }
                    }
                }
            case "??????" :
                if (1 >= et_name_signup.length()) {
                    et_name_signup.requestFocus();
                    Toast.makeText(SignupActivity.this, "????????? ??????????????????", Toast.LENGTH_SHORT).show();
                    return false;
                }else {
                    return true;
                }
            case "??????" :
                if (10 >= et_phone_signup.length() || !et_code_signup.getText().toString().equals(pinCode)) {

                    et_phone_signup.requestFocus();
                    btn_ok_signup.setEnabled(false);
                    Toast.makeText(SignupActivity.this, "????????? ?????? ??????????????????", Toast.LENGTH_SHORT).show();
                    et_code_signup.setText("");
                    return false;
                }else {
                    return true;
                }
        }
        return false;
    };
    private boolean ruleCheck(String check) {
        Pattern pattern;
        Matcher matcher;
        switch (check) {
            case "?????????" :
                pattern = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$");
                matcher = pattern.matcher(et_email_signup.getText().toString());
                if(matcher.find()){
                    //????????? ????????? ?????? ???
                    return true;
                }else{
                    //????????? ????????? ?????? ?????? ???
                    Toast.makeText(SignupActivity.this, "???????????? ????????? ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            case "????????????" :
                //??????, ??????, ???????????? ??? 2?????? ??????(8~15???)
                pattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$");
                matcher = pattern.matcher(et_pw_signup.getText().toString());
                if(matcher.find()){
                    //????????? ????????? ?????? ???
                    return true;
                }else{
                    //????????? ????????? ?????? ?????? ???
                    Toast.makeText(SignupActivity.this, "??????????????? ??????, ??????, ???????????? ??? 2?????? ??????(8~15???)?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    return false;
                }
            default:
                break;
        }
        return false;
    };

    private String connectInsertData() {
        String result = null;
        try {
            User_NT userNT = new User_NT(SignupActivity.this, urlAddr, "insert");
            Object obj = userNT.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    private void getPwWithMobile(String phoneNum) {
        /**
         * @Method Name : getPwWithMobile
         * @????????? : 2021/06/21
         * @????????? : biso
         * @Method ?????? : ??????????????? Pincode??? ????????????.
         * @???????????? :
         * @Param&return : [phoneNum] & void
         */
        mobileCounter.countDownTimer(codeTimer);
        mobileCounter.stopCounter(codeTimer);
        ShareVar.code_context = "signup";
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(SignupActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(SignupActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(SignupActivity.this, "????????? ????????? ????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(SignupActivity.this, "??????(Radio)??? ??????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(SignupActivity.this, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(SignupActivity.this, "SMS ?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(SignupActivity.this, "SMS ?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));
        GMailSender gMailSender = new GMailSender("tunaweather@gmail.com", "tkwhckacl000");
        //????????????
        pinCode=gMailSender.getEmailCode();
        Log.d("Message", "pin code : " + pinCode);
        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(phoneNum, null, "Weather Signup Code : "+pinCode, sentIntent, deliveredIntent);
        et_code_signup.setVisibility(View.VISIBLE);
        codeTimer.setVisibility(View.VISIBLE);
        btn_pin_ok_signup.setVisibility(View.VISIBLE);
        mobileCounter.countDownTimer(codeTimer).start();
    }
    /** ?????? ?????? **/
    private void processIntent(Intent intent){
        if(intent != null){
            // ??????????????? ????????? ???????????? ????????????, ????????????.(???????????? edittext??? ????????? ????????? ????????? ???????????????.)
            String string = intent.getStringExtra("signupCode");
            et_code_signup.setText(string);
        }
    }
    // (2) ?????? ????????? ??????????????? ???????????? ?????? ?????? ?????? ??????
    // (????????? ?????? onCreate()??? ????????? ?????? ????????? ????????? ??????????????? ???????????? ?????? ????????? SMS????????? ????????????!
    @Override
    protected void onNewIntent(Intent intent) {
        processIntent(intent);
        super.onNewIntent(intent);
    }
}