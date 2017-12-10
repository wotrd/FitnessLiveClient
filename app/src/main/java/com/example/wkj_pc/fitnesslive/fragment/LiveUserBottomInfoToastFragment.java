package com.example.wkj_pc.fitnesslive.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.Attention;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
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
            /** 进行关注与是否关注设置 */
            case R.id.user_bottom_fragment_attention_info_alert_attention_btn:
                ToastUtils.showToast(getActivity(),"niaho", Toast.LENGTH_SHORT);
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        String getUserInfoAccountUrl=getString(R.string.app_customer_live_getLiveUserInfo_url);
        SharedPreferences spref = getActivity().getSharedPreferences("clickaccount", Context.MODE_PRIVATE);
        account = spref.getString("account", null);
        type = spref.getString("type", null);
        getClickUserInfoByAccount(getUserInfoAccountUrl, account);

    }

    /**
     * 设置弹窗信息显示
     */
    private void initView() {
          attentionNum.setText("关注："+clickUser.getAttentionnum());
          fansnum.setText("粉丝："+clickUser.getFansnum());
          nickname.setText(clickUser.getNickname());
          personalSign.setText(clickUser.getPersonalsign());
          Glide.with(getActivity()).load(clickUser.getAmatar()).asBitmap().into(userLogo);
          if (type.equals("live")){     //用户为直播用户
              if (MainApplication.loginUser.getAccount().equals(account)){  //登录用户点击头像
                  attentionBtn.setVisibility(View.GONE);
              }else {   //点击其他用户头像，验证是否已经关注
                  if (verifyIsAttentioned(account)){
                      attentionBtn.setText("已关注");
                  }
              }
          }else {   //用户为观众用户
              if (MainApplication.loginUser.getAccount().equals(account)){  //登录用户点击头像
                  attentionBtn.setVisibility(View.GONE);
              }else {   //点击其他用户头像，验证是否已经关注
                  if (verifyIsAttentioned(account)){
                      attentionBtn.setText("已关注");
                  }
              }
          }
          //attentionBtn;
    }

    /** 验证用户是否已经关注
     * @param account 通过account验证是否已经关注该用户
     * */
    private boolean verifyIsAttentioned(String account) {
        for (Attention attention:MainApplication.attentions){
            if (attention.getGzaccount().equals(account)){
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
                    clickUser = GsonUtils.getGson().fromJson(responseData, User.class);
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
    }
}
