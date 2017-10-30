// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.loginToolbar = Utils.findRequiredViewAsType(source, R.id.login_toolbar, "field 'loginToolbar'", Toolbar.class);
    target.mobileNum = Utils.findRequiredViewAsType(source, R.id.user_login_mobile_num_edit_text, "field 'mobileNum'", EditText.class);
    target.editPassword = Utils.findRequiredViewAsType(source, R.id.edit_password, "field 'editPassword'", EditText.class);
    target.openSlideSelectLoginImg = Utils.findRequiredViewAsType(source, R.id.open_slide_select_login_img, "field 'openSlideSelectLoginImg'", ImageView.class);
    target.imageView = Utils.findRequiredViewAsType(source, R.id.imageView, "field 'imageView'", ImageView.class);
    target.closeSelectSlideBtn = Utils.findRequiredViewAsType(source, R.id.close_select_slide_btn, "field 'closeSelectSlideBtn'", LinearLayout.class);
    target.qqLoginView = Utils.findRequiredViewAsType(source, R.id.qq_login_view, "field 'qqLoginView'", ImageView.class);
    target.wechatLoginView = Utils.findRequiredViewAsType(source, R.id.wechat_login_view, "field 'wechatLoginView'", ImageView.class);
    target.weiboLoginView = Utils.findRequiredViewAsType(source, R.id.weibo_login_view, "field 'weiboLoginView'", ImageView.class);
    target.showSlideOutImg = Utils.findRequiredViewAsType(source, R.id.show_slide_out_img, "field 'showSlideOutImg'", LinearLayout.class);
    target.accountLinearlayout = Utils.findRequiredViewAsType(source, R.id.account_linearlayout, "field 'accountLinearlayout'", LinearLayout.class);
    target.passwordLinearlayout = Utils.findRequiredViewAsType(source, R.id.password_linearlayout, "field 'passwordLinearlayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginToolbar = null;
    target.mobileNum = null;
    target.editPassword = null;
    target.openSlideSelectLoginImg = null;
    target.imageView = null;
    target.closeSelectSlideBtn = null;
    target.qqLoginView = null;
    target.wechatLoginView = null;
    target.weiboLoginView = null;
    target.showSlideOutImg = null;
    target.accountLinearlayout = null;
    target.passwordLinearlayout = null;
  }
}
