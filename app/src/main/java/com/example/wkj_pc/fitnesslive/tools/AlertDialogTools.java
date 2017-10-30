package com.example.wkj_pc.fitnesslive.tools;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

/**
 * Created by wkj_pc on 2017/8/20.
 */

public class AlertDialogTools {
    public static void showDialog(Context context, int iconid, boolean cancelable, String positiveBtn,
                                  DialogInterface.OnClickListener verifylistener, String negativeBtn ,
                                  DialogInterface.OnClickListener negativeListener,String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setIcon(iconid)
                .setCancelable(cancelable)
                .setTitle(title)
                .setMessage(message);
        if (!TextUtils.isEmpty(positiveBtn)){
            builder.setPositiveButton(positiveBtn, verifylistener);
        }
        if (!TextUtils.isEmpty(negativeBtn)){
            builder.setNegativeButton(negativeBtn, negativeListener);
        }
        builder.create().show();
    }
}
