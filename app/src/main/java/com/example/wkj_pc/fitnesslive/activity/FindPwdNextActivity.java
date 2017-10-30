package com.example.wkj_pc.fitnesslive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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

public class FindPwdNextActivity extends AppCompatActivity {

    @BindView(R.id.find_pwd_next_edit_text)
    EditText findPwdNextEditText;
    @BindView(R.id.find_pwd_next_linearlayout)
    LinearLayout findPwdNextLinearlayout;
    @BindView(R.id.find_pwd_next_verify_pwd_edit_text)
    EditText findPwdNextVerifyPwdEditText;
    @BindView(R.id.find_pwd_next_verify_pwd_linearlayout)
    LinearLayout findPwdNextVerifyPwdLinearlayout;
    private String userUpdatePasswordUrl;
    private String mobileNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd_next);
        ButterKnife.bind(this);
        mobileNum = getIntent().getStringExtra("mobileNum");
        userUpdatePasswordUrl = getResources().getString(R.string.app_customer_user_update_password_url);
    }

    @OnClick({R.id.login_user_find_pwd_back_text_view, R.id.alter_pwd_nextstep_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_user_find_pwd_back_text_view:
                finish();
                break;
            case R.id.alter_pwd_nextstep_btn:
                if (TextUtils.isEmpty(findPwdNextEditText.getText().toString().trim()) ||
                        findPwdNextEditText.getText().toString().trim().length()<6){
                    findPwdNextLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_warn_btn);
                    ToastUtils.showToast(this,"密码格式错误！", Toast.LENGTH_SHORT);
                    return;
                }else {
                    findPwdNextLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_btn);
                }
                if (TextUtils.isEmpty(findPwdNextVerifyPwdEditText.getText().toString().trim()) ||
                    !findPwdNextEditText.getText().toString().trim().equals(findPwdNextVerifyPwdEditText.getText().toString().trim())){
                    findPwdNextVerifyPwdLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_warn_btn);
                    ToastUtils.showToast(this,"密码输入不一致！", Toast.LENGTH_SHORT);
                    return;
                }else{
                    findPwdNextVerifyPwdLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_btn);
                }
                LoginUtils.registerUser(userUpdatePasswordUrl,mobileNum ,
                        findPwdNextEditText.getText().toString().trim(), new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {}
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String responseData = response.body().string();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (responseData.contains("true")){
                                            ToastUtils.showToast(FindPwdNextActivity.this,"修改成功！",Toast.LENGTH_SHORT);
                                            startActivity(new Intent(FindPwdNextActivity.this,LoginActivity.class));
                                            finish();
                                        } else {
                                            ToastUtils.showToast(FindPwdNextActivity.this,"服务器繁忙...",Toast.LENGTH_SHORT);
                                            startActivity(new Intent(FindPwdNextActivity.this,FindPasswordActivity.class));
                                            finish();
                                        }
                                    }
                                });
                            }
                        });
                break;
        }
    }
}
