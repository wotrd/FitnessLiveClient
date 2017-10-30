package com.example.wkj_pc.fitnesslive.activity;

import android.Manifest;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import com.example.wkj_pc.fitnesslive.R;
import com.example.wkj_pc.fitnesslive.fragment.MainPageFragment;
import com.example.wkj_pc.fitnesslive.service.LiveService;
import com.werb.permissionschecker.PermissionChecker;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.home_main_content_fragment)
    LinearLayout homeMainContentFragment;
    private MainPageFragment mainPgaeFragment;
    private FragmentManager fragmentManager;
    public static android.support.v4.app.FragmentManager manager;
    static final String[] PERMISSIONS = new String[]{
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    private PermissionChecker permissionChecker;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        permissionChecker = new PermissionChecker(this); // initialize，must need
        type = 2;
        startService(new Intent(this, LiveService.class));
        manager=getSupportFragmentManager();
        fragmentManager = getFragmentManager();
        FragmentTransaction tran = fragmentManager.beginTransaction();
        mainPgaeFragment=new MainPageFragment();
        tran.add(R.id.home_main_content_fragment,mainPgaeFragment);
        tran.commit();
        if (permissionChecker.isLackPermissions(PERMISSIONS)) {
            permissionChecker.requestPermissions();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionChecker.PERMISSION_REQUEST_CODE:
                if (permissionChecker.hasAllPermissionsGranted(grantResults)) {
                } else {
                    permissionChecker.showDialog();
                }
                break;
        }
    }
}
