package com.widget.miaotu.master.mine.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.orhanobut.logger.Logger;
import com.qmuiteam.qmui.util.QMUIDrawableHelper;
import com.widget.miaotu.R;
import com.widget.miaotu.base.MBaseActivity;
import com.widget.miaotu.base.MVCControl;
import com.widget.miaotu.common.utils.GlideUtils;
import com.widget.miaotu.common.utils.other.CommonUtil;
import com.widget.miaotu.common.utils.other.ShareLinkInfo;
import com.widget.miaotu.common.utils.other.ShareUtils;
import com.widget.miaotu.http.BaseObserver;
import com.widget.miaotu.http.bean.BaseEntity;
import com.widget.miaotu.http.retrofit.RetrofitFactory;
import com.widget.miaotu.http.retrofit.TransformerUi;
import com.yanzhenjie.permission.AndPermission;

import butterknife.BindView;
import butterknife.OnClick;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * 邀请好友界面
 */
public class InvitFirendActivity extends MBaseActivity {

    @BindView(R.id.iv_invite_share_ewm)
    ImageView iv_erWeiMa;
    @BindView(R.id.ll_invite_share_bctp)
    LinearLayout llBaoCunTUPian;
    @BindView(R.id.ll_invite_share_wx)
    LinearLayout llShareWx;
    @BindView(R.id.ll_invite_share_pyq)
    LinearLayout llSharePyq;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_back)
    ImageButton btn_back;
    @BindView(R.id.fl_saveShareImage)
    FrameLayout fl_saveShareImage;

    @Override
    protected boolean isUseFullScreenMode() {
        return  true;
    }

    @Override
    protected MVCControl createControl() {
        return null;
    }

    @Override
    protected void initView() {
        tvTitle.setText("邀请好友");
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        showWaiteDialog("正在加载中...");
        RetrofitFactory.getInstence().API().getShareCode().compose(TransformerUi.setThread()).subscribe(new BaseObserver<Object>(this) {
            @Override
            protected void onSuccess(BaseEntity<Object> t) throws Exception {
                hideWaiteDialog();
                GlideUtils.loadUrl(InvitFirendActivity.this, t.getData().toString(), iv_erWeiMa);
            }

            @Override
            protected void onFail(Throwable throwable) throws Exception {
                hideWaiteDialog();
                ToastUtils.showShort("请求异常");
            }
        });

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_invit_firend;
    }

    @Override
    protected void initDetailData() {

    }

    @OnClick({R.id.ll_invite_share_wx, R.id.ll_invite_share_pyq, R.id.ll_invite_share_bctp})
    public void onClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_invite_share_wx://分享到微信
                shareOther(1);
                break;
            case R.id.ll_invite_share_pyq://分享到朋友圈
                shareOther(2);
                break;
            case R.id.ll_invite_share_bctp://保存图片
                saveImage();
                break;
        }

    }


    private void shareOther(int type) {
        Bitmap createFromViewBitmap = QMUIDrawableHelper.createBitmapFromView(fl_saveShareImage);
        ShareLinkInfo shareLinkInfo = new ShareLinkInfo("苗途好友"
                , " ", "好友邀请", "", "");
        switch (type) {

            case 1://微信分享

                ShareUtils.shareBitmap(  createFromViewBitmap,Wechat.NAME, shareLinkInfo);
                break;
            case 2:
                ShareUtils.shareBitmap( createFromViewBitmap,WechatMoments.NAME, shareLinkInfo);
                break;
        }

    }


    @SuppressLint("WrongConstant")
    private void saveImage() {

        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .onGranted(permissions -> {

                    Bitmap createFromViewBitmap = QMUIDrawableHelper.createBitmapFromView(fl_saveShareImage);

//                    CommonUtil.saveImageToGallery( InvitFirendActivity.this,createFromViewBitmap);

                    if ( CommonUtil.saveBitmap(InvitFirendActivity.this,createFromViewBitmap )){
                        ToastUtils.showShort("保存成功");
                    }else {
                        ToastUtils.showShort("保存失败");
                    }
                })
                .onDenied(permissions -> {
                    ToastUtils.showShort("需要存储权限");
                })
                .start();


    }



}



