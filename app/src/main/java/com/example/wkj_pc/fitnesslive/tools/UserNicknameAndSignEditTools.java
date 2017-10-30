package com.example.wkj_pc.fitnesslive.tools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
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

/**
 * Created by wkj_pc on 2017/9/1.
 */

public class UserNicknameAndSignEditTools implements View.OnClickListener{
    private Activity context;
    private PopupWindow popupWindow;
    private final ImageView clearDataImageView;
    private final EditText dataEditText;
    private final TextView confirm;
    private final TextView cancel;
    private final TextView contentTextView;
    private String updateUserInfoUrl;
    String type;
    String content;
    public UserNicknameAndSignEditTools(Activity context, String content,String edithint, String updateUserInfoUrl){
        this.context=context;
        this.updateUserInfoUrl=updateUserInfoUrl;
        this.content = content;
        LayoutInflater inflater = LayoutInflater.from(context);
        View contentView = inflater.inflate(R.layout.tools_about_edit_user_infos_item, null, false);
        clearDataImageView = (ImageView) contentView.findViewById(R.id.tools_user_info_edit_clear);
        dataEditText = (EditText) contentView.findViewById(R.id.tools_user_info_edit_text);
        confirm = (TextView) contentView.findViewById(R.id.tools_user_info_edit_confirm_text_view);
        cancel = (TextView) contentView.findViewById(R.id.tools_user_info_edit_cancel_text_view);
        clearDataImageView.setOnClickListener(this);
        dataEditText.setOnClickListener(this);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
        contentTextView = (TextView) contentView.findViewById(R.id.tools_user_info_edit_content_text_view);
        contentTextView.setText(content);
        if (null!=edithint){
            dataEditText.setText(edithint);
            dataEditText.setSelection(edithint.length());
        }
        popupWindow = new PopupWindow(contentView, ViewPager.LayoutParams.MATCH_PARENT,ViewPager.LayoutParams.MATCH_PARENT,true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.bottom_menu));
        popupWindow.setBackgroundDrawable(dw);
    }
    public void show(){
        View rootView=((ViewGroup)context.findViewById(android.R.id.content)).getChildAt(0);
        popupWindow.setClippingEnabled(false);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }
 
    @Override
    public void onClick(View view) {
        context.getWindow().setBackgroundDrawable( new ColorDrawable(context.getResources().getColor(R.color.color_white)));
        switch (view.getId()) {
            case R.id.tools_user_info_edit_clear:
                dataEditText.setText("");
                break;
            case R.id.tools_user_info_edit_confirm_text_view:
                if (content.equals("修改昵称")){
                    type="nickname";
                }else {
                    type="personalsign";
                }
                LoginUtils.updateUserEditInfos(updateUserInfoUrl, MainApplication.loginUser.getAccount(),
                        dataEditText.getText().toString(),type,new Callback(){
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        if (responseData.contains("failed")){
                            context.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showToast(context,"服务器繁忙，请稍后...", Toast.LENGTH_SHORT);
                                }
                            });
                        }else {
                            if (content.equals("修改昵称"))
                            {
                                MainApplication.loginUser.setNickname(dataEditText.getText().toString());
                            }else
                            {
                                MainApplication.loginUser.setPersonalsign(dataEditText.getText().toString());
                            }
                        }
                        context.startActivity(new Intent(context, UserInfoEditActivity.class));
                        context.finish();
                    }
                });
                break;
            case R.id.tools_user_info_edit_cancel_text_view:
                popupWindow.dismiss();
                break;
        }
    }

}
