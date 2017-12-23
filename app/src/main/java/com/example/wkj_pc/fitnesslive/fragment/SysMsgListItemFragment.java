package com.example.wkj_pc.fitnesslive.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.SysMessage;
import org.litepal.crud.DataSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *显示列表信息
 */
public class SysMsgListItemFragment extends Fragment {


    private TextView title;
    private TextView content;
    private TextView time;
    private SysMessage sysMessage;
    private SharedPreferences sysmessage_id;
    private String smTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sysmessage_id = getActivity().getSharedPreferences("sysmessage_id", Context.MODE_PRIVATE);
        smTime = sysmessage_id.getString("sm_time", "");
        List<SysMessage> sysMessages = DataSupport.where("time=" + smTime).find(SysMessage.class);
        if (null!= sysMessages && sysMessages.size()>0){
            sysMessage = sysMessages.get(0);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        if (null==sysMessage) return;
        if (!TextUtils.isEmpty(sysMessage.getTitle())){
            title.setText(sysMessage.getTitle());
        }
        content.setText("\u3000\u3000"+sysMessage.getContent());
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd:hh:MM");
        Date date = new Date();
        date.setTime(Long.parseLong(sysMessage.getTime()));
        time.setText(format.format(date));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_sys_msg_list_item2, container, false);
        title = inflate.findViewById(R.id.fragment_sys_msg_lsit_item2_title);
        content = inflate.findViewById(R.id.fragment_sys_msg_lsit_item2_content);
        time = inflate.findViewById(R.id.fragment_sys_msg_list_item2_time);
        ImageView backImg = inflate.findViewById(R.id.fragment_sys_msg_item_back_img_view);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataSupport.deleteAll(SysMessage.class,"time=?",smTime);
                FragmentManager manager = getFragmentManager();
                FragmentTransaction tran = manager.beginTransaction();
                tran.replace(R.id.sys_message_content_fragment,new SysMsgListFragment());
                tran.commit();
            }
        });
        return inflate;
    }

}
