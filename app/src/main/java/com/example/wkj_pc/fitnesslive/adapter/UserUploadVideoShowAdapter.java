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
import com.example.wkj_pc.fitnesslive.activity.VideoPlayerActivity;
import com.example.wkj_pc.fitnesslive.po.UploadVideo;

import java.util.List;

/**
 * Created by wkj on 2017/9/12.
 */

public class UserUploadVideoShowAdapter extends RecyclerView.Adapter<UserUploadVideoShowAdapter.ViewHolder>{
    private List<UploadVideo> uploadVideos;
    private Context context;

    public UserUploadVideoShowAdapter(List<UploadVideo> lists) {
        this.uploadVideos=lists;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTextView;
        private ImageView videoImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = (TextView) itemView.findViewById(R.id.user_upload_video_show_title_text_view);
            videoImageView = (ImageView) itemView.findViewById(R.id.user_upload_video_show_img_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UploadVideo uploadVideo = uploadVideos.get(getAdapterPosition());
                    Intent intent = new Intent(context, VideoPlayerActivity.class);
                    intent.putExtra("videourl",uploadVideo.getVideourl());
                    intent.putExtra("thumbnailurl",uploadVideo.getThumbnailurl());
                    context.startActivity(intent);
                }
            });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_upload_video_show_items,parent,false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        if (!TextUtils.isEmpty(uploadVideos.get(position).getTitle())){
            holder.titleTextView.setText(uploadVideos.get(position).getTitle());
        }
        Glide.with(context).load(uploadVideos.get(position).getThumbnailurl()).asBitmap().into(holder.videoImageView);
    }

    @Override
    public int getItemCount() {
        return uploadVideos.size();
    }

}
