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
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.activity.UserInfoShowActivity;
import com.example.wkj_pc.fitnesslive.po.Fans;

import java.util.List;

/**
 * Created by wkj on 2017/11/4.
 */
/** 粉丝页面显示适配器，将粉丝列表进行显示，显示用户昵称账户头像，点击跳转道详细页面 */
public class FansShowAdapter extends RecyclerView.Adapter<FansShowAdapter.FansHolder>{
    private final List<Fans> fans;
    private Context context;

    public FansShowAdapter(List<Fans> fans,Context context) {
        this.fans=fans;
        this.context=context;
    }
    class FansHolder extends RecyclerView.ViewHolder{
        private final ImageView logoImg;
        private final TextView nickname;
        private final TextView account;
        public FansHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,UserInfoShowActivity.class);
                    intent.putExtra("account",fans.get(getAdapterPosition()).getFaccount());
                    context.startActivity(intent);
                }
            });
            logoImg = (ImageView) itemView.findViewById(R.id.fragment_own_relative_user_item_logo_img_view);
            nickname = (TextView) itemView.findViewById(R.id.fragment_own_relative_user_item_nickname);
            account = (TextView) itemView.findViewById(R.id.fragment_own_relatvie_user_item_account);
        }
    }
    @Override
    public FansHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_own_relative_user_item, parent, false);
        return new FansHolder(view);
    }
    @Override
    public void onBindViewHolder(FansHolder holder, int position) {
        Fans fans = this.fans.get(position);
        if (!TextUtils.isEmpty(fans.getFamatar())){
            Glide.with(context).load(fans.getFamatar()).asBitmap().into(holder.logoImg);
        }
        holder.nickname.setText(fans.getFaccount());
        holder.account.setText(fans.getFnickname());
    }
    @Override
    public int getItemCount() {
        if (null!=fans){
            return fans.size();
        }else {
            return 0;
        }
    }
}
