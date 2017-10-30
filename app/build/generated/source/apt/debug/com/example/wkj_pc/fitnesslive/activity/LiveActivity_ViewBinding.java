// Generated code from Butter Knife. Do not modify!
package com.example.wkj_pc.fitnesslive.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.wkj_pc.fitnesslive.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LiveActivity_ViewBinding implements Unbinder {
  private LiveActivity target;

  @UiThread
  public LiveActivity_ViewBinding(LiveActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LiveActivity_ViewBinding(LiveActivity target, View source) {
    this.target = target;

    target.liveChattingMessageRecyclerView = Utils.findRequiredViewAsType(source, R.id.live_chatting_message_recycler_view, "field 'liveChattingMessageRecyclerView'", RecyclerView.class);
    target.loginLiveLogo = Utils.findRequiredViewAsType(source, R.id.login_live_logo, "field 'loginLiveLogo'", ImageView.class);
    target.watchPeopleNumber = Utils.findRequiredViewAsType(source, R.id.watch_people_number, "field 'watchPeopleNumber'", TextView.class);
    target.attentionUserRcyclerView = Utils.findRequiredViewAsType(source, R.id.attention_user_show_recycler_view, "field 'attentionUserRcyclerView'", RecyclerView.class);
    target.changeBeautySpinner = Utils.findRequiredViewAsType(source, R.id.change_beauty_spinner, "field 'changeBeautySpinner'", Spinner.class);
    target.mPublishBtn = Utils.findRequiredViewAsType(source, R.id.start_live_btn, "field 'mPublishBtn'", Button.class);
    target.editText = Utils.findRequiredViewAsType(source, R.id.editText, "field 'editText'", EditText.class);
    target.mCameraSwitchBtn = Utils.findRequiredViewAsType(source, R.id.swCam, "field 'mCameraSwitchBtn'", ImageView.class);
    target.closeLiveIconBtn = Utils.findRequiredViewAsType(source, R.id.close_live_icon, "field 'closeLiveIconBtn'", ImageView.class);
    target.bottomLiveShowlinearLayout = Utils.findRequiredViewAsType(source, R.id.begin_live_show_linearlayout, "field 'bottomLiveShowlinearLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LiveActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.liveChattingMessageRecyclerView = null;
    target.loginLiveLogo = null;
    target.watchPeopleNumber = null;
    target.attentionUserRcyclerView = null;
    target.changeBeautySpinner = null;
    target.mPublishBtn = null;
    target.editText = null;
    target.mCameraSwitchBtn = null;
    target.closeLiveIconBtn = null;
    target.bottomLiveShowlinearLayout = null;
  }
}
