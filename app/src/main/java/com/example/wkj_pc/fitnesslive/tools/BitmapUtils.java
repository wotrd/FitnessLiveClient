package com.example.wkj_pc.fitnesslive.tools;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
/**
 * Created by wkj_pc on 2017/6/26.
 */

public class BitmapUtils {

/**
 * 绘制bitmap
 * */
    public static Bitmap decorateBitmapWithNums(Bitmap bitmap, Context context,int count){
        int iconSize= (int) context.getResources().getDimension(android.R.dimen.app_icon_size);
        Canvas canvas=new Canvas(bitmap);
        Paint paint=new Paint();
        paint.setColor(Color.RED);
        paint.setFilterBitmap(true);//设置bitmap的滤镜
        paint.setFlags(Paint.ANTI_ALIAS_FLAG|Paint.DEV_KERN_TEXT_FLAG);//设置消除锯齿
        paint.setTextSize(30);
        paint.setDither(true); //设置防抖动、
        canvas.drawCircle(iconSize+iconSize/3,iconSize/3,iconSize/4,paint);
        paint.setColor(Color.WHITE);
        paint.setFakeBoldText(true);
        paint.setTextSize(60);
        canvas.drawText(count+"",iconSize+iconSize/5,iconSize/2,paint);
        return bitmap;
    }
}
