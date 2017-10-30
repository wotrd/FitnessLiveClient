package com.example.wkj_pc.fitnesslive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.tools.LogUtils;

/**
 * Created by wkj_pc on 2017/8/8.
 */

public class NetWorkReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //设置当前网络状态
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (null==networkInfo)
        {
            MainApplication.networkinfo=false;
            LogUtils.logDebug("NETWORKRECEIVER",false+"");
        }else {
            LogUtils.logDebug("NETWORKRECEIVER",networkInfo.isAvailable()+"");
            MainApplication.networkinfo=networkInfo.isAvailable();
        }
    }
}
