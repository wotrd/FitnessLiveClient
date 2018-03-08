package com.example.wkj_pc.fitnesslive.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.fragment.SysMsgListFragment;

/**
 * 系统消息显示处理activity
 */
public class SysMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sys_message);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction tran = fragmentManager.beginTransaction();
        SysMsgListFragment sysMsgFragment = new SysMsgListFragment();
        tran.add(R.id.sys_message_content_fragment, sysMsgFragment);
        tran.commit();
    }
}
