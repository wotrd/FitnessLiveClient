package com.example.wkj_pc.fitnesslive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.Attention;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wkj on 2017/11/4.
 */

public class AttentionsShowAdapter extends RecyclerView.Adapter<AttentionsShowAdapter.AttentionViewHolder>{
    private final List<Attention> attentions;
    private final Context context;

    class AttentionViewHolder extends RecyclerView.ViewHolder {

        private final CircleImageView logoImg;
        private final TextView account;
        private final TextView nickname;

        public AttentionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ToastUtils.showToast(context,attentions.get(getAdapterPosition()).getGzaccount(), Toast.LENGTH_SHORT);
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
