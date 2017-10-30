package com.example.wkj_pc.fitnesslive.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.tools.AlertDialogTools;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import com.example.wkj_pc.fitnesslive.tools.ValidationTools;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FindPasswordActivity extends AppCompatActivity {

    @BindView(R.id.fing_pwd_phonenumber_edit_text)
    EditText fingPwdPhonenumberEditText;
    @BindView(R.id.find_pwd_mobile_number_linearlayout)
    LinearLayout findPwdMobileNumberLinearlayout;
    @BindView(R.id.find_pwd_verifycode_edit_text)
    EditText findPwdVerifycodeEditText;
    @BindView(R.id.find_pwd_verifycode_linearlayout)
    LinearLayout findPwdVerifycodeLinearlayout;
    @BindView(R.id.login_user_find_pwd_back_text_view)
    ImageView loginUserFindPwdBackTextView;
    @BindView(R.id.getVerifycodeTextView)
    TextView getVerifycodeTextView;
    @BindView(R.id.find_pwd_nextstep_btn)
    Button findPwdNextstepBtn;
    private String getVerifyCodeUrl;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 0:
                    AlertDialogTools.showDialog(FindPasswordActivity.this, R.mipmap.ic_begin_live_icon, true, "前往",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(FindPasswordActivity.this,RegisterActivity.class));
                                    finish();
                                }
                            },"取消",null,"提醒","该用户不存在，是否立即注册？");
                    break;
                case 1:
                    ToastUtils.showToast(FindPasswordActivity.this,"服务器繁忙！",Toast.LENGTH_SHORT);
                     break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        getVerifyCodeUrl=getResources().getString(R.string.app_get_verifycode_url);
        fingPwdPhonenumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    getVerifycodeTextView.setEnabled(true);
                    findPwdMobileNumberLinearlayout.setBackgroundResource(R.drawable.shape_stroke_bg_btn);
                } else {
                    findPwdMobileNumberLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_btn);
                }
            }
        });
        findPwdVerifycodeEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    findPwdVerifycodeLinearlayout.setBackgroundResource(R.drawable.shape_stroke_bg_btn);
                } else {
                    findPwdVerifycodeLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_btn);
                }
            }
        });
    }
    /** 安卓倒计时*/
    public void countDownTime(int time){
        final int[] tag = {time};
        final Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getVerifycodeTextView.setEnabled(false);
                        getVerifycodeTextView.setText("重新发送("+tag[0]+")");
                    }
                });
                if (tag[0]==0) {
                    MainApplication.verifyCode=null;
                    getVerifycodeTextView.setText("获取验证码");
                    timer.cancel();
                }
                tag[0]--;
            }
        },0,1000);
    }

    @OnClick({R.id.login_user_find_pwd_back_text_view,R.id.getVerifycodeTextView, R.id.find_pwd_nextstep_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.getVerifycodeTextView:
                if (!MainApplication.networkinfo) {   //如果当前网络不可用的话，停止登录活动
                    AlertDialogTools.showDialog(this, R.mipmap.ic_begin_live_icon, true, "确定", null, null, null, "提醒", "网络状态异常！");
                    return;
                }
                if (!ValidationTools.checkMobile(fingPwdPhonenumberEditText.getText().toString().trim()))
                {
                    ToastUtils.showToast(this, "手机号格式错误！", Toast.LENGTH_SHORT);
                    return;
                }
                /** 请求服务器获取信息验证码*/
                LoginUtils.longGetUserLiveTagFromServer(getVerifyCodeUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData=response.body().string();
                        if (responseData.contains("true:")){
                            MainApplication.verifyCode=responseData.substring(5);
                            countDownTime(60);
                        }else if (responseData.contains("false:")){
                            Message message = handler.obtainMessage();
                            message.what=0;
                            handler.sendMessage(message);
                        }else {
                            Message message = handler.obtainMessage();
                            message.what=1;
                            handler.sendMessage(message);
                        }
                    }
                });
                break;
            case R.id.find_pwd_nextstep_btn:
                if (!MainApplication.networkinfo) {   //如果当前网络不可用的话，停止登录活动
                    AlertDialogTools.showDialog(this, R.mipmap.ic_begin_live_icon, true, "确定", null, null, null, "提醒", "网络状态异常！");
                    return;
                }
                if (!ValidationTools.checkMobile(fingPwdPhonenumberEditText.getText().toString().trim()))
                {
                    ToastUtils.showToast(this, "手机号格式错误！", Toast.LENGTH_SHORT);
                    return;
                }else {
                    findPwdMobileNumberLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_btn);
                }
                if (!TextUtils.isEmpty(findPwdVerifycodeEditText.getText().toString().trim()) &&
                        MainApplication.verifyCode.equals(findPwdVerifycodeEditText.getText().toString().trim())) {
                    Intent intent = new Intent(this, FindPwdNextActivity.class);
                    intent.putExtra("mobileNum",fingPwdPhonenumberEditText.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }else {
                    findPwdVerifycodeLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_warn_btn);
                    ToastUtils.showToast(this, "验证码格式错误！", Toast.LENGTH_SHORT);
                    return;
                }
                break;
            case R.id.login_user_find_pwd_back_text_view:
                finish();
                break;
        }
    }


}



