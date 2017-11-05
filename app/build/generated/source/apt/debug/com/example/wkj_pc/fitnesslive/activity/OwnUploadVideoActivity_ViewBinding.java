// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class OwnUploadVideoActivity_ViewBinding implements Unbinder {
  private OwnUploadVideoActivity target;

  private View view2131231081;

  private View view2131231085;

  private View view2131231079;

  @UiThread
  public OwnUploadVideoActivity_ViewBinding(OwnUploadVideoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public OwnUploadVideoActivity_ViewBinding(final OwnUploadVideoActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.user_upload_video_show_recycler_view, "field 'userUploadVideoShowRecyclerView' and method 'onViewClicked'");
    target.userUploadVideoShowRecyclerView = Utils.castView(view, R.id.user_upload_video_show_recycler_view, "field 'userUploadVideoShowRecyclerView'", RecyclerView.class);
    view2131231081 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_video_show_cancel_text_view, "field 'userVideoShowCancelTextView' and method 'onViewClicked'");
    target.userVideoShowCancelTextView = Utils.castView(view, R.id.user_video_show_cancel_text_view, "field 'userVideoShowCancelTextView'", TextView.class);
    view2131231085 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_upload_video_show_confirm_text_view, "field 'userUploadVideoShowConfirmTextView' and method 'onViewClicked'");
    target.userUploadVideoShowConfirmTextView = Utils.castView(view, R.id.user_upload_video_show_confirm_text_view, "field 'userUploadVideoShowConfirmTextView'", TextView.class);
    view2131231079 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.userUploadVideoShowBgLinearlayout = Utils.findRequiredViewAsType(source, R.id.user_upload_video_show_bg_linearlayout, "field 'userUploadVideoShowBgLinearlayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    OwnUploadVideoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userUploadVideoShowRecyclerView = null;
    target.userVideoShowCancelTextView = null;
    target.userUploadVideoShowConfirmTextView = null;
    target.userUploadVideoShowBgLinearlayout = null;

    view2131231081.setOnClickListener(null);
    view2131231081 = null;
    view2131231085.setOnClickListener(null);
    view2131231085 = null;
    view2131231079.setOnClickListener(null);
    view2131231079 = null;
  }
}
