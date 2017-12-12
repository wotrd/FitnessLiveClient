package com.example.wkj_pc.fitnesslive.adapter;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.fragment.LiveUserBottomInfoToastFragment;
import com.example.wkj_pc.fitnesslive.po.LiveChattingMessage;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.WebSocket;

/**
 * Created by wkj_pc on 2017/7/31.
 */


public class WatchUserLiveAdapter extends RecyclerView.Adapter<WatchUserLiveAdapter.ViewHolder>{

    private final FragmentManager manager;
    private final WebSocket webSocket;
    private final String type;
    private List<User> watchUsers;
    private Context context;
    public static User alertUser;
    public WatchUserLiveAdapter(List<User> users, Context context, FragmentManager manager,String type, WebSocket webSocket){
        watchUsers=users;
        this.manager=manager;
        this.context=context;
        this.webSocket=webSocket;
        this.type=type;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView amatar;
        public ViewHolder(View itemView) {
            super(itemView);
            amatar= (CircleImageView) itemView.findViewById(R.id.circle_image_view);
            amatar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /** 发送更新用户关注信息 */
                    LiveChattingMessage sendMsg=new LiveChattingMessage();
                    sendMsg.setMid(0);
                    sendMsg.setIntent(3);
                    webSocket.send(GsonUtils.getGson().toJson(sendMsg));
                    /** 设置头像列表的用户弹窗 */
                    alertUser=watchUsers.get(getAdapterPosition());
                    SharedPreferences.Editor editor = context.getSharedPreferences("clickamatar",Context.MODE_PRIVATE).edit();
                    editor.putString("account", alertUser.getAccount());
                    editor.putString("type", type);
                    editor.apply();
                    new LiveUserBottomInfoToastFragment().show(manager,"dialog");
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.watch_user_show_reyclerview,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (null!=watchUsers.get(position).getAmatar()){
            Glide.with(context).load(watchUsers.get(position).getAmatar()).asBitmap().into(holder.amatar);
        }else {
            holder.amatar.setImageResource(R.drawable.head_img);
        }
    }
    @Override
    public int getItemCount() {
        return watchUsers.size();
    }
}
