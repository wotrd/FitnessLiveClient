// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class UploadVideoActivity_ViewBinding implements Unbinder {
  private UploadVideoActivity target;

  private View view2131231076;

  private View view2131231031;

  private View view2131231033;

  @UiThread
  public UploadVideoActivity_ViewBinding(UploadVideoActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public UploadVideoActivity_ViewBinding(final UploadVideoActivity target, View source) {
    this.target = target;

    View view;
    target.userUploadVideoTitleEditText = Utils.findRequiredViewAsType(source, R.id.user_upload_video_title_edit_text, "field 'userUploadVideoTitleEditText'", EditText.class);
    view = Utils.findRequiredView(source, R.id.user_upload_video_thumbnails_img_view, "field 'userUploadVideoThumbnailsImgView' and method 'onViewClicked'");
    target.userUploadVideoThumbnailsImgView = Utils.castView(view, R.id.user_upload_video_thumbnails_img_view, "field 'userUploadVideoThumbnailsImgView'", ImageView.class);
    view2131231076 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tools_user_info_edit_cancel_text_view, "method 'onViewClicked'");
    view2131231031 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.tools_user_info_edit_confirm_text_view, "method 'onViewClicked'");
    view2131231033 = view;
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
    UploadVideoActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.userUploadVideoTitleEditText = null;
    target.userUploadVideoThumbnailsImgView = null;

    view2131231076.setOnClickListener(null);
    view2131231076 = null;
    view2131231031.setOnClickListener(null);
    view2131231031 = null;
    view2131231033.setOnClickListener(null);
    view2131231033 = null;
  }
}
