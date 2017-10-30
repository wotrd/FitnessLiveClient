package com.example.wkj_pc.fitnesslive.tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.activity.UserInfoEditActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.R.id.content;

/**
 * Created by wkj_pc on 2017/9/7.
 */

public class UserSexEditTools implements View.OnClickListener{
    private Activity context;
    private PopupWindow popupWindow;
    private final TextView confirm;
    private final TextView cancel;
    private String updateUserInfoUrl;
    String type;
    private final LinearLayout manLinearLayout;
    private final ImageView manImageView;
    private final LinearLayout womanLinearLayout;
    private final ImageView womanImageView;
    private String sexTag;

    public UserSexEditTools(Activity context, String content, String updateUserInfoUrl){
        this.context=context;
        this.updateUserInfoUrl=updateUserInfoUrl;
        sexTag = content;
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.tools_about_edit_user_info_item, null, false);
        manImageView = (ImageView) contentView.findViewById(R.id.tools_user_sex_man_image_view);
        manLinearLayout = (LinearLayout) contentView.findViewById(R.id.tools_user_sex_edit_man_select_linearlayout);
        confirm = (TextView) contentView.findViewById(R.id.tools_user_sex_edit_confirm_text_view);
        cancel = (TextView) contentView.findViewById(R.id.tools_user_sex_edit_cancel_text_view);
        womanImageView = (ImageView) contentView.findViewById(R.id.tools_user_sex_woman_image_view);
        womanLinearLayout = (LinearLayout) contentView.findViewById(R.id.tools_user_sex_edit_woman_select_linearlayout);
        womanImageView.setOnClickListener(this);
        manImageView.setOnClickListener(this);
        manLinearLayout.setOnClickListener(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        womanLinearLayout.setOnClickListener(this);
        setSexMethod(content);
        popupWindow = new PopupWindow(contentView, ViewPager.LayoutParams.MATCH_PARENT,
                ViewPager.LayoutParams.MATCH_PARENT,true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.bottom_menu));
        popupWindow.setBackgroundDrawable(dw);
    }

    private void setSexMethod(String sexTag) {
        if (sexTag.equals("男")){
            manImageView.setVisibility(View.VISIBLE);
            womanImageView.setVisibility(View.INVISIBLE);
        }else {
            manImageView.setVisibility(View.INVISIBLE);
            womanImageView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        context.getWindow().setBackgroundDrawable( new ColorDrawable(context.getResources().getColor(R.color.color_white)));
        switch (view.getId()) {
            case R.id.tools_user_sex_edit_man_select_linearlayout:
                sexTag = "男";
                setSexMethod(sexTag);
                break;
            case R.id.tools_user_sex_edit_woman_select_linearlayout:
                sexTag = "女";
                setSexMethod(sexTag);
                break;
            case R.id.tools_user_sex_edit_confirm_text_view:
                LoginUtils.updateUserSex(updateUserInfoUrl, MainApplication.loginUser.getAccount(),
                        sexTag,new Callback(){
                            @Override
                            public void onFailure(Call call, IOException e) {}
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseData = response.body().string();
                                if (responseData.contains("failed")) {
                                    context.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showToast(context, "服务器繁忙，请稍后...", Toast.LENGTH_SHORT);
                                        }
                                    });
                                }
                                MainApplication.loginUser.setGender(sexTag);
                                context.startActivity(new Intent(context, UserInfoEditActivity.class));
                                context.finish();
                            }
                        });
                break;
            case R.id.tools_user_sex_edit_cancel_text_view:
                popupWindow.dismiss();
                break;
        }
    }
    public void show(){
        View rootView=((ViewGroup)context.findViewById(content)).getChildAt(0);
        popupWindow.setClippingEnabled(false);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
