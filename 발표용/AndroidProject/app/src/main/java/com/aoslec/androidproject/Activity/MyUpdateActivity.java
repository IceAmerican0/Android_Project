package com.aoslec.androidproject.Activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.Share.SaveSharedPreferences;
import com.aoslec.androidproject.Share.ShareVar;
import com.aoslec.androidproject.Util.GMailSender;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.kakao.network.ApiErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyUpdateActivity extends Activity {

    GoogleSignInClient mGoogleSignInClient;

    EditText nowPw, newPw1, newPw2, newname, newphone;

    Button signout, pwChange, namebutton, phonebutton;

    TextView namecontent, nametitle, namehint, phonecontent, phonetitle, phonehint;

    String pinCode, newPhoneNum;

    int count = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_update);

        if (SaveSharedPreferences.getLoginMethod(MyUpdateActivity.this).equals("")) {
        } else {
            Toast.makeText(MyUpdateActivity.this, "?????? ????????? ?????? ????????? ???????????????.", Toast.LENGTH_SHORT).show();
        }

        nowPw = findViewById(R.id.et_pw_myupdate);
        newPw1 = findViewById(R.id.et_newpw1_myupdate);
        newPw2 = findViewById(R.id.et_newpw2_myupdate);
        newname = findViewById(R.id.et_new_name_myupdate);
        newphone = findViewById(R.id.et_new_phone_myupdate);

        nametitle = findViewById(R.id.tv_title_name_myupdate);
        namecontent = findViewById(R.id.tv_content_name_myupdate);
        namehint = findViewById(R.id.tv_hint_name_myupdate);

        phonetitle = findViewById(R.id.tv_title_phone_myupdate);
        phonecontent = findViewById(R.id.tv_content_phone_myupdate);
        phonehint = findViewById(R.id.tv_hint_phone_myupdate);

        signout = findViewById(R.id.btn_signout_myupdate);
        pwChange = findViewById(R.id.btn_changepw_myupdate);
        namebutton = findViewById(R.id.btn_change_name_myupdate);
        phonebutton = findViewById(R.id.btn_change_phone_myupdate);

        nametitle.setText("?????? ??????");
        namebutton.setText("?????? ????????????");
        newname.setVisibility(View.GONE);
        phonebutton.setText("?????? ????????????");
        phonetitle.setText("????????? ??????");
        phonehint.setText("?????? ????????? ????????? ?????? ????????? ???????????????.");


        namebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeDialog("??????");
            }
        });
        phonebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newPhoneNum = newphone.getText().toString().trim();
                getPwWithMobile(newPhoneNum);
            }
        });
        pwChange.setOnClickListener(onClickListener);
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.v("","??????");
//                AlertDialog.Builder builder = new AlertDialog.Builder(MyUpdateActivity.this);
//                builder.setTitle("?????? ?????????????????????????");
//                builder.setMessage("???????????? ???????????? ???????????? ????????????!");
//                builder.setNegativeButton("????????????", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
                switch (SaveSharedPreferences.getLoginMethod(MyUpdateActivity.this)) {
                    case "Kakao" :
                        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                            @Override
                            public void onFailure(ErrorResult errorResult) { //???????????? ?????? ???
                                int result = errorResult.getErrorCode();
                                if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                    Toast.makeText(MyUpdateActivity.this, "???????????? ????????? ??????????????????. ?????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(MyUpdateActivity.this, "??????????????? ??????????????????. ?????? ????????? ?????????.", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void onSessionClosed(ErrorResult errorResult) { //????????? ????????? ???????????? ???
                                //?????? ????????????????????? Toast ???????????? ????????? ????????? ????????? ?????????
                                Toast.makeText(MyUpdateActivity.this, "????????? ????????? ???????????????. ?????? ???????????? ?????????.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
                                startActivity(intent);
                            }
                            @Override
                            public void onNotSignedUp() { //???????????? ?????? ???????????? ???????????? ?????? ???
                                //???????????? ?????? ??????????????? Toast ???????????? ????????? ????????? ????????? ?????????
                                Toast.makeText(MyUpdateActivity.this, "???????????? ?????? ???????????????. ?????? ???????????? ?????????.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
                                startActivity(intent);
                            }
                            @Override
                            public void onSuccess(Long result) { //??????????????? ????????????
                                //"??????????????? ??????????????????."?????? Toast ???????????? ????????? ????????? ????????? ?????????
                                SaveSharedPreferences.clearAllData(MyUpdateActivity.this);
                                Toast.makeText(MyUpdateActivity.this, "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
                                startActivity(intent);
                            }
                        });
                        break;
                    case "Google" :
                        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build();
                        mGoogleSignInClient = GoogleSignIn.getClient(MyUpdateActivity.this, gso);
                        GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(MyUpdateActivity.this);
                        mGoogleSignInClient.revokeAccess();
                        SaveSharedPreferences.clearAllData(MyUpdateActivity.this);
                        break;
                    case "" :
                        String urlAddr = ShareVar.sUrl + "update_outdate_user.jsp?email=" + SaveSharedPreferences.getPrefEmail(MyUpdateActivity.this);
                        Log.v("Message", urlAddr);
                        String result = connectUpdateData(urlAddr);
                        if (result.equals("1")) {
                            SaveSharedPreferences.setPrefAutoLogin(MyUpdateActivity.this, "n");
                            SaveSharedPreferences.setPrefIsLogin(MyUpdateActivity.this,"n");
                            SaveSharedPreferences.setFirstVisitUser(MyUpdateActivity.this,"y");
                            SaveSharedPreferences.setPrefPhone(MyUpdateActivity.this, "");
                            SaveSharedPreferences.setPrefEmail(MyUpdateActivity.this, "");
                            SaveSharedPreferences.setPrefName(MyUpdateActivity.this, "");
                            SaveSharedPreferences.setPrefImage(MyUpdateActivity.this, "");
                            SaveSharedPreferences.setPrefPw(MyUpdateActivity.this, "");
                            Toast.makeText(MyUpdateActivity.this, "??????????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
                            SaveSharedPreferences.clearAllData(MyUpdateActivity.this);
                            Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
                            startActivity(intent);
                            MyUpdateActivity.this.finish();
                        } else {
                            Toast.makeText(MyUpdateActivity.this, "?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    default:
                        break;
                }
                Intent intent = new Intent(MyUpdateActivity.this, SignInActivity.class);
                startActivity(intent);
//                    }
//                });
//                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        namecontent.setText(SaveSharedPreferences.getPrefName(MyUpdateActivity.this));
        phonecontent.setText(SaveSharedPreferences.getPrefPhone(MyUpdateActivity.this));
        namehint.setVisibility(View.GONE);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(newPw1.getText().toString().trim()==null||nowPw.getText().toString().trim()==null) {
                Toast.makeText(MyUpdateActivity.this, "??????????????? ??????????????????.", Toast.LENGTH_SHORT).show();
            }else {
                if (SaveSharedPreferences.getPrefPw(MyUpdateActivity.this).equals(nowPw.getText().toString().trim())) {
                    if (7 >= newPw1.getText().toString().trim().length()) {
                        Toast.makeText(MyUpdateActivity.this, "??????????????? ?????? 8??? ?????????.", Toast.LENGTH_SHORT).show();
                        newPw1.requestFocus();
                    } else {
                        if (newPw1.getText().toString().trim().equals(newPw2.getText().toString().trim())) {
                            checkValidation(newPw1.getText().toString().trim());
                        } else {
                            Toast.makeText(MyUpdateActivity.this, "??????????????? ?????? ????????????.", Toast.LENGTH_SHORT).show();
                            newPw2.requestFocus();
                        }
                    }
                } else {
                    Toast.makeText(MyUpdateActivity.this, "?????? ??????????????? ???????????????.", Toast.LENGTH_SHORT).show();
                    nowPw.requestFocus();
                }
            }
        }
    };
    private void checkValidation(String new1){
        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#$%^&*])(?=.*[0-9!@#$%^&*]).{8,15}$");
        Matcher matcher = pattern.matcher(new1);
        if (!matcher.find()) {
            Toast.makeText(MyUpdateActivity.this, "???????????? ????????? ??????????????????", Toast.LENGTH_SHORT).show();
        }else {
            passwordChange(new1);
        }
    }
    private void passwordChange(String new1) {
        String urlAddr = ShareVar.sUrl + "update_pw_user.jsp?email=" + SaveSharedPreferences.getPrefEmail(MyUpdateActivity.this) + "&pw="+new1;
        Log.v("Message", urlAddr);
        String result = connectUpdateData(urlAddr);
        if (result.equals("1")) {
            SaveSharedPreferences.setPrefAutoLogin(MyUpdateActivity.this, "n");
            SaveSharedPreferences.setPrefPw(MyUpdateActivity.this, new1);
            Toast.makeText(MyUpdateActivity.this, "??????????????? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MyUpdateActivity.this, "?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
        }
    }
    private String connectUpdateData(String urlAddr) {
        String result = null;
        try {
            User_NT userNT = new User_NT(MyUpdateActivity.this, urlAddr, "update");
            Object obj = userNT.execute().get();
            result = (String) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private AlertDialog makeDialog(String title) {
        final EditText et = new EditText(MyUpdateActivity.this);

        if (title.equals("??????")) {
            et.setHint("???????????? ????????? ??????????????????!");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyUpdateActivity.this);
            builder.setTitle(title+" ??????");
            builder.setMessage(title+"??? ?????????????????????????");
            builder.setView(et);
            builder.setPositiveButton("???", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String value = et.getText().toString();
                    updateUserData(title, value);
                    dialog.dismiss();
                }
            });
            builder.setNeutralButton("?????????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }

            });
            AlertDialog alertDialog = builder.show();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    alertDialog.getButton(AlertDialog.BUTTON1).setTextColor(Color.BLACK);
                    alertDialog.getButton(AlertDialog.BUTTON3).setTextColor(Color.rgb(245, 127, 23));
                }
            });

            return alertDialog;
        }else {
            et.setHint("????????? ??????????????????!");
            AlertDialog.Builder builder = new AlertDialog.Builder(MyUpdateActivity.this);
            builder.setTitle("????????? ?????? ??????");
            builder.setMessage(title);
            builder.setView(et);
            builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String value = et.getText().toString().trim();
                    if (pinCode.equals(value)) {
                        updateUserData("???????????????", newPhoneNum);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(MyUpdateActivity.this, "????????? ???????????? ????????????.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("?????? ????????????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getPwWithMobile(newphone.getText().toString().trim());
                }
            });
            builder.setNeutralButton("?????????", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }

            });
            AlertDialog alertDialog = builder.show();
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    alertDialog.getButton(AlertDialog.BUTTON1).setTextColor(Color.BLACK);
                    alertDialog.getButton(AlertDialog.BUTTON3).setTextColor(Color.rgb(245, 127, 23));
                }
            });

            return alertDialog;
        }
