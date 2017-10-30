package com.example.wkj_pc.fitnesslive.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.text.TextUtils;

import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.LiveTheme;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ThreadPoolExecutorUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutorService;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.os.AsyncTask.execute;

public class LiveService extends Service {
    private String getHomeLiveUserInfoUrl;
    private String getHomeLiveUserTagUrl;
    private ExecutorService cachedThreaPoolExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        cachedThreaPoolExecutors = ThreadPoolExecutorUtils.getCachedThreaPoolExecutors();
        getHomeLiveUserInfoUrl=getResources().getString(R.string.app_customer_live_getHomeLiveUserInfos_url);
        getHomeLiveUserTagUrl=getResources().getString(R.string.app_customer_live_getHomeLivetags_url);
    }
    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        Runnable liveUserRunnable = new Runnable() {
            @Override
            public void run() {
                LoginUtils.longGetUserLiveInfosFromServer(getHomeLiveUserInfoUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        if (!TextUtils.isEmpty(responseData)) {
                            try {
                                List<User> users = GsonUtils.getGson().fromJson(responseData,
                                        new TypeToken<List<User>>() {
                                        }.getType());
                                MainApplication.liveUsers = users;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        };
        Runnable liveThemeRunnable = new Runnable() {
            @Override
            public void run() {
                LoginUtils.longGetUserLiveTagFromServer(getHomeLiveUserTagUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseData = response.body().string();
                        try {
                            List<LiveTheme> liveTags = GsonUtils.getGson().fromJson(responseData,
                                    new TypeToken<List<LiveTheme>>() {
                                    }.getType());
                            MainApplication.liveThemes = liveTags;
                            MainApplication.nativeLiveThemes.clear();
                            for (int i = 0; i < liveTags.size(); i++) {
                                if (null != MainApplication.loginUser && liveTags.get(i).getUid().equals(MainApplication.loginUser.getUid())) {
                                    MainApplication.nativeLiveThemes.add(liveTags.get(i).getLttheme());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        };
        cachedThreaPoolExecutors.execute(liveUserRunnable);
        cachedThreaPoolExecutors.execute(liveThemeRunnable);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerTime= SystemClock.elapsedRealtime()+60*1000;
        Intent intent1=new Intent(this,LoginService.class);
        PendingIntent pi=PendingIntent.getService(this,0,intent1,0);
        if (Build.VERSION.SDK_INT>=23) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME, triggerTime, pi);
        }
        else {
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, triggerTime, pi);
        }
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
