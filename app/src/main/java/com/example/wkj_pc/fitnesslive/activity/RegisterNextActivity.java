package com.example.wkj_pc.fitnesslive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisterNextActivity extends AppCompatActivity {

    @BindView(R.id.register_user_next_edit_text)
    EditText registerUserNextEditText;
    @BindView(R.id.register_user_next_linearlayout)
    LinearLayout registerUserNextLinearlayout;
    @BindView(R.id.register_user_next_verify_pwd_edit_text)
    EditText registerUserNextVerifyPwdEditText;
    @BindView(R.id.register_user_next_verify_pwd_linearlayout)
    LinearLayout registerUserNextVerifyPwdLinearlayout;
    private String mobileNum;
    private String registerUserUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        ButterKnife.bind(this);
        mobileNum = getIntent().getStringExtra("mobileNum");
        registerUserUrl = getResources().getString(R.string.app_customer_register_user_url);
    }

    @OnClick({R.id.register_user_next_back_text_view, R.id.register_user_nextstep_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_user_next_back_text_view:
                finish();
                break;
            case R.id.register_user_nextstep_btn:
                if (TextUtils.isEmpty(registerUserNextEditText.getText().toString().trim()) ||
                        registerUserNextEditText.getText().toString().trim().length()<6){
                    registerUserNextLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_warn_btn);
                    ToastUtils.showToast(this,"密码格式错误！", Toast.LENGTH_SHORT);
                    return;
                }else {
                    registerUserNextLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_btn);
                }
                if (TextUtils.isEmpty(registerUserNextVerifyPwdEditText.getText().toString().trim()) ||
                        !registerUserNextEditText.getText().toString().trim().equals(registerUserNextVerifyPwdEditText.getText().toString().trim())){
                    registerUserNextVerifyPwdLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_warn_btn);
                    ToastUtils.showToast(this,"密码输入不一致！", Toast.LENGTH_SHORT);
                    return;
                }else{
                    registerUserNextVerifyPwdLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_btn);
                }
                LoginUtils.updateUserPassword(registerUserUrl,mobileNum ,
                        registerUserNextEditText.getText().toString().trim(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {}
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String responseData = response.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (responseData.contains(":true")){
                                            ToastUtils.showToast(RegisterNextActivity.this,"注册成功！",Toast.LENGTH_SHORT);
                                            startActivity(new Intent(RegisterNextActivity.this,LoginActivity.class));
                                            finish();
                                        }else if (responseData.contains(":false")) {

                                            ToastUtils.showToast(RegisterNextActivity.this,"该用户已经存在!",Toast.LENGTH_SHORT);
                                            startActivity(new Intent(RegisterNextActivity.this,LoginActivity.class));
                                            finish();
                                        }else{
                                            ToastUtils.showToast(RegisterNextActivity.this,"服务器繁忙...",Toast.LENGTH_SHORT);
                                        }
                                    }
                                });
                            }
                        });
                break;
        }
    }
}
