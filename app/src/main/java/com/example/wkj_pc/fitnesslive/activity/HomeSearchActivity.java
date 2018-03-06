package com.example.wkj_pc.fitnesslive.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.adapter.SearchUserShowAdapter;
import com.example.wkj_pc.fitnesslive.po.Attention;
import com.example.wkj_pc.fitnesslive.po.Fans;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * 主页用户搜索，在主页fragment的右上角显示图标，点击进入搜索页面
 * 在改页面点击搜索按钮可以将搜索框的用户账户或者昵称进行搜索。
 * 搜索出的结果可以直接进入正在直播用的直播间，也可以点击头像进入该用户的信息页面显示
 * 点击取消按钮，返会搜索本来页面。也可以点击右上角图标退出搜索。
 */
public class HomeSearchActivity extends AppCompatActivity {

    private RecyclerView showUserRecyclerView;
    private EditText input;
    private String searchUserUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        searchUserUrl=getResources().getString(R.string.app_server_prefix_url)+"customer/login/customerSearchUser";

        TextView backText = (TextView) findViewById(R.id.activity_home_user_search_cancel_text_view);
        backText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        showUserRecyclerView = (RecyclerView) findViewById(R.id.activity_home_user_search_show_recycler_view);
        input = (EditText) findViewById(R.id.activity_home_user_search_input_edit_text);
        input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_UP){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    if (!TextUtils.isEmpty(input.getText().toString())){
                        searchUser(input.getText().toString());
                    }
                }
                return false;
            }
        });
    }
    /** 根据输入信息搜索用户 */
    public void searchUser(String searchText){
        LoginUtils.searchUser(searchUserUrl, searchText, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                if (!TextUtils.isEmpty(responseData)){
                    try {
                        final List<User> users = GsonUtils.getGson().fromJson(responseData,new TypeToken<List<User>>(){}.getType());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRecyclerView(users);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else {
                    ToastUtils.showToast(HomeSearchActivity.this,"kongde ",Toast.LENGTH_SHORT);
                }
            }
        });
    }
    /** 展示搜索用户列表 */
    private void initRecyclerView(List<User> users) {
        LinearLayoutManager manager=new LinearLayoutManager(this);
        showUserRecyclerView.setLayoutManager(manager);
        SearchUserShowAdapter adapter=new SearchUserShowAdapter(users, this);
        showUserRecyclerView.setAdapter(adapter);
    }
    @Override
    protected void onDestroy() {
        /** 在退出之前获取登录用户的信息*/
        String longRequestUrl = getResources().getString(R.string.app_server_prefix_url)+"customer/login/getUserInfo";
        LoginUtils.longRequestServer(longRequestUrl, MainApplication.loginUser.getAccount(),
                MainApplication.cookie, new Callback() {
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
        String attentionUserUrl = getResources().getString(R.string.app_server_prefix_url)+"customer/login/getAttentionUserInfo";
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
        String fansUserUrl= getResources().getString(R.string.app_server_prefix_url)+"customer/login/getFansUserInfo";
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
        super.onDestroy();
    }
}
