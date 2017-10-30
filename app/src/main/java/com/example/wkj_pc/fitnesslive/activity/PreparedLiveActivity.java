package com.example.wkj_pc.fitnesslive.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.adapter.LiveThemeEditSetAdapter;
import com.example.wkj_pc.fitnesslive.tools.BottomMenuUtils;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.CropOptions;
import com.jph.takephoto.model.LubanOptions;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PreparedLiveActivity extends TakePhotoActivity {

    @BindView(R.id.prepared_live_user_back_image_view)
    ImageView preparedLiveUserBackImageView;
    @BindView(R.id.prepared_live_set_to_live_text_view)
    TextView preparedLiveSetToLiveTextView;
    @BindView(R.id.prepared_live_big_img_set_img_view)
    ImageView preparedLiveBigImgSetImgView;
    @BindView(R.id.prepared_live_theme_edit_text)
    EditText preparedLiveThemeEditText;
    @BindView(R.id.prepared_live_theme_append_button)
    TextView preparedLiveThemeAppendButton;
    @BindView(R.id.prepared_live_theme_edit_show_recycler_view)
    RecyclerView preparedLiveThemeEditShowRecyclerView;
    @BindView(R.id.prepared_live_big_img_set_text_view)
    TextView preparedLiveBigImgSetTextView;
    private TakePhoto takePhoto;
    private String updateUserInfoUrl;
    private String updateUserLiveThemesUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepared_live);
        ButterKnife.bind(this);
        takePhoto = getTakePhoto();
        updateUserLiveThemesUrl = getResources().getString(R.string.app_customer_live_updateLiveUserStyle);
        updateUserInfoUrl = getResources().getString(R.string.app_update_user_info_url);
        Glide.with(this).load(MainApplication.loginUser.getLivebigpic()).
                error(R.drawable.biglivepic).into(preparedLiveBigImgSetImgView);
    }
    @Override
    protected void onResume() {
        super.onResume();
        initLiveThemes();
    }
    /** 设置直播风格显示 */
    private void initLiveThemes() {
        StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        preparedLiveThemeEditShowRecyclerView.setLayoutManager(manager);
        LiveThemeEditSetAdapter adapter=new LiveThemeEditSetAdapter(MainApplication.nativeLiveThemes);
        preparedLiveThemeEditShowRecyclerView.setAdapter(adapter);
    }

    @OnClick({R.id.prepared_live_user_back_image_view,R.id.prepared_live_big_img_set_text_view,
            R.id.prepared_live_set_to_live_text_view, R.id.prepared_live_big_img_set_img_view,
            R.id.prepared_live_theme_append_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.prepared_live_set_to_live_text_view:  //准备直播
                String content = GsonUtils.getGson().toJson(MainApplication.nativeLiveThemes);
                LoginUtils.updateLiveUserThemes(updateUserLiveThemesUrl,MainApplication.loginUser.getUid(),content,new Callback()
                {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        if (responseData.equals("failed")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast(PreparedLiveActivity.this,"服务器繁忙！",Toast.LENGTH_SHORT);
                                }
                            });
                        }
                    }
                });
                startActivity(new Intent(PreparedLiveActivity.this,LiveActivity.class));
                finish();
                break;
            case R.id.prepared_live_big_img_set_text_view:  //弹出 选择大图
                showBottomSelectorMenu();
                break;
            case R.id.prepared_live_big_img_set_img_view:   //弹出 选择大图
                showBottomSelectorMenu();
                break;
            case R.id.prepared_live_theme_append_button://添加直播风格
                if (!TextUtils.isEmpty(preparedLiveThemeEditText.getText().toString())){
                    MainApplication.nativeLiveThemes.add(preparedLiveThemeEditText.getText().toString());
                    preparedLiveThemeEditShowRecyclerView.scrollToPosition(MainApplication.nativeLiveThemes.size()-1);
                    preparedLiveThemeEditText.setText("");
                }
                break;
            case R.id.prepared_live_user_back_image_view:   //设置直播信息返回
                finish();
                break;
        }
    }
    /** 弹出底部菜单选项，选择获取图片方法*/
    private void showBottomSelectorMenu() {
        getWindow().setBackgroundDrawable( new ColorDrawable(getResources().getColor(R.color.bottom_menu)));
        BottomMenuUtils bottomMenuUtils=new BottomMenuUtils(this, new View.OnClickListener() {
            private Uri imageUri;
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.user_edit_get_photo_from_album:
                        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                        imageUri = Uri.fromFile(file);
                        configCompress(takePhoto);
                        configTakePhotoOption(takePhoto);
                        takePhoto.onPickFromGalleryWithCrop(imageUri,getCropOptions());
                        break;
                    case R.id.user_edit_take_photo:
                        File file1=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                        if (!file1.getParentFile().exists())file1.getParentFile().mkdirs();
                        imageUri = Uri.fromFile(file1);
                        configCompress(takePhoto);
                        configTakePhotoOption(takePhoto);
                        takePhoto.onPickFromCaptureWithCrop(imageUri,getCropOptions());
                        break;
                }
            }
        });
        bottomMenuUtils.show();
    }
    @Override
    public void takeCancel() {
        super.takeCancel();
    }
    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }
    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }
    private void showImg(final ArrayList<TImage> images) {
        TImage tImage = images.get(0);
        final File file = new File(images.get(0).getCompressPath());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                /** 展示用户大图片*/
                Glide.with(PreparedLiveActivity.this).load(file).error(R.drawable.head_img).into(preparedLiveBigImgSetImgView);
            }
        });
        FileInputStream inputStream=null;
        try {
            inputStream=new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //更新用户直播大图
        LoginUtils.updateUserLiveBigPicUrl(updateUserInfoUrl, MainApplication.loginUser.getAccount(),
                inputStream, new Callback(){
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        if (responseData.contains("loginfailed")){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast(PreparedLiveActivity.this,"修改失败", Toast.LENGTH_SHORT);
                                    preparedLiveBigImgSetImgView.setImageBitmap(BitmapFactory.
                                            decodeResource(getResources(),R.mipmap.ic_amatar_img));
                                }
                            });
                        }else if (responseData.contains("true:")){
                            String amatarUrl = responseData.substring(5);
                            MainApplication.loginUser.setLivebigpic(amatarUrl);
                        }
                    }
                });
    }
    /**配置图片选项*/
    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());
    }
    /** 配置压缩图片 */
    private void configCompress(TakePhoto takePhoto){
        boolean showProgressBar=true;
        boolean enableRawFile=true;
        CompressConfig config;
        LubanOptions option=new LubanOptions.Builder()
                .setMaxHeight(600)
                .setMaxWidth(700)
                .setMaxSize(900)
                .create();
        config= CompressConfig.ofLuban(option);
        config.enableReserveRaw(enableRawFile);
        takePhoto.onEnableCompress(config,showProgressBar);
    }
    /** 配置图片裁剪 */
    private CropOptions getCropOptions(){
        boolean withWonCrop=true;
        CropOptions.Builder builder=new CropOptions.Builder();
        builder.setOutputX(700).setOutputY(600);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
}
