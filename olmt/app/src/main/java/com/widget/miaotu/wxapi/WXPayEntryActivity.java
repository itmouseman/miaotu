package com.widget.miaotu.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.magicsoft.geekfj.windingfjpay.IPayBean;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.common.utils.rxbus.WxPayResult;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    public  OnResultListener listener;

    public void setOnResultListener(OnResultListener listener) {
        this.listener = listener;
    }

    public interface OnResultListener {

        void onPaySuccess();

        void onPayFail();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wxeab3fe32632897e3");//填写微信的APPKey
//        api = WXAPIFactory.createWXAPI(this, "wx7efd0146f0a682ea");//填写微信的APPKey老的
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            Logger.e("===========" + resp.errCode);
            if (resp.errCode == 0) {
                RxBus.getInstance().post(new WxPayResult(0));
                Toast.makeText(this, "支付成功!", Toast.LENGTH_SHORT).show();
            } else if (resp.errCode == -2) {
                RxBus.getInstance().post(new WxPayResult(1));
                Toast.makeText(this, "支付取消！", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "支付失败！", Toast.LENGTH_SHORT).show();
                RxBus.getInstance().post(new WxPayResult(2));
            }
            finish();
        }
    }
}
