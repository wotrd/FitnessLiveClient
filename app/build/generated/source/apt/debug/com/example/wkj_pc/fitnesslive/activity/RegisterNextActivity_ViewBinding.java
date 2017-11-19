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

public class RegisterNextActivity_ViewBinding implements Unbinder {
  private RegisterNextActivity target;

  private View view2131230962;

  private View view2131230967;

  @UiThread
  public RegisterNextActivity_ViewBinding(RegisterNextActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterNextActivity_ViewBinding(final RegisterNextActivity target, View source) {
    this.target = target;

    View view;
    target.registerUserNextEditText = Utils.findRequiredViewAsType(source, R.id.register_user_next_edit_text, "field 'registerUserNextEditText'", EditText.class);
    target.registerUserNextLinearlayout = Utils.findRequiredViewAsType(source, R.id.register_user_next_linearlayout, "field 'registerUserNextLinearlayout'", LinearLayout.class);
    target.registerUserNextVerifyPwdEditText = Utils.findRequiredViewAsType(source, R.id.register_user_next_verify_pwd_edit_text, "field 'registerUserNextVerifyPwdEditText'", EditText.class);
    target.registerUserNextVerifyPwdLinearlayout = Utils.findRequiredViewAsType(source, R.id.register_user_next_verify_pwd_linearlayout, "field 'registerUserNextVerifyPwdLinearlayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.register_user_next_back_text_view, "method 'onViewClicked'");
    view2131230962 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.register_user_nextstep_btn, "method 'onViewClicked'");
    view2131230967 = view;
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
    RegisterNextActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.registerUserNextEditText = null;
    target.registerUserNextLinearlayout = null;
    target.registerUserNextVerifyPwdEditText = null;
    target.registerUserNextVerifyPwdLinearlayout = null;

    view2131230962.setOnClickListener(null);
    view2131230962 = null;
    view2131230967.setOnClickListener(null);
    view2131230967 = null;
  }
}
