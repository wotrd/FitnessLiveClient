// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FindPwdNextActivity_ViewBinding implements Unbinder {
  private FindPwdNextActivity target;

  private View view2131230889;

  private View view2131230757;

  @UiThread
  public FindPwdNextActivity_ViewBinding(FindPwdNextActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FindPwdNextActivity_ViewBinding(final FindPwdNextActivity target, View source) {
    this.target = target;

    View view;
    target.findPwdNextEditText = Utils.findRequiredViewAsType(source, R.id.find_pwd_next_edit_text, "field 'findPwdNextEditText'", EditText.class);
    target.findPwdNextLinearlayout = Utils.findRequiredViewAsType(source, R.id.find_pwd_next_linearlayout, "field 'findPwdNextLinearlayout'", LinearLayout.class);
    target.findPwdNextVerifyPwdEditText = Utils.findRequiredViewAsType(source, R.id.find_pwd_next_verify_pwd_edit_text, "field 'findPwdNextVerifyPwdEditText'", EditText.class);
    target.findPwdNextVerifyPwdLinearlayout = Utils.findRequiredViewAsType(source, R.id.find_pwd_next_verify_pwd_linearlayout, "field 'findPwdNextVerifyPwdLinearlayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.login_user_find_pwd_back_text_view, "method 'onViewClicked'");
    view2131230889 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.alter_pwd_nextstep_btn, "method 'onViewClicked'");
    view2131230757 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    FindPwdNextActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.findPwdNextEditText = null;
    target.findPwdNextLinearlayout = null;
    target.findPwdNextVerifyPwdEditText = null;
    target.findPwdNextVerifyPwdLinearlayout = null;

    view2131230889.setOnClickListener(null);
    view2131230889 = null;
    view2131230757.setOnClickListener(null);
    view2131230757 = null;
  }
}
