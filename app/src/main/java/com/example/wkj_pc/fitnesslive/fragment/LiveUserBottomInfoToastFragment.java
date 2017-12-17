package com.example.wkj_pc.fitnesslive.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.Attention;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import java.io.IOException;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/** 点击头像在下面显示用户的普通信息 */

public class LiveUserBottomInfoToastFragment extends BottomSheetDialogFragment implements View.OnClickListener{
    private User clickUser;  //此时被点击的用户
    private TextView attentionNum; //此时被点击的用户的关注数量
    private TextView fansnum;       //此时被点击的用户的粉丝数量
    private TextView nickname;      //此时被点击的用户的昵称
    private TextView personalSign;  //此时被点击的用户的个性签名
    private CircleImageView userLogo;   //此时被点击的用户的头像
    private Button attentionBtn;
    private String account;
    private String type;
    private String isAttentionUrl;
    private Attention attention = new Attention();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_user_bottom_info_toast, container, false);
        userLogo = (CircleImageView)
                view.findViewById(R.id.user_bottom_fragment_attention_info_alert_logo);
        attentionBtn = (Button) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_attention_btn);
        attentionBtn.setOnClickListener(this);
        attentionNum = (TextView) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_attentionnum);
        fansnum = (TextView) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_fansnum);
        nickname = (TextView) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_nickname);
        personalSign = (TextView) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_personalsign);
        return view;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            /** 进行关注与取消关注设置 */
            case R.id.user_bottom_fragment_attention_info_alert_attention_btn:
                if (attentionBtn.getText().toString().equals("关注")){    //进行关注
                    attention.setGzaccount(clickUser.getAccount());
                    attention.setGzamatar(clickUser.getAmatar());
                    attention.setGzid(0);
                    attention.setGznickname(clickUser.getNickname());
                    attention.setUid(MainApplication.loginUser.getUid());
                    attention.setGzphonenumber(clickUser.getPhonenum());
                    LoginUtils.setUserIsAttention(isAttentionUrl, GsonUtils.getGson().toJson(attention), "attention", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {}
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                        }
                    });
                    attentionBtn.setText("已关注");
                    getDialog().cancel();
                }else {     //取消关注
                    LoginUtils.setUserIsAttention(isAttentionUrl, GsonUtils.getGson().toJson(attention), "canceled", new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {}
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                        }
                    });
                    attentionBtn.setText("关注");
                    getDialog().cancel();
                }
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        isAttentionUrl=getString(R.string.app_set_user_is_attention_url);
        String getUserInfoAccountUrl=getString(R.string.app_customer_live_getLiveUserInfo_url);
        SharedPreferences spref = getActivity().getSharedPreferences("clickamatar", Context.MODE_PRIVATE);
        account = spref.getString("account", "");
        type = spref.getString("type", "");
        getClickUserInfoByAccount(getUserInfoAccountUrl, account);

    }

    /**
     * 设置弹窗信息显示
     */
    private void initView() {

          attentionNum.setText("关注："+clickUser.getAttentionnum());
          fansnum.setText("粉丝："+clickUser.getFansnum());
          if (!TextUtils.isEmpty(clickUser.getNickname())){
              nickname.setText(clickUser.getNickname());
          }
          if (!TextUtils.isEmpty(clickUser.getPersonalsign())){
              personalSign.setText(clickUser.getPersonalsign());
          }
          if (!TextUtils.isEmpty(clickUser.getAmatar())){
              Glide.with(getActivity()).load(clickUser.getAmatar()).asBitmap().into(userLogo);
          }
          if (type.equals("live")){     //用户为直播用户
              if (MainApplication.loginUser.getAccount().equals(account)){  //登录用户点击头像
                  attentionBtn.setVisibility(View.INVISIBLE);
              }else {   //点击其他用户头像，验证是否已经关注
                  if (verifyIsAttentioned(account)){
                      attentionBtn.setText("已关注");
                  }
              }
          }else {   //用户为观众用户
             if (MainApplication.loginUser.getAccount().equals(account)){  //登录用户点击头像
                  attentionBtn.setVisibility(View.INVISIBLE);
              }else {   //点击其他用户头像，验证是否已经关注
                  if (verifyIsAttentioned(account)){
                      attentionBtn.setText("已关注");
                  }
              }
          }
    }

    /** 验证用户是否已经关注
     * @param account 通过account验证是否已经关注该用户
     * */
    private boolean verifyIsAttentioned(String account) {
        if (null==MainApplication.attentions || MainApplication.attentions.size()==0)
            return false;
        for (Attention att:MainApplication.attentions){
            if (att.getGzaccount().equals(account)){
                attention=att;
                return true;
            }
        }
        return false;
    }

    /**
     * 获取点击用户的信息
     * @param getUserInfoAccountUrl  连接的地址
     * @param account 通过账户获取
     */
    private void getClickUserInfoByAccount(String getUserInfoAccountUrl,String account){
        LoginUtils.getLiveUserInfo(getUserInfoAccountUrl, account, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try{
//                    System.out.println("--------------responsedata="+responseData);
                    clickUser = GsonUtils.getGson().fromJson(responseData, User.class);
//                    System.out.println("---clickUser="+clickUser.getAccount()+clickUser.getFansnum()+clickUser.getAttentionnum()
//                    +clickUser.getUid());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initView();
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        clickUser=null;
        String updateUserInfoUrl = getResources().getString(R.string.app_get_user_info_url);
        //更新登录用户的信息，在退出时。
        LoginUtils.getLiveUserInfo(updateUserInfoUrl, MainApplication.loginUser.getAccount(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                try{
                    MainApplication.loginUser = GsonUtils.getGson().fromJson(responseData, User.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
