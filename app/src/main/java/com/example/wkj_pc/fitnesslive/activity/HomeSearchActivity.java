package com.example.wkj_pc.fitnesslive.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import com.example.wkj_pc.fitnesslive.R;

/**
 * 主页用户搜索，在主页fragment的右上角显示图标，点击进入搜索页面
 * 在改页面点击搜索按钮可以将搜索框的用户账户或者昵称进行搜索。
 * 搜索出的结果可以直接进入正在直播用的直播间，也可以点击头像进入该用户的信息页面显示
 * 点击取消按钮，返会搜索本来页面。也可以点击右上角图标退出搜索。
 */
public class HomeSearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

    }


}
