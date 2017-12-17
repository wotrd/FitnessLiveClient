package com.example.wkj_pc.fitnesslive.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by wkj_pc on 2017/8/20.
 */

public class ToastUtils {
    private static Toast toast;
    public static void showToast(Context context,
                                 String content,int time) {
        if (toast == null) {
            toast = Toast.makeText(context, content, time);
        } else {
            toast.setText(content);
        }
        toast.show();
    }
}
