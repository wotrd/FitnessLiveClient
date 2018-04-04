package com.example.wkj_pc.fitnesslive.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.activity.UserInfoShowActivity;
import com.example.wkj_pc.fitnesslive.po.Attention;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;
/**
 * Created by wkj on 2017/11/4.
 */
/** 关注页面显示适配器，将关注列表进行显示，显示用户昵称账户头像，点击跳转道详细页面 */
public class AttentionsShowAdapter extends RecyclerView.Adapter<AttentionsShowAdapter.AttentionViewHolder>{
    private final List<Attention> attentions;
    private final Context context;

    class AttentionViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView logoImg;
        private final TextView account;
        private final TextView nickname;

        public AttentionViewHolder(View itemView) {
            super(itemView);
            /** 跳转道个人信息页面进行展示*/
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UserInfoShowActivity.class);
                    intent.putExtra("account",attentions.get(getAdapterPosition()).getGzaccount());
                    intent.putExtra("target","attention");
                    context.startActivity(intent);
                }
            });
            nickname = (TextView) itemView.findViewById(R.id.fragment_own_relative_user_item_nickname);
            account = (TextView) itemView.findViewById(R.id.fragment_own_relatvie_user_item_account);
            logoImg = (CircleImageView) itemView.findViewById(R.id.fragment_own_relative_user_item_logo_img_view);
        }
    }
    @Override
    public void onBindViewHolder(AttentionViewHolder holder, int position) {
        Attention attention = attentions.get(position);
        holder.account.setText(attention.getGzaccount());
        holder.nickname.setText(attention.getGznickname());
        if (!TextUtils.isEmpty(attention.getGzamatar())){
            Glide.with(context).load(attention.getGzamatar()).asBitmap().into(holder.logoImg);
        }
    }
    @Override
    public AttentionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_own_relative_user_item, parent, false);
        return new AttentionViewHolder(view);
    }
    public AttentionsShowAdapter(List<Attention> attentions, Context context) {
        this.attentions=attentions;
        this.context=context;
    }
    @Override
    public int getItemCount() {
        if (null!=attentions){
            return attentions.size();
        }else {
            return 0;
        }
    }

}
