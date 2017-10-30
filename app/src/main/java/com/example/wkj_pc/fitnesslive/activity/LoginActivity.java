package com.example.wkj_pc.fitnesslive.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.service.LoginService;
import com.example.wkj_pc.fitnesslive.tools.AlertDialogTools;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LogUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import com.mancj.slideup.SlideUp;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WbAuthListener;
import com.sina.weibo.sdk.auth.WbConnectErrorMessage;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_toolbar)
    Toolbar loginToolbar;
    @BindView(R.id.user_login_mobile_num_edit_text)
    EditText mobileNum;
    @BindView(R.id.edit_password)
    EditText editPassword;
    @BindView(R.id.open_slide_select_login_img)
    ImageView openSlideSelectLoginImg;
    @BindView(R.id.imageView)
    ImageView imageView;
    @BindView(R.id.close_select_slide_btn)
    LinearLayout closeSelectSlideBtn;
    @BindView(R.id.qq_login_view)
    ImageView qqLoginView;
    @BindView(R.id.wechat_login_view)
    ImageView wechatLoginView;
    @BindView(R.id.weibo_login_view)
    ImageView weiboLoginView;
    @BindView(R.id.show_slide_out_img)
    LinearLayout showSlideOutImg;
    @BindView(R.id.account_linearlayout)
    LinearLayout accountLinearlayout;
    @BindView(R.id.password_linearlayout)
    LinearLayout passwordLinearlayout;
    private String loginUrl;
    private Tencent mTencent;
    private User user;

    /** 微信*/
    public static final String APPID="wxc2ddb5d13625b5f5";
    public static final String APPSECRET="90895c3c8ecc7a8d443d55bb74233ec1";

    private SharedPreferences cookieSp;
    private SharedPreferences.Editor editor;
    public static final int LOGINSUCCESS=1;
    public static final int LOGINFAILED=0;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
           switch (msg.what){
               case LOGINFAILED:
                   ToastUtils.showToast(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT);
                   break;
           }
        }
    };
    private SsoHandler mSsoHandler;
    private Oauth2AccessToken accessToken;
    private String loginType="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_login);
        ButterKnife.bind(this);
        cookieSp = getSharedPreferences("cookie", MODE_PRIVATE);
        editor=cookieSp.edit();
        loginUrl=getString(R.string.app_login_url);
        initActionBar();
        initSlideUp();
        initListener();

    }
    /** 找回密码*/
    public void forgetPassword(View view){
        startActivity(new Intent(this,FindPasswordActivity.class));
    }
    /** 用户注册*/
    public void toRegister(View view){
        startActivity(new Intent(this,RegisterActivity.class));
    }

    /** 响应微博图片点击登录 */
    public void weboLogin(View view){
        loginType="weibo";
        AuthInfo mAuthInfo = new AuthInfo(this, com.example.wkj_pc.fitnesslive.weiboapi.Constants.APP_KEY,
                com.example.wkj_pc.fitnesslive.weiboapi.Constants.REDIRECT_URL,
                com.example.wkj_pc.fitnesslive.weiboapi.Constants.SCOPE);
        WbSdk.install(this,mAuthInfo);
        /*有客户端有限拉起客户端，没有的话使用web认证 */
        mSsoHandler = new SsoHandler(LoginActivity.this);
        mSsoHandler.authorize(new MyWiboAuthListener());

    }


