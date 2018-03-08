package com.example.wkj_pc.fitnesslive.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.adapter.AttentionsShowAdapter;
import com.example.wkj_pc.fitnesslive.adapter.FansShowAdapter;
import com.example.wkj_pc.fitnesslive.po.Attention;
import com.example.wkj_pc.fitnesslive.po.Fans;
import java.util.List;

/** 在个人信息页面点击关注和粉丝跳转显示的页面 */
public class RelativeUserInfoFragment extends Fragment {

    private ImageView backImg;
    private RecyclerView userRecyclearview;
    private SharedPreferences preferences;
    private String title;
    private List<Fans> fans;
    private List<Attention> attentions;
    private String type;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getActivity().getSharedPreferences("own_userinfo_show_frag_type", Context.MODE_PRIVATE);
        type = preferences.getString("relativetype", "");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_relative_user_info, container, false);

        TextView textView = (TextView) view.findViewById(R.id.own_user_info_fragment_title_content_text_view);
        backImg = (ImageView) view.findViewById(R.id.own_user_info_relative_fragment_back_img_view);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentManager manager = getActivity().getFragmentManager();
                manager.popBackStack();
            }
        });
        userRecyclearview = (RecyclerView) view.findViewById(R.id.own_user_info_user_recycler_view);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        userRecyclearview.setLayoutManager(manager);
        if (type.equals("fansnum")){
            textView.setText("粉丝");
            if (null!=MainApplication.fans){
                FansShowAdapter adpter=new FansShowAdapter(MainApplication.fans,getActivity());
                userRecyclearview.setAdapter(adpter);
            }
        }else {
            textView.setText("关注");
            if (null!=MainApplication.attentions){
                AttentionsShowAdapter adpter=new AttentionsShowAdapter(MainApplication.attentions,getActivity());
                userRecyclearview.setAdapter(adpter);
            }
        }
        return view;
    }
}
