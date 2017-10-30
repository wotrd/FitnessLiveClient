package com.example.wkj_pc.fitnesslive.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wkj_pc.fitnesslive.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DynamicFragment extends Fragment {


    public DynamicFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dynamic, container, false);
    }

}
