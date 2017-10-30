package com.example.wkj_pc.fitnesslive.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.activity.HomeSearchActivity;
import com.example.wkj_pc.fitnesslive.activity.LoginActivity;
import com.example.wkj_pc.fitnesslive.activity.MainActivity;
import com.example.wkj_pc.fitnesslive.activity.SysMessageActivity;
import com.example.wkj_pc.fitnesslive.adapter.HomeLiveVideoShowAdapter;
import com.example.wkj_pc.fitnesslive.po.LiveTheme;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.BitmapUtils;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainPageFragment extends Fragment implements View.OnClickListener{

    private FragmentManager manager;
    private ImageView homeMessageReceiverBtn;
    private RecyclerView homeUserLiveShowRecyclerView;
    private LinearLayout bottomLinearLayout;
    private Timer timer;
    private TimerTask timerTask;
    private HomeLiveVideoShowAdapter liveadapter;

    /** 接收到定时器的消息，跟新页面 */
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:     //跟新主要上直播的用户
                    initRecyclerView();
                    break;
            }
        }
    };
    private String getHomeLiveUserInfoUrl;
    private String getHomeLiveUserTagUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        manager=getFragmentManager();
        getHomeLiveUserInfoUrl = getResources().getString(R.string.app_customer_live_getHomeLiveUserInfos_url);
        getHomeLiveUserTagUrl = getResources().getString(R.string.app_customer_live_getHomeLivetags_url);
        getLiveInfos(getHomeLiveUserTagUrl, getHomeLiveUserInfoUrl);
    }
    @Override
    public void onResume() {
        super.onResume();
        /** 设置定时器，定时更新页面*/
        timer=new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (MainApplication.liveUsers!=null){
                    Message message = new Message();
                    message.what=1;
                    handler.sendMessage(message);
                    cancel();
                }
            }
        };
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (MainApplication.liveUsers!=null){
                    Message message = new Message();
                    message.what=1;
                    handler.sendMessage(message);
                }
            }
        },0,2*60*1000);
        timer.schedule(timerTask,0,500);
        setSysMessageShow();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_page, container, false);
        homeMessageReceiverBtn = (ImageView) view.findViewById(R.id.home_message_receive_btn);
        homeMessageReceiverBtn.setOnClickListener(this);
        TextView ownInfo= (TextView) view.findViewById(R.id.home_personinfo_image_view);
        ownInfo.setOnClickListener(this);
        ImageView homeLiveVideoImageView  = (ImageView) view.findViewById(R.id.home_live_video_image_view);
        homeLiveVideoImageView.setOnClickListener(this);
        homeUserLiveShowRecyclerView = (RecyclerView) view.findViewById(R.id.home_user_live_show_recycler_view);
        bottomLinearLayout = (LinearLayout) view.findViewById(R.id.bottom_linearlayout);
        ImageView homeUserSearchImgView= (ImageView) view.findViewById(R.id.home_user_search_img_view);
        homeUserSearchImgView.setOnClickListener(this);
        if (MainApplication.liveUsers!=null){
            initRecyclerView();
        }
        return view;
    }

    /** 初始化直播用户的页面，头像风格和大图 */
    private void initRecyclerView() {
        LinearLayoutManager lMamager=new LinearLayoutManager(getActivity());
        homeUserLiveShowRecyclerView.setLayoutManager(lMamager);
        liveadapter = new HomeLiveVideoShowAdapter(MainApplication.liveUsers,getActivity());
        homeUserLiveShowRecyclerView.setAdapter(liveadapter);
        liveadapter.notifyDataSetChanged();
    }
    /**  fragment创建的时候获取直播用户的信息 */
    private void getLiveInfos(final String getHomeLiveUserTagUrl, final String getHomeLiveUserInfoUrl) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginUtils.longGetUserLiveTagFromServer(getHomeLiveUserTagUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        try{
                            List<LiveTheme> liveTags = GsonUtils.getGson().fromJson(responseData,
                                    new TypeToken<List<LiveTheme>>() {}.getType());
                            MainApplication.liveThemes=liveTags;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                LoginUtils.longGetUserLiveInfosFromServer(getHomeLiveUserInfoUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        if (!TextUtils.isEmpty(responseData)){
                            try{
                                List<User> users= GsonUtils.getGson().fromJson(responseData,
                                        new TypeToken<List<User>>(){}.getType());
                                MainApplication.liveUsers = users;
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }).start();
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.home_live_video_image_view:   //弹出拍摄或者直播图标
                //滑出直播和拍摄选项
                if (null == MainApplication.loginUser)
                {
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }else {
                    new BottomSheetDialogFrag().show(MainActivity.manager,"dialog");
                }
                break;
            case R.id.home_message_receive_btn:     //打开系统消息处理activity
                startActivity(new Intent(getActivity(), SysMessageActivity.class));
                break;
            case R.id.home_user_search_img_view:    //用户直播搜索框
                startActivity(new Intent(getActivity(), HomeSearchActivity.class));
                break;
            case R.id.home_personinfo_image_view:   //切换到个人中心fragment
                FragmentTransaction tran = manager.beginTransaction();
                tran.replace(R.id.home_main_content_fragment,new OwnUserInfoFragment());
                tran.commit();
                break;
        }
    }
    /*设置系统消息显示*/
    private void setSysMessageShow() {
        int count = 3;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.icon_action_message_receiver)
                .copy(Bitmap.Config.ARGB_8888, true);
        if (count < 1) {
            homeMessageReceiverBtn.setImageBitmap(bitmap);
        } else {
            Bitmap showBitmap = BitmapUtils.decorateBitmapWithNums(bitmap, getActivity(), count);
            homeMessageReceiverBtn.setImageBitmap(showBitmap);
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}