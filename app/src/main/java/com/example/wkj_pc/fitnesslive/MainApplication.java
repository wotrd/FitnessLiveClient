package com.example.wkj_pc.fitnesslive;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.wkj_pc.fitnesslive.po.Attention;
import com.example.wkj_pc.fitnesslive.po.Fans;
import com.example.wkj_pc.fitnesslive.po.LiveTheme;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.receiver.NetWorkReceiver;
import org.litepal.LitePal;
import java.util.ArrayList;
import java.util.List;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by wkj_pc on 2017/6/11.
 */

public class MainApplication extends Application {
    public static String cookie;
    public static User loginUser;   //普通用户的登录信息
    public static Boolean networkinfo;  //网络状态情况
    public static List<LiveTheme> liveThemes;
    public static String verifyCode; //请求服务器获取的验证码
    public static List<String> nativeLiveThemes;
    public static List<Attention> attentions;
    public static List<Fans> fans;
    private NetWorkReceiver netWorkReceiver=new NetWorkReceiver();
    public static final String THEMID = "themId";
    public static List<User> liveUsers ;
    public static Bitmap amatarBitmap;
    public static Bitmap bigLiveBitmap;
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(getApplicationContext());
        LitePal.getDatabase();
        //极光推送注册
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //注册网络广播监听事件
        IntentFilter filter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(netWorkReceiver,filter);
        nativeLiveThemes=new ArrayList<>();
        amatarBitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_amatar_img);
        bigLiveBitmap=BitmapFactory.decodeResource(getResources(),R.drawable.biglivepic);
    }

    public static Context getContext(){
        return getContext();
    }

}
