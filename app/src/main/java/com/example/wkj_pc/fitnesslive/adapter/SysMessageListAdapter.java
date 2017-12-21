package com.example.wkj_pc.fitnesslive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.SysMessage;
import com.example.wkj_pc.fitnesslive.tools.TimeUtils;
import java.util.List;

/**
 * Created by wotrd on 2017/12/19.
 * 系统消息显示列表adapter
 */

public class SysMessageListAdapter extends RecyclerView.Adapter<SysMessageListAdapter.ViewHolder>{
    private final List<SysMessage> messageList;
    private final Context context;

    public SysMessageListAdapter(List<SysMessage> messageList, Context context) {
        this.messageList=messageList;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.fragment_sys_msg_list_item, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SysMessage message = messageList.get(position);
        holder.content.setText(message.getContent());
        String time = TimeUtils.showTime(Long.parseLong(message.getTime()));
        holder.time.setText(time);
        if (!TextUtils.isEmpty(message.getTitle())){
            holder.title.setText(message.getTitle());
        }
    }
    @Override
    public int getItemCount() {
        return messageList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView title;
        private final TextView content;
        private final TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.fragment_sys_msg_list_item_title_text_view);
            time = itemView.findViewById(R.id.fragment_sys_msg_list_item_time_text_view);
            content = itemView.findViewById(R.id.fragment_sys_msg_list_item_content_text_view);
        }
    }
}
