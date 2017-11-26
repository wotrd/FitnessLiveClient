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
import io.vov.vitamio.widget.VideoView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class WatchUserLiveActivity_ViewBinding implements Unbinder {
  private WatchUserLiveActivity target;

  private View view2131231108;

  private View view2131231106;

  private View view2131231105;

  @UiThread
  public WatchUserLiveActivity_ViewBinding(WatchUserLiveActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public WatchUserLiveActivity_ViewBinding(final WatchUserLiveActivity target, View source) {
    this.target = target;

    View view;
    target.watchVideoView = Utils.findRequiredViewAsType(source, R.id.watch_video_view, "field 'watchVideoView'", VideoView.class);
    view = Utils.findRequiredView(source, R.id.watcher_login_watch_live_logo, "field 'loginWatchLiveLogo' and method 'onViewClicked'");
    target.loginWatchLiveLogo = Utils.castView(view, R.id.watcher_login_watch_live_logo, "field 'loginWatchLiveLogo'", ImageView.class);
    view2131231108 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    target.watcherWatchPeopleNumber = Utils.findRequiredViewAsType(source, R.id.watcher_watch_people_number, "field 'watcherWatchPeopleNumber'", TextView.class);
    target.watcherWatchFansPeopleNumber = Utils.findRequiredViewAsType(source, R.id.watcher_watch_fans_people_number, "field 'watcherWatchFansPeopleNumber'", TextView.class);
    target.watcherAttentionUserWatchShowRecyclerView = Utils.findRequiredViewAsType(source, R.id.watcher_attention_user_watch_show_recycler_view, "field 'watcherAttentionUserWatchShowRecyclerView'", RecyclerView.class);
    target.watcherWatchVideoChattingEditText = Utils.findRequiredViewAsType(source, R.id.watcher_watch_video_chatting_edit_text, "field 'watcherWatchVideoChattingEditText'", EditText.class);
    target.watcherLiveChattingMessageRecyclerView = Utils.findRequiredViewAsType(source, R.id.watcher_live_chatting_message_recycler_view, "field 'watcherLiveChattingMessageRecyclerView'", RecyclerView.class);
    target.watcherWhileLiveCloseShowTextView = Utils.findRequiredViewAsType(source, R.id.watcher_while_live_close_show_text_view, "field 'watcherWhileLiveCloseShowTextView'", TextView.class);
    view = Utils.findRequiredView(source, R.id.watcher_ic_send_watch_comment_message_icon, "field 'watcherIcSendWatchCommentMessageIcon' and method 'onViewClicked'");
    target.watcherIcSendWatchCommentMessageIcon = Utils.castView(view, R.id.watcher_ic_send_watch_comment_message_icon, "field 'watcherIcSendWatchCommentMessageIcon'", ImageView.class);
    view2131231106 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.watcher_close_watch_live_icon, "field 'watcherCloseWatchLiveIcon' and method 'onViewClicked'");
    target.watcherCloseWatchLiveIcon = Utils.castView(view, R.id.watcher_close_watch_live_icon, "field 'watcherCloseWatchLiveIcon'", ImageView.class);
    view2131231105 = view;
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
    WatchUserLiveActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.watchVideoView = null;
    target.loginWatchLiveLogo = null;
    target.watcherWatchPeopleNumber = null;
    target.watcherWatchFansPeopleNumber = null;
    target.watcherAttentionUserWatchShowRecyclerView = null;
    target.watcherWatchVideoChattingEditText = null;
    target.watcherLiveChattingMessageRecyclerView = null;
    target.watcherWhileLiveCloseShowTextView = null;
    target.watcherIcSendWatchCommentMessageIcon = null;
    target.watcherCloseWatchLiveIcon = null;

    view2131231108.setOnClickListener(null);
    view2131231108 = null;
    view2131231106.setOnClickListener(null);
    view2131231106 = null;
    view2131231105.setOnClickListener(null);
    view2131231105 = null;
  }
}
