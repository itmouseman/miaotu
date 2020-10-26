package com.widget.miaotu.master.home.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SPStaticUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.magicsoft.geekfj.windingfjpay.IPayBean;
import com.magicsoft.geekfj.windingfjpay.PayFactory;
import com.magicsoft.geekfj.windingfjpay.WxPayBean;
import com.orhanobut.logger.Logger;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.common.constant.SPConstant;
import com.widget.miaotu.common.utils.dialog.PayDialog;
import com.widget.miaotu.common.utils.rxbus.RxBus;
import com.widget.miaotu.common.utils.rxbus.WxPayResult;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.bean.PostV5UserVipInfoBean;
import com.widget.miaotu.http.bean.PostWxPayBean;
import com.widget.miaotu.http.bean.head.HeadPostPayWxBean;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.widget.miaotu.master.mvp.AddVipControl;
import com.widget.miaotu.master.mvp.AddVipView;
import com.widget.miaotu.wxapi.WXPayEntryActivity;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 添加vip
 */
public class AddVIPNewActivity extends MBaseActivity<AddVipControl> implements AddVipView {
    @BindView(R.id.iv_buy_vip_back)
    ImageView ivBuyVipBack;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.ll_phone)
    LinearLayout llPhone;
    @BindView(R.id.tv_vip)
    TextView tvVip;
    @BindView(R.id.tv_buy_vip_title)
    TextView tv_buy_vip_title;
    private PostV5UserVipInfoBean postV5UserVipInfoBean;


    @Override
    protected void initDetailData() {


        RetrofitFactory.getInstence().API().postV5UserVipInfo().compose(TransformerUi.setThread()).subscribe(new BaseObserver<PostV5UserVipInfoBean>(this) {
            @Override
            protected void onSuccess(BaseEntity<PostV5UserVipInfoBean> t) throws Exception {
                if (t.getStatus() == 100) {
                    postV5UserVipInfoBean = t.getData();
                    tv_buy_vip_title.setText(postV5UserVipInfoBean.getProductInfo().get(0).getTitle());
                    tvVip.setText("VIP会员￥" + postV5UserVipInfoBean.getProductInfo().get(0).getPrice() + "/年 立即开通");
                    Logger.e(postV5UserVipInfoBean.toString());
                } else {
                    ToastUtils.showShort(t.getMessage());
                }

            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                ToastUtils.showShort("网络请求出错");
            }
        });


        llPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //电话联系
                if (ActivityCompat.checkSelfPermission(AddVIPNewActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddVIPNewActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                PhoneUtils.call(postV5UserVipInfoBean.getProductInfo().get(0).getContactMobile());
            }
        });
        tvVip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PayDialog(AddVIPNewActivity.this, R.style.dialog_center)
                        .setPrice("" + postV5UserVipInfoBean.getProductInfo().get(0).getPrice() + "元/年")
                        .setOnCheckPayListener(new PayDialog.OnCheckPayListener() {
                            @Override
                            public void onCheckPay(int type) {
                                //微信支付 type 2  支付宝支付 type1
                                Logger.e("点击  " + type);
                                //去请求支付接口

                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mControl.postPayWx(new HeadPostPayWxBean(postV5UserVipInfoBean.getProductInfo().get(0).getId() + "", "", "1"));

                                    }
                                }).start();

                            }
                        });

            }
        });


        RxBus.getInstance().toObservableSticky(this, WxPayResult.class).subscribe(new Consumer<WxPayResult>() {
            @Override
            public void accept(WxPayResult wxPayResult) throws Exception {
                Logger.e("微信支付结果" + wxPayResult.getStateCode());
                if (wxPayResult.getStateCode() == 0) {//支付成功
                    SPStaticUtils.put(SPConstant.IS_VIP, 2 + "");
                    finish();
                } else if (wxPayResult.getStateCode() == 1) {//取消支付

                } else if (wxPayResult.getStateCode() == 2) {//支付失败

                }
            }
        });
    }

    @Override
    protected AddVipControl createControl() {
        return new AddVipControl();
    }

    @Override
    protected void initView() {
        initHardwareAccelerate();
        String url = getIntent().getStringExtra("url");

        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.clearCache(true);
        webview.loadUrl(url);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setVerticalScrollBarEnabled(false);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //重写shouldOverrideUrlLoading()方法，使得打开网页时不调用系统浏览器， 而是在本WebView中显示
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }
        });


    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_vip;
    }

    @OnClick(R.id.iv_buy_vip_back)
    public void onClick() {
        finish();
    }


    @Override
    public void VipOrderVxSuccess(PostWxPayBean data) {
        Logger.e(data.toString());
        WxPayBean wxPayBean = new WxPayBean();
        wxPayBean.setAppid(data.getAppid());
        wxPayBean.setNoncestr(data.getNoncestr());
//        wxPayBean.setNotifyUrl("");
//        wxPayBean.setOutTradeNo("");
        wxPayBean.setPackageX(data.getPackageX());
        wxPayBean.setPartnerid(data.getPartnerid());
        wxPayBean.setPrepayid(data.getPrepayid());
        wxPayBean.setSign(data.getSign());
        wxPayBean.setTimestamp(Integer.parseInt(data.getTimestamp()));
//        wxPayBean.setTotalAmount("");


        Gson gson = new Gson();
        String m = gson.toJson(wxPayBean);
        Logger.e(m);
//

        IWXAPI api = WXAPIFactory.createWXAPI(AddVIPNewActivity.this, (String) null);
        api.registerApp("wxeab3fe32632897e3");
        PayReq req = new PayReq();
        req.appId = data.getAppid();
        req.partnerId = data.getPartnerid();
        req.prepayId = data.getPrepayid();
        req.packageValue = "Sign=WXPay";
        req.nonceStr = data.getNoncestr();
        req.timeStamp = String.valueOf(data.getTimestamp());
        req.sign = data.getSign();
        api.sendReq(req);

//        PayFactory.createPay(PayFactory.WXPAY, this, m).setOnResultListener(new IPayBean.OnResultListener() {
//            @Override
//            public void onPaySuccess() {
//                Logger.e("支付成功");
//            }
//
//            @Override
//            public void onPayFail() {
//                Logger.e("支付失败");
//            }
//        });

    }


    @Override
    public void VipOrderVxFail(String message) {

    }
}
