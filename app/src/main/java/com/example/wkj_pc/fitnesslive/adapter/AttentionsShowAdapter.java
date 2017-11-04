package com.example.wkj_pc.fitnesslive.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.Attention;
import java.util.List;

/**
 * Created by wkj on 2017/11/4.
 */

public class AttentionsShowAdapter extends RecyclerView.Adapter<AttentionsShowAdapter.AttentionViewHolder>{
    private final List<Attention> attentions;
    static class AttentionViewHolder extends RecyclerView.ViewHolder {
        public AttentionViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public void onBindViewHolder(AttentionViewHolder holder, int position) {

    }
    @Override
    public AttentionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_own_relative_user_item, parent, false);
        return new AttentionViewHolder(view);
    }
    public AttentionsShowAdapter(List<Attention> attentions) {
        this.attentions=attentions;
    }
    @Override
    public int getItemCount() {
        return attentions.size();
    }
}
