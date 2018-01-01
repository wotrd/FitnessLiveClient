package com.example.wkj_pc.fitnesslive.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.UploadVideo;
import java.util.List;

/**
 * Created by wotrd on 20181/1.
 * 系统视频显示adapter
 */

public class SysVideoShowAdapter extends RecyclerView.Adapter<SysVideoShowAdapter.ViewHolder>{
    private final List<UploadVideo> videos;
    private final Context context;

    class ViewHolder extends RecyclerView.ViewHolder{
        private final ImageView bigpic;
        private final TextView title;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            bigpic = itemView.findViewById(R.id.sys_video_show_img_view);
            title = itemView.findViewById(R.id.sys_video_show_title_text_view);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_sys_video_item, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(context).load(videos.get(position).getThumbnailurl()).asBitmap().into(holder.bigpic);
        holder.title.setText(videos.get(position).getTitle());

    }
    @Override
    public int getItemCount() {
        return videos.size();
    }
    public SysVideoShowAdapter(List<UploadVideo> videos, Context context) {
        this.videos=videos;
        this.context=context;
    }
}
