package com.example.wkj_pc.fitnesslive.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wkj_pc.fitnesslive.R;

import java.util.List;

/**
 * Created by wkj_pc on 2017/9/8.
 */

public class LiveThemeEditSetAdapter extends RecyclerView.Adapter<LiveThemeEditSetAdapter.ViewHolder>{
    private List<String> nativeLiveThemes;
    public LiveThemeEditSetAdapter(List<String> nativeLiveThemes) {
        this.nativeLiveThemes=nativeLiveThemes;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.prepared_live_themes_edit_items,
                parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.livetheme.setText(nativeLiveThemes.get(position));
        holder.destroyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nativeLiveThemes.size()>0){
                    nativeLiveThemes.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position,getItemCount());
                }
            }
        });
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView livetheme;
        private final ImageView destroyImg;
        public ViewHolder(View itemView) {
            super(itemView);
            livetheme = (TextView) itemView.findViewById(R.id.prepared_live_theme_edit_text_view);
            destroyImg = (ImageView) itemView.findViewById(R.id.prepared_live_theme_edit_destroy_img_view);
        }
    }

    @Override
    public int getItemCount() {
        return nativeLiveThemes.size();
    }
}
