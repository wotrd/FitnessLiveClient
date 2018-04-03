package com.example.wkj_pc.fitnesslive.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.MainApplication;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.activity.UserInfoShowActivity;
import com.example.wkj_pc.fitnesslive.po.Attention;
import com.example.wkj_pc.fitnesslive.po.User;
import com.example.wkj_pc.fitnesslive.tools.GsonUtils;
import com.example.wkj_pc.fitnesslive.tools.LoginUtils;
import java.io.IOException;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by wotrd on 2017/12/31.
 * 主页用户搜索显示
 */

public class SearchUserShowAdapter extends RecyclerView.Adapter<SearchUserShowAdapter.ViewHolder> {
    private final Context context;
    private final List<User> searchUsers;
    private String isAttentionUrl;

    public SearchUserShowAdapter(List<User> searchUsers, Context context) {
        this.searchUsers = searchUsers;
        this.context = context;
        isAttentionUrl=context.getResources().getString(R.string.app_server_prefix_url)+"customer/live/setUserIsAttention";
    }
    class ViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView logo;
        private final TextView personalsign;
        private final TextView account;
        private final ImageView addAttention;
        private String type="";
        public ViewHolder(View itemView) {
            super(itemView);
            account = itemView.findViewById(R.id.activity_home_search_user_item_account);
            personalsign = itemView.findViewById(R.id.activity_home_search_user_item_personalsign);
            logo = itemView.findViewById(R.id.activity_home_search_user_item_logo_image_view);
            addAttention = itemView.findViewById(R.id.activity_home_search_user_item_add_attention_image_view);
            addAttention.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type.equals("attentioned")){
                        type="canceled";
                        Glide.with(context).load(R.mipmap.icon_home_search_attention).override(70,70).into(addAttention);
                    }else {
                        type="attentioned";
                        Glide.with(context).load(R.mipmap.icon_home_search_success).override(60,60).into(addAttention);
                    }
                    Attention attention=new Attention();
                    attention.setUid(MainApplication.loginUser.getUid());
                    User user=searchUsers.get(getAdapterPosition());
                    attention.setGzphonenumber(user.getPhonenum());
                    attention.setGzamatar(user.getAmatar());
                    attention.setGznickname(user.getNickname());
                    attention.setGzaccount(user.getAccount());
                    LoginUtils.setUserIsAttention(isAttentionUrl, GsonUtils.getGson().toJson(attention), type,
                        new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {}
                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                            }
                        });
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, UserInfoShowActivity.class);
                    intent.putExtra("account",searchUsers.get(getAdapterPosition()).getAccount());
                    context.startActivity(intent);
                }
            });
        }
    }
    private boolean verifyIsAttention(String account){
        if (null==MainApplication.attentions || MainApplication.attentions.size()==0){
            return false;
        }
        for (Attention attention:MainApplication.attentions){
            if (account.equals(attention.getGzaccount())){
                return true;
            }
        }
        return false;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = searchUsers.get(position);
        holder.account.setText(user.getAccount());
        if (!TextUtils.isEmpty(user.getPersonalsign())) {
            holder.personalsign.setText(user.getPersonalsign());
        }
        if (!TextUtils.isEmpty(user.getAmatar())){
            Glide.with(context).load(user.getAmatar()).asBitmap().into(holder.logo);
        }
        if (verifyIsAttention(user.getAccount())){
            holder.type="attentioned";
            Glide.with(context).load(R.mipmap.icon_home_search_success).override(60,60).into(holder.addAttention);
        }else {
            holder.type="canceled";
            Glide.with(context).load(R.mipmap.icon_home_search_attention).override(70,70).into(holder.addAttention);
        }
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
