package com.example.wkj_pc.fitnesslive.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserInfoShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_show);
        Intent intent = getIntent();
        String url = getResources().getString(R.string.app_get_user_info_url);
        String account = intent.getStringExtra("account");
        LoginUtils.getRelativeUserInfo(url, account, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                ToastUtils.showToast(UserInfoShowActivity.this,responseData, Toast.LENGTH_SHORT);
            }
        });
    }
}
