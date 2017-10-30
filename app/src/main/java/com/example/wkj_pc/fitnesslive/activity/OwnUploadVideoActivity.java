package com.example.wkj_pc.fitnesslive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.adapter.UserUploadVideoShowAdapter;
import com.example.wkj_pc.fitnesslive.po.UploadVideo;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class OwnUploadVideoActivity extends AppCompatActivity {

    @BindView(R.id.user_upload_video_show_recycler_view)
    RecyclerView userUploadVideoShowRecyclerView;   //用户上传的视频展示
    @BindView(R.id.user_video_show_cancel_text_view)
    TextView userVideoShowCancelTextView;
    @BindView(R.id.user_upload_video_show_confirm_text_view)
    TextView userUploadVideoShowConfirmTextView;
    @BindView(R.id.user_upload_video_show_bg_linearlayout)
    LinearLayout userUploadVideoShowBgLinearlayout;
    private List<UploadVideo> uploadVideos;
    private String getUploadVideoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_upload_video);
        ButterKnife.bind(this);
        getUploadVideoUrl = getResources().getString(R.string.app_customer_live_getUserUploadVideoUrl);
        if (null !=MainApplication.loginUser){
            getUploadVideo(getUploadVideoUrl, MainApplication.loginUser.getUid());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null !=MainApplication.loginUser){
            getUploadVideo(getUploadVideoUrl, MainApplication.loginUser.getUid());
        }
        initRecyclerView();
    }
    /**显示用户上传的视频进行显示*/
    private void initRecyclerView() {
        if (null == uploadVideos || uploadVideos.size() == 0) {
            //空空如也，立即上传
            userUploadVideoShowBgLinearlayout.setVisibility(View.VISIBLE);
            userUploadVideoShowRecyclerView.setVisibility(View.GONE);
        }else {
            userUploadVideoShowBgLinearlayout.setVisibility(View.GONE);
            userUploadVideoShowRecyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager manager = new LinearLayoutManager(this);
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            UserUploadVideoShowAdapter adapter = new UserUploadVideoShowAdapter(uploadVideos);
            userUploadVideoShowRecyclerView.setLayoutManager(manager);
            userUploadVideoShowRecyclerView.setAdapter(adapter);
        }
    }

    @OnClick({R.id.user_video_show_cancel_text_view, R.id.user_upload_video_show_confirm_text_view, R.id.user_upload_video_show_recycler_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_upload_video_show_confirm_text_view: //确认上传手机本地的视频
                startActivity(new Intent(this, UploadNativeVideoActivity.class));
                break;
            case R.id.user_video_show_cancel_text_view:     //用户点击取消
                finish();
                break;
        }
    }
    /**
     * 获取用户上传的视频
     */
    public void getUploadVideo(String getUploadVideoUrl, int uid) {
        LoginUtils.getUserUploadVideos(getUploadVideoUrl, uid, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    uploadVideos = GsonUtils.getGson().fromJson(responseData,
                            new TypeToken<List<UploadVideo>>() {}.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initRecyclerView();
                        }
                    });
                }
            }
        });
    }
}
