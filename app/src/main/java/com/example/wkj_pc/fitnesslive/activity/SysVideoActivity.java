package com.example.wkj_pc.fitnesslive.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.adapter.SysVideoShowAdapter;
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

/**
 * 系统视频显示
 */

public class SysVideoActivity extends AppCompatActivity {

    @BindView(R.id.sys_video_show_recycler_view)
    RecyclerView sysVideoShowRecyclerView;
    private String videoUrl;
    private List<UploadVideo> sysVideos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_video);
        ButterKnife.bind(this);
        videoUrl=getResources().getString(R.string.app_customer_get_sys_video);
        getSysVideo();
    }
    /** 退出*/
    @OnClick(R.id.sys_video_show_back_img_view)
    public void onViewClicked() {
        finish();
    }
    /** 显示系统视频*/
    private void initRecyclerView(){
        if (null!=sysVideos && sysVideos.size()>0){
            LinearLayoutManager manager=new LinearLayoutManager(this);
            sysVideoShowRecyclerView.setLayoutManager(manager);
            SysVideoShowAdapter adapter=new SysVideoShowAdapter(sysVideos,this);
            sysVideoShowRecyclerView.setAdapter(adapter);
        }
    }
    /** 获取系统视频*/
    public void getSysVideo() {
        LoginUtils.getSysVideo(videoUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try{
                    if (!TextUtils.isEmpty(responseData)){
                        sysVideos = GsonUtils.getGson().fromJson(responseData, new TypeToken<List<UploadVideo>>() {
                        }.getType());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
