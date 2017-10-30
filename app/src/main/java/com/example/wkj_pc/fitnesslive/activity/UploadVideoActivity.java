package com.example.wkj_pc.fitnesslive.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.tools.AlertProgressDialogUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

public class UploadVideoActivity extends AppCompatActivity {

    @BindView(R.id.user_upload_video_title_edit_text)
    EditText userUploadVideoTitleEditText;
    @BindView(R.id.user_upload_video_thumbnails_img_view)
    ImageView userUploadVideoThumbnailsImgView;
    private String uploadVideoUrl;  //上传服务器地址
    private String path;    //视频地址
    private Bitmap videoThumbnail; //视频缩略图显示

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        ButterKnife.bind(this);
        uploadVideoUrl = getResources().getString(R.string.app_customer_live_uploadUserVideoUrl);
        path = getIntent().getStringExtra("path");
        initThumbnails();
    }
    /** 初始化缩略图 */
    public void initThumbnails(){
        videoThumbnail = ThumbnailUtils.createVideoThumbnail(path, MINI_KIND);
        userUploadVideoThumbnailsImgView.setImageBitmap(videoThumbnail);
    }
    @OnClick({R.id.tools_user_info_edit_cancel_text_view,
            R.id.tools_user_info_edit_confirm_text_view,
            R.id.user_upload_video_thumbnails_img_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tools_user_info_edit_confirm_text_view:   //视频上传
                uploadImg(path,videoThumbnail);
                break;
            case R.id.user_upload_video_thumbnails_img_view:    //点击缩略图观看视频
                Intent intent = new Intent(this, VideoPlayerActivity.class);
                intent.putExtra("videourl",path);
                startActivity(intent);
                break;
            case R.id.tools_user_info_edit_cancel_text_view:    //取消上传
                finish();
                break;
        }
    }
    /** 用户上传视频*/
    private void uploadImg(String path ,Bitmap bitmap) {

        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] bytes = outputStream.toByteArray();
        String content = Base64.encodeToString(bytes, Base64.DEFAULT);
        String title=null;
        if (TextUtils.isEmpty(userUploadVideoTitleEditText.getText().toString())){
            title="";
        }else {
            title=userUploadVideoTitleEditText.getText().toString();
        }
        File file = new File(path);
        try {
            FileInputStream stream = new FileInputStream(new File(path));
            AlertProgressDialogUtils.alertProgressShow(this,false,"正在上传中");
            OkHttpUtils.post()
                    .addFile("file", "uploadvideo", file)//
                    .url(uploadVideoUrl)
                    .addParams("uid", String.valueOf(MainApplication.loginUser.getUid()))
                    .addParams("title",title)
                    .addParams("thumbnail",content)
                    .addHeader("Content-type", "multipart/form-data")
                    // .addHeader("Content-type", "application/x-www-form-urlencoded")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e) {}
                        @Override
                        public void onResponse(Call call, final String s) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (s.contains("failed")){
                                        AlertProgressDialogUtils.alertProgressClose();
                                        ToastUtils.showToast(UploadVideoActivity.this,"服务器繁忙...",Toast.LENGTH_SHORT);
                                    }else {
                                        ToastUtils.showToast(UploadVideoActivity.this,"上传成功",Toast.LENGTH_SHORT);
                                        finish();
                                    }
                                }
                            });
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
