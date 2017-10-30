package com.example.wkj_pc.fitnesslive.fragment;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.tools.ToastUtils;
import de.hdodenhof.circleimageview.CircleImageView;

public class LiveUserBottomInfoToastFragment extends BottomSheetDialogFragment implements View.OnClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_live_user_bottom_info_toast, container, false);
        CircleImageView userLogo = (CircleImageView)
                view.findViewById(R.id.user_bottom_fragment_attention_info_alert_logo);
        Button attentionBtn = (Button) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_attention_btn);
        attentionBtn.setOnClickListener(this);
        TextView attentionNum = (TextView) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_attentionnum);
        TextView fansnum = (TextView) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_fansnum);
        TextView nickname = (TextView) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_nickname);
        TextView personalSign = (TextView) view.findViewById(R.id.user_bottom_fragment_attention_info_alert_personalsign);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_bottom_fragment_attention_info_alert_attention_btn:
                ToastUtils.showToast(getActivity(),"niaho", Toast.LENGTH_SHORT);
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
