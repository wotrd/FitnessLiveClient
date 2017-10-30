package com.example.wkj_pc.fitnesslive.wxapi;

import android.app.Activity;
import android.os.Bundle;

import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI wxapi;
    private String APPID="wxc2ddb5d13625b5f5";
    @Override
    public void onReq(BaseReq baseReq) {
        //System.out.println("---------"+baseReq.toString());
    }

    @Override
    public void onResp(BaseResp baseResp) {
        //System.out.println("---------"+baseResp.errCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wxapi = WXAPIFactory.createWXAPI(this, APPID, false);
        wxapi.handleIntent(getIntent(), this);
    }
}
