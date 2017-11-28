package com.example.wkj_pc.fitnesslive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.adapter.UserUploadVideoShowAdapter;
import com.example.wkj_pc.fitnesslive.po.Attention;
import com.example.wkj_pc.fitnesslive.po.UploadVideo;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ThreadPoolExecutorUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 用户个人信息显示activity
 */
public class UserInfoShowActivity extends AppCompatActivity {

    @BindView(R.id.activity_user_info_show_big_pic_img_view)
    ImageView activityUserInfoShowBigPicImgView;
    @BindView(R.id.activity_user_info_show_account_text_view)
    TextView activityUserInfoShowAccountTextView;
    @BindView(R.id.activity_user_info_show_fansnum_text_view)
    TextView activityUserInfoShowFansnumTextView;
    @BindView(R.id.activity_user_info_show_attentionnum_text_view)
    TextView activityUserInfoShowAttentionnumTextView;
    @BindView(R.id.activity_user_info_show_personalsign_text_view)
    TextView activityUserInfoShowPersonalsignTextView;
    @BindView(R.id.activity_user_info_show_user_nickname_text_view)
    TextView activityUserInfoShowUserNicknameTextView;
    @BindView(R.id.activity_user_info_show_user_is_attention_text_view)
    TextView activityUserInfoShowIsAttentionTextView;
    @BindView(R.id.activity_user_info_show_video_recycler_view)
    RecyclerView activityUserInfoShowVideoRecyclerView;
    private User user;
    private String type;
    private String account;
    private Attention attention;
    String isAttentionUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_show);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        String url = getResources().getString(R.string.app_get_user_info_url);
        isAttentionUrl=getResources().getString(R.string.app_get_user_is_attention_url);
        String videoUrl = getResources().getString(R.string.app_customer_live_getUserUploadVideosUrl);
        account = intent.getStringExtra("account");
        //如果type!=attention的话，将关注隐藏
        type = intent.getStringExtra("type");
        if (TextUtils.isEmpty(type)){
            activityUserInfoShowIsAttentionTextView.setVisibility(View.GONE);
        }else{
            for (Attention att:MainApplication.attentions){
                if (att.getGzaccount().equals(account)){
                    attention=att;
                    break;
                }
            }
        }
        /** 设置用户信息显示 */
        LoginUtils.getRelativeUserInfo(url, account, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try {
                    user = GsonUtils.getGson().fromJson(responseData, User.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (!TextUtils.isEmpty(user.getLivebigpic())) {
                                Glide.with(UserInfoShowActivity.this).load(user.getLivebigpic())
                                        .asBitmap().into(activityUserInfoShowBigPicImgView);
                            }
                            activityUserInfoShowAccountTextView.setText("账户：" + user.getAccount());
                            activityUserInfoShowFansnumTextView.setText("粉丝：" + user.getFansnum());
                            activityUserInfoShowAttentionnumTextView.setText("关注：" + user.getAttentionnum());
                            activityUserInfoShowPersonalsignTextView.setText(user.getPersonalsign());
                            activityUserInfoShowUserNicknameTextView.setText(user.getNickname());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        /** 设置下方的视频显示*/
        LoginUtils.getUserUploadVideosByAccount(videoUrl, account, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    try {
                        final List<UploadVideo> uploadVideos = GsonUtils.getGson().fromJson(responseData,
                                new TypeToken<List<UploadVideo>>() {
                                }.getType());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRecyclerView(uploadVideos);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    /**
     * 设置下方的视频
     */
    private void initRecyclerView(List<UploadVideo> videos) {
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        UserUploadVideoShowAdapter outerShowAdapter = new UserUploadVideoShowAdapter(videos);
        activityUserInfoShowVideoRecyclerView.setLayoutManager(manager);
        activityUserInfoShowVideoRecyclerView.setAdapter(outerShowAdapter);
    }
    /** 点击事件处理*/
    @OnClick({R.id.activity_user_info_show_back_img_view,
            R.id.activity_user_info_show_user_is_attention_text_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_user_info_show_back_img_view:
                finish();   //点击左上角返回图片
                break;
            case R.id.activity_user_info_show_user_is_attention_text_view:
                //设置是否关注用户，在显示粉丝的时候不出现，隐藏。
                if (activityUserInfoShowIsAttentionTextView.getText().toString().equals("已关注")){
                    activityUserInfoShowIsAttentionTextView.setText("关注");
                    type="canceled";
                }else {
                    activityUserInfoShowIsAttentionTextView.setText("已关注");
                    type="attentioned";
                }
                LoginUtils.setUserIsAttention(isAttentionUrl,GsonUtils.getGson().toJson(attention), type,
                        new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                });
                break;
        }
    }
    @Override
    protected void onDestroy() {
        /** 在退出之前获取登录用户的信息*/
        if (!TextUtils.isEmpty(type)){
            final String longRequestUrl = getResources().getString(R.string.app_get_user_info_url);
            Runnable loginUserRunnable = new Runnable() {
                @Override
                public void run() {
                    LoginUtils.longRequestServer(longRequestUrl, MainApplication.loginUser.getAccount(),
                            MainApplication.cookie, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {}
                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseData = response.body().string();
                                    try {
                                        User loginUser = GsonUtils.getGson().fromJson(responseData, User.class);
                                        MainApplication.loginUser = loginUser;
                                    } catch (Exception e) {
                                        MainApplication.loginUser = null;
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            };
            ThreadPoolExecutorUtils.getCachedThreaPoolExecutors().execute(loginUserRunnable);
        }
        super.onDestroy();
    }
}