/** 微博认证监听类 */
    class MyWiboAuthListener implements WbAuthListener{

        @Override
        public void onSuccess(Oauth2AccessToken oauth2AccessToken) {

            if (oauth2AccessToken.isSessionValid()) {
                user=new User();
                user.setToken("wb:"+oauth2AccessToken.getToken());
                user.setPhonenum(oauth2AccessToken.getPhoneNum());
                String uid = oauth2AccessToken.getUid();
                user.setAccount(uid);
                /** 获取认证成功者的用户信息，然后，请求服务器 */
                String getUserInfoUrl="https://api.weibo.com/2/users/show.json?access_token="+oauth2AccessToken.getToken()
                        +"&uid="+uid;
                LoginUtils.getUserInfoWithWeibo(getUserInfoUrl, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        LogUtils.logDebug("LoginActivity","--------"+"shibia");
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()){
                            String responseDate = response.body().string();
                            try {
                                JSONObject jsonObject=new JSONObject(responseDate);
                                user.setNickname(jsonObject.getString("screen_name"));
                                user.setName(jsonObject.getString("name"));
                                user.setAmatar(jsonObject.getString("avatar_large"));
                                String gender = jsonObject.getString("gender");
                                if (gender.equals("m")){
                                    gender="男";
                                }else {
                                    gender="女";
                                }
                                user.setGender(gender);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            doLogin();//访问后台服务器
                        }else {
                            ToastUtils.showToast(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT);
                        }
                    }
                 });
            }else {
                ToastUtils.showToast(LoginActivity.this,"登录失败！",Toast.LENGTH_SHORT);
            }
        }
        @Override
        public void cancel() {
            ToastUtils.showToast(LoginActivity.this,
                    "取消认证！", Toast.LENGTH_LONG);
        }
        @Override
        public void onFailure(WbConnectErrorMessage wbConnectErrorMessage) {
            ToastUtils.showToast(LoginActivity.this, wbConnectErrorMessage.getErrorMessage(), Toast.LENGTH_LONG);
        }
    }

    /**
     *    响应微信图片点击进行登录
     */
    public void wechatLogin(View view){
        IWXAPI wxapi = WXAPIFactory.createWXAPI(this, APPID, true);
        if (!wxapi.isWXAppInstalled()) {
            ToastUtils.showToast(this, "您还未安装微信客户端", Toast.LENGTH_SHORT);
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        wxapi.sendReq(req);
    }
    /** 获取微信登录信息 */
    public void getUserInfoWithWechat(){
    }

    /** 获取qq登录用户信息*/
    public void getUserInfoWithQq(){
        UserInfo userInfo = new UserInfo(this,mTencent.getQQToken());
        userInfo.getUserInfo(new IUiListener() {
            @Override
            public void onComplete(Object o) {
                JSONObject object=(JSONObject)o;
                try {
                    if (object.getInt("ret")==0){
                        user.setNickname(object.getString("nickname"));
                        user.setGender(object.getString("gender"));
                        user.setAmatar(object.getString("figureurl_qq_1"));
                    }else {
                        ToastUtils.showToast(LoginActivity.this, "登录失败！", Toast.LENGTH_SHORT);
                    }
                    doLogin();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(UiError uiError) {
            }
            @Override
            public void onCancel() {
            }
        });
    }
    /** 访问后台服务器 */
    public void doLogin(){

        if (!MainApplication.networkinfo){   //如果当前网络不可用的话，停止登录活动
            AlertDialogTools.showDialog(this,R.mipmap.ic_begin_live_icon,true,"确定",null,null,null,"提醒","网络状态异常！");
            return;
        }
        final String cookies = cookieSp.getString("cookie",null);
        String userinfo = GsonUtils.getGson().toJson(user);
        LoginUtils.toRequestServerForLogin(loginUrl, userinfo, cookies, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = handler.obtainMessage();
                message.what=LOGINFAILED;
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String cookie=response.header("set-cookie");
                    if (cookies==null || (!cookies.equals(cookie))) {
                        editor.putString("cookie",cookie);
                        MainApplication.cookie=cookie;
                    }
                    String responseData=response.body().string();
                    try {
                        User user = GsonUtils.getGson().fromJson(responseData, User.class);
                        MainApplication.loginUser=user;
                        setNativeLiveThemes();
                        startService(new Intent(LoginActivity.this, LoginService.class));
                        finish();
                    }catch (Exception e) {
                        Message message = handler.obtainMessage();
                        message.what = LOGINFAILED;
                        handler.sendMessage(message);
                    }
                }
            }
        });
    }


    /* qq登录需要使用的内部类，即监听器，监听登录情况并反馈！ */
    public IUiListener listener=new IUiListener() {
        @Override
        public void onComplete(Object o) {
            if (null==o ){
                ToastUtils.showToast(LoginActivity.this,"登录失败！",Toast.LENGTH_SHORT);
                return;
            }
            JSONObject object=(JSONObject)o;
            if (object.length()==0){
                ToastUtils.showToast(LoginActivity.this,"登录失败！",Toast.LENGTH_SHORT);
                return;
            }
            try {
                if (object.getInt("ret")==0){
                    String token=object.getString(Constants.PARAM_ACCESS_TOKEN);
                    String openid=object.getString(Constants.PARAM_OPEN_ID);
                    String expires=object.getString(Constants.PARAM_EXPIRES_IN);
                    mTencent.setOpenId(openid);
                    mTencent.setAccessToken(token,expires);
                    user=new User();
                    user.setAccount(openid);
                    user.setToken("qq:"+token);
                    getUserInfoWithQq();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onError(UiError uiError) {
        }
        @Override
        public void onCancel() {
        }
    } ;
    /* 响应qq图片点击进行登录 */
    public void qqLogin(View view)
    {
        loginType="qq";
        /*进行权限请求*/
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)!=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,new String []{
                        Manifest.permission.ACCESS_NETWORK_STATE},1);
            }else {
                qqToLogin();
            }
    }
    /*登录qq方法*/
    public void qqToLogin(){
        mTencent = Tencent.createInstance("1106140497", this.getApplicationContext());//app id
        mTencent.login(this, "all",listener);
    }
