package com.example.wkj_pc.fitnesslive.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.Attention;
import com.example.wkj_pc.fitnesslive.po.Fans;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ThreadPoolExecutorUtils;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginService extends Service {
    private String longRequestUrl;
    private ExecutorService cachedThreaPoolExecutors;
    private String fansUserUrl;
    private String attentionUserUrl;

    @Override
    public IBinder onBind(Intent intent) {
        throw null;
    }
    @Override
    public void onCreate() {
        cachedThreaPoolExecutors = ThreadPoolExecutorUtils.getCachedThreaPoolExecutors();
        longRequestUrl = getResources().getString(R.string.app_server_prefix_url)+"customer/login/getUserInfo";
        fansUserUrl = getResources().getString(R.string.app_server_prefix_url)+"customer/login/getFansUserInfo";
        attentionUserUrl = getResources().getString(R.string.app_server_prefix_url)+"customer/login/getAttentionUserInfo";
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (MainApplication.loginUser!=null){
            /** 获取登录用户的信息*/
           LoginUtils.longRequestServer(longRequestUrl, MainApplication.loginUser.getAccount(), MainApplication.cookie, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        try {
                            User loginUser = GsonUtils.getGson().fromJson(responseData, User.class);
                            MainApplication.loginUser = loginUser;
                        } catch (Exception e) {
                            MainApplication.loginUser = null;
                            e.printStackTrace();
                        }
                    }
                });
            /**  获取登录用户的关注和粉丝用户 */
            LoginUtils.getRelativeUserInfo(attentionUserUrl, MainApplication.loginUser.getUid(), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {}
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    try {
                        List<Attention> attentions = GsonUtils.getGson().fromJson(responseData, new TypeToken<List<Attention>>() {
                        }.getType());
                        MainApplication.attentions = attentions;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            LoginUtils.getRelativeUserInfo(fansUserUrl, MainApplication.loginUser.getUid(), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                            try {
                                List<Fans> fans = GsonUtils.getGson().fromJson(responseData, new TypeToken<List<Fans>>() {}.getType());
                                MainApplication.fans = fans;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                });
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            long triggerTime= SystemClock.elapsedRealtime()+25*60*1000;
            Intent intent1=new Intent(this,LoginService.class);
            PendingIntent pi=PendingIntent.getService(this,0,intent1,0);
            if (Build.VERSION.SDK_INT>=23) {
                alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME, triggerTime, pi);
            }
            else {
                alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, triggerTime, pi);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
