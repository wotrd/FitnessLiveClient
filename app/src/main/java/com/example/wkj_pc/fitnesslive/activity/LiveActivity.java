package com.example.wkj_pc.fitnesslive.activity;

import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.adapter.WatchUserLiveAdapter;
import com.example.wkj_pc.fitnesslive.adapter.LiveChattingMessagesAdapter;
import com.example.wkj_pc.fitnesslive.fragment.BottomSheetDialogFrag;
import com.example.wkj_pc.fitnesslive.fragment.LiveUserBottomInfoToastFragment;
import com.example.wkj_pc.fitnesslive.po.LiveChattingMessage;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LogUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.OkHttpClientFactory;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import com.github.faucamp.simplertmp.RtmpHandler;
import com.google.gson.reflect.TypeToken;
import com.seu.magicfilter.utils.MagicFilterType;
import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncodeHandler;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.SrsRecordHandler;
import java.io.IOException;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


public class LiveActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.live_chatting_message_recycler_view)
    RecyclerView liveChattingMessageRecyclerView;   //直播聊天信息显示
    @BindView(R.id.login_live_logo)     //直播人logo
    ImageView loginLiveLogo;
    @BindView(R.id.watch_people_number) //观看直播人数
            TextView watchPeopleNumber;
    @BindView(R.id.attention_user_show_recycler_view)   //观众的logo
            RecyclerView attentionUserRcyclerView;
    @BindView(R.id.change_beauty_spinner)   //改变滤镜
            Spinner changeBeautySpinner;
    @BindView(R.id.start_live_btn)      //开始直播按钮
            Button mPublishBtn;
    @BindView(R.id.editText)        //聊天信息输入框
            EditText editText;
    @BindView(R.id.swCam)           //切换摄像头
            ImageView mCameraSwitchBtn;
    @BindView(R.id.close_live_icon)     //关闭直播按钮
            ImageView closeLiveIconBtn;
    @BindView(R.id.begin_live_show_linearlayout)    // 底部线性布局
            LinearLayout bottomLiveShowlinearLayout;

    private SrsCameraView liveView;
    private String[] clarifyitems = new String[]{"BEAUTY", "COOL", "EARLYBIRD", "EVERGREEN", "N1977", "NOSTALGIA",
            "ROMANCE", "SUNRISE", "SUNSET", "TENDER", "TOASTER2", "VALENCIA", "WALDEN", "WARM", "NONE"};
    //滤镜类型
    private String pushVideoStreamUrl;  //srs服务器推流地址
    private List<Integer> amatarLists = new ArrayList<>();  //观看者头像集合
    private String messageWebSocketUrl; //直播聊天传输地址
    private SrsPublisher mPublisher;       //直播推流发着者
    private WebSocket baseWebSocket; //聊天用websocket
    public  List<LiveChattingMessage> liveMessages = new ArrayList<>();//直播聊天信息
    private LiveChattingMessagesAdapter chattingAdapter;
    private LiveChattingMessage message;
    private TextView fansPeopleNumber;//粉丝数量
    private String closeLiveStatusUrl;
    private Timer timer;
    private List<User> watcherUsers;//观看人信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        设置页面布局方向，当该window对用户可见时，让设备屏幕处于高亮（bright）状态。
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_live);
        ButterKnife.bind(this);
        closeLiveStatusUrl = getResources().getString(R.string.app_customer_live_closeLiveStatusUrl);
        /* 获取websocket地址，设置聊天*/
        fansPeopleNumber= (TextView) findViewById(R.id.fans_people_number);
        messageWebSocketUrl = getResources().getString(R.string.app_message_websocket_customer_live_url) +
                MainApplication.loginUser.getAccount()+"/" + MainApplication.loginUser.getAccount()+"/live";
        getWebSocket(messageWebSocketUrl);  //不用开启子线程,自己开启线程
        /*设置直播推流地址*/

        pushVideoStreamUrl = getResources().getString(R.string.app_video_upload_srs_server_url)+MainApplication.loginUser.getAccount();

        mPublisher = new SrsPublisher((SrsCameraView) findViewById(R.id.live_view));
        if (null!=MainApplication.loginUser.getAmatar()){
            Glide.with(this).load(MainApplication.loginUser.getAmatar()).asBitmap().into(loginLiveLogo);
        }
        loginLiveLogo.setImageResource(R.mipmap.ic_launcher);
        /*设置观看者横向显示*/
        initAmatartRecyclerView();
        fansPeopleNumber.setText("粉丝: "+MainApplication.loginUser.getFansnum());
        /*设置聊天信息展示*/
        initChattingMessageShowRecyclerView();
        mPublishBtn.setOnClickListener(this);
        loginLiveLogo.setOnClickListener(this);
        mCameraSwitchBtn.setOnClickListener(this);
        initPublisher();
        closeLiveIconBtn.setOnClickListener(this);
        initBeautySpinner();
    }
    /* 直播间聊天websocket*/
    public void getWebSocket(String address){
        Request request=new Request.Builder().url(address)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newWebSocket(request, new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, Response response) {
                super.onOpen(webSocket, response);
                baseWebSocket=webSocket;
                sendPingToServer();
            }
            @Override
            public void onMessage(WebSocket webSocket, String text) {
                super.onMessage(webSocket, text);
               /*处理收到的信息*/
                if (TextUtils.isEmpty(text)) //收到信息为空时，获取 /*如果收到信息为空，则返回不处理*/
                    return;
                /*如果返回信息为success，表示websocket连接建立成功，发送提示信息*/
                if (text.contentEquals("success")){
                    LiveChattingMessage message=new LiveChattingMessage();
                    message.setMid(0);
                    message.setFrom(MainApplication.loginUser.getNickname());
                    message.setTo("server");
                    message.setContent("来到直播间！");
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd:HH/mm/SS");
                    message.setTime(format.format(new Date()));
                    message.setIntent(1);
                    webSocket.send( GsonUtils.getGson().toJson(message));
                }else {
                    try{
                        message = GsonUtils.getGson().fromJson(text, LiveChattingMessage.class);
                        if (message.getIntent() == 1){    //聊天
                            liveMessages.add(message);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    chattingAdapter.notifyItemInserted(liveMessages.size()-1);
                                    liveChattingMessageRecyclerView.scrollToPosition(liveMessages.size()-1);
                                }
                            });
                        }else if (message.getIntent() == 2 ){  //粉丝
                            final int fansnum=message.getFansnumber();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    fansPeopleNumber.setText("粉丝:"+fansnum);
                                }
                            });
                        }
                    }catch (Exception e){
                        /** 发生异常后，验证时候能装换成用户集合，然后展示在listview中*/
                        try {
                            watcherUsers = GsonUtils.getGson().fromJson(text, new TypeToken<List<User>>() {
                           }.getType());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    initAmatartRecyclerView();
                                }
                            });
                        }catch (Exception e1){
                            e1.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                super.onClosing(webSocket, code, reason);
                baseWebSocket=null;
            }
            @Override
            public void onClosed(WebSocket webSocket, int code, String reason) {
                super.onClosed(webSocket, code, reason);
                baseWebSocket=null;
            }
            @Override
            public void onFailure(WebSocket webSocket, Throwable t, Response response) {
                super.onFailure(webSocket, t, response);
                baseWebSocket=null;
            }
        });
    }
    /** 设置观众和直播员头像显示 */
    private void initAmatartRecyclerView() {
        if (watcherUsers!=null){
            watchPeopleNumber.setText("观看人数:"+watcherUsers.size());
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            attentionUserRcyclerView.setLayoutManager(layoutManager);
            WatchUserLiveAdapter adapter = new WatchUserLiveAdapter(watcherUsers,this,getSupportFragmentManager());
            attentionUserRcyclerView.setAdapter(adapter);
        }
    }
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_live_logo:
                new LiveUserBottomInfoToastFragment().show(getSupportFragmentManager(),"dialog");
                break;
            case R.id.start_live_btn:
                //开始
                mPublishBtn.setVisibility(View.GONE);
                bottomLiveShowlinearLayout.setVisibility(View.VISIBLE);
                mPublisher.startPublish(pushVideoStreamUrl);
                mPublisher.startCamera();
                break;
            //停止推流
            case R.id.close_live_icon:
                mPublisher.stopRecord();
                mPublisher.stopEncode();
                mPublisher.stopPublish();
                mPublisher.stopCamera();
                finish();
                break;
            //切换摄像头
            case R.id.swCam:
                mPublisher.switchCameraFace((mPublisher.getCamraId() + 1) % Camera.getNumberOfCameras());
                break;
        }
    }
    /** 发送直播聊天信息到服务器 */
    public void sendLiveChattingMessage(View view){
        String editTextMsg = editText.getText().toString().trim();
        if (TextUtils.isEmpty(editTextMsg)){
            return;
        }
        final LiveChattingMessage sendMsg=new LiveChattingMessage();
        sendMsg.setFrom(MainApplication.loginUser.getNickname());
        sendMsg.setContent(editTextMsg);
        sendMsg.setTo("server");
        sendMsg.setMid(0);
        sendMsg.setIntent(1);
        SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
        sendMsg.setTime(format.format(new Date()));
        if (null!=baseWebSocket){
            baseWebSocket.send(GsonUtils.getGson().toJson(sendMsg));
        }else{
           getWebSocket(messageWebSocketUrl);
           baseWebSocket.send(GsonUtils.getGson().toJson(sendMsg));
        }
        editText.setText("");
    }
    //设置心跳防止websocket断线
    public void sendPingToServer(){
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (null!=baseWebSocket){
                    baseWebSocket.send("");
                }
            }
        },0,3000);
    }
    /*设置直播聊天信息展示*/
    public void initChattingMessageShowRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        liveChattingMessageRecyclerView.setLayoutManager(layoutManager);
        chattingAdapter = new LiveChattingMessagesAdapter(liveMessages);
        liveChattingMessageRecyclerView.setAdapter(chattingAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mPublisher.resumeRecord();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mPublisher.pauseRecord();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        if (null!=baseWebSocket) {
            baseWebSocket.cancel();
        }
        LoginUtils.closeLiveStatus(MainApplication.loginUser.getAccount(),closeLiveStatusUrl,new Callback(){
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {}
        });
        mPublisher.stopPublish();
        mPublisher.stopRecord();
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPublisher.stopEncode();
        mPublisher.stopRecord();
        mPublisher.setScreenOrientation(newConfig.orientation);
        if (mPublishBtn.getVisibility() == View.GONE) {
            mPublisher.startEncode();
        }
        mPublisher.startCamera();
    }
    /*  设置发布直播视频*/
    private void initPublisher() {
        //设置编码状态回调
        mPublisher.setEncodeHandler(new SrsEncodeHandler(new SrsEncodeHandler.SrsEncodeListener() {
            @Override
            public void onNetworkWeak() {
                ToastUtils.showToast(getApplicationContext(), "网络型号弱", Toast.LENGTH_SHORT);
            }
            @Override
            public void onNetworkResume() {}
            @Override
            public void onEncodeIllegalArgumentException(IllegalArgumentException e) {}
        }));
        mPublisher.setRecordHandler(new SrsRecordHandler(new SrsRecordHandler.SrsRecordListener() {
            @Override
            public void onRecordPause() {
                ToastUtils.showToast(getApplicationContext(), "Record paused", Toast.LENGTH_SHORT);
            }
            @Override
            public void onRecordResume() {
                ToastUtils.showToast(getApplicationContext(), "Record resumed", Toast.LENGTH_SHORT);
            }
            @Override
            public void onRecordStarted(String msg) {
                ToastUtils.showToast(getApplicationContext(), "Recording file: " + msg, Toast.LENGTH_SHORT);
            }
            @Override
            public void onRecordFinished(String msg) {
                ToastUtils.showToast(getApplicationContext(), "MP4 file saved: " + msg, Toast.LENGTH_SHORT);
            }
            @Override
            public void onRecordIllegalArgumentException(IllegalArgumentException e) {}
            @Override
            public void onRecordIOException(IOException e) {}
        }));
        //rtmp推流状态回调
        mPublisher.setRtmpHandler(new RtmpHandler(new RtmpHandler.RtmpListener() {
            @Override
            public void onRtmpConnecting(String msg) {
                ToastUtils.showToast(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            }
            @Override
            public void onRtmpConnected(String msg) {
                ToastUtils.showToast(getApplicationContext(), msg, Toast.LENGTH_SHORT);
            }
            @Override
            public void onRtmpVideoStreaming() {}
            @Override
            public void onRtmpAudioStreaming() {}
            @Override
            public void onRtmpStopped() {
                ToastUtils.showToast(getApplicationContext(), "已停止", Toast.LENGTH_SHORT);
            }
            @Override
            public void onRtmpDisconnected() {
                ToastUtils.showToast(getApplicationContext(), "未连接服务器", Toast.LENGTH_SHORT);
            }
            @Override
            public void onRtmpVideoFpsChanged(double fps) {}
            @Override
            public void onRtmpVideoBitrateChanged(double bitrate) {}
            @Override
            public void onRtmpAudioBitrateChanged(double bitrate) {}
            @Override
            public void onRtmpSocketException(SocketException e) {}
            @Override
            public void onRtmpIOException(IOException e) {}
            @Override
            public void onRtmpIllegalArgumentException(IllegalArgumentException e) {}
            @Override
            public void onRtmpIllegalStateException(IllegalStateException e) {}
        }));
        //预览分辨率
        mPublisher.setPreviewResolution(1280, 720);
        //推流分辨率
        mPublisher.setOutputResolution(720, 1280);
        //传输率
        mPublisher.setVideoHDMode();
        //开启美颜（其他滤镜效果在MagicFilterType中查看）
        mPublisher.switchCameraFilter(MagicFilterType.CALM);
        //打开摄像头，开始预览（未推流）
        mPublisher.startCamera();
    }
    /*  设置美颜效果*/
    private void initBeautySpinner() {
        final Spinner changeBeautySp = (Spinner) findViewById(R.id.change_beauty_spinner);
        changeBeautySp.setDropDownWidth(300);
        changeBeautySp.setPrompt("滤镜");
        changeBeautySp.setDropDownHorizontalOffset(20);
        changeBeautySp.setGravity(Gravity.CENTER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, clarifyitems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        changeBeautySp.setAdapter(adapter);
        changeBeautySp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        mPublisher.switchCameraFilter(MagicFilterType.BEAUTY);
                        break;
                    case 2:
                        mPublisher.switchCameraFilter(MagicFilterType.COOL);
                        break;
                    case 3:
                        mPublisher.switchCameraFilter(MagicFilterType.EARLYBIRD);
                        break;
                    case 4:
                        mPublisher.switchCameraFilter(MagicFilterType.EVERGREEN);
                        break;
                    case 5:
                        mPublisher.switchCameraFilter(MagicFilterType.N1977);
                        break;
                    case 6:
                        mPublisher.switchCameraFilter(MagicFilterType.NOSTALGIA);
                        break;
                    case 7:
                        mPublisher.switchCameraFilter(MagicFilterType.ROMANCE);
                        break;
                    case 8:
                        mPublisher.switchCameraFilter(MagicFilterType.SUNRISE);
                        break;
                    case 9:
                        mPublisher.switchCameraFilter(MagicFilterType.SUNSET);
                        break;
                    case 10:
                        mPublisher.switchCameraFilter(MagicFilterType.TENDER);
                        break;
                    case 11:
                        mPublisher.switchCameraFilter(MagicFilterType.TOASTER2);
                        break;
                    case 12:
                        mPublisher.switchCameraFilter(MagicFilterType.VALENCIA);
                        break;
                    case 13:
                        mPublisher.switchCameraFilter(MagicFilterType.WALDEN);
                        break;
                    case 14:
                        mPublisher.switchCameraFilter(MagicFilterType.WARM);
                        break;
                    case 15:
                    default:
                        mPublisher.switchCameraFilter(MagicFilterType.NONE);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
}