/*处理请求权限结果*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    qqToLogin();
                }else {
                    ToastUtils.showToast(LoginActivity.this,"permisstion failed!",Toast.LENGTH_SHORT);
                }
                break;
        }
    }
    /** 跳转到注册activity */
    public void roRegister(View view)
    {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    /** 手机号登录 */
    public void toLogin(View view){

        loginType="account";
        if (!MainApplication.networkinfo){   //如果当前网络不可用的话，停止登录活动
            AlertDialogTools.showDialog(this,R.mipmap.ic_begin_live_icon,true,"确定",null,null,null,"提醒","网络状态异常！");
            return;
        }
        if (TextUtils.isEmpty(mobileNum.getText().toString().trim())){
            mobileNum.setError("账号不能为空！");
            return;
        }else if (TextUtils.isEmpty(editPassword.getText().toString().trim())){
            editPassword.setError("密码不能为空！");
            return;
        }
        final User loginUser=new User();
        loginUser.setPhonenum(mobileNum.getText().toString());
        loginUser.setPassword(editPassword.getText().toString());
        loginUser.setToken("mobile");
        String loginInfo = GsonUtils.getGson().toJson(loginUser);
        // 将cookie存储到sp中，以便进入同一个会话中
        final String cookies = cookieSp.getString("cookie", null);
        LoginUtils.toRequestServerForLogin(loginUrl, loginInfo, cookies, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Message message = handler.obtainMessage();
                message.what=LOGINFAILED;
                handler.sendMessage(message);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    String cookie = response.header("set-cookie");
                    if (null==cookies || (!cookie.equals(cookies))){
                        editor.putString("cookie",cookie);
                        MainApplication.cookie=cookie;
                    }
                    String responseData=response.body().string();
                    if (responseData.equals("error")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mobileNum.setError("账号或密码错误！");
                            }
                        });

                    }else if (responseData.equals("none")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mobileNum.setError("账号不存在！");
                            }
                        });
                    }else {
                        try {
                            User user = GsonUtils.getGson().fromJson(responseData, User.class);
                            MainApplication.loginUser=user;
                            setNativeLiveThemes();
                            startService(new Intent(LoginActivity.this,LoginService.class));
                            finish();
                        }catch (Exception e){
                            Message message = handler.obtainMessage();
                            message.what=LOGINFAILED;
                            handler.sendMessage(message);
                        }
                    }
                }else {
                    Message message = handler.obtainMessage();
                    message.what=LOGINFAILED;
                    handler.sendMessage(message);
                }
            }
        });
    }
    private void  setNativeLiveThemes(){
        MainApplication.nativeLiveThemes.clear();
        for (int i=0;i<MainApplication.liveThemes.size();i++) {
            if (MainApplication.loginUser!=null && MainApplication.loginUser.getUid()==
                    MainApplication.liveThemes.get(i).getUid()){
                MainApplication.nativeLiveThemes.add( MainApplication.liveThemes.get(i).getLttheme());
            }
        }
    }
/** 初始化监听事件，设置文本输入框的颜色变化*/
    private void initListener() {
        mobileNum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    accountLinearlayout.setBackgroundResource(R.drawable.shape_stroke_bg_btn);
                }else {
                    accountLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_btn);
                }
            }
        });
        editPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    passwordLinearlayout.setBackgroundResource(R.drawable.shape_stroke_bg_btn);
                }else {
                    passwordLinearlayout.setBackgroundResource(R.drawable.shape_nostroke_bg_btn);
                }
            }
        });
    }
/** 设置第三方滑动view */
    private void initSlideUp() {
        final SlideUp slideUp = new SlideUp(showSlideOutImg);
        slideUp.hideImmediately();
        closeSelectSlideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openSlideSelectLoginImg.setVisibility(View.VISIBLE);
                slideUp.animateOut();
            }
        });
        openSlideSelectLoginImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slideUp.animateIn();
                openSlideSelectLoginImg.setVisibility(View.GONE);
            }
        });
        slideUp.setSlideListener(new SlideUp.SlideListener() {
            @Override
            public void onSlideDown(float v) {
            }

            @Override
            public void onVisibilityChanged(int i) {
                if (i == View.GONE) {
                    openSlideSelectLoginImg.setVisibility(View.VISIBLE);
                }
            }
        });
    }
/** 设置toolbar的显示效果*/
    private void initActionBar() {
        setSupportActionBar(loginToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_action_login_back_gray);
    }
/**  处理菜单的点击事件*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    /*处理qq登录返回的activity结果*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (loginType!="weibo"){
            Tencent.onActivityResultData(requestCode,resultCode,data,listener);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
}
