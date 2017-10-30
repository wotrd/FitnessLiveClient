package com.example.wkj_pc.fitnesslive.tools;

import android.util.Log;

/**
 * Created by wkj_pc on 2017/6/16.
 */

public class LogUtils {
    private static final int VERBOSE=0;
    private static final int DEBUG=1;
    private static final int INFO=2;
    private static final int WARN=3;
    private static final int ERROR=4;
    private static final int TAG=0;
    public static void logVerbose(String tag,String content){
        if (VERBOSE>=TAG){
            Log.v(tag,content);
        }
    }
    public static void logDebug(String tag,String content){
        if (DEBUG>=TAG){
            Log.d(tag,content);
        }
    }
    public static void logInfo(String tag,String content){
        if (INFO>=TAG){
            Log.i(tag,content);
        }
    }
    public static void logWarn(String tag,String content){
        if (WARN>=TAG){
            Log.w(tag,content);
        }
    }
}
