package com.example.wkj_pc.fitnesslive.fragment;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.wkj_pc.fitnesslive.R;

/** 在个人信息页面点击关注和粉丝跳转显示的页面 */
public class RelativeUserInfoFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_relative_user_info, container, false);
    }
}