//        et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//        et.setTransformationMethod(PasswordTransformationMethod.getInstance());

    }
    private void updateUserData(String title, String value) {
        String key = "";
        switch (title) {
            case "??????" :
                title = "name";
                break;
            case "???????????????" :
                title = "phone";
                break;
        }
        String urlAddr = ShareVar.sUrl + "update_user.jsp?"+"email="+ SaveSharedPreferences.getPrefEmail(MyUpdateActivity.this)+"&"+title+"="+value;

        Log.v("Message",urlAddr);
        String result = null;
        try {
            User_NT networkTask = new User_NT(MyUpdateActivity.this, urlAddr, "update");
            Object obj = networkTask.execute().get();
            result = (String) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
        if(result.equals("1")) {
            Toast.makeText(MyUpdateActivity.this, value+"??? ?????? ???????????????.", Toast.LENGTH_SHORT).show();
            switch (title) {
                case "??????" :
                    SaveSharedPreferences.setPrefName(MyUpdateActivity.this, value);
                    break;
                case "????????? ??????" :
                    SaveSharedPreferences.setPrefPhone(MyUpdateActivity.this, value);
                    break;
            }
        }else {
            Toast.makeText(MyUpdateActivity.this, "?????? ?????????????????????.", Toast.LENGTH_SHORT).show();
        }
        onResume();
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
        count ++;
        ShareVar.code_context = "find";
        PendingIntent sentIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_SENT_ACTION"), 0);
        PendingIntent deliveredIntent = PendingIntent.getBroadcast(this, 0, new Intent("SMS_DELIVERED_ACTION"), 0);

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch(getResultCode()){
                    case Activity.RESULT_OK:
                        Toast.makeText(MyUpdateActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(MyUpdateActivity.this, "?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(MyUpdateActivity.this, "????????? ????????? ????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(MyUpdateActivity.this, "??????(Radio)??? ??????????????????", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(MyUpdateActivity.this, "PDU Null", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_SENT_ACTION"));

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(MyUpdateActivity.this, "SMS ?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(MyUpdateActivity.this, "SMS ?????? ??????", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter("SMS_DELIVERED_ACTION"));
        GMailSender gMailSender = new GMailSender("tunaweather@gmail.com", "tkwhckacl000");
        //????????????
        pinCode=gMailSender.getEmailCode();
        SmsManager mSmsManager = SmsManager.getDefault();
        mSmsManager.sendTextMessage(phoneNum, null, "Weather Find Password Code : "+pinCode, sentIntent, deliveredIntent);
        if (count == 1) {
            makeDialog("????????? ???????????????!");
        }
    }

}