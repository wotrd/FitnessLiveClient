// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UserInfoEditActivity_ViewBinding implements Unbinder {
  private UserInfoEditActivity target;

  private View view2131230731;

  private View view2131230723;

  private View view2131230724;

  private View view2131230728;

  private View view2131230726;

  @UiThread
  public UserInfoEditActivity_ViewBinding(UserInfoEditActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UserInfoEditActivity_ViewBinding(final UserInfoEditActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.about_user_info_edit_back_text_view, "method 'onViewClicked'");
    view2131230731 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.about_user_edit_amatar_linearlayout, "method 'onViewClicked'");
    view2131230723 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.about_user_edit_nickname_linearlayout, "method 'onViewClicked'");
    view2131230724 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.about_user_edit_sex_linearlayout, "method 'onViewClicked'");
    view2131230728 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.about_user_edit_person_sign_linearlayout, "method 'onViewClicked'");
    view2131230726 = view;
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
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    target = null;


    view2131230731.setOnClickListener(null);
    view2131230731 = null;
    view2131230723.setOnClickListener(null);
    view2131230723 = null;
    view2131230724.setOnClickListener(null);
    view2131230724 = null;
    view2131230728.setOnClickListener(null);
    view2131230728 = null;
    view2131230726.setOnClickListener(null);
    view2131230726 = null;
  }
}
