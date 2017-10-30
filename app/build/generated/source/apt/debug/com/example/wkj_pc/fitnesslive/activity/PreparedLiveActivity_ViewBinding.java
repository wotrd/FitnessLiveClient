// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PreparedLiveActivity_ViewBinding implements Unbinder {
  private PreparedLiveActivity target;

  private View view2131230942;

  private View view2131230936;

  private View view2131230934;

  private View view2131230937;

  private View view2131230935;

  @UiThread
  public PreparedLiveActivity_ViewBinding(PreparedLiveActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PreparedLiveActivity_ViewBinding(final PreparedLiveActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.prepared_live_user_back_image_view, "field 'preparedLiveUserBackImageView' and method 'onViewClicked'");
    target.preparedLiveUserBackImageView = Utils.castView(view, R.id.prepared_live_user_back_image_view, "field 'preparedLiveUserBackImageView'", ImageView.class);
    view2131230942 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.prepared_live_set_to_live_text_view, "field 'preparedLiveSetToLiveTextView' and method 'onViewClicked'");
    target.preparedLiveSetToLiveTextView = Utils.castView(view, R.id.prepared_live_set_to_live_text_view, "field 'preparedLiveSetToLiveTextView'", TextView.class);
    view2131230936 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.prepared_live_big_img_set_img_view, "field 'preparedLiveBigImgSetImgView' and method 'onViewClicked'");
    target.preparedLiveBigImgSetImgView = Utils.castView(view, R.id.prepared_live_big_img_set_img_view, "field 'preparedLiveBigImgSetImgView'", ImageView.class);
    view2131230934 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.preparedLiveThemeEditText = Utils.findRequiredViewAsType(source, R.id.prepared_live_theme_edit_text, "field 'preparedLiveThemeEditText'", EditText.class);
    view = Utils.findRequiredView(source, R.id.prepared_live_theme_append_button, "field 'preparedLiveThemeAppendButton' and method 'onViewClicked'");
    target.preparedLiveThemeAppendButton = Utils.castView(view, R.id.prepared_live_theme_append_button, "field 'preparedLiveThemeAppendButton'", TextView.class);
    view2131230937 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.preparedLiveThemeEditShowRecyclerView = Utils.findRequiredViewAsType(source, R.id.prepared_live_theme_edit_show_recycler_view, "field 'preparedLiveThemeEditShowRecyclerView'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.prepared_live_big_img_set_text_view, "field 'preparedLiveBigImgSetTextView' and method 'onViewClicked'");
    target.preparedLiveBigImgSetTextView = Utils.castView(view, R.id.prepared_live_big_img_set_text_view, "field 'preparedLiveBigImgSetTextView'", TextView.class);
    view2131230935 = view;
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
    PreparedLiveActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.preparedLiveUserBackImageView = null;
    target.preparedLiveSetToLiveTextView = null;
    target.preparedLiveBigImgSetImgView = null;
    target.preparedLiveThemeEditText = null;
    target.preparedLiveThemeAppendButton = null;
    target.preparedLiveThemeEditShowRecyclerView = null;
    target.preparedLiveBigImgSetTextView = null;

    view2131230942.setOnClickListener(null);
    view2131230942 = null;
    view2131230936.setOnClickListener(null);
    view2131230936 = null;
    view2131230934.setOnClickListener(null);
    view2131230934 = null;
    view2131230937.setOnClickListener(null);
    view2131230937 = null;
    view2131230935.setOnClickListener(null);
    view2131230935 = null;
  }
}
