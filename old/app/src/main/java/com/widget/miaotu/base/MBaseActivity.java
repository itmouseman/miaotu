package com.widget.miaotu.base;

import com.kaopiz.kprogresshud.KProgressHUD;

public abstract class MBaseActivity<C extends MVCControl>  extends BaseActivity {

    protected C mControl;
    private KProgressHUD mWaiteDialog;
    protected void showWaiteDialog(String msg) {
        if (mWaiteDialog == null) {
            mWaiteDialog = KProgressHUD.create(this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setDimAmount(0.5f).setCancellable(false);
        }
        mWaiteDialog.setLabel(msg).show();
    }

    protected void hideWaiteDialog() {
        if (mWaiteDialog != null) {
            mWaiteDialog.dismiss();
        }
    }


    protected abstract C createControl();

    protected abstract void initView();

    @Override
    protected void initData() {
        mControl = createControl();
        if (mControl != null) {
            mControl.attachView(this);
        }
        initDetailData();
    }


    protected abstract void initDetailData();

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mControl != null) {
            mControl.detatchView();
        }
    }

}
