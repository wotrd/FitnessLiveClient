package com.example.wkj_pc.fitnesslive.tools;

import com.google.gson.Gson;

/**
 * Created by wkj_pc on 2017/6/13.
 */

public class GsonUtils {
    private static Gson instance;
    private GsonUtils(){
    }
    public static Gson getGson(){
        if (null==instance){
            synchronized(GsonUtils.class) {
                return (null==instance)?new Gson():instance;
            }
        }else {
            return instance;
        }
    }
}
