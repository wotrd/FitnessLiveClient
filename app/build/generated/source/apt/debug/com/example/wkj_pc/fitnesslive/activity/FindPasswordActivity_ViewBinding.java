// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class FindPasswordActivity_ViewBinding implements Unbinder {
  private FindPasswordActivity target;

  private View view2131230893;

  private View view2131230843;

  private View view2131230832;

  @UiThread
  public FindPasswordActivity_ViewBinding(FindPasswordActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public FindPasswordActivity_ViewBinding(final FindPasswordActivity target, View source) {
    this.target = target;

    View view;
    target.fingPwdPhonenumberEditText = Utils.findRequiredViewAsType(source, R.id.fing_pwd_phonenumber_edit_text, "field 'fingPwdPhonenumberEditText'", EditText.class);
    target.findPwdMobileNumberLinearlayout = Utils.findRequiredViewAsType(source, R.id.find_pwd_mobile_number_linearlayout, "field 'findPwdMobileNumberLinearlayout'", LinearLayout.class);
    target.findPwdVerifycodeEditText = Utils.findRequiredViewAsType(source, R.id.find_pwd_verifycode_edit_text, "field 'findPwdVerifycodeEditText'", EditText.class);
    target.findPwdVerifycodeLinearlayout = Utils.findRequiredViewAsType(source, R.id.find_pwd_verifycode_linearlayout, "field 'findPwdVerifycodeLinearlayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.login_user_find_pwd_back_text_view, "field 'loginUserFindPwdBackTextView' and method 'onViewClicked'");
    target.loginUserFindPwdBackTextView = Utils.castView(view, R.id.login_user_find_pwd_back_text_view, "field 'loginUserFindPwdBackTextView'", ImageView.class);
    view2131230893 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.getVerifycodeTextView, "field 'getVerifycodeTextView' and method 'onViewClicked'");
    target.getVerifycodeTextView = Utils.castView(view, R.id.getVerifycodeTextView, "field 'getVerifycodeTextView'", TextView.class);
    view2131230843 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.find_pwd_nextstep_btn, "field 'findPwdNextstepBtn' and method 'onViewClicked'");
    target.findPwdNextstepBtn = Utils.castView(view, R.id.find_pwd_nextstep_btn, "field 'findPwdNextstepBtn'", Button.class);
    view2131230832 = view;
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
    FindPasswordActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.fingPwdPhonenumberEditText = null;
    target.findPwdMobileNumberLinearlayout = null;
    target.findPwdVerifycodeEditText = null;
    target.findPwdVerifycodeLinearlayout = null;
    target.loginUserFindPwdBackTextView = null;
    target.getVerifycodeTextView = null;
    target.findPwdNextstepBtn = null;

    view2131230893.setOnClickListener(null);
    view2131230893 = null;
    view2131230843.setOnClickListener(null);
    view2131230843 = null;
    view2131230832.setOnClickListener(null);
    view2131230832 = null;
  }
}
