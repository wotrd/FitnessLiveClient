package com.example.wkj_pc.fitnesslive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.User;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wotrd on 2017/12/31.
 * 主页用户搜索显示
 */

public class SearchUserShowAdapter extends RecyclerView.Adapter<SearchUserShowAdapter.ViewHolder> {
    private final Context context;
    private final List<User> searchUsers;
    public SearchUserShowAdapter(List<User> searchUsers, Context context) {
        this.searchUsers = searchUsers;
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
//        private final CircleImageView logo;
        private final TextView personalsign;
        private final TextView account;

        public ViewHolder(View itemView) {
            super(itemView);
            account = itemView.findViewById(R.id.activity_home_search_user_item_account);
            personalsign = itemView.findViewById(R.id.activity_home_search_user_item_personalsign);
//            logo = itemView.findViewById(R.id.activity_home_search_user_item_logo_image_view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = searchUsers.get(position);
        holder.account.setText(user.getAccount());
        if (!TextUtils.isEmpty(user.getPersonalsign())) {
            holder.personalsign.setText(user.getPersonalsign());
        }
     /*   if (!TextUtils.isEmpty(user.getAmatar())){
            Glide.with(context).load(user.getAmatar()).asBitmap().into(holder.logo);
        }*/
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.activity_home_user_search_show_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public int getItemCount() {
        return searchUsers.size();
    }
}
