package com.example.wkj_pc.fitnesslive.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.po.UploadVideo;
import java.util.List;

import static android.provider.MediaStore.Video.Thumbnails.MINI_KIND;

//import static io.vov.vitamio.provider.MediaStore.Video.Thumbnails.MINI_KIND;

/**
 * Created by wkj on 2017/9/13.
 */

public class UploadNativeVideoAdapter extends RecyclerView.Adapter<UploadNativeVideoAdapter.ViewHolder>{

    private List<UploadVideo> videos;
    private int lastposition=-1;
    private Context context;
    private final Bitmap checkedbm;
    private final Bitmap uncheckedbm;

    public UploadNativeVideoAdapter(List<UploadVideo> videos,Context context) {
        this.videos=videos;
        this.context=context;
        checkedbm = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.icon_upload_native_video_checked);
        uncheckedbm = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.icon_upload_native_video_check_normal);
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView videoImageView;
        private ImageView checkImg;
        public ViewHolder(View itemView) {
            super(itemView);
            checkImg = (ImageView) itemView.findViewById(R.id.upload_native_video_check_img_view);
            videoImageView = (ImageView) itemView.findViewById(R.id.upload_native_video_show_image_view);
            checkImg.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  if (getAdapterPosition()==lastposition){
                      return;
                  }else {
                      videos.get(getAdapterPosition()).setIsselected(true);
                      if (lastposition!=-1){
                          videos.get(lastposition).setIsselected(false);
                          notifyItemChanged(lastposition);
                      }
                      lastposition=getAdapterPosition();
                      notifyItemChanged(getAdapterPosition());
                  }
              }
          });
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upload_native_video_show_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (videos.get(position).isselected()){
            holder.checkImg.setImageBitmap(checkedbm);
        }else {
            holder.checkImg.setImageBitmap( uncheckedbm);
        }
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videos.get(position).getVideourl(),MINI_KIND);
        holder.videoImageView.setImageBitmap(bitmap);
    }
    @Override
    public int getItemCount() {
        return videos.size();
    }
}