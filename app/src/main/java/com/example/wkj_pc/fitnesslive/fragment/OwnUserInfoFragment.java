package com.example.wkj_pc.fitnesslive.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.activity.LoginActivity;
import com.example.wkj_pc.fitnesslive.activity.MainActivity;
import com.example.wkj_pc.fitnesslive.activity.SysMessageActivity;
import com.example.wkj_pc.fitnesslive.activity.SysVideoActivity;
import com.example.wkj_pc.fitnesslive.activity.UserInfoEditActivity;
import com.example.wkj_pc.fitnesslive.po.SysMessage;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.BitmapUtils;
import org.litepal.crud.DataSupport;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.content.Context.MODE_PRIVATE;


public class OwnUserInfoFragment extends Fragment implements View.OnClickListener{

    private CircleImageView amatarView; //头像
    private TextView ownNickname;
    private TextView ownAccount;
    private TextView ownUserInfoMyAttention;
    private ImageView ownUserRank;
    private TextView ownUserInfoGrade;
    private LinearLayout ownUserVideoLinearLayout;
    private LinearLayout ownUserInfoAboutUsLinearlayout;
    private LinearLayout ownUserInfoDestroyLinearLayout;
    private ImageView ownMessageReceiverBtn;
    private String loginQuitUrl;
    private SharedPreferences cookieSp;
    private LinearLayout ownUserInfoAmatartAccountLinearLayout;
    private TextView ownFansNum;
    private FragmentTransaction tran;
    private FragmentManager manager;
    private FragmentTransaction tran1;
    private SharedPreferences relativeUsershowfragtype;
    private LinearLayout ownUserInfoMyAttentionLinearLayout;
    private LinearLayout ownUserInfoMyFansLinearLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginQuitUrl=getResources().getString(R.string.app_server_prefix_url)+"customer/login/quitLogin";
        cookieSp = getActivity().getSharedPreferences("cookie", MODE_PRIVATE);
        relativeUsershowfragtype = getActivity().getSharedPreferences("own_userinfo_show_frag_type", MODE_PRIVATE);
        manager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_own_user_info, container, false);
        ImageView ownLiveVideoImageView = (ImageView) view.findViewById(R.id.own_live_video_image_view);
        ownLiveVideoImageView.setOnClickListener(this);
        TextView ownMainPageTextView = (TextView) view.findViewById(R.id.own_main_page_text_view);
        ownMainPageTextView.setOnClickListener(this);
        ownMessageReceiverBtn = (ImageView) view.findViewById(R.id.own_message_receive_btn);
        ownMessageReceiverBtn.setOnClickListener(this);
        amatarView = (CircleImageView) view.findViewById(R.id.icon_amatar_image);
        ownNickname = (TextView) view.findViewById(R.id.own_nickname);
        ownAccount = (TextView) view.findViewById(R.id.own_account);
        ownUserInfoMyAttention = (TextView) view.findViewById(R.id.own_user_info_my_attention);
        ownFansNum = (TextView) view.findViewById(R.id.own_user_fansnum);
        ownUserRank = (ImageView) view.findViewById(R.id.icon_own_user_rank);
        ownUserInfoGrade = (TextView) view.findViewById(R.id.own_user_info_grade);
        ownUserVideoLinearLayout = (LinearLayout) view.findViewById(R.id.own_user_video_linearlayout);
        ownUserVideoLinearLayout.setOnClickListener(this);
        ownUserInfoAboutUsLinearlayout = (LinearLayout) view.findViewById(R.id.own_user_info_about_us_linearlayout);
        ownUserInfoAboutUsLinearlayout.setOnClickListener(this);
        ownUserInfoDestroyLinearLayout = (LinearLayout) view.findViewById(R.id.own_user_info_clear_cache_linearlayout);
        ownUserInfoDestroyLinearLayout.setOnClickListener(this);
        ownUserInfoAmatartAccountLinearLayout = (LinearLayout) view.findViewById(R.id.own_user_info_amatar_account_linearlayout);
        ownUserInfoAmatartAccountLinearLayout.setOnClickListener(this);
        ownUserInfoMyAttentionLinearLayout = (LinearLayout) view.findViewById(R.id.own_user_info_my_attention_linearlayout);
        ownUserInfoMyAttentionLinearLayout.setOnClickListener(this);
        ownUserInfoMyFansLinearLayout = (LinearLayout) view.findViewById(R.id.own_user_fansnum_linearlayout);
        ownUserInfoMyFansLinearLayout.setOnClickListener(this);
        return view;
    }
    /*设置系统消息显示*/
    private void setSysMessageShow() {
        if (null!=MainApplication.loginUser){
            List<SysMessage> messageSList = DataSupport.where("isread = ?", "0").
                    order("time").find(SysMessage.class);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_action_message_receiver)
                    .copy(Bitmap.Config.ARGB_8888, true);
            if (null == messageSList || messageSList.size() < 1) {
                ownMessageReceiverBtn.setImageBitmap(bitmap);
            } else {
                Bitmap showBitmap = BitmapUtils.decorateBitmapWithNums(bitmap, getActivity(), messageSList.size());
                ownMessageReceiverBtn.setImageBitmap(showBitmap);
            }
        }
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            /** 用户个人信息页面点击粉丝按钮，跳转到粉丝列表页面显示 */
            case R.id.own_user_fansnum_linearlayout:
                if (null == MainApplication.loginUser){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else {
                    SharedPreferences.Editor edit = relativeUsershowfragtype.edit();
                    edit.putString("relativetype","fansnum");
                    edit.apply();
                    tran = manager.beginTransaction();
                    tran.replace(R.id.home_main_content_fragment,new RelativeUserInfoFragment());
                    tran.addToBackStack("fans");
                    tran.commit();
                }
                break;
            /** 用户点击个人信息页面，关注按钮跳转到关注用户列表页面显示    */
            case R.id.own_user_info_my_attention_linearlayout:
                if (null == MainApplication.loginUser){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else {
                    SharedPreferences.Editor edit = relativeUsershowfragtype.edit();
                    edit.putString("relativetype","attnetionsnum");
                    edit.apply();
                    tran = manager.beginTransaction();
                    tran.replace(R.id.home_main_content_fragment,new RelativeUserInfoFragment());
                    tran.addToBackStack("fans");
                    tran.commit();
                }
                break;
            case R.id.own_live_video_image_view:
                //滑出直播和拍摄选项
                if (null == MainApplication.loginUser)
                {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else {
                    new BottomSheetDialogFrag().show(MainActivity.manager,"dialog");
                }
                break;
            case R.id.own_main_page_text_view:   //切换到主页fragment
                tran = manager.beginTransaction();
                tran.replace(R.id.home_main_content_fragment,new MainPageFragment());
                tran.commit();
                break;
            case R.id.own_message_receive_btn:  // 跳转去处理系统的通知消息
                if (null==MainApplication.loginUser)
                {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else {
                    startActivity(new Intent(getActivity(), SysMessageActivity.class));
                }
                break;
            case R.id.own_user_info_clear_cache_linearlayout:   //清楚缓存
                DataSupport.deleteAll(User.class,"");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.get(getActivity()).clearDiskCache();
                    }
                }).start();

                break;
            case R.id.own_user_video_linearlayout:   //个人视频
                startActivity(new Intent(getActivity(),SysVideoActivity.class));
                break;
            case R.id.own_user_info_amatar_account_linearlayout:
                if (null==MainApplication.loginUser){
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else {
                    startActivity(new Intent(getActivity(),UserInfoEditActivity.class));
                }
                break;
            case R.id.own_user_info_about_us_linearlayout:   //关于我们
                tran = manager.beginTransaction();
                tran.replace(R.id.home_main_content_fragment,new AboutUsFragment());
                tran.addToBackStack(null);
                tran.commit();
                break;
        }
    }
    /**
     *   /处于活动界面的时候，显示登录信息
     *   有系统消息来到后，显示在toolbar中
     */
    @Override
    public void onResume() {
        super.onResume();
        //显示登录用户信息
        if (null != MainApplication.loginUser) {
            if (!TextUtils.isEmpty(MainApplication.loginUser.getAmatar())){
                Glide.with(this).load(MainApplication.loginUser.getAmatar())
                        .asBitmap().into(amatarView);
            }
            ownNickname.setText("昵称："+MainApplication.loginUser.getNickname());
            ownAccount.setText("账号："+MainApplication.loginUser.getAccount());
            ownUserInfoGrade.setText((null==MainApplication.loginUser.getGrade())?"0":
                    MainApplication.loginUser.getGrade().toString());
            if (null!=MainApplication.loginUser){
                if (MainApplication.loginUser.getGrade()>100){
                 ownUserRank.setImageResource(R.mipmap.grade_huangjin);
                }else if (MainApplication.loginUser.getGrade()>50){
                    ownUserRank.setImageResource(R.mipmap.grade_baiyin);
                }
            }
            ownUserInfoMyAttention.setText((null==MainApplication.loginUser.getAttentionnum())?"0"
                    :MainApplication.loginUser.getAttentionnum()+"");
            ownFansNum.setText((null==MainApplication.loginUser.getFansnum())?"0":
                    MainApplication.loginUser.getFansnum()+"");
        }else {
            amatarView.setImageResource(R.drawable.head_img);
            ownNickname.setText("昵称：小灰灰");
            ownAccount.setText("账号：0000000");
            ownUserInfoGrade.setText("0");
            ownUserRank.setImageResource(R.mipmap.grade_qingtong);
            ownUserInfoMyAttention.setText("0");
            ownFansNum.setText("0");
        }
        if (null!=MainApplication.loginUser){
            setSysMessageShow();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

