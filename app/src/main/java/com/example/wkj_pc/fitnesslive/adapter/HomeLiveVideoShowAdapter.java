package com.example.wkj_pc.fitnesslive.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.activity.LoginActivity;
import com.example.wkj_pc.fitnesslive.activity.WatchUserLiveActivity;
import com.example.wkj_pc.fitnesslive.po.LiveTheme;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wkj_pc on 2017/8/21.
 *  用户直播主页显示adapter
 */

public class HomeLiveVideoShowAdapter extends RecyclerView.Adapter<HomeLiveVideoShowAdapter.ViewHolder>{
    private List<User> liveUsers;
    private Context context;
    private  List<String> tags=new ArrayList<>();
    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView liveUsersAmatar;
        ImageView liveUserBigImg;
        TextView homeUserLiveNumsShow;
        TextView liveUserNicknameShow;
        private final RecyclerView homeLiveUserTag;

        public ViewHolder(View itemView) {
            super(itemView);
            liveUsersAmatar = (CircleImageView) itemView.findViewById(R.id.home_live_user_logo);
            liveUserBigImg = (ImageView)itemView.findViewById(R.id.home_live_user_show_img);
            homeUserLiveNumsShow = (TextView) itemView.findViewById(R.id.home_user_live_num_show_text_view);
            liveUserNicknameShow = (TextView) itemView.findViewById(R.id.home_live_user_nickname_show_text_view);
            homeLiveUserTag = (RecyclerView) itemView.findViewById(R.id.home_live_user_tag_recyclerview);
            liveUserBigImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=null;
                    if (null==MainApplication.loginUser){
                        intent=new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }else {
                        intent=new Intent(context, WatchUserLiveActivity.class);
                        intent.putExtra("liveuseraccount",liveUsers.get(getAdapterPosition()).getAccount());
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    public HomeLiveVideoShowAdapter(List<User> homeUserLives,Context context) {
        this.context=context;
        liveUsers=homeUserLives;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_user_live_show_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = liveUsers.get(position);
        if (TextUtils.isEmpty(user.getAmatar())){
            holder.liveUsersAmatar.setImageBitmap(MainApplication.amatarBitmap);
        }else {
            Glide.with(context).load(user.getAmatar()).asBitmap().into(holder.liveUsersAmatar);
        }
        if (TextUtils.isEmpty(user.getLivebigpic())){
            holder.liveUserBigImg.setImageBitmap(MainApplication.bigLiveBitmap);
        }else {
            Glide.with(context).load(user.getLivebigpic()).asBitmap().into(holder.liveUserBigImg);
        }
        holder.homeUserLiveNumsShow.setText(user.getFansnum()+"人再看");
        holder.liveUserNicknameShow.setText(user.getNickname());
        if (MainApplication.liveThemes!=null){
            List<LiveTheme> themes = MainApplication.liveThemes;
            tags.clear();
            for ( int i=0;i<themes.size();i++){
                LiveTheme liveTheme=themes.get(i);
                if (liveTheme.getUid().equals(user.getUid()))
                {
                    tags.add(liveTheme.getLttheme());
                }
            }
            initRecyelerView(holder.homeLiveUserTag,tags );
        }
    }
    public void initRecyelerView(RecyclerView recyclerView,List<String> tags){
        LiveUserTagAdapter adapter = new LiveUserTagAdapter(tags);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    public int getItemCount() {
        return liveUsers.size();
    }
}
