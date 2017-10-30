package com.example.wkj_pc.fitnesslive.activity;

import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.tools.BottomMenuUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import com.example.wkj_pc.fitnesslive.tools.UserNicknameAndSignEditTools;
import com.example.wkj_pc.fitnesslive.tools.UserSexEditTools;
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

import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserInfoEditActivity extends TakePhotoActivity {
    private CircleImageView amatarImageView;
    private TextView nickname;
    private TextView account;
    private TextView sex;
    private TextView personalSign;  //个性签名
    private TakePhoto takePhoto=getTakePhoto();
    private Uri imageUri;
    String updateUserInfoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_edit);
        ButterKnife.bind(this);
        updateUserInfoUrl=getResources().getString(R.string.app_update_user_info_url);
        amatarImageView = (CircleImageView) findViewById(R.id.about_user_edit_amatar_img_view);
        nickname = (TextView) findViewById(R.id.about_user_edit_nickname_text_view);
        account = (TextView) findViewById(R.id.about_user_edit_user_account_text_view);
        sex = (TextView) findViewById(R.id.about_user_edit_sex_text_view);
        personalSign = (TextView) findViewById(R.id.about_user_edit_person_sign_text_view);
    }

    @OnClick({R.id.about_user_info_edit_back_text_view, R.id.about_user_edit_amatar_linearlayout,
            R.id.about_user_edit_nickname_linearlayout, R.id.about_user_edit_sex_linearlayout,
            R.id.about_user_edit_person_sign_linearlayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.about_user_edit_sex_linearlayout:
                getWindow().setBackgroundDrawable( new ColorDrawable(getResources().getColor(R.color.bottom_menu)));
                UserSexEditTools userInfoEditTools=new UserSexEditTools(this,MainApplication.loginUser.getGender(),updateUserInfoUrl);
                userInfoEditTools.show();
                break;
            case R.id.about_user_edit_nickname_linearlayout:
                UserNicknameAndSignEditTools editNickname=new UserNicknameAndSignEditTools(this,"修改昵称",MainApplication.loginUser.getNickname(),updateUserInfoUrl);
                editNickname.show();
                break;
            case R.id.about_user_edit_person_sign_linearlayout:
                UserNicknameAndSignEditTools editSign=new UserNicknameAndSignEditTools(this,"个人签名",MainApplication.loginUser.getPersonalsign(),updateUserInfoUrl);
                editSign.show();
                break;
            case R.id.about_user_edit_amatar_linearlayout: //弹出底部获取图片菜单，使用takephotoactivity，修改头像
                getWindow().setBackgroundDrawable( new ColorDrawable(getResources().getColor(R.color.bottom_menu)));
                BottomMenuUtils bottomMenuUtils=new BottomMenuUtils(this, new View.OnClickListener() {
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
                break;
            case R.id.about_user_info_edit_back_text_view:
                finish();
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (!TextUtils.isEmpty(MainApplication.loginUser.getAmatar())) {
            Glide.with(getApplicationContext()).load(MainApplication.loginUser.getAmatar()).asBitmap().into(amatarImageView);
        }
        if (!TextUtils.isEmpty(MainApplication.loginUser.getAccount())) {
            account.setText(MainApplication.loginUser.getAccount());
        }
        if (!TextUtils.isEmpty(MainApplication.loginUser.getNickname())) {
            nickname.setText(MainApplication.loginUser.getNickname()+">");
        }
        if (!TextUtils.isEmpty(MainApplication.loginUser.getGender())) {
            sex.setText(MainApplication.loginUser.getGender()+">");
        }
        if (!TextUtils.isEmpty(MainApplication.loginUser.getPersonalsign())) {
            personalSign.setText(MainApplication.loginUser.getPersonalsign()+">");
        }
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
                Glide.with(UserInfoEditActivity.this).load(file).error(R.drawable.head_img).into(amatarImageView);
            }
        });
        FileInputStream inputStream=null;
        try {
            inputStream=new FileInputStream(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        LoginUtils.updateUserAmatar(updateUserInfoUrl, MainApplication.loginUser.getAccount(),
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
                            ToastUtils.showToast(UserInfoEditActivity.this,"修改失败", Toast.LENGTH_SHORT);
                            amatarImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_amatar_img));
                        }
                    });
                }else if (responseData.contains("true:")){
                    String amatarUrl = responseData.substring(5);
                    MainApplication.loginUser.setAmatar(amatarUrl);
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
                .setMaxHeight(400)
                .setMaxWidth(400)
                .setMaxSize(700)
                .create();
        config= CompressConfig.ofLuban(option);
        config.enableReserveRaw(enableRawFile);
        takePhoto.onEnableCompress(config,showProgressBar);
    }
    /** 配置图片裁剪 */
    private CropOptions getCropOptions(){
        boolean withWonCrop=true;
        CropOptions.Builder builder=new CropOptions.Builder();
        builder.setOutputX(400).setOutputY(400);
        builder.setWithOwnCrop(withWonCrop);
        return builder.create();
    }
}