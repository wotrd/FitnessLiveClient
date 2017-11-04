// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RegisterActivity_ViewBinding implements Unbinder {
  private RegisterActivity target;

  private View view2131230956;

  private View view2131230954;

  private View view2131230963;

  private View view2131230953;

  private View view2131230964;

  private View view2131230955;

  private View view2131230962;

  @UiThread
  public RegisterActivity_ViewBinding(RegisterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public RegisterActivity_ViewBinding(final RegisterActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.register_user_mobile_edit_text, "field 'registerUserMobileEditText' and method 'onViewClicked'");
    target.registerUserMobileEditText = Utils.castView(view, R.id.register_user_mobile_edit_text, "field 'registerUserMobileEditText'", EditText.class);
    view2131230956 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.register_mobile_number_linearlayout, "field 'registerMobileNumberLinearlayout' and method 'onViewClicked'");
    target.registerMobileNumberLinearlayout = Utils.castView(view, R.id.register_mobile_number_linearlayout, "field 'registerMobileNumberLinearlayout'", LinearLayout.class);
    view2131230954 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.register_user_verifycode_edit_text, "field 'registerUserVerifycodeEditText' and method 'onViewClicked'");
    target.registerUserVerifycodeEditText = Utils.castView(view, R.id.register_user_verifycode_edit_text, "field 'registerUserVerifycodeEditText'", EditText.class);
    view2131230963 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.registerGetVerifycodeTextView, "field 'registerGetVerifycodeTextView' and method 'onViewClicked'");
    target.registerGetVerifycodeTextView = Utils.castView(view, R.id.registerGetVerifycodeTextView, "field 'registerGetVerifycodeTextView'", TextView.class);
    view2131230953 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.register_user_verifycode_linearlayout, "field 'registerUserVerifycodeLinearlayout' and method 'onViewClicked'");
    target.registerUserVerifycodeLinearlayout = Utils.castView(view, R.id.register_user_verifycode_linearlayout, "field 'registerUserVerifycodeLinearlayout'", LinearLayout.class);
    view2131230964 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.register_user_find_pwd_back_text_view, "method 'onViewClicked'");
    view2131230955 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.register_user_nextstep_btn, "method 'onViewClicked'");
    view2131230962 = view;
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
    RegisterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.registerUserMobileEditText = null;
    target.registerMobileNumberLinearlayout = null;
    target.registerUserVerifycodeEditText = null;
    target.registerGetVerifycodeTextView = null;
    target.registerUserVerifycodeLinearlayout = null;

    view2131230956.setOnClickListener(null);
    view2131230956 = null;
    view2131230954.setOnClickListener(null);
    view2131230954 = null;
    view2131230963.setOnClickListener(null);
    view2131230963 = null;
    view2131230953.setOnClickListener(null);
    view2131230953 = null;
    view2131230964.setOnClickListener(null);
    view2131230964 = null;
    view2131230955.setOnClickListener(null);
    view2131230955 = null;
    view2131230962.setOnClickListener(null);
    view2131230962 = null;
  }
}
