package com.example.wkj_pc.fitnesslive.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wkj_pc.fitnesslive.R;


public class AboutUsFragment extends Fragment implements View.OnClickListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        ImageView aboutUsPageTextView = (ImageView) view.findViewById(R.id.about_us_page_destroy_text_view);
        aboutUsPageTextView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_us_page_destroy_text_view:
                FragmentManager manager = getFragmentManager();
                FragmentTransaction tran = manager.beginTransaction();
                tran.replace(R.id.home_main_content_fragment,new OwnUserInfoFragment());
                tran.commit();
                break;
        }
    }
}
