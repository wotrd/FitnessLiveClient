package com.example.wkj_pc.fitnesslive.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.wkj_pc.fitnesslive.R;
import com.jph.takephoto.model.TImage;

import java.io.File;

public class PicResultActivity extends Activity {
    TImage image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_result);
        image= (TImage) getIntent().getSerializableExtra("images");
        showImg();
    }
    private void showImg() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.llImages);
        View view = LayoutInflater.from(this).inflate(R.layout.image_show, null);
        ImageView imageView1 = (ImageView) view.findViewById(R.id.imgShow1);
        Glide.with(this).load(new File(image.getCompressPath())).into(imageView1);
        linearLayout.addView(view);

    }
}
