package com.example.wkj_pc.fitnesslive.tools;

import android.util.Base64;


import com.example.wkj_pc.fitnesslive.po.UploadVideo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created by wkj_pc on 2017/6/13.
 */

public class LoginUtils {

    /** 获取微博用户信息*/
    public static void getUserInfoWithWeibo(String url, Callback callback){
         Request request=new Request.Builder().url(url)
                 .build();
         OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 长期请求服务器*/
    public static void toRequestServerForLogin(String requestUrl, String userinfo, String cookie, Callback callback) {
        Request request=null;
        RequestBody body=new FormBody.Builder()
                .add("user",userinfo).build();
        if (null == cookie) {
            request = new Request.Builder().url(requestUrl).post(body).build();
        } else {
            request = new Request.Builder().addHeader("cookie", cookie)
                    .url(requestUrl).post(body).build();
        }
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 登录成功后定期获取用户信息*/
    public static void longRequestServer(String path,String account,String cookie, Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("account",account).build();
        Request request = new Request.Builder().url(path)
                    .post(body).addHeader("cookie",cookie).build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /**退出登录请求*/
    public static void toRequestQuitLogin(String requestUrl,String content, String cookie) {
        RequestBody body=new FormBody.Builder()
                .add("user",content).build();
        Request request=null;
        if (null != cookie){
            request = new Request.Builder().url(requestUrl)
                    .post(body).addHeader("cookie",cookie).build();
        }else {
            request = new Request.Builder().url(requestUrl)
                    .post(body).build();
            try {
                OkHttpClientFactory.getOkHttpClientInstance().newCall(request).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /** 获取主页用户的的直播信息进行展示 */
    public static void longGetUserLiveInfosFromServer(String getHomeLiveUserInfoUrl, Callback callback) {
        Request request=new Request.Builder().url(getHomeLiveUserInfoUrl)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 获取主页用户的的直播风格进行展示 */
    public static void longGetUserLiveTagFromServer(String getHomeLiveTagUrl, Callback callback) {
        Request request=new Request.Builder().url(getHomeLiveTagUrl)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 请求服务器获取信息验证码 */
    public static void getMobileVerifyCodeFromServer(String getVerifycodeUrl, Callback callback) {
        Request request=new Request.Builder().url(getVerifycodeUrl)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 用户修改密码 */
    public static void updateUserPassword(String getVerifycodeUrl ,String mobileNum,String password,Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("mobilenum",mobileNum)
                .add("password",password)
                .build();
        Request request=new Request.Builder().url(getVerifycodeUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 用户注册*/
    public static void registerUser(String registerUserUrl, String mobileNum, String password, Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("mobilenum",mobileNum)
                .add("password",password)
                .build();
        Request request=new Request.Builder().url(registerUserUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 更新用户头像*/
    public static void updateUserAmatar(String updateUserInfoUrl, String account, InputStream inputstream,
                                        Callback callback) {
        byte [] buffer= null;
        try {
            buffer = new byte[inputstream.available()];
            inputstream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody body=new FormBody.Builder()
                .add("account",account)
                .add("content", Base64.encodeToString(buffer,Base64.DEFAULT))
                .add("type", "amatar")
                .build();
        Request request=new Request.Builder().url(updateUserInfoUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 更新用户信息*/
    public static void updateUserEditInfos(String updateUserInfoUrl, String account, String content,
                                           String type, Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("account",account)
                .add("content", content)
                .add("type", type)
                .build();
        Request request=new Request.Builder().url(updateUserInfoUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);

    }
    /** 更新用户性别*/
    public static void updateUserSex(String updateUserInfoUrl, String account, String content,
                                     Callback callback) {
        RequestBody body = new FormBody.Builder()
                .add("account", account)
                .add("content", content)
                .add("type", "sex")
                .build();
        Request request = new Request.Builder().url(updateUserInfoUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 更新用户直播大图*/
    public static void updateUserLiveBigPicUrl(String updateUserInfoUrl, String account, FileInputStream inputStream, Callback callback) {
        byte [] buffer= null;
        try {
            buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        RequestBody body=new FormBody.Builder()
                .add("account",account)
                .add("content", Base64.encodeToString(buffer,Base64.DEFAULT))
                .add("type", "livebigimg")
                .build();
        Request request=new Request.Builder().url(updateUserInfoUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 更新用户直播风格*/
    public static void updateLiveUserThemes(String updateUserLiveThemesUrl, int uid, String content, Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("uid",String.valueOf(uid))
                .add("livethemes", content)
                .build();
        Request request=new Request.Builder().url(updateUserLiveThemesUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);

    }
    /** 用户通过账户上传的视频*/
    public static void getUserUploadVideosByAccount(String getUploadVideoUrl,String account,Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("account",account)
                .build();
        Request request=new Request.Builder().url(getUploadVideoUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 获取用户上传的视频*/
    public static void getUserUploadVideos(String getUploadVideoUrl,int uid,Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("uid",String.valueOf(uid))
                .build();
        Request request=new Request.Builder().url(getUploadVideoUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 关闭用户直播状态*/
    public static void closeLiveStatus(String account,String closeLiveStatusUrl,Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("account", account)
                .build();
        Request request=new Request.Builder().url(closeLiveStatusUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
/** 获取直播用户信息 */
    public static void getLiveUserInfo(String getLiveuserInfoUrl, String liveUserAccount, Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("account",liveUserAccount)
                .build();
        Request request=new Request.Builder()
                .url(getLiveuserInfoUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 获取关注和粉丝的信息*/
    public static void getRelativeUserInfo(String longRequestUrl, int uid, Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("uid",String.valueOf(uid))
                .build();
        Request request=new Request.Builder()
                .url(longRequestUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 获取关注和粉丝的信息*/
    public static void getRelativeUserInfo(String longRequestUrl, String account, Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("account",account)
                .build();
        Request request=new Request.Builder()
                .url(longRequestUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
    /** 设置用户是否关注 */
    public static void setUserIsAttention(String setAttentionUrl, String attention,String type, Callback callback) {
        RequestBody body=new FormBody.Builder()
                .add("attention",attention)
                .add("type",type)
                .build();
        Request request=new Request.Builder()
                .url(setAttentionUrl)
                .post(body)
                .build();
        OkHttpClientFactory.getOkHttpClientInstance().newCall(request).enqueue(callback);
    }
}
