package com.example.wkj_pc.fitnesslive.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.adapter.UploadNativeVideoAdapter;
import com.example.wkj_pc.fitnesslive.po.UploadVideo;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadNativeVideoActivity extends AppCompatActivity {

    List<UploadVideo> nativeVideos;
    @BindView(R.id.upload_native_video_choose_recycler_view)
    RecyclerView uploadNativeVideoChooseRecyclerView;
    @BindView(R.id.upload_native_video_show_image_view)
    ImageView uploadNativeVideoShowImageView;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    initRecyclerView();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_native_video);
        ButterKnife.bind(this);
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else {
            getNativeVideos();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        initRecyclerView();
    }

    @OnClick({R.id.user_upload_native_video_cancel_text_view, R.id.user_upload_native_video_choose_text_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_upload_native_video_choose_text_view:
                String path = null;
                for (UploadVideo video : nativeVideos) {
                    if (video.isselected()) {
                        path = video.getVideourl();
                    }
                }
                Intent intent = new Intent(this, UploadVideoActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
                finish();
                break;
            case R.id.user_upload_native_video_cancel_text_view:
                finish();
                break;
        }
    }

    /**
     * 显示本地视频 （单选进行上传，跳转到上传页面）
     */
    public void initRecyclerView() {
        if (null == nativeVideos || nativeVideos.size() == 0) {
            uploadNativeVideoShowImageView.setVisibility(View.VISIBLE);
            uploadNativeVideoChooseRecyclerView.setVisibility(View.GONE);
        } else {
            uploadNativeVideoShowImageView.setVisibility(View.GONE);
            uploadNativeVideoChooseRecyclerView.setVisibility(View.VISIBLE);
            UploadNativeVideoAdapter adapter = new UploadNativeVideoAdapter(nativeVideos,getApplicationContext());
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            uploadNativeVideoChooseRecyclerView.setAdapter(adapter);
            uploadNativeVideoChooseRecyclerView.setLayoutManager(manager);
        }
    }
    /**
     * 获取全部本地视频
     */
    public void getNativeVideos() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                String[] proj = { MediaStore.Video.Thumbnails.DATA};
                Cursor mCursor = getContentResolver().query(mImageUri,proj,MediaStore.Video.Media.MIME_TYPE + "=?",
                        new String[]{"video/mp4"},MediaStore.Video.Media.DATE_MODIFIED+" desc");
                if(mCursor!=null){
                    nativeVideos=new ArrayList<UploadVideo>();
                    while (mCursor.moveToNext()) {
                        // 获取视频的路径
                        String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Video.Media.DATA));
                        UploadVideo video=new UploadVideo();
                        video.setVideourl(path);
                        video.setThumbnailurl(path);
                        nativeVideos.add(video);
                    }
                }
                //更新界面
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initRecyclerView();
                    }
                });
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults.length>0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED ){
                getNativeVideos();
            }else {
                ToastUtils.showToast(this,"permission failed", Toast.LENGTH_SHORT);
            }
        }
    }
}