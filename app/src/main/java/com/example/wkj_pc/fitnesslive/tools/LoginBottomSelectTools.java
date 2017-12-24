package com.example.wkj_pc.fitnesslive.tools;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import com.example.wkj_pc.fitnesslive.R;

/**
 * Created by wotrd on 2017/12/24.
 * 弹出底部qq微信微博登录图标选择
 */

public class LoginBottomSelectTools implements  View.OnClickListener,View.OnTouchListener{
    private PopupWindow popupWindow;
    private ImageView qq, wechat, wb;
    private View mMenuView;
    private Activity mContext;
    private View.OnClickListener clickListener;

    public LoginBottomSelectTools(Activity context, View.OnClickListener clickListener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        this.clickListener=clickListener;
        mContext=context;
        mMenuView = inflater.inflate(R.layout.activity_user_login_select_by_amatar_popupwindow_item, null);
        qq = (ImageView) mMenuView.findViewById(R.id.qq_login_view);
        wechat = (ImageView) mMenuView.findViewById(R.id.wechat_login_view);
        wb = (ImageView) mMenuView.findViewById(R.id.weibo_login_view);
        qq.setOnClickListener(this);
        wechat.setOnClickListener(this);
        wb.setOnClickListener(this);
        popupWindow=new PopupWindow(mMenuView, ActionBar.LayoutParams.MATCH_PARENT,
                ActionBar.LayoutParams.WRAP_CONTENT,true);
        popupWindow.setAnimationStyle(R.style.popwin_anim_style);
        ColorDrawable dw = new ColorDrawable(context.getResources().getColor(R.color.bottom_menu));
        popupWindow.setBackgroundDrawable(dw);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mContext.getWindow().setBackgroundDrawable( new ColorDrawable(mContext.getResources().getColor(R.color.color_white)));
            }
        });
        mMenuView.setOnTouchListener(this);
    }
    /**
     * 显示菜单
     */
    public void show(){
        //得到当前activity的rootView
        View rootView=((ViewGroup)mContext.findViewById(android.R.id.content)).getChildAt(0);
        popupWindow.showAtLocation(rootView, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);
    }
    @Override
    public void onClick(View view) {
        popupWindow.dismiss();
        mContext.getWindow().setBackgroundDrawable( new ColorDrawable(mContext.getResources().getColor(R.color.color_white)));
        switch (view.getId()) {
            default:
                clickListener.onClick(view);
                break;
        }
    }
    @Override
    public boolean onTouch(View arg0, MotionEvent event) {
        int height = mMenuView.findViewById(R.id.pop_layout).getTop();
        int y=(int) event.getY();
        if(event.getAction()== MotionEvent.ACTION_UP){
            if(y<height){
                popupWindow. dismiss();
            }
        }
        return true;
    }
}