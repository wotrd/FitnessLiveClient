// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UploadNativeVideoActivity_ViewBinding implements Unbinder {
  private UploadNativeVideoActivity target;

  private View view2131231069;

  private View view2131231070;

  @UiThread
  public UploadNativeVideoActivity_ViewBinding(UploadNativeVideoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UploadNativeVideoActivity_ViewBinding(final UploadNativeVideoActivity target,
      View source) {
    this.target = target;

    View view;
    target.uploadNativeVideoChooseRecyclerView = Utils.findRequiredViewAsType(source, R.id.upload_native_video_choose_recycler_view, "field 'uploadNativeVideoChooseRecyclerView'", RecyclerView.class);
    target.uploadNativeVideoShowImageView = Utils.findRequiredViewAsType(source, R.id.upload_native_video_show_image_view, "field 'uploadNativeVideoShowImageView'", ImageView.class);
    view = Utils.findRequiredView(source, R.id.user_upload_native_video_cancel_text_view, "method 'onViewClicked'");
    view2131231069 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.user_upload_native_video_choose_text_view, "method 'onViewClicked'");
    view2131231070 = view;
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
    UploadNativeVideoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.uploadNativeVideoChooseRecyclerView = null;
    target.uploadNativeVideoShowImageView = null;

    view2131231069.setOnClickListener(null);
    view2131231069 = null;
    view2131231070.setOnClickListener(null);
    view2131231070 = null;
  }
}
