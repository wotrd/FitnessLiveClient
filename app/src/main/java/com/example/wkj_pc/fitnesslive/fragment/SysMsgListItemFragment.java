package com.example.wkj_pc.fitnesslive.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.wkj_pc.fitnesslive.R;
/**
 *显示列表信息
 */
public class SysMsgListItemFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_sys_msg_list_item2, container, false);
        ImageView backImg = inflate.findViewById(R.id.fragment_sys_msg_item_back_img_view);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return inflate;
    }

}
