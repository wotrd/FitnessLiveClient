package com.example.wkj_pc.fitnesslive.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.adapter.SysMessageListAdapter;
import com.example.wkj_pc.fitnesslive.po.SysMessage;
import org.litepal.crud.DataSupport;
import java.util.List;

/**
 * 系统消息列表片段
 */
public class SysMsgListFragment extends Fragment {

    private List<SysMessage> messageList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        messageList = DataSupport.where("isread=?","0").
                where("owner='all' or owner=?", MainApplication.loginUser.getAccount()).
                order("time desc").
                find(SysMessage.class);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sys_msg_list, container, false);
        RecyclerView msgs = view.findViewById(R.id.fragment_sys_msg_recycler_view);
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        msgs.setLayoutManager(manager);
        SysMessageListAdapter adapter=new SysMessageListAdapter(messageList);
        msgs.setAdapter(adapter);
        ImageView backimg = view.findViewById(R.id.fragment_sys_msg_back_img_view);
        backimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        return view;
    }
}
