package com.example.wkj_pc.fitnesslive.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wkj_pc.fitnesslive.R;

import java.util.List;

/**
 * Created by wkj_pc on 2017/8/22.
 */

public class LiveUserTagAdapter extends RecyclerView.Adapter<LiveUserTagAdapter.ViewHolder> {
    private List<String> tags;
    public LiveUserTagAdapter(List<String> tags) {
        this.tags=tags;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView liveTag;
        public ViewHolder(View itemView) {
            super(itemView);
            liveTag = (TextView) itemView.findViewById(R.id.home_live_user_show_style_text_view);
        }
    }
    @Override
    public LiveUserTagAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_user_tag_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LiveUserTagAdapter.ViewHolder holder, int position) {
        holder.liveTag.setText(tags.get(position));
    }
    @Override
    public int getItemCount() {
        return tags.size();
    }
}
