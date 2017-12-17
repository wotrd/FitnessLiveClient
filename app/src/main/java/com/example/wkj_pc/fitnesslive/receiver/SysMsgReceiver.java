package com.example.wkj_pc.fitnesslive.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.example.wkj_pc.fitnesslive.po.SysMessage;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import cn.jpush.android.api.JPushInterface;

/**
 * 系统消息推送，可以推送给个人也可以推送给系统
 * 保存到数据库中。
 */

public class SysMsgReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        //String title = bundle.getString(JPushInterface.EXTRA_TITLE);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        //String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        SysMessage message1=new SysMessage();
        message1.setContent("------gagag");
        message1.setFrom("server");
        if (!TextUtils.isEmpty(message)){
            try{
                SysMessage sysMessage = GsonUtils.getGson().fromJson(message, SysMessage.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            message1.save();
        }
    }
}
