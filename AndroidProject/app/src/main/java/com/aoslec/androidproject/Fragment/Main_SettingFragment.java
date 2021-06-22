package com.aoslec.androidproject.Fragment;

import android.content.Intent;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aoslec.androidproject.Activity.MainActivity;
import com.aoslec.androidproject.Activity.MyPageActivity;
import com.aoslec.androidproject.Activity.Pay_1_Activity;
import com.aoslec.androidproject.Activity.SignInActivity;
import com.aoslec.androidproject.GoogleMap.MapActivity;
import com.aoslec.androidproject.NetworkTask.User_NT;
import com.aoslec.androidproject.R;
import com.aoslec.androidproject.ShareVar.SaveSharedPreferences;
import com.aoslec.androidproject.ShareVar.ShareVar;
import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.kakao.network.ApiErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;


public class Main_SettingFragment extends Fragment {

    private FirebaseAuth mAuth ;
    GoogleSignInClient mGoogleSignInClient;

    FrameLayout layout_icon_list;
    Button sign_out_btn, logout_btn;
    Button btn_intent_login, btn_intent_map, btn_intent_pay;
    ImageView profile;
    TextView name;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_setting, container, false);

        sign_out_btn = view.findViewById(R.id.sign_out_button);
        logout_btn = view.findViewById(R.id.logout_button);
        btn_intent_pay = view.findViewById(R.id.pay_page_btn);
        btn_intent_map = view.findViewById(R.id.map_page_btn);
        btn_intent_login = view.findViewById(R.id.login_page_btn);
        layout_icon_list = view.findViewById(R.id.layout_icon_list);
        profile = view.findViewById(R.id.iv_icon_setting);
        name = view.findViewById(R.id.tv_name_setting);

        btn_intent_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Pay_1_Activity.class);
                startActivity(intent);
            }
        });
        btn_intent_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });
        btn_intent_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SignInActivity.class);
                startActivity(intent);
            }
        });
        layout_icon_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPageActivity.class);
                startActivity(intent);
            }
        });
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyPageActivity.class);
                startActivity(intent);
            }
        });

//        email = view.findViewById(R.id.tv_email_setting);

        //profile 이미지를 동그랗게
        profile.setBackground(new ShapeDrawable(new OvalShape()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            profile.setClipToOutline(true);
        }
        if (SaveSharedPreferences.getPrefImage(getContext()) != null) {
            Glide.with(this).load(SaveSharedPreferences.getPrefImage(getContext())).into(profile);
        }else {
            Glide.with(this).load(R.drawable.kakao_default_profile_image).into(profile);
        }
        if (SaveSharedPreferences.getPrefEmail(getContext()) != null) {
            sign_out_btn.setVisibility(View.VISIBLE);
            logout_btn.setVisibility(View.VISIBLE);
        }

        name.setText(SaveSharedPreferences.getPrefName(getContext())+">");
//        email.setText(SaveSharedPreferences.getPrefEmail(getContext()));

        logout_btn.setOnClickListener(logoutAction);
        sign_out_btn.setOnClickListener(signoutAction);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //액션바 타이틀 변경
        FragmentActivity activity = getActivity();
        if (activity != null) {
            ((MainActivity) activity).setActionBarTitle("설정");
            // getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Setting");
        }
    }
    View.OnClickListener logoutAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (SaveSharedPreferences.getLoginMethod(getContext())) {
                case "Kakao" :
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            SaveSharedPreferences.setPrefIsLogin(getActivity(), "n");
                            SaveSharedPreferences.setPrefAutoLogin(getActivity(), "n");
                            Intent intent = new Intent(getActivity(), SignInActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    });
                    break;
                case "Google" :
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail() // email addresses도 요청함
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                    GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(getContext());
                    mGoogleSignInClient.signOut();
                    SaveSharedPreferences.setPrefIsLogin(getActivity(), "n");
                    SaveSharedPreferences.setPrefAutoLogin(getActivity(), "n");
                    getActivity().finish();
                    break;
                case "" :
                    SaveSharedPreferences.setPrefIsLogin(getActivity(), "n");
                    SaveSharedPreferences.setPrefAutoLogin(getActivity(), "n");
                    Intent intent = new Intent(getContext(), SignInActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
                default:
                    break;
            }
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener signoutAction = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (SaveSharedPreferences.getLoginMethod(getContext())) {
                case "Kakao" :
                    UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                        @Override
                        public void onFailure(ErrorResult errorResult) { //회원탈퇴 실패 시
                            int result = errorResult.getErrorCode();
                            if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                Toast.makeText(getContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "회원탈퇴에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onSessionClosed(ErrorResult errorResult) { //로그인 세션이 닫혀있을 시
                            //다시 로그인해달라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                            Toast.makeText(getContext(), "로그인 세션이 닫혔습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), SignInActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onNotSignedUp() { //가입되지 않은 계정에서 회원탈퇴 요구 시
                            //가입되지 않은 계정이라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                            Toast.makeText(getContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), SignInActivity.class);
                            startActivity(intent);
                        }
                        @Override
                        public void onSuccess(Long result) { //회원탈퇴에 성공하면
                            //"회원탈퇴에 성공했습니다."라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                            SaveSharedPreferences.clearAllData(getContext());
                            Toast.makeText(getContext(), "회원탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(), SignInActivity.class);
                            startActivity(intent);
                        }
                    });
                    break;
                case "Google" :
                    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestEmail()
                            .build();
                    mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
                    GoogleSignInAccount gsa = GoogleSignIn.getLastSignedInAccount(getContext());
                    mGoogleSignInClient.revokeAccess();
                    SaveSharedPreferences.clearAllData(getContext());
                    break;
                case "" :
                    String urlAddr = ShareVar.sUrl + "update_outdate_user.jsp?email=" + SaveSharedPreferences.getPrefEmail(getContext());
                    Log.v("Message", urlAddr);
                    String result = connectUpdateData(urlAddr);
                    if (result.equals("1")) {
                        SaveSharedPreferences.setPrefAutoLogin(getContext(), "n");
                        Toast.makeText(getContext(), "정상적으로 탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
                        SaveSharedPreferences.clearAllData(getContext());
                        Intent intent = new Intent(getActivity(), SignInActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Toast.makeText(getContext(), "탈퇴 실패되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
            Intent intent = new Intent(getActivity(), SignInActivity.class);
            startActivity(intent);
        }

        private String connectUpdateData(String urlAddr) {
            String result = null;
            try {
                User_NT userNT = new User_NT(getContext(), urlAddr, "update");
                Object obj = userNT.execute().get();
                result = (String) obj;
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }
    };

}